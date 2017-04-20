package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class GetLobbyUsersHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x3C;
	
	public GetLobbyUsersHandler(LobbyServer lobbyServer, UserSession userSession) {
		super(lobbyServer, userSession);
	}
	
	public GetLobbyUsersHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		// no fields
	}

	@Override
	public void processMessage() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public byte[] getResponse(UserSession userSession, boolean isOnline) {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.GET_LOBBY_USERS_RESPONSE);
		output.putString(0x14, userSession.getUser().username); // name
		output.putInt(0x24, userSession.getUser().playerLevel); // level
		output.putInt(0x28, 0);
		output.putInt(0x2C, 0);
		output.putBoolean(0x30, userSession.getUser().isMale); // isMale
		output.putBoolean(0x31, isOnline); // is online
		output.putInt(0x34, 0);
		output.putInt(0x38, userSession.getUser().missionLevel); // mission level
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		for (UserSession userSession : lobbyServer.getUserSessions()) {
			sendTCPMessage(getResponse(userSession, true));
		}
	}

	@Override
	public byte[] getResponse() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
