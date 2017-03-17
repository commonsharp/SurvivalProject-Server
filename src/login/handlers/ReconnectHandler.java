package login.handlers;

import net.GenericMessage;

public class ReconnectHandler extends GenericMessage {
	public static final int RESPONSE_ID = 0; //?
	
	public ReconnectHandler(byte[] messageBytes) {
		super(messageBytes, 0x14, RESPONSE_ID);
		// TODO Auto-generated constructor stub
	}

	protected String username;
	protected int unknown1;
	protected int unknown2;
	protected int unknown3;
	

	@Override
	public void interpretBytes(byte[] messageBytes) {
		username = inputBuffer.getString(0x14);
		unknown1 = inputBuffer.getInt(0x21);
		unknown2 = inputBuffer.getInt(0x25);
		unknown3 = inputBuffer.getInt(0x29);
	}

	@Override
	public void processFields() {
		// Add to database and stuff
	}

	@Override
	public void changeData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPayload() {
		// TODO Auto-generated method stub
		
	}
}
