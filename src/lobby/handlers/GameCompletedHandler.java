package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import net.objects.Room;
import tools.ExtendedByteBuffer;

public class GameCompletedHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x1A0;
	
	public GameCompletedHandler(LobbyServer lobbyServer, UserSession userSession) {
		super(lobbyServer, userSession);
	}

	@Override
	public void interpretBytes() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processMessage() {
		// TODO Auto-generated method stub
		
	}

	public byte[] getResponse(int[] results, int scoringTeam) {
		Room room = lobbyServer.getRoom(userSession.getUser().getRoomIndex());
		
		for (int i = 0; i < 8; i++) {
			if (room.getUserSession(i) != null) {
				room.isAlive[i] = true;
			}
			else {
				room.isAlive[i] = false;
			}
		}
		
		for (int i = 8; i < 40; i++) {
			room.isAlive[i] = true;
		}
		
		
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.GAME_COMPLETED_RESPONSE);
		
		// result array. -1 = do nothing. 0 = next round. 1 = win. 2 = lose. 3 = draw. others = leaves the game with no message
		output.putInts(0x14, results);
		output.putInt(0x34, userSession.getUser().getGameKO()); // ko
		output.putInt(0x38, 0);
		output.putInt(0x3C, 0);
		output.putLong(0x40, 1234); // new experience
		output.putLong(0x48, 4321); // new code
		output.putLong(0x50, 125); // new avatar money
		output.putInt(0x58, 400); // exp array
		output.putInt(0x78, 300); // code array
		output.putInt(0x98, 4); // element type array
		output.putInt(0xB8, 5); // element count array
		output.putInt(0xD8, 3); // element multiplier array
		output.putInt(0xF8, scoringTeam); // winning team
		
		 // this array cannot have zeroes or there will be a division by 0.
		// This is the lucky bonuses
		int[] unknowns = new int[8];
		for (int i = 0; i < 8; i++)
			unknowns[i] = 5;
		
		output.putInts(0xFC, unknowns);
		output.putInt(0x11C, 0);
		output.putInt(0x13C, 0);
		output.putInt(0x15C, 0);
		
		for (int i = 0; i < 8; i++) {
			output.putInt(0x11C + i * 4, 0);
			output.putInt(0x13C + i * 4, 0);
			output.putInt(0x15C + i * 4, 0);
		}
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
		lobbyServer.sendRoomMessage(userSession, getResponse(), false);
	}

	@Override
	public byte[] getResponse() {
		// TODO Auto-generated method stub
		return null;
	}

}
