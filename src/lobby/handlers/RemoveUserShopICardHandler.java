package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.UserSession;

public class RemoveUserShopICardHandler extends LobbyHandler {
	int shopIndex;
	
	public RemoveUserShopICardHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		shopIndex = input.getInt(0x14);
	}

	@Override
	public void processMessage() throws SQLException {
		userSession.getUser().bigUserShop.addShop(null, shopIndex);
	}

	@Override
	public byte[] getResponse() throws SQLException {
		return null;
	}

	@Override
	public void afterSend() throws IOException, SQLException {
	}

}
