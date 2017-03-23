package lobby.handlers;

import java.io.IOException;

import net.GenericHandler;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

// SAME AS QUEST RESPONSE!!!
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
	
	public byte[] getResponse(int result) {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);

		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, RESPONSE_ID);
		
		int[] slotResults = new int[8];
		
		for (int i = 0; i < 8; i++)
			slotResults[i] = result;
		
		 // result array. 0 = resurrection. 1 = quest success. 2 = quest failed. 4 = player left game? others = nothing.
		output.putInts(0x14, slotResults); // result (per slot)
		output.putInt(0x34, 13); //ko
		output.putInt(0x38, 15); // ? maybe guild exp?
		output.putInt(0x3C, 200); // exp array (per slot)
		output.putInt(0x5C, 300); // code array (per slot)
		output.putLong(0x80, 400); // new exp
		output.putLong(0x88, 500); // new code
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
