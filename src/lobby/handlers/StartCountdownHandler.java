package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.ExperienceHelper;
import net.Messages;
import net.UserSession;
import net.objects.Room;
import tools.ExtendedByteBuffer;

public class StartCountdownHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x118;
	
	public StartCountdownHandler(LobbyServer lobbyServer, UserSession userSession) {
		super(lobbyServer, userSession);
	}
	
	public StartCountdownHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		input.getInt(0x14);
		input.getInt(0x18);
		input.getInt(0x1C);
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.MISSION_START_COUNTDOWN_RESPONSE);
		Room room = lobbyServer.getRoom(userSession.getUser().roomIndex);
		
		output.putInt(0x14, 0);
		output.putInt(0x18, 0);
		output.putInt(0x1C, 600000);
		
		if (room.roomStartTime != 0) {
			output.putInt(0x20, (int) (System.currentTimeMillis() - room.roomStartTime));
		}
		else {
			System.out.println("in");
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(600000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						System.out.println("in start countdown");
						InfinityPointsHandler iph = new InfinityPointsHandler(lobbyServer, userSession);
						
						int multiplier = 1;
						int[] elements = new int[8];
						int[] levels = new int[8];
						
						for (int i = 0; i < 8; i++) {
							UserSession currentUserSession = room.getUserSession(i);
							
							if (currentUserSession != null) {
								elements[i] = (int) (Math.random() * 4) + 1;
								levels[i] = ExperienceHelper.getLevel(currentUserSession.getUser().playerExperience + 8000 * multiplier);
							}
						}
						
						int[] slots = new int[8];
						
						for (int i = 0; i < 8; i++) {
							slots[i] = i;
						}
						
						int minimumIndex;
						for (int i = 0; i < 8; i++) {
							minimumIndex = -1;
							if (room.getUserSession(i) != null) {
								minimumIndex = i;
								for (int j = i; j < 8; j++) {
									if (room.getUserSession(j) != null && room.infinityPoints[minimumIndex] > room.infinityPoints[j]) {
										minimumIndex = j;
									}
								}
								
								int temp = room.infinityPoints[minimumIndex];
								room.infinityPoints[minimumIndex] = room.infinityPoints[i];
								room.infinityPoints[i] = temp;
								
								temp = slots[minimumIndex];
								slots[minimumIndex] = slots[i];
								slots[i] = temp;
							}
						}
						
						System.out.println(Arrays.toString(slots));
						int[] coins = new int[8];
						int k = 0;
						for (int i = 0; i < 8; i++) {
							if (room.getUserSession(slots[i]) != null) {
								coins[slots[i]] = 3 + k++;
							}
						}
						
						// If we have 8 players, the first one gets 11, not 10.
						if (k == 8) {
							coins[slots[7]] = 11;
						}
						
						System.out.println(Arrays.toString(coins));
						
						for (int i = 0; i < 8; i++) {
							UserSession currentUserSession = room.getUserSession(i);
							
							if (currentUserSession != null) {
								
								currentUserSession.sendMessage(iph.getResponse(currentUserSession.getUser(), multiplier, levels, elements, coins));
							}
						}
						
						iph.afterSend();
					} catch (IOException | SQLException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
		
		return output.toArray();
	}
	
	@Override
	public void afterSend() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processMessage() {
		// TODO Auto-generated method stub
		
	}

}
