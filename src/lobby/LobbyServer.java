package lobby;

import lobby.client.messages.GetTopGuildsMarkRequest;
import lobby.client.messages.GetTopGuildsRequest;
import lobby.client.messages.JoinLobbyRequest;
import net.GenericClientMessage;
import net.GenericTCPServer;
import tools.HexTools;

public class LobbyServer extends GenericTCPServer {
	public LobbyServer(int port) {
		super("Lobby server", port);
	}

	@Override
	public GenericClientMessage processPacket(int messageID, byte[] messageBytes) {
		GenericClientMessage message = null;

		switch (messageID) {
		case 0x4301:
			message = new JoinLobbyRequest(messageBytes);
			break;
		case 0x4388:
			message = new GetTopGuildsRequest(messageBytes);
			break;
		case 0x4486:
			message = new GetTopGuildsMarkRequest(messageBytes);
			break;
		}
		
		
		return message;
	}

}
