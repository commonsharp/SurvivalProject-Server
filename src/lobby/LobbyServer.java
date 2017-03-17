package lobby;

import lobby.handlers.GetTopGuildsMarkHandler;
import lobby.handlers.GetTopGuildsHandler;
import lobby.handlers.JoinLobbyHandler;
import net.GenericMessage;
import net.GenericTCPServer;

public class LobbyServer extends GenericTCPServer {
	public LobbyServer(int port) {
		super("Lobby server", port);
	}

	@Override
	public GenericMessage processPacket(int messageID, byte[] messageBytes) {
		GenericMessage message = null;

		switch (messageID) {
		case 0x4301:
			message = new JoinLobbyHandler(messageBytes);
			break;
		case 0x4388:
			message = new GetTopGuildsHandler(messageBytes);
			break;
		case 0x4486:
			message = new GetTopGuildsMarkHandler(messageBytes);
			break;
		}
		
		
		return message;
	}

}
