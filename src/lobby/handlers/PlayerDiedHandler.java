package lobby.handlers;

import java.io.IOException;

import net.GenericHandler;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class PlayerDiedHandler extends GenericHandler {
	public static final int REQUEST_ID = 0x4396;
	public static final int RESPONSE_ID = 0x4397;
	public static final int RESPONSE_LENGTH = 0x9999;
	
	public PlayerDiedHandler(UserTCPSession tcpServer, byte[] messageBytes) {
		super(tcpServer, messageBytes);
	}

	@Override
	public void interpretBytes() {
		
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);

		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, RESPONSE_ID);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
