package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserTCPSession;
import net.objects.GameMode;
import net.objects.Room;
import tools.ExtendedByteBuffer;

public class PlayerDeathHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x118;
	
	protected int killerIndex;
	
	public PlayerDeathHandler(LobbyServer lobbyServer, UserTCPSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		killerIndex = input.getInt(0x18);
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.PLAYER_DEATH_RESPONSE);
		output.putInt(0x14, userSession.getUser().roomSlot); // killed slot
		output.putInt(0x18, killerIndex); // killer slot
		output.putInt(0x1C, 0); // code array possibly
		output.putInt(0x3C, 6); // exp array
		output.putInt(0x5C, 0);
		output.putInt(0x60, 0);
		output.putInt(0x64, 0);
		output.putInt(0x68, 0);
		output.putInt(0x6C, 0);
		output.putInt(0x70, 0);
		output.putInt(0x74, 0);
		output.putInt(0x78, 0);
		output.putInt(0x7C, 0); // another array 0x20
		output.putInt(0x9C, 2); // elements type
		output.putInt(0xA0, 5); // elements amount
		output.putInt(0xA4, 4); // elements multiplier
		output.putInt(0xA8, 0); // can resurrect
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
		Room room = lobbyServer.getRoom(userSession.getUser().roomIndex);
		
		lobbyServer.sendRoomMessage(userSession, getResponse(), false);
		
		if (room.getGameMode() == GameMode.SURVIVAL) {
			int[] results = new int[8];
			
			for (int i = 0; i < 8; i++) {
				results[i] = -1;
			}
			
			if (userSession.getUser().lives > 0) {
				results[userSession.getUser().roomSlot] = 0;
			}
			else {
				results[userSession.getUser().roomSlot] = 3;
			}
			
			lobbyServer.sendRoomMessage(userSession, new RoundCompletedHandler(lobbyServer, userSession).getResponse(results, -1), true);
		}
		else if (room.getGameMode() == GameMode.TEAMPLAY || room.getGameMode() == GameMode.DODGE || room.getGameMode() == GameMode.DUEL ||
				room.getGameMode() == GameMode.ASSAULT) {
			if (room.isAllTeamDead()) {
				int score = (userSession.getUser().roomTeam == 10) ? 20 : 10;
				
				if (score == 10) {
					room.blueScore++;
				}
				else {
					room.redScore++;
				}
				
				int[] results = new int[8];
				
				for (int i = 0; i < 8; i++) {
					results[i] = -1;
					
					if (room.getUser(i) != null) {
						results[i] = 0;
						
						if (room.blueScore == 3 || room.redScore == 3) {
							results[i] = (room.getUser(i).getUser().roomTeam == score) ? 1 : 2;
						}
					}
				}
				
				lobbyServer.sendRoomMessage(userSession, new RoundCompletedHandler(lobbyServer, userSession).getResponse(results, score), true);
			}
		}
		else if (room.getGameMode() == GameMode.LUCKY_3 || room.getGameMode() == GameMode.MAGIC_LUCKY_3) {
			int[] results = new int[8];
			
			room.getUser(killerIndex).getUser().gameKO++;
			for (int i = 0; i < 8; i++) {
				results[i] = -1;
			}
			
			if (room.getUser(killerIndex).getUser().gameKO >= 3) {
				for (int i = 0; i < 8; i++) {
					if (room.getUser(i) != null) {
						results[i] = 2;
					}
				}
				
				results[killerIndex] = 1;
			}
			else {
				results[userSession.getUser().roomSlot] = 0;
			}
			
			lobbyServer.sendRoomMessage(userSession, new RoundCompletedHandler(lobbyServer, userSession).getResponse(results, -1), true);
		}
		else if (room.getGameMode() == GameMode.SYMBOL) {
			int[] results = new int[8];
			
			for (int i = 0; i < 8; i++) {
				results[i] = -1;
			}
			
			results[userSession.getUser().roomSlot] = 0;
			
			lobbyServer.sendRoomMessage(userSession, new RoundCompletedHandler(lobbyServer, userSession).getResponse(results, -1), true);
		}
	}

	@Override
	public void processMessage() {
		userSession.getUser().isAlive = false;
		userSession.getUser().lives--;
	}

}
