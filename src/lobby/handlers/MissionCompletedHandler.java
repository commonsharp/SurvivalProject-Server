package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class MissionCompletedHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x1C;
	
	int progression;
	
	public MissionCompletedHandler(LobbyServer lobbyServer, UserTCPSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		// 1 if you pass the gate. 2 if you moved through the portal
		progression = input.getInt(0x14);
		System.out.println("Progression : " + progression);
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.MISSION_COMPLETED_RESPONSE);
		
		int result;
		
		if (progression == 1) {
			result = 1; // wait
		}
		else if (userSession.getUser().missionLevel < 300) {
			result = 3; // next mission
		}
		else {
			result = 4; // mission level 300 = leave the game
		}
		
		output.putInt(0x14, result); // 2, 3, 4
		output.putInt(0x18, userSession.getUser().missionLevel);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		lobbyServer.sendRoomMessage(userSession, getResponse(), false);
		sendTCPMessage(new GetMissionLevelHandler(lobbyServer, userSession).getResponse());
	}

	@Override
	public void processMessage() throws SQLException {
		if (progression == 2 && userSession.getUser().missionLevel < 300) {
			userSession.getUser().missionLevel++;
			userSession.getUser().saveUser();
		}
	}

}
