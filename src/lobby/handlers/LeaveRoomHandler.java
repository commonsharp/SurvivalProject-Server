package lobby.handlers;

import net.GenericHandler;
import net.UserTCPSession;

public class LeaveRoomHandler extends GenericHandler {
	public static final int REQUEST_ID = 0x4318;
	public static final int RESPONSE_ID = 0x4319;
	public static final int RESPONSE_LENGTH = 0x4319;
	
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
		output.putInt(0x14, 2);
		output.putInt(0x18, 2);
	}

	@Override
	public void afterSend() {
		// TODO Auto-generated method stub
		
	}
}
