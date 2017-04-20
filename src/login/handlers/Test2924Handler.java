package login.handlers;

import java.io.IOException;
import java.sql.SQLException;

import login.LoginHandler;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class Test2924Handler extends LoginHandler {
	public static final int RESPONSE_LENGTH = 0x18;
	
	public Test2924Handler(UserSession userSession) {
		super(userSession);
	}

	@Override
	public void interpretBytes() {
		
	}

	@Override
	public void processMessage() throws SQLException {
		
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, 0x2924);
		/*
		 * 2 - Invalid password. Please try again
		 */
		output.putInt(0x14, 1);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
	}
}
