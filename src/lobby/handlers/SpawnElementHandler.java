package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class SpawnElementHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x28;
	
	public SpawnElementHandler(LobbyServer lobbyServer, UserSession userSession) {
		super(lobbyServer, userSession);
	}

	@Override
	public void interpretBytes() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processMessage() throws SQLException {
		
	}

	@Override
	public byte[] getResponse() throws SQLException {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.SPAWN_ELEMENT_RESPONSE);
		
		output.putInt(0x14, 0); // this is only used if 0x18 is NOT 5. this value is subtracted from another value and if it's less than 0, it's set to 0 o_o
		output.putInt(0x18, 1 + (int)(Math.random() * 4)); // element type. it can also be 5~9 (used in events)
		output.putInt(0x1C, 100 + (int)(Math.random() * 800)); // x
		output.putInt(0x20, 100 + (int)(Math.random() * 800)); // y
		output.putInt(0x24, (int)(Math.random() * 360)); // angle
		
		output.putInt(0x1C, 500);
		output.putInt(0x20, 500);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
	}
}
