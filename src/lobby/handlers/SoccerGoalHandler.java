package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import net.objects.Room;
import tools.ExtendedByteBuffer;

public class SoccerGoalHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x28;
	
	protected int goal;
	
	boolean isUpdateData = true;
	
	public SoccerGoalHandler(LobbyServer lobbyServer, UserSession userSession) {
		super(lobbyServer, userSession);
	}
	
	public SoccerGoalHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
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
		lobbyServer.sendRoomMessage(userSession, getResponse(), true);
		
		Room room = lobbyServer.getRoom(userSession.getUser().roomIndex);
		
		if (room.blueScore == 3 || room.redScore == 3) {
			room.isStarted = false;
			int[] results = new int[8];
			
			int winningTeam = (room.blueScore == 3) ? 10 : 20; 
			
			for (int i = 0; i < 8; i++) {
				results[i] = -1;
				
				if (room.getUserSession(i) != null) {
					results[i] = (room.getUserSession(i).getUser().roomTeam == winningTeam) ? 1 : 2;
				}
			}
			
			lobbyServer.sendRoomMessage(userSession, new GameCompletedHandler(lobbyServer, userSession).getResponse(results, -1), true);
		}
	}

	@Override
	public byte[] getResponse() {
		int result = 3; // 3 and 5 = reset timer. 4 = nothing??
		if (goal == 0) {
			result = 2;
		}
		else if (goal == 1) {
			result = 1;
		}
		
		return getResponse(lobbyServer.getRoom(userSession.getUser().roomIndex), result);
	}

	public byte[] getResponse(Room room, int result) {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.SOCCER_GOAL_RESPONSE);
		output.putInt(0x14, result);
		output.putInt(0x18, room.blueScore);
		output.putInt(0x1C, room.redScore);
		output.putInt(0x20, (int)(System.currentTimeMillis() - lobbyServer.gameServer.serverStartTime));
		output.putInt(0x24, 0);
		
		return output.toArray();
	}

	@Override
	public void processMessage() {
		if (goal == 0) {
			lobbyServer.getRoom(userSession.getUser().roomIndex).redScore++;
		}
		else if (goal == 1) {
			lobbyServer.getRoom(userSession.getUser().roomIndex).blueScore++;
		}
	}
}
