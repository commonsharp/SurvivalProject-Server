package net;

public class Room {
	protected int roomID;
	protected String roomName;
	protected int gameType;
	protected int gameMap;
	protected int maxNumberOfPlayers;
	protected byte isWithScrolls;
	protected byte isWithTeams;
	protected int cardsLimit;
	protected byte isLimitAnger;
	protected int[] characters;
	
	protected UserTCPSession[] users = new UserTCPSession[8];
	
	public Room(int roomID, String roomName, int gameType, int gameMap, int numberOfPlayers, byte isWithScrolls,
			byte isWithTeams, int cardsLimit, byte isLimitAnger, int[] characters) {
		this.roomID = roomID;
		this.roomName = roomName;
		this.gameType = gameType;
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

	public int getGameType() {
		return gameType;
	}

	public void setGameType(int gameType) {
		this.gameType = gameType;
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

	public int getSlot() {
		//TODO check if the room is full
		for (int i = 0; i < 8; i++) {
			if (users[i] == null) {
				return i;
			}
		}
		
		// room is full or something
		return -1;
	}

	public UserTCPSession[] getUsers() {
		return users;
	}
}
