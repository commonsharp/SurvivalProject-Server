package login.handlers;

import net.GenericHandler;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class GetChannelUsersPercentageHandler extends GenericHandler {
	public static final int REQUEST_ID = 0x2917;
	public static final int RESPONSE_ID = 0x2918;
	public static final int RESPONSE_LENGTH = 0x1000;
	
	public GetChannelUsersPercentageHandler(UserTCPSession tcpServer, byte[] messageBytes) {
		super(tcpServer, messageBytes);
	}

	@Override
	public void interpretBytes() {
	}

	@Override
	public void afterSend() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, RESPONSE_ID);
		output.putInt(0x14, 0); // tutorial channel
		output.putInt(0x18, 33); // beginner channel
		output.putInt(0x1C, 66); // hero channel
		output.putInt(0x20, 100); // epic channel
		
		return output.toArray();
	}
}
