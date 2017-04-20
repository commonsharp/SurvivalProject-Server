package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.UserSession;

public class UserShopExtraSearchHandler extends LobbyHandler {

	public UserShopExtraSearchHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		// no fields
	}

	@Override
	public void processMessage() throws SQLException {
	}

	@Override
	public byte[] getResponse() throws SQLException {
		return new UserShopSearchHandler(lobbyServer, userSession).getResponse();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		
	}
}
