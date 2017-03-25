package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserTCPSession;
import net.objects.Room;
import tools.ExtendedByteBuffer;

public class LeaveRoomHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x28;
	
	
	public LeaveRoomHandler(LobbyServer lobbyServer, UserTCPSession tcpServer, byte[] messageBytes) {
		super(lobbyServer, tcpServer, messageBytes);
	}

	@Override
	public void interpretBytes() {
//		roomID = input.
		
	}

	@Override
	public void afterSend() throws IOException {
		lobbyServer.sendRoomMessage(userSession, getResponse(), false);
		lobbyServer.sendBroadcastMessage(userSession, new LobbyRoomsChangedHandler(lobbyServer, userSession).getResponse(lobbyServer.getRoom(userSession.getUser().roomIndex)));
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.LEAVE_ROOM_RESPONSE);
		output.putInt(0x14, userSession.getUser().roomSlot);
		output.putString(0x18, userSession.getUser().username);
		lobbyServer.getRoom(userSession.getUser().roomIndex).setUserSession(userSession.getUser().roomSlot, null);
		
		return output.toArray();
	}

	@Override
	public void processMessage() {
		Room room = lobbyServer.getRoom(userSession.getUser().roomIndex);
		room.setCharacter(userSession.getUser().roomSlot, 0);
		room.setNumberOfUsers(room.getNumberOfUsers() - 1);
		userSession.getUser().isInRoom = false;
		userSession.getUser().isInGame = false;
	}
}
