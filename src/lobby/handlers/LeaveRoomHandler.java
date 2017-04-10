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

public class LeaveRoomHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x28;
	
	
	public LeaveRoomHandler(LobbyServer lobbyServer, UserSession tcpServer, byte[] messageBytes) {
		super(lobbyServer, tcpServer, messageBytes);
	}

	@Override
	public void interpretBytes() {
		// There are no fields in here
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
		
		lobbyServer.onJoinLobby(userSession);
		
		lobbyServer.sendRoomMessage(userSession, new NewMasterHandler(lobbyServer, userSession).getResponse(1), true);
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.LEAVE_ROOM_RESPONSE);
		output.putInt(0x14, userSession.getUser().roomSlot);
		output.putString(0x18, userSession.getUser().username);
		output.putByte(0x25, (byte) 0); // master card
		lobbyServer.getRoom(userSession.getUser().roomIndex).setUserSession(userSession.getUser().roomSlot, null);
		
		return output.toArray();
	}

	@Override
	public void processMessage() {
		Room room = lobbyServer.getRoom(userSession.getUser().roomIndex);
		room.setCharacter(userSession.getUser().roomSlot, 0);
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
		
		if (room.getGameMode() != GameMode.COMMUNITY) {
			userSession.getUser().playerLoses++;
		}
	}
}
