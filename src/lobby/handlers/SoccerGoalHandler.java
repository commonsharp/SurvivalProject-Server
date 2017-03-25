package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class SoccerGoalHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x28;
	
	protected int goal;
	
	boolean isUpdateData = true;
	
	public SoccerGoalHandler(LobbyServer lobbyServer, UserTCPSession userSession) {
		super(lobbyServer, userSession);
	}
	
	public SoccerGoalHandler(LobbyServer lobbyServer, UserTCPSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
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
//		lobby.roomMessage(userSession, userSession.getUser().roomIndex, getResponse(3));
//		sendTCPMessage(getResponse(3));
		
		lobbyServer.sendRoomMessage(userSession, getResponse(), true);
		
		if (lobbyServer.getRoom(userSession.getUser().roomIndex).blueGoals == 3 || lobbyServer.getRoom(userSession.getUser().roomIndex).redGoals == 3) {
			lobbyServer.sendRoomMessage(userSession, new ResultsHandler(lobbyServer, userSession).getResponse(), true);
		}
	}

	@Override
	public byte[] getResponse() {
		if (isUpdateData) {
			if (goal == 0) {
				lobbyServer.getRoom(userSession.getUser().roomIndex).redGoals++;
			}
			else if (goal == 1) {
				lobbyServer.getRoom(userSession.getUser().roomIndex).blueGoals++;
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
		
		return getResponse(userSession.getUser().roomIndex, result);
	}

	public byte[] getResponse(int roomID, int result) {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.SOCCER_GOAL_RESPONSE);
		output.putInt(0x14, result);
		output.putInt(0x18, lobbyServer.getRoom(roomID).blueGoals);
		output.putInt(0x1C, lobbyServer.getRoom(roomID).redGoals);
		output.putInt(0x20, (int)(System.currentTimeMillis() / 1000));
		output.putInt(0x24, (int)(System.currentTimeMillis() / 1000));
		
		return output.toArray();
	}

	@Override
	public void processMessage() {
		// TODO Auto-generated method stub
		
	}
}
