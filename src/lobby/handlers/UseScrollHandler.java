package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.UserSession;

public class UseScrollHandler extends LobbyHandler {
	protected int scrollIndex;
	
	public UseScrollHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		input.getInt(0x14);
		scrollIndex = input.getInt(0x18);
		input.getInt(0x1C);
		input.getByte(0x20);
	}

	@Override
	public void processMessage() throws SQLException {
		userSession.getUser().scrolls[scrollIndex] = 0;
		userSession.getUser().saveUser();
	}

	@Override
	public byte[] getResponse() {
		return null;
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		
	}

}
