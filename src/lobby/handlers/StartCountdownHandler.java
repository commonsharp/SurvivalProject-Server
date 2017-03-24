package lobby.handlers;

import java.io.IOException;

import net.GenericHandler;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class StartCountdownHandler extends GenericHandler {
	public static final int REQUEST_ID = 0x4454;
	public static final int RESPONSE_ID = 0x4455;
	public static final int RESPONSE_LENGTH = 0x118;
	
	public StartCountdownHandler(UserTCPSession userSession, byte[] messageBytes) {
		super(userSession, messageBytes);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void interpretBytes() {
		input.getInt(0x14);
		input.getInt(0x18);
		input.getInt(0x1C);
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, RESPONSE_ID);
		
		output.putInt(0x14, 0);
		output.putInt(0x18, 0);
		output.putInt(0x1C, 6000);
		output.putInt(0x20, 0);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
