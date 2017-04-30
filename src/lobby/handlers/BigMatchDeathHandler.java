package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

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

public class BigMatchDeathHandler extends LobbyHandler {
	public static final int REQUEST_ID = Messages.BIG_MATCH_DEATH_REQUEST;
	public static final int RESPONSE_ID = Messages.BIG_MATCH_DEATH_RESPONSE;
	public static final int RESPONSE_LENGTH = 0xA4;
	
	protected int killedSlot;
	protected int killerSlot;
	protected int[] damageDone;
	protected int[] slots;
	
	
	protected int[] experienceGained;
	protected int[] luckyMultiplier;
	protected long[] experiences;
	protected int elementType;
	protected int elementAmount;
	protected int elementMultiplier;
	int npcMultiplier;
	static int sum = 0;
	
	public BigMatchDeathHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		// 1C and 24 are used together somehow
		input.getInt(0x14); // not used i think
		killedSlot = input.getInt(0x18); // 0~7 for players. 8+ for npc
		input.getInt(0x1C); // ?
		killerSlot = input.getInt(0x20);
		input.getInt(0x24); // ?
		damageDone = input.getInts(0x28, 3);
		slots = input.getInts(0x34, 3);
		
//		System.out.println("damage: " + Arrays.toString(damageDone));
//		System.out.println("slots: " + Arrays.toString(slots));
//		
		System.out.println("0x14: " + input.getInt(0x14));
		System.out.println("0x1C: " + input.getInt(0x1C));
		System.out.println("0x24: " + input.getInt(0x24));
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);

		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, RESPONSE_ID);
		
		output.putInt(0x14, killedSlot);
		output.putInt(0x18, killerSlot);
		output.putInts(0x1C, luckyMultiplier);
		output.putInts(0x2C, experienceGained);
		output.putInts(0x3C, slots);
		output.putInts(0x4C, ExperienceHelper.getLevels(experiences));
		output.putInt(0x6C, 0); // something with slots maybe.
		
		System.out.println("Death time: " + sum + " " + (int)(Math.floor(sum * 0.2)));
		
		if (elementAmount != 0) {
			output.putInt(0x8C, elementType);
			output.putInt(0x90, elementAmount);
			output.putInt(0x94, elementMultiplier);
		}
		
		output.putInt(0x98, npcMultiplier); // npc points multiplier (not code/experience) (in big match survival). you get more points for killing those
		// -1 - everyone is "shining". 0 - death timer works. 1 - death timer works+screen shakes. i think this is time.
		output.putInt(0x9C, 0); // if this is 0, then the death time percentage can change. -1 = 100% blue. having 100% gives the entire team crit
		output.putInt(0xA0, 0);
		
		return output.toArray();
	}
	
	// 4/5% for 0, 1/5% for 3, 1/25% for 5, 1/625% for 10
	public static int getNpcMultiplier() {
		int random = (int) Math.ceil(Math.log(Math.random()) / Math.log(0.2f)) - 1;
		
		if (random > 3) {
			random = 3;
		}
		
		int[] values = {0, 3, 5, 10};
		
		return values[random];
	}

	@Override
	public void afterSend() throws IOException {
		lobbyServer.sendRoomMessage(userSession, getResponse(), false);
		
		Room room = lobbyServer.getRoom(userSession.getUser().getRoomIndex());
		
		if (room.getGameMode() == GameMode.BIG_MATCH_SURVIVAL || room.getGameMode() == GameMode.BIG_MATCH_AUTO_TEAM) {
			if (killedSlot < 8) {
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
			}
		}
		
		else if (room.getGameMode() == GameMode.BIG_MATCH_DEATH_MATCH) {
			if (room.isAllTeamDeadWithNpc()) {
				int[] results = new int[8];
				
				int j;
				for (j = 0; j < 40; j++) {
					if (room.isAlive[j])
						break;
				}
				
				int winningTeam = 0;
				
				if (j < 8) {
					winningTeam = room.getUserSession(j).getUser().getRoomTeam();
				}
				else {
					winningTeam = (j >= 24) ? 20 : 10;
				}
				
				for (int i = 0; i < 8; i++) {
					results[i] = -1;
					
					if (room.getUserSession(i) != null) {
						results[i] = 0;
					}
				}
				
				lobbyServer.sendRoomMessage(userSession, new GameCompletedHandler(lobbyServer, userSession).getResponse(results, winningTeam), true);
			}
		}
	}

	@Override
	public void processMessage() throws SQLException {
		Room room = lobbyServer.getRoom(userSession.getUser().getRoomIndex());
		if (killedSlot < 8) {
			userSession.getUser().setLives(userSession.getUser().getLives() - 1);
			room.getUserSession(killedSlot).getUser().setPlayerDowns(room.getUserSession(killedSlot).getUser().getPlayerDowns() - 1);
		}
		if (killerSlot < 8) {
			room.getUserSession(killerSlot).getUser().setGameKO(room.getUserSession(killerSlot).getUser().getGameKO() + 1);
			room.getUserSession(killerSlot).getUser().setPlayerKOs(room.getUserSession(killerSlot).getUser().getPlayerKOs() + 1);
		}
		
		lobbyServer.getRoom(userSession.getUser().getRoomIndex()).isAlive[killedSlot] = false;
		
		int randomLucky = ExperienceHelper.getLuckyMultiplier();
		experienceGained = ExperienceHelper.getExperience(damageDone);
		
		
		for (int i = 0; i < 3; i++) {
			experienceGained[i] = 0; // remove
			if (room.npcMultipliers[killedSlot] > 0) {
				experienceGained[i] *= room.npcMultipliers[killedSlot];
			}
		}
		
		experienceGained[0] = 10;
		slots[0] = 0;
		randomLucky = 1;
		luckyMultiplier = new int[3];
		for (int i = 0; i < 3; i++) {
			luckyMultiplier[i] = randomLucky;
		}
		
		// Update guild points
		GuildScore.updateGuildPoints(lobbyServer, room, slots, experienceGained, luckyMultiplier);
		
		experiences = new long[8];
		
		for (int i = 0; i < slots.length; i++) {
			if (slots[i] < 8 && room.getUserSession(slots[i]) != null) {
				room.getUserSession(slots[i]).getUser().setPlayerExperience(room.getUserSession(slots[i]).getUser().getPlayerExperience() + experienceGained[i] * luckyMultiplier[i]);
				room.getUserSession(slots[i]).getUser().setPlayerCode(room.getUserSession(slots[i]).getUser().getPlayerCode() + experienceGained[i] * luckyMultiplier[i]);
				room.getUserSession(slots[i]).getUser().setPlayerLevel(ExperienceHelper.getLevel(room.getUserSession(slots[i]).getUser().getPlayerExperience()));
				User.saveUser(room.getUserSession(slots[i]).getUser());
			}
		}
		
		for (int i = 0; i < 8; i++) {
			if (room.getUserSession(i) != null) {
				experiences[i] = room.getUserSession(i).getUser().getPlayerExperience();
			}
		}
		
		elementType = (int)(Math.random() * 4) + 1;
		elementAmount = ExperienceHelper.getElementCount(true);
		elementMultiplier = ExperienceHelper.getLuckyMultiplier();
		
//		npcMultiplier = BigMatchDeathHandler.getNpcMultiplier();
		npcMultiplier = 0;
		if (killedSlot < 8) {
			room.npcMultipliers[killedSlot] = npcMultiplier;
		}
		
		for (int i = 0; i < 3; i++) {
			sum += experienceGained[i];
		}
	}

}
