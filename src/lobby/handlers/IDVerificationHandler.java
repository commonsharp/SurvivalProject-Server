package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import database.DatabaseHelper;
import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class IDVerificationHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x18;
	
	protected String username;
	
	public IDVerificationHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		username = input.getString(0x14);
	}

	@Override
	public void processMessage() throws SQLException {
		
	}

	@Override
	public byte[] getResponse() throws SQLException {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.ID_VERIFICATION_RESPONSE);
		
		/*
		 * 0 - ID is wrong.&#xA;Please enter the correct ID.
		 * 1 - ID certification is completed.
		 * 2 - Using demo version. this command is not available lol
		 * 3 - Cannot send a message to this user
		 */
		int response;
		
		if (lobbyServer.findUserSession(username) != null || DatabaseHelper.isUserExists(username)) {
			response = 1;
		}
		else {
			response = 0;
		}
		
		output.putInt(0x14, response);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		
	}
}
