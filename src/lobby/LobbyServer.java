package lobby;

import lobby.client.messages.JoinLobbyRequest;
import net.ClientGenericMessage;
import net.GenericTCPServer;
import tools.HexTools;

public class LobbyServer extends GenericTCPServer {
	public LobbyServer(int port) {
		super("Lobby server", port);
	}

	@Override
	public ClientGenericMessage processPacket(int messageID, byte[] messageBytes) {
		ClientGenericMessage message = null;

		switch (messageID) {
		case 0x4301:
			message = new JoinLobbyRequest(messageBytes);
			System.out.println(message.getResponse()[0x92c]);
//			HexTools.printHexArray(message.getResponse(), false);
			break;
		}
		
		return message;
	}

}
