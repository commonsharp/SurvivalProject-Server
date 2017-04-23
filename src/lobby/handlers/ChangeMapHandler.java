package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import net.objects.Room;
import tools.ExtendedByteBuffer;

public class ChangeMapHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x24;
	
	public ChangeMapHandler(LobbyServer lobbyServer, UserSession userSession) {
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
		output.putInt(0x4, Messages.CHANGE_MAP_RESPONSE);
		
		Room room = lobbyServer.getRoom(userSession.getUser().roomIndex);
		output.putInt(0x14, room.getGameMap()); // map
		output.putInt(0x18, 1); // sub map. 0 = random map
		output.putInt(0x1C, 5); // current round. 0 based
		output.putInt(0x20, 50);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
