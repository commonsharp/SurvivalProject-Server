package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.UserTCPSession;

public class BuyCardHandler extends LobbyHandler {
	public BuyCardHandler(LobbyServer lobbyServer, UserTCPSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		input.getInt(0x14);
		input.getInt(0x18); // item id
		input.getInt(0x1C); // premium days
		input.getInt(0x20);
		input.getInt(0x24);
		input.getInt(0x28);
	}

	@Override
	public void processMessage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getResponse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void afterSend() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
