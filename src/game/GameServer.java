package game;
import game.client.JoinServerRequest;
import net.GenericClientMessage;
import net.GenericUDPServer;

public class GameServer extends GenericUDPServer {
	public GameServer(int port) {
		super("Game server", port);
	}
	
	@Override
	public GenericClientMessage processPacket(int messageID, byte[] messageBytes) {
		GenericClientMessage message = null;
		
		switch (messageID) {
		case 0x1101:
			message = new JoinServerRequest(messageBytes);
			break;
		}
		return message;
	}
}
