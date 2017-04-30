package login.handlers;

import java.io.IOException;
import java.sql.SQLException;

import login.LoginHandler;
import net.Messages;
import net.UserSession;
import net.objects.User;
import tools.ExtendedByteBuffer;

public class TutorialCompletedNewVersionHandler extends LoginHandler {
	public static final int RESPONSE_LENGTH = 0x95;
	
	public TutorialCompletedNewVersionHandler(UserSession userSession) {
		super(userSession);
	}

	@Override
	public void interpretBytes() {
		
	}

	@Override
	public void processMessage() throws SQLException {
		userSession.getUser().setPlayerLevel(1);
		User.saveUser(userSession.getUser());
//		User.saveCards(userSession.getUser(), userSession.getUser().getCards());
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.TUTORIAL_COMPLETED_NEW_VERSION_RESPONSE);
		
		for (int i = 0; i < 10; i++) {
			output.putInt(0x14 + 4 * i, 1111); // ID 
			output.putInt(0x3C + 4 * i, 5); // level
			output.putInt(0x64 + 4 * i, 8); // premium days
		}
		
		output.putInt(0x8C, 5000);
		output.putInt(0x90, 0); // ?
		output.putBoolean(0x94, false);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
	}
}
