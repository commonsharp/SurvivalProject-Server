package lobby.handlers;

import java.io.IOException;

import lobby.LobbyServer;
import net.GenericHandler;
import net.UserTCPSession;

public class RoomNameChangedHandler extends GenericHandler {
	public static final int REQUEST_ID = 0x4350;
	
	int roomID;
	String roomName;
	
	LobbyServer lobby;
	public RoomNameChangedHandler(LobbyServer lobby, UserTCPSession tcpServer, byte[] messageBytes) {
		super(tcpServer, messageBytes);
		this.lobby = lobby;
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
		lobby.getRoom(roomID).setRoomName(roomName);
		lobby.broadcastMessage(tcpServer, new LobbyRoomsChangedHandler(tcpServer).getResponse(lobby.getRoom(roomID)));
	}
}
