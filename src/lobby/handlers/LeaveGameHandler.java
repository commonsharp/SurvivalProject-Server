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
		output.putInt(0x14, userSession.getUser().roomSlot);
		output.putString(0x18, userSession.getUser().username);
		output.putInt(0x28, 0); // this value is added to MyInfo+3198
		output.putInt(0x2C, 0); // this value is added to MyInfo+319C
		output.putByte(0x30, (byte) 0); // master card
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		Room room = lobbyServer.getRoom(userSession.getUser().roomIndex);
		
		lobbyServer.sendRoomMessage(userSession, getResponse(), false);
		sendTCPMessage(new LobbyRoomsChangedHandler(lobbyServer, userSession).getResponse(room));
		lobbyServer.sendBroadcastMessage(userSession, new LobbyRoomsChangedHandler(lobbyServer, userSession).getResponse(room));
	
		if (room.getNumberOfPlayers() == 0) {
			lobbyServer.setRoom(room.getRoomID(), null);
		}
		else {
			if (room.masterIndex == userSession.getUser().roomSlot) {
				room.masterIndex = room.getAnyPlayerSlot();
				lobbyServer.sendRoomMessage(userSession, new NewMasterHandler(lobbyServer, userSession).getResponse(room.masterIndex), true);
			}
			if (room.blueKingIndex == userSession.getUser().roomSlot) {
				room.blueKingIndex = room.getAnyPlayerSlot();
				lobbyServer.sendRoomMessage(userSession, new NewKingHandler(lobbyServer, userSession).getResponse(), true);
			}
			
			if (room.getGameMode() == GameMode.HERO && userSession.getUser().roomSlot == room.heroSlot) {
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
		
		userSession.getUser().roomIndex = -1;
		
		lobbyServer.onJoinLobby(userSession);
	}

	@Override
	public void processMessage() {
		processMessage(userSession);
	}
	
	public void processMessage(UserSession userSession) {
		Room room = lobbyServer.getRoom(userSession.getUser().roomIndex);
		room.users[userSession.getUser().roomSlot] = null;
		room.setNumberOfUsers(room.getNumberOfPlayers() - 1);
		room.isAlive[userSession.getUser().roomSlot] = false;
		
		if (userSession.getUser().roomTeam == 10) {
			room.bluePlayersCount--;
		}
		else {
			room.redPlayersCount--;
		}
		
		userSession.getUser().isInRoom = false;
		userSession.getUser().isInGame = false;
		userSession.getUser().playerLoses++;
		
		if (room.getGameMode().getValue() != GameMode.COMMUNITY.getValue()) {
			userSession.getUser().playerLoses++;
		}
	}
}
