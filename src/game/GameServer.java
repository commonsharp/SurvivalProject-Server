package game;
import game.handlers.JoinServerHandler;
import net.GenericHandler;
import net.GenericUDPServer;

public class GameServer extends GenericUDPServer {
	public GameServer(int port) {
		super("Game server", port);
	}
	
	@Override
	public GenericHandler processPacket(GenericUDPServer udpServer, int messageID, byte[] messageBytes) {
		GenericHandler message = null;
		
		switch (messageID) {
		case JoinServerHandler.REQUEST_ID:
			message = new JoinServerHandler(udpServer, messageBytes);
			break;
		}
		
		return message;
	}
}