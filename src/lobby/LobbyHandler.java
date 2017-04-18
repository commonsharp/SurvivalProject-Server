package lobby;

import net.UserSession;
import net.handlers.TCPHandler;

public abstract class LobbyHandler extends TCPHandler {
	protected LobbyServer lobbyServer;
	
	public LobbyHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(userSession, messageBytes);
		this.lobbyServer = lobbyServer;
	}
	
	public LobbyHandler(LobbyServer lobbyServer, UserSession userSession) {
		super(userSession);
		this.lobbyServer = lobbyServer;
	}
}
