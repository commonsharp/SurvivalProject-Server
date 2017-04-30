package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.ExperienceHelper;
import net.Messages;
import net.UserSession;
import net.objects.GameMode;
import net.objects.GuildScore;
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

		Room room = lobbyServer.getRoom(userSession.getUser().getRoomIndex());
		
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.ENJOY_MODE_DEATH_RESPONSE);
		
		output.putInt(0x14, monsterIndex);
		output.putInt(0x18, userSession.getUser().getRoomSlot()); // slot
		output.putInt(0x1C, -1); // byte
		output.putInt(0x20, -1);
		
		int randomLucky = ExperienceHelper.getLuckyMultiplier();
		int[] experienceGained = ExperienceHelper.getExperience(damageDone);
		
//		experienceGained[0] = 200;
//		randomLucky = 1;
		
		int[] luckyMultiplier = new int[8];
		for (int i = 0; i < 8; i++) {
			luckyMultiplier[i] = randomLucky;
		}
		
		// Update guild points
		GuildScore.updateGuildPoints(lobbyServer, room, experienceGained, luckyMultiplier);
		
		output.putInts(0x24, luckyMultiplier);
		output.putInts(0x44, experienceGained);
		
		long[] experiences = new long[8];
		
		for (int i = 0; i < 8; i++) {
			if (room.getUserSession(i) != null) {
				room.getUserSession(i).getUser().setPlayerExperience(room.getUserSession(i).getUser().getPlayerExperience() + experienceGained[i] * luckyMultiplier[i]);
				room.getUserSession(i).getUser().setPlayerCode(room.getUserSession(i).getUser().getPlayerCode() + experienceGained[i] * luckyMultiplier[i]);
				room.getUserSession(i).getUser().setPlayerLevel(ExperienceHelper.getLevel(room.getUserSession(i).getUser().getPlayerExperience()));
				experiences[i] = room.getUserSession(i).getUser().getPlayerExperience();
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
			userSession.getUser().setWhiteCard(elementType - 1, userSession.getUser().getWhiteCard(elementType - 1) + elementAmount * elementMultiplier);
			output.putInt(0xA4, elementType);
			output.putInt(0xA8, elementAmount);
			output.putInt(0xAC, elementMultiplier);
		}
		
		output.putInt(0xB0, molePoints);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		Room room = lobbyServer.getRoom(userSession.getUser().getRoomIndex());
		lobbyServer.sendRoomMessage(userSession, getResponse(), false);
		
		if (room.isQuestType() || room.getGameMode() == GameMode.RACE || room.getGameMode() == GameMode.MOLE || room.getGameMode() == GameMode.MISSION) {
			if (room.getGameMode() == GameMode.RACE && monsterIndex == 33) {
				int[] results = new int[8];
				
				int winningTeam = userSession.getUser().getRoomTeam();
				
				for (int i = 0; i < 8; i++) {
					results[i] = -1;
					
					if (room.getUserSession(i) != null) {
						results[i] = (room.getUserSession(i).getUser().getRoomTeam() == winningTeam) ? 1 : 2;
					}
				}
				
				lobbyServer.sendRoomMessage(userSession, new GameCompletedHandler(lobbyServer, userSession).getResponse(results, -1), true);
			}
			else if (room.getGameMode() == GameMode.MOLE) {
				int userTeam = userSession.getUser().getRoomTeam();
				
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
							results[i] = (room.getUserSession(i).getUser().getRoomTeam() == winningTeam) ? 1 : 2;
						}
					}
					
					lobbyServer.sendRoomMessage(userSession, new GameCompletedHandler(lobbyServer, userSession).getResponse(results, -1), true);
				}
			}
		}
		else if (room.getGameMode() == GameMode.SYMBOL) {
			room.symbols[monsterIndex] = userSession.getUser().getRoomTeam();
			
			int firstSymbol = room.symbols[0];
			boolean isDone = true;
			
			for (int i = 0; i < room.numberOfCrystals; i++) {
				if (room.symbols[i] == 0 || room.symbols[i] != firstSymbol) {
					isDone = false;
				}
			}
			
			if (isDone) {
				int[] results = new int[8];
				
				for (int i = 0; i < 8; i++) {
					results[i] = -1;
					
					if (room.getUserSession(i) != null) {
						results[i] = (room.getUserSession(i).getUser().getRoomTeam() == firstSymbol) ? 1 : 2;
					}
				}
				
				lobbyServer.sendRoomMessage(userSession, new GameCompletedHandler(lobbyServer, userSession).getResponse(results, -1), true);
			}
		}
		else if (room.getGameMode() == GameMode.CRYSTAL) {
			room.symbols[monsterIndex] = userSession.getUser().getRoomTeam();
			
			int firstSymbol = room.symbols[0];
			boolean isDone = true;
			
			for (int i = 0; i < room.numberOfCrystals; i++) {
				if (room.symbols[i] == 0 || room.symbols[i] != firstSymbol) {
					isDone = false;
				}
			}
			
			if (isDone) {
				int userTeam = room.symbols[monsterIndex];
				
				if (userTeam == 10) {
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
						
						if (room.blueScore == 4 || room.redScore == 4) {
							results[i] = (room.getUserSession(i).getUser().getRoomTeam() == userTeam) ? 1 : 2;
							
							if (results[i] == 1) {
								room.getUserSession(i).getUser().setPlayerWins(room.getUserSession(i).getUser().getPlayerWins() + 1);
							}
							else {
								room.getUserSession(i).getUser().setPlayerLoses(room.getUserSession(i).getUser().getPlayerLoses() + 1);
							}
						}
					}
				}
				
				lobbyServer.sendRoomMessage(userSession, new GameCompletedHandler(lobbyServer, userSession).getResponse(results, userTeam), true);
			}
		}
		else if (room.getGameMode() == GameMode.INFINITY_SYMBOL && monsterIndex < 4) {
			int[] oldPoints = Arrays.copyOf(room.infinityPoints, room.infinityPoints.length);
			
			UserSession currentUserSession;
			int scoringTeam = userSession.getUser().getRoomTeam();
			
			for (int i = 0; i < 8; i++) {
				currentUserSession = room.getUserSession(i);
				
				if (currentUserSession != null) {
					if (room.symbols[monsterIndex] == 0) {
						if (currentUserSession.getUser().getRoomTeam() == scoringTeam) {
							room.infinityPoints[i]++;
						}
					}
					else {
						if (currentUserSession.getUser().getRoomTeam() == scoringTeam) {
							room.infinityPoints[i]++;
						}
						else {
							room.infinityPoints[i]--;
						}
					}
				}
			}
			
			room.infinityPoints[userSession.getUser().getRoomSlot()]++;
			
			int[] difference = new int[8];
			for (int i = 0; i < 8; i++) {
				difference[i] = room.infinityPoints[i] - oldPoints[i];
			}
			
			lobbyServer.sendRoomMessage(userSession, new InfinityKingPointsHandler(lobbyServer, userSession).getResponse(difference, 2, monsterIndex), true);
			room.symbols[monsterIndex] = userSession.getUser().getRoomTeam();
		}
	}

	@Override
	public void processMessage() {
		Room room = lobbyServer.getRoom(userSession.getUser().getRoomIndex());
		room.getUserSession(userSession.getUser().getRoomSlot()).getUser().setGameKO(room.getUserSession(userSession.getUser().getRoomSlot()).getUser().getGameKO() + 1);
	}
}
