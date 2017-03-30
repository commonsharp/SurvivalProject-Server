package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class GetListOfUsersHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x1AC;
	public GetListOfUsersHandler(LobbyServer lobbyServer, UserTCPSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void interpretBytes() {
		// no fields
	}

	@Override
	public void processMessage() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.GET_FRIENDS_RESPONSE);
		String[] users = new String[24];
		
		for (int i = 0; i < users.length; i++) {
			users[i] = "user " + (i + 1); 
		}
		
		output.putStrings(0x14, users, 0xD);
		
		// there is one more integer for each player, starting at 0x14C
		// -1 = can't invite. other values = can invite?
		output.putInt(0x14C, -1);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
