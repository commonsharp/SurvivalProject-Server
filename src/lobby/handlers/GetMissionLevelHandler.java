package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class GetMissionLevelHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x1C;

	public GetMissionLevelHandler(LobbyServer lobbyServer, UserSession userSession) {
		super(lobbyServer, userSession);
	}

	@Override
	public void interpretBytes() {
		
	}

	@Override
	public void processMessage() throws SQLException {
		
	}

	@Override
	public byte[] getResponse() throws SQLException {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.GET_MISSION_LEVEL_RESPONSE);
		output.putInt(0x14, userSession.getUser().missionLevel);
		output.putInt(0x18, 0); // ?
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		
	}
}
