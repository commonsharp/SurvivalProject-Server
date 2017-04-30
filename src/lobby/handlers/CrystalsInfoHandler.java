package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.UserSession;
import net.objects.Room;

public class CrystalsInfoHandler extends LobbyHandler {
	private byte[] bytes;
	
	public CrystalsInfoHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
		this.bytes = messageBytes;
	}

	@Override
	public void interpretBytes() {
		System.out.println(input.getInt(0x14));
		Room room = lobbyServer.getRoom(userSession.getUser().getRoomIndex());
		
		int numberOfCrystals = 0;
		for (int i = 0; i < 4; i++) {
			System.out.println(i + " 1? " + input.getInt(0x18 + 4 * i));
			
			if (input.getInt(0x48 + 4 * i) != 30) {
				room.symbols[i] = input.getInt(0x48 + 4 * i);
				numberOfCrystals++;
			}
		}
		
		room.numberOfCrystals = numberOfCrystals;
	}

	@Override
	public void processMessage() throws SQLException {
	}

	@Override
	public byte[] getResponse() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public byte[] getResponse2() {
		return bytes;
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		lobbyServer.sendRoomMessage(userSession, getResponse2(), true);
	}

}
