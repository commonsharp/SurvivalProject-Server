package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

// spawn a new money bubble
public class SpawnCodeHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x30;
	
	public SpawnCodeHandler(LobbyServer lobbyServer, UserSession userSession) {
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
		output.putInt(0x4, Messages.SPAWN_CODE_RESPONSE);
		
		output.putInt(0x14, userSession.getUser().roomSlot);
		output.putInt(0x18, (int)(Math.random() * 2)); // type
		output.putInt(0x1C, lobbyServer.getRoom(userSession.getUser().roomIndex).coinLocation++); // an index of a predefined set of locations. i think there are only 4 locations. it takes index % 4, so 0 and 4 are the same locations.
		output.putInt(0x20, 0); // dx
		output.putInt(0x24, 0); // dy. up to 941 (included)
		output.putInt(0x28, 20000); // time before pop in ms
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
