package lobby;

import net.ClientGenericMessage;
import net.GenericServer;

public class LobbyServer extends GenericServer {
	public LobbyServer(int port) {
		super("Lobby server", port);
	}

	@Override
	public ClientGenericMessage processPacket(int messageID, byte[] messageBytes) {
		ClientGenericMessage message = null;

		switch (messageID) {
		
		}
		
		return message;
	}

}
