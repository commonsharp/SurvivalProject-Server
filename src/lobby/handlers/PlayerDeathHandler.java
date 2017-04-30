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
import net.objects.User;
import tools.ExtendedByteBuffer;

public class PlayerDeathHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0xA9;
	
	protected int killerIndex;
	protected int[] damageDone;
	
	int[] luckyMultiplier;
	int[] experienceGained;
	long[] experiences;
	int elementType;
	int elementAmount;
	int elementMultiplier;
	
	public PlayerDeathHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
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
		output.putInt(0x14, userSession.getUser().getRoomSlot()); // killed slot
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
		
		output.putBoolean(0xA8, userSession.getUser().isGameExtraLife()); // can resurrect
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		Room room = lobbyServer.getRoom(userSession.getUser().getRoomIndex());
		
		lobbyServer.sendRoomMessage(userSession, getResponse(), false);
		
		if (room.getGameMode() == GameMode.SURVIVAL || room.getGameMode() == GameMode.KING_SURVIVAL) {
			int[] results = new int[8];
			
			for (int i = 0; i < 8; i++) {
				results[i] = -1;
			}
			
			if (userSession.getUser().getLives() > 0) {
				results[userSession.getUser().getRoomSlot()] = 0;
			}
			else {
				if (userSession.getUser().getGameKO() > 0) {
					results[userSession.getUser().getRoomSlot()] = 1;
					userSession.getUser().setPlayerWins(userSession.getUser().getPlayerWins() + 1);
				}
				else {
					results[userSession.getUser().getRoomSlot()] = 2;
					userSession.getUser().setPlayerLoses(userSession.getUser().getPlayerLoses() + 1);
				}
			}
			
			lobbyServer.sendRoomMessage(userSession, new GameCompletedHandler(lobbyServer, userSession).getResponse(results, -1), true);
			
			if (room.getGameMode() == GameMode.KING_SURVIVAL && userSession.getUser().getRoomSlot() == room.blueKingIndex) {
				room.blueKingIndex = killerIndex;
				lobbyServer.sendRoomMessage(userSession, new NewKingHandler(lobbyServer, userSession).getResponse(), true);
			}
		}
		else if (room.getGameMode() == GameMode.TEAMPLAY || room.getGameMode() == GameMode.DODGE || room.getGameMode() == GameMode.DUEL ||
				room.getGameMode() == GameMode.ASSAULT || room.getGameMode() == GameMode.HERO) {
			if (room.isAllTeamDead()) {
				int score = (userSession.getUser().getRoomTeam() == 10) ? 20 : 10;
				
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
							results[i] = (room.getUserSession(i).getUser().getRoomTeam() == score) ? 1 : 2;
							
							if (results[i] == 1) {
								room.getUserSession(i).getUser().setPlayerWins(room.getUserSession(i).getUser().getPlayerWins() + 1);
							}
							else {
								room.getUserSession(i).getUser().setPlayerLoses(room.getUserSession(i).getUser().getPlayerLoses() + 1);
							}
						}
					}
				}
				
				lobbyServer.sendRoomMessage(userSession, new GameCompletedHandler(lobbyServer, userSession).getResponse(results, score), true);
			}
		}
		else if (room.getGameMode() == GameMode.LUCKY_3 || room.getGameMode() == GameMode.MAGIC_LUCKY_3) {
			int[] results = new int[8];
			
			for (int i = 0; i < 8; i++) {
				results[i] = -1;
			}
			
			if (room.getUserSession(killerIndex).getUser().getGameKO() >= 3) {
				for (int i = 0; i < 8; i++) {
					if (room.getUserSession(i) != null) {
						if (i != killerIndex) {
							results[i] = 2;
							room.getUserSession(i).getUser().setPlayerLoses(room.getUserSession(i).getUser().getPlayerLoses() + 1);
						}
					}
				}
				
				room.getUserSession(killerIndex).getUser().setPlayerWins(room.getUserSession(killerIndex).getUser().getPlayerWins() + 1);
				results[killerIndex] = 1;
			}
			else {
				results[userSession.getUser().getRoomSlot()] = 0;
			}
			
			lobbyServer.sendRoomMessage(userSession, new GameCompletedHandler(lobbyServer, userSession).getResponse(results, -1), true);
		}
		else if (room.getGameMode() == GameMode.SYMBOL || room.getGameMode() == GameMode.CRYSTAL) {
//			int[] results = new int[8];
//			
//			for (int i = 0; i < 8; i++) {
//				results[i] = -1;
//			}
//			
//			results[userSession.getUser().roomSlot] = 0;
			
			sendTCPMessage(new RoundCompletedHandler(lobbyServer, userSession).getResponse(0));
//			lobbyServer.sendRoomMessage(userSession, new RoundCompletedHandler(lobbyServer, userSession).getResponse(), true);
//			lobbyServer.sendRoomMessage(userSession, new GameCompletedHandler(lobbyServer, userSession).getResponse(results, -1), true);
		}
		else if (room.isQuestType() || room.getGameMode() == GameMode.MISSION) {
			if (room.isAllTeamDead()) {
				int[] results = new int[8];
				
				for (int i = 0; i < 8; i++) {
					results[i] = -1;
					
					if (room.getUserSession(i) != null) {
						results[i] = 2;
						room.getUserSession(i).getUser().setPlayerLoses(room.getUserSession(i).getUser().getPlayerLoses() + 1);
					}
				}
				
				lobbyServer.sendRoomMessage(userSession, new CrystalDeathHandler(lobbyServer, userSession).getResponse(results), true);
			}
			
			userSession.getUser().setGameExtraLife(false);
		}
		else if (room.getGameMode() == GameMode.FIGHT_CLUB) {
			int result;
			
			if (userSession.getUser().getGameKO() > 0) {
				result = 1;
				userSession.getUser().setPlayerWins(userSession.getUser().getPlayerWins() + 1);
			}
			else {
				result = 2;
				userSession.getUser().setPlayerLoses(userSession.getUser().getPlayerLoses() + 1);
			}
			
			sendTCPMessage(new RoundCompletedHandler(lobbyServer, userSession).getResponse(result));
			
			// it doesn't work...?
//			lobbyServer.sendRoomMessage(userSession, new SpawnHandler(lobbyServer, userSession).getResponse2(), true);
		}
		// mission training
		else if (room.getGameMode() == GameMode.TRAINING_5) {
			int result;
			
			if (userSession.getUser().getGameKO() > 0) {
				result = 1;
				userSession.getUser().setPlayerWins(userSession.getUser().getPlayerWins() + 1);
			}
			else {
				result = 2;
				userSession.getUser().setPlayerLoses(userSession.getUser().getPlayerLoses() + 1);
			}
			
			sendTCPMessage(new RoundCompletedHandler(lobbyServer, userSession).getResponse(result));
		}
		else if (room.getGameMode() == GameMode.INFINITY_KING) {
			int[] oldPoints = Arrays.copyOf(room.infinityPoints, room.infinityPoints.length);
			
			if (userSession.getUser().getRoomSlot() == room.blueKingIndex || userSession.getUser().getRoomSlot() == room.redKingIndex) {
				int team = (userSession.getUser().getRoomSlot() == room.blueKingIndex) ? 10 : 20;
				int otherTeamNumberOfPlayers = team == 10 ? room.bluePlayersCount : room.redPlayersCount;
				int splitPoints = (int) Math.ceil(room.infinityPoints[userSession.getUser().getRoomSlot()] / (float) otherTeamNumberOfPlayers);
				room.infinityPoints[userSession.getUser().getRoomSlot()] = 1;
				
				for (int i = 0; i < 8; i++) {
					UserSession currentUserSession = room.getUserSession(i);
					if (currentUserSession != null) {
						if (currentUserSession.getUser().getRoomTeam() != team) {
							room.infinityPoints[i] += splitPoints;
						}
					}
				}
			}
			else {
				room.infinityPoints[userSession.getUser().getRoomSlot()]++;
			}
			
			int[] difference = new int[8];
			for (int i = 0; i < 8; i++) {
				difference[i] = room.infinityPoints[i] - oldPoints[i];
			}
			
			lobbyServer.sendRoomMessage(userSession, new InfinityKingPointsHandler(lobbyServer, userSession).getResponse(difference, 4, 0), true);
		}
	}

	@Override
	public void processMessage() throws SQLException {
		userSession.getUser().setLives(userSession.getUser().getLives() - 1);
		userSession.getUser().setPlayerDowns(userSession.getUser().getPlayerDowns() + 1);
		
		Room room = lobbyServer.getRoom(userSession.getUser().getRoomIndex());
		
		int randomLucky = ExperienceHelper.getLuckyMultiplier();
		experienceGained = ExperienceHelper.getExperience(damageDone);
		
		experienceGained[0] = 100;
		randomLucky = 1;
		
		luckyMultiplier = new int[8];
		for (int i = 0; i < 8; i++) {
			luckyMultiplier[i] = randomLucky;
		}
		
		experiences = new long[8];
		
		for (int i = 0; i < 8; i++) {
			if (room.getUserSession(i) != null) {
				room.getUserSession(i).getUser().setPlayerExperience(room.getUserSession(i).getUser().getPlayerExperience() + experienceGained[i] * luckyMultiplier[i]);
				room.getUserSession(i).getUser().setPlayerCode(room.getUserSession(i).getUser().getPlayerCode() + experienceGained[i] * luckyMultiplier[i]);
				room.getUserSession(i).getUser().setPlayerLevel(ExperienceHelper.getLevel(room.getUserSession(i).getUser().getPlayerExperience()));
				User.saveUser(room.getUserSession(i).getUser());
				experiences[i] = room.getUserSession(i).getUser().getPlayerExperience();
			}
		}
		
		// Update guild points
		GuildScore.updateGuildPoints(lobbyServer, room, experienceGained, luckyMultiplier);
				
		elementType = (int)(Math.random() * 4) + 1;
		elementAmount = ExperienceHelper.getElementCount(true);
		elementMultiplier = ExperienceHelper.getLuckyMultiplier();
		
		// not sure if this is needed.
		if (killerIndex < 8) {
			room.getUserSession(killerIndex).getUser().setGameKO(room.getUserSession(killerIndex).getUser().getGameKO() + 1);
			room.getUserSession(killerIndex).getUser().setPlayerKOs(room.getUserSession(killerIndex).getUser().getPlayerKOs() + 1);
			
			User.saveUser(room.getUserSession(killerIndex).getUser());
			User.saveUser(userSession.getUser());
		}
		
		lobbyServer.getRoom(userSession.getUser().getRoomIndex()).isAlive[userSession.getUser().getRoomSlot()] = false;
	}

}
