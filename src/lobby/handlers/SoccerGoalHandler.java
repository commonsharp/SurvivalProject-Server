package lobby.handlers;

import java.io.IOException;

import net.GenericHandler;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class SoccerGoalHandler extends GenericHandler {
	public static final int RESPONSE_ID = 0x4345;
	public static final int RESPONSE_LENGTH = 0x28;
	
	public SoccerGoalHandler(UserTCPSession tcpServer, byte[] messageBytes) {
		super(tcpServer, messageBytes);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void interpretBytes() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterSend() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, RESPONSE_ID);
		output.putInt(0x14, 3);
		output.putInt(0x18, 0);
		output.putInt(0x1C, 0);
		output.putInt(0x20, (int)(System.currentTimeMillis() / 1000));
		output.putInt(0x24, (int)(System.currentTimeMillis() / 1000));
		
		return output.toArray();
	}

}
