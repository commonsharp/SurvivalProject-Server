package login.handlers;

import net.GenericHandler;
import net.UserTCPSession;

public class ReconnectHandler extends GenericHandler {
	public static final int RESPONSE_ID = 0; //?
	public static final int RESPONSE_LENGTH = 0x1000;
	
	public ReconnectHandler(UserTCPSession tcpServer, byte[] messageBytes) {
		super(tcpServer, messageBytes, RESPONSE_LENGTH, RESPONSE_ID);
		// TODO Auto-generated constructor stub
	}

	protected String username;
	protected int unknown1;
	protected int unknown2;
	protected int unknown3;
	

	@Override
	public void interpretBytes() {
		username = input.getString(0x14);
		unknown1 = input.getInt(0x21);
		unknown2 = input.getInt(0x25);
		unknown3 = input.getInt(0x29);
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

	@Override
	public void afterSend() {
		// TODO Auto-generated method stub
		
	}
}
