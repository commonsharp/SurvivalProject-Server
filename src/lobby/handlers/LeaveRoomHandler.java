package lobby.handlers;

import java.io.IOException;

import net.GenericHandler;
import net.UserTCPSession;

public class LeaveRoomHandler extends GenericHandler {
	public static final int REQUEST_ID = 0x4318;
	public static final int RESPONSE_ID = 0x4319;
	public static final int RESPONSE_LENGTH = 0x28;
	
	public LeaveRoomHandler(UserTCPSession tcpServer, byte[] messageBytes) {
		super(tcpServer, messageBytes, RESPONSE_LENGTH, RESPONSE_ID);
	}

	@Override
	public void interpretBytes() {
		
	}

	@Override
	public void processFields() {
		
	}

	@Override
	public void changeData() {
		// TODO Auto-generated method stub
	}

	@Override
	public void addPayload() {
		output.putInt(0x14, 0); // slot?
		output.putString(0x18, tcpServer.getUser().username);
	}

	@Override
	public void afterSend() throws IOException {
		// this line has weird side effects. do not use.
//		sendTCPMessage(new RoomPlayersChangedHandler(tcpServer, new byte[3000]).getResponse());
	}
}
