package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import net.objects.GameMode;
import net.objects.Room;
import tools.ExtendedByteBuffer;

public class LeaveGameHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x31;
	public boolean isDisconnected;
	
	public LeaveGameHandler(LobbyServer lobbyServer, UserSession userSession) {
		super(lobbyServer, userSession);
	}
	
	public LeaveGameHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		
	}

	@Override
	public byte[] getResponse() {
		return getResponse(userSession);
	}
	
	public byte[] getResponse(UserSession userSession) {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.LEAVE_GAME_RESPONSE);
		output.putInt(0x14, userSession.getUser().getRoomSlot());
		output.putString(0x18, userSession.getUser().getUsername());
		output.putInt(0x28, 0); // this value is added to MyInfo+3198
		output.putInt(0x2C, 0); // this value is added to MyInfo+319C
		output.putByte(0x30, (byte) 0); // master card
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		Room room = lobbyServer.getRoom(userSession.getUser().getRoomIndex());
		
		lobbyServer.sendRoomMessage(userSession, getResponse(), false);
		sendTCPMessage(new LobbyRoomsChangedHandler(lobbyServer, userSession).getResponse(room));
		lobbyServer.sendBroadcastMessage(userSession, new LobbyRoomsChangedHandler(lobbyServer, userSession).getResponse(room));
	
		if (room.getNumberOfPlayers() == 0) {
			lobbyServer.setRoom(room.getRoomID(), null);
		}
		else {
			if (room.masterIndex == userSession.getUser().getRoomSlot()) {
				room.masterIndex = room.getAnyPlayerSlot();
				lobbyServer.sendRoomMessage(userSession, new NewMasterHandler(lobbyServer, userSession).getResponse(room.masterIndex), true);
			}
			if (room.blueKingIndex == userSession.getUser().getRoomSlot()) {
				room.blueKingIndex = room.getAnyPlayerSlot();
				lobbyServer.sendRoomMessage(userSession, new NewKingHandler(lobbyServer, userSession).getResponse(), true);
			}
			
			if (room.getGameMode() == GameMode.HERO && userSession.getUser().getRoomSlot() == room.heroSlot) {
				int[] results = new int[8];
				for (int i = 0; i < 8; i++) {
					results[i] = -1;
					
					if (room.getUserSession(i) != null) {
						results[i] = 1;
					}
				}
				
				lobbyServer.sendRoomMessage(userSession, new GameCompletedHandler(lobbyServer, userSession).getResponse(results, 0), false);
			}
		}
		
		userSession.getUser().setRoomIndex(-1);
		
		if (!isDisconnected) {
			lobbyServer.onJoinLobby(userSession);
		}
	}

	@Override
	public void processMessage() {
		processMessage(userSession);
	}
	
	public void processMessage(UserSession userSession) {
		Room room = lobbyServer.getRoom(userSession.getUser().getRoomIndex());
		room.users[userSession.getUser().getRoomSlot()] = null;
		room.setNumberOfUsers(room.getNumberOfPlayers() - 1);
		room.isAlive[userSession.getUser().getRoomSlot()] = false;
		
		if (userSession.getUser().getRoomTeam() == 10) {
			room.bluePlayersCount--;
		}
		else {
			room.redPlayersCount--;
		}
		
		userSession.getUser().setInRoom(false);
		userSession.getUser().setInGame(false);
		userSession.getUser().setPlayerLoses(userSession.getUser().getPlayerLoses() + 1);
		
		if (room.getGameMode().getValue() != GameMode.COMMUNITY.getValue()) {
			userSession.getUser().setPlayerLoses(userSession.getUser().getPlayerLoses() + 1);
		}
	}
}
