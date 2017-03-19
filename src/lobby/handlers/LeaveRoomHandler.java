package lobby.handlers;

import java.io.IOException;

import lobby.LobbyServer;
import net.GenericHandler;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class LeaveRoomHandler extends GenericHandler {
	public static final int REQUEST_ID = 0x4318;
	public static final int RESPONSE_ID = 0x4319;
	public static final int RESPONSE_LENGTH = 0x28;
	
	protected LobbyServer lobby;
	
	public LeaveRoomHandler(LobbyServer lobby, UserTCPSession tcpServer, byte[] messageBytes) {
		super(tcpServer, messageBytes);
		this.lobby = lobby;
	}

	@Override
	public void interpretBytes() {
//		roomID = input.
		
	}

	@Override
	public void afterSend() throws IOException {
//		lobby.broadcastMessage(tcpServer, new LobbyRoomsChangedHandler(tcpServer).getResponse(lobby.getRoom(0)));
		// this line has weird side effects. do not use.
//		sendTCPMessage(new RoomPlayersChangedHandler(tcpServer, new byte[3000]).getResponse());
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, RESPONSE_ID);
		output.putInt(0x14, 0); // slot?
		output.putString(0x18, tcpServer.getUser().username);
		
		return output.toArray();
	}
}
