package lobby.handlers;

import java.io.IOException;

import lobby.LobbyServer;
import net.GenericHandler;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class MissionCompletedHandler extends GenericHandler {
	public static final int REQUEST_ID = 0x4456;
	public static final int RESPONSE_ID = 0x4457;
	public static final int RESPONSE_LENGTH = 0x118;
	
	int progression;
	LobbyServer lobby;
	
	public MissionCompletedHandler(LobbyServer lobby, UserTCPSession userSession, byte[] messageBytes) {
		super(userSession, messageBytes);
		this.lobby = lobby;
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
		output.putInt(0x4, RESPONSE_ID);
		
		int result;
		
		if (progression == 1) {
			result = 2; // wait
		}
		else if (userSession.getUser().missionLevel < 300) {
			result = 3; // next mission
			System.out.println("here");
			userSession.getUser().missionLevel++;
		}
		else {
			result = 4; // mission level 300 = leave the game
		}
		
		output.putInt(0x14, result); // 2, 3, 4
		output.putInt(0x18, userSession.getUser().missionLevel);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
		lobby.roomMessage(userSession.getUser().roomIndex, getResponse());
	}

}
