package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.UserSession;
import net.objects.Room;
import tools.ExtendedByteBuffer;

public class NewBoostersHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x34;
	
	public NewBoostersHandler(LobbyServer lobbyServer, UserSession userSession) {
		super(lobbyServer, userSession);
	}

	@Override
	public void interpretBytes() {
		
	}

	@Override
	public void processMessage() throws SQLException {
		
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, 0x4472);
		
		Room room = lobbyServer.getRoom(userSession.getUser().getRoomIndex());
		UserSession currentUserSession;
		
		for (int i = 0; i < 8; i++) {
			currentUserSession = room.getUserSession(i);
			
			if (currentUserSession != null) {
				output.putInt(0x14 + 4 * i, currentUserSession.getUser().getBooster());
			}
		}
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		
	}

}
