package lobby.handlers;

import java.io.IOException;

import net.GenericHandler;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class ResultsHandler extends GenericHandler {
	public static final int RESPONSE_ID = 0x4361;
	public static final int RESPONSE_LENGTH = 0x90;
	
	public ResultsHandler(UserTCPSession userSession) {
		super(userSession);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void interpretBytes() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, RESPONSE_ID);
		
		output.putInt(0x14, 1); // 0 = no result
		output.putInt(0x18, 1); // 1 = win
//		output.putInt(0x14, );
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
