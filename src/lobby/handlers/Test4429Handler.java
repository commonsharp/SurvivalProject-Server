package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class Test4429Handler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x20;
	
	public Test4429Handler(LobbyServer lobbyServer, UserSession userSession) {
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
		output.putInt(0x4, 0x4429);
		/*
		 * 1 - you won the lottery event.
		 * 2 - lottery event has ended
		 * 3 - secondary event has ended?
		 * 4 - you can purchase 4 times per day
		 * 5 - not enough cash
		 * 6 - please try again in a few minutes
		 */
		
		int response = 1;
		
		output.putInt(0x14, response);
		output.putInt(0x18, 1); // element type
		output.putInt(0x1C, 5); // element count
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
