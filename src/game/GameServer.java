package game;
import game.handlers.JoinServerHandler;
import net.GenericMessage;
import net.GenericUDPServer;

public class GameServer extends GenericUDPServer {
	public GameServer(int port) {
		super("Game server", port);
	}
	
	@Override
	public GenericMessage processPacket(int messageID, byte[] messageBytes) {
		GenericMessage message = null;
		
		switch (messageID) {
		case 0x1101:
			message = new JoinServerHandler(messageBytes);
			break;
		}
		return message;
	}
}
