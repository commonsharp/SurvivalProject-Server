package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class Test4483Handler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x18;
	
	public Test4483Handler(LobbyServer lobbyServer, UserSession userSession) {
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
		output.putInt(0x4, Messages.MESSAGE_4483);
		
		// translated with google translate...
		output.putInt(0x14, 2); // 1 - "The kill mark has been moved". 2 -s "The kill mark is only available for kills"
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
