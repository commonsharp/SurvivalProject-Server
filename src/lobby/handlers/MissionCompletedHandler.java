package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import net.objects.Room;
import tools.ExtendedByteBuffer;

public class MissionCompletedHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x1C;
	
	int progression;
	
	public MissionCompletedHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
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
		else if (userSession.getUser().missionLevel % 300 != 0) {
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
		lobbyServer.sendBroadcastMessage(userSession, new LobbyRoomsChangedHandler(lobbyServer, userSession).getResponse(lobbyServer.getRoom(userSession.getUser().roomIndex)));
	}

	@Override
	public void processMessage() throws SQLException, IOException {
		Room room = lobbyServer.getRoom(userSession.getUser().roomIndex);
		if (progression == 1) {
			int[] results = new int[8];
			
			for (int i = 0; i < 8; i++) {
				results[i] = -1;
				
				if (room.getUserSession(i) != null) {
					results[i] = 1;
				}
			}
			
			lobbyServer.sendRoomMessage(userSession, new GameCompletedHandler(lobbyServer, userSession).getResponse(results, 10), true);
			
			if (room.missionLevel == userSession.getUser().missionLevel) {
				userSession.getUser().missionLevel++;
				
				if (userSession.getUser().missionLevel % 5 == 0 && userSession.getUser().missionLevel % 10 != 0) {
					userSession.getUser().missionLevel++;
				}
			}
			
			room.missionLevel++;
			
			if (room.missionLevel % 5 == 0 && room.missionLevel % 10 != 0) {
				room.missionLevel++;
			}
			
			userSession.getUser().saveUser();
		}

		if (progression == 2) {
			room.roomStartTime = System.currentTimeMillis();
		}
//		sendTCPMessage(new GetMissionLevelHandler(lobbyServer, userSession).getResponse());
	}

}
