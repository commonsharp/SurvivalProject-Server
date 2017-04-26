package game;

import net.handlers.UDPHandler;

public abstract class GameHandler extends UDPHandler {
	protected GameServer gameServer;
	
	public GameHandler(GameServer gameServer, byte[] messageBytes) {
		super(gameServer, messageBytes);
		this.gameServer = gameServer;
	}
	
	public GameHandler(GameServer gameServer) {
		super(gameServer);
		this.gameServer = gameServer;
	}
}
