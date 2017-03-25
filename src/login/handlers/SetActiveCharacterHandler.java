package login.handlers;

import login.LoginHandler;
import net.Messages;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

// request - V
public class SetActiveCharacterHandler extends LoginHandler {
	public static final int RESPONSE_LENGTH = 0x1C;

	protected String username;
	
	protected int unknown1;
	
	public SetActiveCharacterHandler(UserTCPSession tcpServer, byte[] messageBytes) {
		super(tcpServer, messageBytes);
	}

	@Override
	public void interpretBytes() {
		username = input.getString(0x14);
		userSession.getUser().activeCharacter = input.getInt(0x24);
	}

	@Override
	public void afterSend() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getResponse() {
		unknown1 = 0; // ?
		
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.SERVERS_INFO_RESPONSE);
		output.putInt(0x14, unknown1);
		output.putInt(0x18, userSession.getUser().activeCharacter);
		
		return output.toArray();
	}

	@Override
	public void processMessage() {
		// TODO Auto-generated method stub
		
	}
}
