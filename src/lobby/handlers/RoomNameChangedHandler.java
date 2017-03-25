package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class RoomNameChangedHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x34;
	
	int roomID;
	String roomName;
	
	public RoomNameChangedHandler(LobbyServer lobbyServer, UserTCPSession tcpServer, byte[] messageBytes) {
		super(lobbyServer, tcpServer, messageBytes);
	}

	@Override
	public void interpretBytes() {
		roomID = input.getInt(0x14);
		roomName = input.getString(0x18);
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.ROOM_NAME_CHANGED_RESPONSE);
		output.putString(0x14, roomName);
		
		return output.toArray();
	}
	
	
	@Override
	public void afterSend() throws IOException {
		lobbyServer.getRoom(roomID).setRoomName(roomName);
		lobbyServer.sendRoomMessage(userSession, getResponse(), false);
		lobbyServer.sendBroadcastMessage(userSession, new LobbyRoomsChangedHandler(lobbyServer, userSession).getResponse(lobbyServer.getRoom(roomID)));
	}

	@Override
	public void processMessage() {
		// TODO Auto-generated method stub
		
	}
}
