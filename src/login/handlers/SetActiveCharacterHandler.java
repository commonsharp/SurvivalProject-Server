package login.handlers;

import net.GenericHandler;
import net.UserTCPSession;

// request - V
public class SetActiveCharacterHandler extends GenericHandler {
	public static final int REQUEST_ID = 0x2911;
	public static final int RESPONSE_ID = 0x2912;
	public static final int RESPONSE_LENGTH = 0x1C;

	protected String username;
	
	protected int unknown1;
	
	public SetActiveCharacterHandler(UserTCPSession tcpServer, byte[] messageBytes) {
		super(tcpServer, messageBytes, RESPONSE_LENGTH, RESPONSE_ID);
	}

	@Override
	public void interpretBytes() {
		username = input.getString(0x14);
		tcpServer.getUser().activeCharacter = input.getInt(0x24);
	}

	@Override
	public void processFields() {
		// Add to database and stuff
	}

	@Override
	public void changeData() {
		unknown1 = 0; // ?
	}

	@Override
	public void addPayload() {
		output.putInt(0x14, unknown1);
		output.putInt(0x18, tcpServer.getUser().activeCharacter);
	}

	@Override
	public void afterSend() {
		// TODO Auto-generated method stub
		
	}
}
