package game;

import net.GenericUDPServer;
import net.handlers.UDPHandler;

public abstract class GameHandler extends UDPHandler {
	public GameHandler(GenericUDPServer udpServer, byte[] messageBytes) {
		super(udpServer, messageBytes);
	}
	
	public GameHandler(GenericUDPServer udpServer) {
		super(udpServer);
	}
}
