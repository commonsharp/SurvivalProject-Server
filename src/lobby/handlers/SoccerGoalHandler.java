package lobby.handlers;

import java.io.IOException;

import lobby.LobbyServer;
import net.GenericHandler;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class SoccerGoalHandler extends GenericHandler {
	public static final int REQUEST_ID = 0x4344;
	public static final int RESPONSE_ID = 0x4345;
	public static final int RESPONSE_LENGTH = 0x28;
	
	protected LobbyServer lobby;
	
	protected int goal;
	
	boolean isUpdateData = true;
	
	public SoccerGoalHandler(LobbyServer lobby, UserTCPSession userSession) {
		super(userSession);
		this.lobby = lobby;
	}
	
	public SoccerGoalHandler(LobbyServer lobby, UserTCPSession userSession, byte[] messageBytes) {
		super(userSession, messageBytes);
		this.lobby = lobby;
	}

	@Override
	public void interpretBytes() {
		goal = input.getByte(0x14); // goal type. 0 - blue. 1 - red
		System.out.println(goal);
		System.out.println(input.getInt(0x18)); // maybe x value
		System.out.println(input.getInt(0x1C)); // maybe y value
		System.out.println(input.getInt(0x20)); // v
		System.out.println(input.getInt(0x24)); // those 2 might be speed or angle or something
	}

	@Override
	public void afterSend() throws IOException {
		// test
		lobby.roomMessage(userSession, userSession.getUser().roomIndex, getResponse(3));
		sendTCPMessage(getResponse(3));
		
		lobby.roomMessage(userSession, userSession.getUser().roomIndex, getResponse());
		
		if (lobby.getRoom(userSession.getUser().roomIndex).blueGoals == 3 || lobby.getRoom(userSession.getUser().roomIndex).redGoals == 3) {
			sendTCPMessage(new ResultsHandler(userSession).getResponse());
			lobby.roomMessage(userSession, userSession.getUser().roomIndex, new ResultsHandler(userSession).getResponse());
		}
	}

	@Override
	public byte[] getResponse() {
		if (isUpdateData) {
			if (goal == 0) {
				lobby.getRoom(userSession.getUser().roomIndex).redGoals++;
			}
			else if (goal == 1) {
				lobby.getRoom(userSession.getUser().roomIndex).blueGoals++;
			}
			
			isUpdateData = false;
		}
		
		int result = 3; // 3 and 5 = reset timer. 4 = nothing??
		if (goal == 0) {
			result = 2;
		}
		else if (goal == 1) {
			result = 1;
		}
		
		return getResponse(result);
	}

	public byte[] getResponse(int result) {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, RESPONSE_ID);
		output.putInt(0x14, result);
		output.putInt(0x18, lobby.getRoom(userSession.getUser().roomIndex).blueGoals);
		output.putInt(0x1C, lobby.getRoom(userSession.getUser().roomIndex).redGoals);
		output.putInt(0x20, (int)(System.currentTimeMillis() / 1000));
		output.putInt(0x24, (int)(System.currentTimeMillis() / 1000));
		
		return output.toArray();
	}
}
