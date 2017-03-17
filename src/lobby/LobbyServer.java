package lobby;

import lobby.handlers.GetTopGuildsMarkHandler;
import lobby.handlers.GetTopGuildsHandler;
import lobby.handlers.JoinLobbyHandler;
import net.GenericMessage;
import net.GenericTCPServer;
import tools.HexTools;

public class LobbyServer extends GenericTCPServer {
	public LobbyServer(int port) {
		super("Lobby server", port);
	}

	@Override
	public GenericMessage processPacket(int messageID, byte[] messageBytes) {
		GenericMessage message = null;

		switch (messageID) {
		case JoinLobbyHandler.REQUEST_ID:
			message = new JoinLobbyHandler(messageBytes);
			break;
		case GetTopGuildsHandler.REQUEST_ID:
			message = new GetTopGuildsHandler(messageBytes);
			break;
		case GetTopGuildsMarkHandler.REQUEST_ID:
			message = new GetTopGuildsMarkHandler(messageBytes);
			break;
		default:
			HexTools.printHexArray(messageBytes, false);
		}
		
		
		return message;
	}

}
