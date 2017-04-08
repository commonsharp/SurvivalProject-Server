package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import net.objects.Room;
import tools.ExtendedByteBuffer;

public class LeaveGameHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x31;
	
	public LeaveGameHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		
	}

	@Override
	public byte[] getResponse() {
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
		
		lobbyServer.onJoinLobby(userSession);
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
		userSession.getUser().playerLoses++;
	}
}
