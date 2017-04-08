package login.handlers;

import login.LoginHandler;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class GetChannelUsersPercentageHandler extends LoginHandler {
	public static final int RESPONSE_LENGTH = 0x1000;
	
	public GetChannelUsersPercentageHandler(UserSession tcpServer, byte[] messageBytes) {
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
		output.putInt(0x4, Messages.GET_CHANNEL_USERS_PERCENTAGE_RESPONSE);
		output.putInt(0x14, 0); // tutorial channel
		output.putInt(0x18, 33); // beginner channel
		output.putInt(0x1C, 66); // hero channel
		output.putInt(0x20, 100); // epic channel
		
		return output.toArray();
	}

	@Override
	public void processMessage() {
		// TODO Auto-generated method stub
		
	}
}
