package lobby;

import net.UserTCPSession;
import net.handlers.TCPHandler;

public abstract class LobbyHandler extends TCPHandler {
	protected LobbyServer lobbyServer;
	
	public LobbyHandler(LobbyServer lobbyServer, UserTCPSession userSession, byte[] messageBytes) {
		super(userSession, messageBytes);
		this.lobbyServer = lobbyServer;
	}
	
	public LobbyHandler(LobbyServer lobbyServer, UserTCPSession userSession) {
		super(userSession);
		this.lobbyServer = lobbyServer;
	}
}
