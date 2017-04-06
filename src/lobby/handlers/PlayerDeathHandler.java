package lobby.handlers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

import database.DatabaseConnection;
import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.ExperienceHelper;
import net.Messages;
import net.UserTCPSession;
import net.objects.GameMode;
import net.objects.Room;
import tools.ExtendedByteBuffer;

public class PlayerDeathHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x118;
	
	protected int killerIndex;
	protected int[] damageDone;
	
	int[] luckyMultiplier;
	int[] experienceGained;
	long[] experiences;
	int elementType;
	int elementAmount;
	int elementMultiplier;
	
	public PlayerDeathHandler(LobbyServer lobbyServer, UserTCPSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		input.getInt(0x14); // killed index
		killerIndex = input.getInt(0x18);
		damageDone = input.getInts(0x1C, 8);
		
		System.out.println("Damage: " + Arrays.toString(damageDone));
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.PLAYER_DEATH_RESPONSE);
		output.putInt(0x14, userSession.getUser().roomSlot); // killed slot
		output.putInt(0x18, killerIndex); // killer slot
		
		output.putInts(0x1C, luckyMultiplier);
		output.putInts(0x3C, experienceGained);
		output.putInts(0x5C, ExperienceHelper.getLevels(experiences));
		output.putInt(0x7C, 0); // another array 0x20
		
		if (elementAmount != 0) {
			output.putInt(0x9C, elementType);
			output.putInt(0xA0, elementAmount);
			output.putInt(0xA4, elementMultiplier);
		}
		
		output.putInt(0xA8, 0); // can resurrect
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
		Room room = lobbyServer.getRoom(userSession.getUser().roomIndex);
		
		lobbyServer.sendRoomMessage(userSession, getResponse(), false);
		
		if (room.getGameMode() == GameMode.SURVIVAL || room.getGameMode() == GameMode.KING_SURVIVAL) {
			int[] results = new int[8];
			
			for (int i = 0; i < 8; i++) {
				results[i] = -1;
			}
			
			if (userSession.getUser().lives > 0) {
				results[userSession.getUser().roomSlot] = 0;
			}
			else {
				if (userSession.getUser().gameKO > 0) {
					results[userSession.getUser().roomSlot] = 1;
					userSession.getUser().playerWins++;
				}
				else {
					results[userSession.getUser().roomSlot] = 2;
					userSession.getUser().playerLoses++;
				}
			}
			
			lobbyServer.sendRoomMessage(userSession, new RoundCompletedHandler(lobbyServer, userSession).getResponse(results, -1), true);
			
			if (room.getGameMode() == GameMode.KING_SURVIVAL && userSession.getUser().roomSlot == room.kingSlot) {
				room.kingSlot = killerIndex;
				lobbyServer.sendRoomMessage(userSession, new NewKingHandler(lobbyServer, userSession).getResponse(), true);
			}
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
					
					if (room.getUserSession(i) != null) {
						results[i] = 0;
						
						if (room.blueScore == 3 || room.redScore == 3) {
							results[i] = (room.getUserSession(i).getUser().roomTeam == score) ? 1 : 2;
							
							if (results[i] == 1) {
								room.getUserSession(i).getUser().playerWins++;
							}
							else {
								room.getUserSession(i).getUser().playerLoses++;
							}
						}
					}
				}
				
				lobbyServer.sendRoomMessage(userSession, new RoundCompletedHandler(lobbyServer, userSession).getResponse(results, score), true);
			}
		}
		else if (room.getGameMode() == GameMode.LUCKY_3 || room.getGameMode() == GameMode.MAGIC_LUCKY_3) {
			int[] results = new int[8];
			
			for (int i = 0; i < 8; i++) {
				results[i] = -1;
			}
			
			if (room.getUserSession(killerIndex).getUser().gameKO >= 3) {
				for (int i = 0; i < 8; i++) {
					if (room.getUserSession(i) != null) {
						if (i != killerIndex) {
							results[i] = 2;
							room.getUserSession(i).getUser().playerLoses++;
						}
					}
				}
				
				room.getUserSession(killerIndex).getUser().playerWins++;
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
		else if (room.isQuestType()) {
			if (room.isAllTeamDead()) {
				int[] results = new int[8];
				
				for (int i = 0; i < 8; i++) {
					results[i] = -1;
					
					if (room.getUserSession(i) != null) {
						results[i] = 2;
						room.getUserSession(i).getUser().playerLoses++;
					}
				}
				
				lobbyServer.sendRoomMessage(userSession, new CrystalDeathHandler(lobbyServer, userSession).getResponse(results), true);
			}
		}
	}

	@Override
	public void processMessage() throws SQLException {
		userSession.getUser().lives--;
		userSession.getUser().playerDowns++;
		
		Room room = lobbyServer.getRoom(userSession.getUser().roomIndex);
		
		int randomLucky = ExperienceHelper.getLuckyMultiplier();
		experienceGained = ExperienceHelper.getExperience(damageDone);
		
		luckyMultiplier = new int[8];
		for (int i = 0; i < 8; i++) {
			luckyMultiplier[i] = randomLucky;
		}
		
		experiences = new long[8];
		
		for (int i = 0; i < 8; i++) {
			if (room.getUserSession(i) != null) {
				room.getUserSession(i).getUser().playerExperience += experienceGained[i] * luckyMultiplier[i];
				room.getUserSession(i).getUser().playerCode += experienceGained[i] * luckyMultiplier[i];
				room.getUserSession(i).getUser().playerLevel = ExperienceHelper.getLevel(room.getUserSession(i).getUser().playerExperience);
				room.getUserSession(i).getUser().saveUser();
				experiences[i] = room.getUserSession(i).getUser().playerExperience;
			}
		}
		
		elementType = (int)(Math.random() * 4) + 1;
		elementAmount = ExperienceHelper.getElementCount();
		elementMultiplier = ExperienceHelper.getLuckyMultiplier();
		
		// not sure if this is needed.
		if (killerIndex < 8) {
			room.getUserSession(killerIndex).getUser().gameKO++;
			room.getUserSession(killerIndex).getUser().playerKOs++;
			
			Connection con = DatabaseConnection.getConnection();
			PreparedStatement ps = con.prepareStatement("UPDATE users SET koCount=? WHERE username=?");
			ps.setInt(1, room.getUserSession(killerIndex).getUser().playerKOs);
			ps.setString(2, room.getUserSession(killerIndex).getUser().username);
			ps.executeUpdate();
			ps.close();
			
			ps = con.prepareStatement("UPDATE users SET downCount=? WHERE username=?");
			ps.setInt(1, userSession.getUser().playerDowns);
			ps.setString(2, userSession.getUser().username);
			ps.executeUpdate();
			ps.close();
			
			con.close();
		}
		
		lobbyServer.getRoom(userSession.getUser().roomIndex).isAlive[userSession.getUser().roomSlot] = false;
	}

}
