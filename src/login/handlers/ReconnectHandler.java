package login.handlers;

import java.io.IOException;
import java.sql.SQLException;

import login.LoginHandler;
import net.UserSession;

public class ReconnectHandler extends LoginHandler {
	public static final int RESPONSE_ID = 0; //?
	public static final int RESPONSE_LENGTH = 0x1000;
	
	public ReconnectHandler(UserSession userSession, byte[] messageBytes) {
		super(userSession, messageBytes);
	}

	protected String username;
	protected int disconnectFrom;
	protected int unknown2;
	protected int unknown3;
	

	@Override
	public void interpretBytes() {
		username = input.getString(0x14);
		disconnectFrom = input.getInt(0x24); // 1 = servers list screen. 2 = lobby screen
		unknown2 = input.getInt(0x28);
		unknown3 = input.getInt(0x2C);
		
		System.out.println(disconnectFrom);
		System.out.println(unknown2);
		System.out.println(unknown3);
	}

	@Override
	public void afterSend() throws IOException, SQLException {
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
