package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.UserTCPSession;

public class RoomNameChangedHandler extends LobbyHandler {
	public static final int REQUEST_ID = 0x4350;
	
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
		return null;
	}
	
	@Override
	public void afterSend() throws IOException {
		lobbyServer.getRoom(roomID).setRoomName(roomName);
		lobbyServer.sendBroadcastMessage(userSession, new LobbyRoomsChangedHandler(lobbyServer, userSession).getResponse(lobbyServer.getRoom(roomID)));
	}

	@Override
	public void processMessage() {
		// TODO Auto-generated method stub
		
	}
}
