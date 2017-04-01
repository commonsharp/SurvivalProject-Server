package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class Test4477Handler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x228;
	
	public Test4477Handler(LobbyServer lobbyServer, UserTCPSession userSession) {
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
		output.putInt(0x4, 0x4477);
		output.putInt(0x14, -1); // setting this to 0 make all monsters disappear
		
		// 33 monsters
		for (int i = 0; i < 33; i++) {
			output.putInt(0x18 + 4 * i, 7);
			output.putInt(0x9C + 4 * i, 0);
			output.putInt(0x120 + 4 * i, 0);
			output.putInt(0x1A4 + 4 * i, 0);
		}
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
