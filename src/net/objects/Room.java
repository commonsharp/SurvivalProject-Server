package net.objects;

import java.util.LinkedList;

import log.Log;
import net.UserSession;

public class Room {
	protected int roomID;
	protected String roomName;
	protected GameMode gameMode;
	protected int gameMap;
	protected int maxNumberOfPlayers;
	protected boolean isWithScrolls;
	protected boolean isWithAutoTeams;
	protected int cardsLimit;
	protected boolean isLimitAnger;
	
	protected int numberOfUsers;
	public UserSession[] users = new UserSession[8];

	public int blueScore;
	public int redScore;
	public boolean isStarted;
	public boolean isRoomCreatedMessageSent = false;
	
	public int[] symbols;
	public boolean[] isAlive;
	
	public int bluePlayersCount;
	public int redPlayersCount;
	
	public String roomCreator;
	
	// multipliers in big match survival
	public int[] npcMultipliers;
	
	public byte[] questInfo;
	
	public LinkedList<Integer> fightClubOrder;
	public int totalTicks;
	public int missionLevel;
	
	public int masterIndex;
	public int blueKingIndex;
	public int redKingIndex;
	public int coinLocation;
	
	public String password;
	public int heroSlot;
	public int numberOfCrystals;
	
	public int[] infinityPoints;
	public long roomStartTime;
	public int roomTimerTime;
	
	public Room(int roomID, String roomName, String password, String roomCreator, int gameMode, int gameMap, int numberOfPlayers, boolean isWithScrolls,
			boolean isWithTeams, int cardsLimit, boolean isLimitAnger) {
		this.roomID = roomID;
		this.roomName = roomName;
		this.password = password;
		this.roomCreator = roomCreator;
		this.gameMode = GameMode.getMode(gameMode);
		this.gameMap = gameMap;
		this.maxNumberOfPlayers = numberOfPlayers;
		this.isWithScrolls = isWithScrolls;
		this.isWithAutoTeams = isWithTeams;
		this.cardsLimit = cardsLimit;
		this.isLimitAnger = isLimitAnger;
		this.blueKingIndex = -1;
		this.redKingIndex = -1;
		this.totalTicks = 0;
		this.masterIndex = 0;
		this.coinLocation = 0;
		this.infinityPoints = new int[8];
		this.roomTimerTime = 8 * 60 * 1000;
		
		for (int i = 0; i < 8; i++) {
			infinityPoints[i] = 1;
		}
		
		symbols = new int[4];
		isAlive= new boolean[40];
		npcMultipliers = new int[40];
		fightClubOrder = new LinkedList<Integer>();
	}
	
//	public int getMostPointsPlayer(int team) {
//		int maxIndex = 0;
//		int max = -1;
//		
//		for (int i = 0; i < 8; i++) {
//			if (users[i] != null && users[i].getUser().roomTeam == team) {
//				if (max <= )
//			}
//		}
//	}
	
	public void setUserSession(int index, UserSession user) {
		this.users[index] = user;
	}
	
	public UserSession getUserSession(int index) {
		return users[index];
	}

	public int getRoomID() {
		return roomID;
	}

	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public GameMode getGameMode() {
		return gameMode;
	}

	public void setGameMode(GameMode gameMode) {
		this.gameMode = gameMode;
	}

	public int getGameMap() {
		return gameMap;
	}

	public void setGameMap(int gameMap) {
		this.gameMap = gameMap;
	}

	public int getMaxNumberOfPlayers() {
		return maxNumberOfPlayers;
	}

	public void setMaxNumberOfPlayers(int maxNumberOfPlayers) {
		this.maxNumberOfPlayers = maxNumberOfPlayers;
	}

	public boolean isWithScrolls() {
		return isWithScrolls;
	}

	public void setWithScrolls(boolean isWithScrolls) {
		this.isWithScrolls = isWithScrolls;
	}

	public boolean isWithAutoTeams() {
		return isWithAutoTeams;
	}

	public void setWithAutoTeams(boolean isWithAutoTeams) {
		this.isWithAutoTeams = isWithAutoTeams;
	}

	public int getCardsLimit() {
		return cardsLimit;
	}

	public void setCardsLimit(int cardsLimit) {
		this.cardsLimit = cardsLimit;
	}

	public boolean isLimitAnger() {
		return isLimitAnger;
	}

	public void setLimitAnger(boolean isLimitAnger) {
		this.isLimitAnger = isLimitAnger;
	}

	public int[] getCharacters() {
//		return characters;
		
		int[] characters = new int[8];
		for (int i = 0; i < 8; i++) {
			if (users[i] != null) {
				if (users[i].getUser().roomTeam == 10) {
					characters[getEmpty(characters, 0)] = users[i].getUser().roomCharacter;
				}
				else {
					characters[getEmpty(characters, 1)] = users[i].getUser().roomCharacter;
				}
			}
		}
		
		return characters;
	}
	private int getEmpty(int[] characters, int offset) {
		for (int i = offset; i < characters.length; i += 2) {
			if (characters[i] == 0) {
				return i;
			}
		}
		
		return -1;
	}

	public UserSession[] getUsers() {
		return users;
	}
	
	public int getNumberOfPlayers() {
		return numberOfUsers;
	}

	public void setNumberOfUsers(int numberOfUsers) {
		this.numberOfUsers = numberOfUsers;
	}

	public boolean shouldStart(int slot) {
		// If it's a training mode, then the game should instantly start
		if (isTrainingType()) {
			return true;
		}
		
		// If you need to wait for everyone before you start the game
		if (isWaitForAll()) {
			boolean isStart = true;
			
			// Go through each of the users
			for (int i = 0; i < 8; i++) {
				UserSession currentSession = users[i];
				
				if (currentSession != null) {
					// If someone is not ready, don't start
					if (currentSession.getUser().roomReady != 1)
						isStart = false;
				}
			}
			
			// Hero mode
			if (getTeamType() == 3) {
				if (bluePlayersCount <= 1 && redPlayersCount <= 1) {
					isStart = false;
				}
				else if (bluePlayersCount > 1 && redPlayersCount > 1) {
					isStart = false;
				}
			}
			
			if (!isStarted && getTeamType() == 1 && bluePlayersCount != redPlayersCount) {
				isStart = false;
			}
			
			return isStart;
		}
		
		// Otherwise, check if the user's ready
		return users[slot].getUser().roomReady == 1;
	}
	
	private boolean isWaitForAll() {
		switch (gameMode) {
		case CHAMP_ASSAULT:
			return true;
		case KING_SLAYER:
			return true;
		case FIGHT_CLUB:
			return false;
		case COMMUNITY:
			return false;
		case HERO:
			return true;
		case INFINITY_SURVIVAL:
			return false;
		case INFINITY_KING:
			return false;
		case INFINITY_SYMBOL:
			return false;
		case DUEL:
			return true;
		case ASSAULT:
			return true;
		case LUCKY_3:
		case MAGIC_LUCKY_3:
			return true;
		case TEAMPLAY:
			return true;
		case SURVIVAL:
			return false;
		case CRYSTAL:
			return true;
		case KING_SURVIVAL:
			return false;
		case BIG_MATCH_DEATH_MATCH:
			return false;
		case BIG_MATCH_AUTO_TEAM:
			return false;
		case SYMBOL:
			return true;
		case DUNGEON_QUEST_1:
		case DUNGEON_QUEST_2:
		case DUNGEON_QUEST_3:
		case DUNGEON_QUEST_4:
		case DUNGEON_QUEST_5:
		case FLAME_QUEST_1:
		case FLAME_QUEST_2:
		case FLAME_QUEST_3:
		case FLAME_QUEST_4:
		case FLAME_QUEST_5:
		case FOREST_QUEST_1:
		case FOREST_QUEST_2:
		case FOREST_QUEST_3:
		case FOREST_QUEST_4:
		case FOREST_QUEST_5:
		case MISSION:
			return false;
		case SOCCER:
		case RACE:
		case DODGE:
		case HOKEY:
		case MOLE:
			return true;
		}
		
		Log.error("No wait for all case");
		return false;
	}

	public int getSlot() {
		//TODO check if the room is full
		for (int i = 0; i < 8; i++) {
			if (users[i] == null) {
				numberOfUsers++;
				return i;
			}
		}
		
		// room is full or something
		return -1;
	}
	
	public int getTeam() {
		int teamType = getTeamType();
		
		if (teamType == 0) {
			bluePlayersCount++;
			return 0;
		}
		else if (getTeamType() == 1) {
			if (bluePlayersCount > redPlayersCount) {
				redPlayersCount++;
				System.out.println("here");
				return 20;
			}
			else {
				bluePlayersCount++;
				return 10;
			}
		}
		else if (getTeamType() == 2) {
			bluePlayersCount++;
			return 10;
		}
		else if (teamType == 3) {
			if (bluePlayersCount == 0 && redPlayersCount == 0) {
				bluePlayersCount++;
				return 10;
			}
			else {
				redPlayersCount++;
				return 20;
			}
		}
		
		Log.error("Team type is -1.");
		return -1;
	}
	
	private int getTeamType() {
		// 0 = all vs all
		// 1 = red vs blue
		// 2 = only blue team (in quests)
		// 3 = hero mode
		switch (gameMode) {
		case CHAMP_ASSAULT:
			return 1;
		case KING_SLAYER:
			return 1;
		case FIGHT_CLUB:
			return 0;
		case COMMUNITY:
			return 0;
		case HERO:
			return 3;
		case INFINITY_SURVIVAL:
			return 0;
		case INFINITY_KING:
			return 1;
		case INFINITY_SYMBOL:
			return 1;
		case DUEL:
			return 1;
		case ASSAULT:
			return 1;
		case SYMBOL:
			return 1;
		case LUCKY_3:
		case MAGIC_LUCKY_3:
			return 0;
		case TEAMPLAY:
			return 1;
		case SURVIVAL:
			return 0;
		case KING_SURVIVAL:
			return 0;
		case CRYSTAL:
			return 1;
		case BIG_MATCH_DEATH_MATCH:
			return 1;
		case BIG_MATCH_AUTO_TEAM:
			return 1;
		case DUNGEON_QUEST_1:
		case DUNGEON_QUEST_2:
		case DUNGEON_QUEST_3:
		case DUNGEON_QUEST_4:
		case DUNGEON_QUEST_5:
		case FLAME_QUEST_1:
		case FLAME_QUEST_2:
		case FLAME_QUEST_3:
		case FLAME_QUEST_4:
		case FLAME_QUEST_5:
		case FOREST_QUEST_1:
		case FOREST_QUEST_2:
		case FOREST_QUEST_3:
		case FOREST_QUEST_4:
		case FOREST_QUEST_5:
		case MISSION:
			return 2;
		case SOCCER:
		case RACE:
		case DODGE:
		case HOKEY:
		case MOLE:
			return 1;
		}
		
		return 0;
	}
	
	public boolean isQuestType() {
		int type = gameMode.getValue();
		return type >= 11 && type <= 14 || type >= 17 && type <= 27;
	}
	
	public boolean isTrainingType() {
		int type = gameMode.getValue();
		
		return type >= 42 && type <= 46;
	}

	public boolean isAllTeamDead() {
		boolean isRedDead = true, isBlueDead = true;
		
		if (redPlayersCount == 0) {
			isRedDead = false;
		}
		if (bluePlayersCount == 0) {
			isBlueDead = false;
		}
		
		for (int i = 0; i < 8; i++) {
			// If the player is alive or if he's dead but has an extra life in quest modes, then the team isn't dead
			if (getUserSession(i) != null && (isAlive[i] || ((isQuestType() || getGameMode() == GameMode.MISSION) && getUserSession(i).getUser().gameExtraLife))) {
				if (getUserSession(i).getUser().roomTeam == 10) {
					isBlueDead = false;
				}
				else {
					isRedDead = false;
				}
			}
		}
		
		return isRedDead || isBlueDead;
	}
	
	public boolean isAllTeamDeadWithNpc() {
		boolean isRedDead = true, isBlueDead = true;
		
		for (int i = 0; i < 40; i++) {
			if (isAlive[i]) {
				if (i >= 24 || (i < 8 && getUserSession(i) != null && getUserSession(i).getUser().roomTeam == 20)) {
					isRedDead = false;
				}
				else {
					isBlueDead = false;
				}
			}
		}
		
		return isRedDead || isBlueDead;
	}
	
	public int getAnyPlayerSlot() {
		for (int i = 0; i < 8; i++) {
			if (users[i] != null) {
				return i;
			}
		}
		
		return -1;
	}
}
