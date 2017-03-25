package net.objects;

import log.Log;
import net.UserTCPSession;

public class Room {
	protected int roomID;
	protected String roomName;
	protected GameMode gameType;
	protected int gameMap;
	protected int maxNumberOfPlayers;
	protected byte isWithScrolls;
	protected byte isWithTeams;
	protected int cardsLimit;
	protected byte isLimitAnger;
	protected int[] characters;
	
	protected int numberOfUsers;
	protected UserTCPSession[] users = new UserTCPSession[8];

	// soccer
	public int blueGoals;
	public int redGoals;
	public boolean isStart;
	public boolean isRoomCreatedMessageSent = false;
	
	public Room(int roomID, String roomName, int gameType, int gameMap, int numberOfPlayers, byte isWithScrolls,
			byte isWithTeams, int cardsLimit, byte isLimitAnger, int[] characters) {
		this.roomID = roomID;
		this.roomName = roomName;
		this.gameType = GameMode.getMode(gameType);
		this.gameMap = gameMap;
		this.maxNumberOfPlayers = numberOfPlayers;
		this.isWithScrolls = isWithScrolls;
		this.isWithTeams = isWithTeams;
		this.cardsLimit = cardsLimit;
		this.isLimitAnger = isLimitAnger;
		
		if (characters == null) {
			characters = new int[10];
			characters[0] = 10;
		}
		else {
			this.characters = characters;
		}
	}
	
	public void setUserSession(int index, UserTCPSession user) {
		this.users[index] = user;
	}
	
	public UserTCPSession getUser(int index) {
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

	public GameMode getGameType() {
		return gameType;
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

	public byte getIsWithScrolls() {
		return isWithScrolls;
	}

	public void setIsWithScrolls(byte isWithScrolls) {
		this.isWithScrolls = isWithScrolls;
	}

	public byte getIsWithTeams() {
		return isWithTeams;
	}

	public void setIsWithTeams(byte isWithTeams) {
		this.isWithTeams = isWithTeams;
	}

	public int getCardsLimit() {
		return cardsLimit;
	}

	public void setCardsLimit(int cardsLimit) {
		this.cardsLimit = cardsLimit;
	}

	public byte getIsLimitAnger() {
		return isLimitAnger;
	}

	public void setIsLimitAnger(byte isLimitAnger) {
		this.isLimitAnger = isLimitAnger;
	}

	public int[] getCharacters() {
		return characters;
	}

	public void setCharacters(int[] characters) {
		this.characters = characters;
	}
	
	public void setCharacter(int index, int character) {
		this.characters[index] = character;
	}
	
	public UserTCPSession[] getUsers() {
		return users;
	}
	
	public int getNumberOfUsers() {
		return numberOfUsers;
	}

	public void setNumberOfUsers(int numberOfUsers) {
		this.numberOfUsers = numberOfUsers;
	}

	public boolean shouldStart(int slot) {
		// If you need to wait for everyone before you start the game
		if (isWaitForAll()) {
			boolean isStart = true;
			
			// Go through each of the users
			for (int i = 0; i < 8; i++) {
				UserTCPSession currentSession = users[i];
				
				if (currentSession != null) {
					// If someone is not ready, don't start
					if (currentSession.getUser().roomReady != 1)
						isStart = false;
				}
			}
			
			return isStart;
		}
		
		// Otherwise, check if the user's ready
		return users[slot].getUser().roomReady == 1;
	}
	
	private boolean isWaitForAll() {
		switch (gameType) {
		case SURVIVAL:
			return false;
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
			return true;
		case SOCCER:
			return true;
		case DODGE:
			return true;
		case HOKEY:
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
			return 0;
		}
		else if (getTeamType() == 1) {
			// need to change. if everybody moves to the red team, this should return blue.
			return (numberOfUsers % 2 == 0) ? 10 : 10;
		}
		else if (getTeamType() == 2) {
			return 10;
		}
		
		Log.error("Team type is -1.");
		return -1;
	}
	
	private int getTeamType() {
		// 0 = all vs all
		// 1 = red vs blue
		// 2 = only blue maybe
		switch (gameType) {
		case SURVIVAL:
			return 0;
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
			return 1;
		case SOCCER:
			return 1;
		case DODGE:
			return 1;
		case HOKEY:
			return 1;
		}
		
		return 0;
	}
}
