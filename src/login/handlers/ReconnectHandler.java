package login.handlers;

import login.LoginHandler;
import net.UserTCPSession;

public class ReconnectHandler extends LoginHandler {
	public static final int RESPONSE_ID = 0; //?
	public static final int RESPONSE_LENGTH = 0x1000;
	
	public ReconnectHandler(UserTCPSession tcpServer, byte[] messageBytes) {
		super(tcpServer, messageBytes);
		// TODO Auto-generated constructor stub
	}

	protected String username;
	protected int unknown1;
	protected int unknown2;
	protected int unknown3;
	

	@Override
	public void interpretBytes() {
		username = input.getString(0x14);
		unknown1 = input.getInt(0x24);
		unknown2 = input.getInt(0x28);
		unknown3 = input.getInt(0x2C);
		
		System.out.println(unknown1);
		System.out.println(unknown2);
		System.out.println(unknown3);
	}

	@Override
	public void afterSend() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getResponse() {
		return null;
	}

	@Override
	public void processMessage() {
		// TODO Auto-generated method stub
		
	}
}
