package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class Test4479Handler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x40;
	
	public Test4479Handler(LobbyServer lobbyServer, UserSession userSession) {
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
		output.putInt(0x4, 0x4479);
		output.putInt(0x14, 3); // map
		output.putInt(0x18, 0);
		output.putInt(0x1C, 0);
		output.putInt(0x20, 0);
		output.putInt(0x24, 5);
		output.putInt(0x28, 5);
		output.putInt(0x2C, 5);
		output.putInt(0x30, 5);
		output.putInt(0x34, 5);
		output.putInt(0x38, 5);
		output.putInt(0x3C, 5);
		
		// There are more fields!!!
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
