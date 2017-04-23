package login.handlers;

import java.io.IOException;
import java.sql.SQLException;

import login.LoginHandler;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class DisconnectNightPlayHandler extends LoginHandler {
	public static final int RESPONSE_LENGTH = 0x14;
	
	public DisconnectNightPlayHandler(UserSession userSession) {
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
		output.putInt(0x4, Messages.DISCONNECT_NIGHT_PLAY_RESPONSE);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
