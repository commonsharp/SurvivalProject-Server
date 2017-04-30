package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import net.objects.Room;
import tools.ExtendedByteBuffer;

public class HokeyGoalHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x24;

	protected int goalTeam;
	
	public HokeyGoalHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		goalTeam = input.getByte(0x14); // goal. 0 = blue. 1 = red
	}

	@Override
	public void processMessage() {
		if (goalTeam == 0) {
			lobbyServer.getRoom(userSession.getUser().getRoomIndex()).blueScore++;
		}
		else if (goalTeam == 1) {
			lobbyServer.getRoom(userSession.getUser().getRoomIndex()).redScore++;
		}
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);

		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.HOKEY_GOAL_RESPONSE);
		
		output.putInt(0x14, 1); // 0, 1, 2, 3
		output.putInt(0x18, 1);
		output.putInt(0x1C, 1);
		output.putInt(0x20, 1);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
		lobbyServer.sendRoomMessage(userSession, getResponse(), false);
		
		Room room = lobbyServer.getRoom(userSession.getUser().getRoomIndex());
		
		if (room.blueScore == 3 || room.redScore == 3) {
			int[] results = new int[8];
			
			int winningTeam = (room.blueScore == 3) ? 10 : 20; 
			
			for (int i = 0; i < 8; i++) {
				results[i] = -1;
				
				if (room.getUserSession(i) != null) {
					results[i] = (room.getUserSession(i).getUser().getRoomTeam() == winningTeam) ? 1 : 2;
				}
			}
			
			lobbyServer.sendRoomMessage(userSession, new GameCompletedHandler(lobbyServer, userSession).getResponse(results, -1), true);
		}
	}

}
