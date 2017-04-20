package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.UserSession;
import net.objects.Room;

public class SymbolStateChanged extends LobbyHandler {
	int[] symbols;
	public SymbolStateChanged(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		symbols = input.getInts(0x14, 4);
	}

	@Override
	public byte[] getResponse() {
		return null;
	}

	@Override
	public void afterSend() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processMessage() {
		Room room = lobbyServer.getRoom(userSession.getUser().roomIndex);
		
		for (int i = 0; i < 4; i++) {
			room.symbols[i] = symbols[i];
		}
	}
}
