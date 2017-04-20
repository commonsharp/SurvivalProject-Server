package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class Test4393Handler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x30;
	
	public Test4393Handler(LobbyServer lobbyServer, UserSession userSession) {
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
		output.putInt(0x4, 0x4393);
//		output.putInt(0x14, 1000000);
//		output.putInt(0x18, 1000000);
//		output.putInt(0x1C, 1000000);
//		output.putInt(0x20, 1000000);
//		output.putInt(0x24, 1000000);
//		output.putInt(0x28, 1000000);
//		output.putInt(0x2C, 1000000);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
