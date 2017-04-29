package lobby.handlers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.Database;
import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.ExperienceHelper;
import net.Messages;
import net.UserSession;
import net.objects.GameMode;
import net.objects.Room;
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
		
		Room room = lobbyServer.getRoom(userSession.getUser().roomIndex);
		
		if (room.getGameMode() == GameMode.BIG_MATCH_SURVIVAL || room.getGameMode() == GameMode.BIG_MATCH_AUTO_TEAM) {
			if (killedSlot < 8) {
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
					winningTeam = room.getUserSession(j).getUser().roomTeam;
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
		Room room = lobbyServer.getRoom(userSession.getUser().roomIndex);
		if (killedSlot < 8) {
			userSession.getUser().lives--;
			room.getUserSession(killedSlot).getUser().playerDowns--;
		}
		if (killerSlot < 8) {
			room.getUserSession(killerSlot).getUser().gameKO++;
			room.getUserSession(killerSlot).getUser().playerKOs++;
		}
		
		lobbyServer.getRoom(userSession.getUser().roomIndex).isAlive[killedSlot] = false;
		
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
		Connection con = Database.getConnection();
		PreparedStatement ps = con.prepareStatement("UPDATE guild_score SET guild_score = guild_score + ? WHERE server_hostname = ? AND server_port = ? AND guild_name = ?;");
		
//		int guildPoints;
//		for (int i = 0; i < slots.length; i++) {
//			if (slots[i] < 8 && room.getUserSession(slots[i]) != null) {
//				guildPoints = ExperienceHelper.experienceToGuildPoints(experienceGained[i] * luckyMultiplier[i], room.getGameMode());
//				
//				if (guildPoints != 0) {
//					ps.setInt(1, guildPoints);
//					ps.setString(2, lobbyServer.hostname);
//					ps.setInt(3, lobbyServer.port);
//					ps.setString(4, room.getUserSession(slots[i]).getUser().guildName);
//					ps.executeUpdate();
//				}
//			}
//		}
		
		ps.close();
		con.close();
		
		experiences = new long[8];
		
		for (int i = 0; i < slots.length; i++) {
			if (slots[i] < 8 && room.getUserSession(slots[i]) != null) {
				room.getUserSession(slots[i]).getUser().playerExperience += experienceGained[i] * luckyMultiplier[i];
				room.getUserSession(slots[i]).getUser().playerCode += experienceGained[i] * luckyMultiplier[i];
				room.getUserSession(slots[i]).getUser().playerLevel = ExperienceHelper.getLevel(room.getUserSession(slots[i]).getUser().playerExperience);
				room.getUserSession(slots[i]).getUser().saveUser();
			}
		}
		
		for (int i = 0; i < 8; i++) {
			if (room.getUserSession(i) != null) {
				experiences[i] = room.getUserSession(i).getUser().playerExperience;
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
