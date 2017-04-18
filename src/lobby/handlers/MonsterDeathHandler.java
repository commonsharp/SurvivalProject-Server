package lobby.handlers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DatabaseConnection;
import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.ExperienceHelper;
import net.Messages;
import net.UserSession;
import net.objects.GameMode;
import net.objects.Room;
import tools.ExtendedByteBuffer;

public class MonsterDeathHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0xB4;
	
	int monsterIndex;
	short monsterType;
	int molePoints;
	
	int[] damageDone;
	
	public MonsterDeathHandler(LobbyServer lobbyServer, UserSession tcpServer, byte[] messageBytes) {
		super(lobbyServer, tcpServer, messageBytes);
	}

	@Override
	public void interpretBytes() {
		monsterIndex = input.getInt(0x14);
		monsterType = input.getShort(0x40);
		molePoints = input.getInt(0x44);
		System.out.println("Monster index: " + monsterIndex);
		/*
		 	14 - monster index
			18
			1C
			20~3C - damageDone
			40 - monster type
			42 - monster level
			44 - integer
		 */
		
		damageDone = new int[8];
		damageDone = input.getInts(0x20, damageDone.length);
		
		System.out.println(input.getInt(0x18));
		System.out.println(input.getInt(0x1C));
		System.out.println("type: " + input.getShort(0x40)); // type
		System.out.println(input.getShort(0x42));
	}

	@Override
	public byte[] getResponse() throws SQLException {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);

		Room room = lobbyServer.getRoom(userSession.getUser().roomIndex);
		
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.ENJOY_MODE_DEATH_RESPONSE);
		
		output.putInt(0x14, monsterIndex);
		output.putInt(0x18, userSession.getUser().roomSlot); // slot
		output.putInt(0x1C, -1); // byte
		output.putInt(0x20, -1);
		
		int randomLucky = ExperienceHelper.getLuckyMultiplier();
		int[] experienceGained = ExperienceHelper.getExperience(damageDone);
		
		experienceGained[0] = 200;
		randomLucky = 1;
		
		int[] luckyMultiplier = new int[8];
		for (int i = 0; i < 8; i++) {
			luckyMultiplier[i] = randomLucky;
		}
		
		// Update guild points
		Connection con = DatabaseConnection.getConnection();
		PreparedStatement ps = con.prepareStatement("UPDATE guild_score SET guild_score = guild_score + ? WHERE server_hostname = ? AND server_port = ? AND guild_name = ?;");
		
		int guildPoints;
		for (int i = 0; i < 8; i++) {
			guildPoints = ExperienceHelper.experienceToGuildPoints(experienceGained[i] * luckyMultiplier[i], room.getGameMode());
			
			if (guildPoints != 0) {
				ps.setInt(1, guildPoints);
				ps.setString(2, LobbyServer.HOSTNAME);
				ps.setInt(3, LobbyServer.PORT);
				ps.setString(4, room.getUserSession(i).getUser().guildName);
				ps.executeUpdate();
			}
		}
		
		ps.close();
		con.close();
		
		output.putInts(0x24, luckyMultiplier);
		output.putInts(0x44, experienceGained);
		
		long[] experiences = new long[8];
		
		for (int i = 0; i < 8; i++) {
			if (room.getUserSession(i) != null) {
				room.getUserSession(i).getUser().playerExperience += experienceGained[i] * luckyMultiplier[i];
				room.getUserSession(i).getUser().playerCode += experienceGained[i] * luckyMultiplier[i];
				room.getUserSession(i).getUser().playerLevel = ExperienceHelper.getLevel(room.getUserSession(i).getUser().playerExperience);
				experiences[i] = room.getUserSession(i).getUser().playerExperience;
			}
		}
		
		output.putInts(0x64, ExperienceHelper.getLevels(experiences));
		
//		for (int i = 0; i < 8; i++) {
//			output.putInt(0x84 + i * 4, 100);
//		}
//		output.putInt(0x84, 100); // another array. unknown yet.
		
		int elementType = (int)(Math.random() * 4) + 1;
		int elementAmount = ExperienceHelper.getElementCount(true);
		int elementMultiplier = ExperienceHelper.getLuckyMultiplier();
		
		if (elementAmount != 0) {
			userSession.getUser().whiteCards[elementType - 1] += elementAmount * elementMultiplier;
			output.putInt(0xA4, elementType);
			output.putInt(0xA8, elementAmount);
			output.putInt(0xAC, elementMultiplier);
		}
		
		output.putInt(0xB0, molePoints);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		Room room = lobbyServer.getRoom(userSession.getUser().roomIndex);
		lobbyServer.sendRoomMessage(userSession, getResponse(), false);
		
		if (room.isQuestType() || room.getGameMode() == GameMode.RACE || room.getGameMode() == GameMode.MOLE || room.getGameMode() == GameMode.MISSION) {
			if (room.getGameMode() == GameMode.RACE && monsterIndex == 33) {
				int[] results = new int[8];
				
				int winningTeam = userSession.getUser().roomTeam;
				
				for (int i = 0; i < 8; i++) {
					results[i] = -1;
					
					if (room.getUserSession(i) != null) {
						results[i] = (room.getUserSession(i).getUser().roomTeam == winningTeam) ? 1 : 2;
					}
				}
				
				lobbyServer.sendRoomMessage(userSession, new GameCompletedHandler(lobbyServer, userSession).getResponse(results, -1), true);
			}
			else if (room.getGameMode() == GameMode.MOLE) {
				int userTeam = userSession.getUser().roomTeam;
				
				if (userTeam == 10) {
					room.blueScore += molePoints;
					
					if (room.blueScore < 0) {
						room.blueScore = 0;
					}
				}
				else {
					room.redScore += molePoints;
					
					if (room.redScore < 0) {
						room.redScore = 0;
					}
				}
				
				if (room.blueScore >= 50 || room.redScore >= 50) {
					int winningTeam = (room.blueScore >= 50) ? 10 : 20;
					
					int[] results = new int[8];
					
					for (int i = 0; i < 8; i++) {
						results[i] = -1;
						
						if (room.getUserSession(i) != null) {
							results[i] = (room.getUserSession(i).getUser().roomTeam == winningTeam) ? 1 : 2;
						}
					}
					
					lobbyServer.sendRoomMessage(userSession, new GameCompletedHandler(lobbyServer, userSession).getResponse(results, -1), true);
				}
			}
		}
		else if (room.getGameMode() == GameMode.SYMBOL) {
			room.symbols[monsterIndex] = userSession.getUser().roomTeam;
			
			int firstSymbol = room.symbols[0];
			boolean isDone = true;
			
			for (int i = 0; i < 4; i++) {
				if (room.symbols[i] == 0 || room.symbols[i] != firstSymbol) {
					isDone = false;
				}
			}
			
			if (isDone) {
				int[] results = new int[8];
				
				for (int i = 0; i < 8; i++) {
					results[i] = -1;
					
					if (room.getUserSession(i) != null) {
						results[i] = (room.getUserSession(i).getUser().roomTeam == firstSymbol) ? 1 : 2;
					}
				}
				
				lobbyServer.sendRoomMessage(userSession, new GameCompletedHandler(lobbyServer, userSession).getResponse(results, -1), true);
			}
		}
	}

	@Override
	public void processMessage() {
		Room room = lobbyServer.getRoom(userSession.getUser().roomIndex);
		room.getUserSession(userSession.getUser().roomSlot).getUser().gameKO++;
	}
}
