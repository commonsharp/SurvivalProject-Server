package net;

public class User {
	public byte[] versionHash; // 36 bytes
	public int versionCode;
	public String username;
	public String password;
	
	public int userType = 30;
	public int activeCharacter = 20;
	public int playerLevel = 10;
	public int usuableCharacterCount = 12;
	public int isMuted;
	public int daysToMute;
	public int ageRestriction = 1;
	public long playerExperience = 1000;
	public long playerMoney = 2000;
	public String guildName = "Obamas";
	public String guildDuty = "MasterLOL";
	public long unknown1; // probably cash (premium money)
	
	// if it's 0, this item is not going to be available (must be set to 1...)
	public byte playerCardItemExist[] = {1, 1, 1, 1, 1, 1, 1, 1};
	
	public int playerCardItemId[] = {1211, 1131, 1211, 1212, 1331, 1123, 1321, 1224};
	public int playerCardItemDays[] = {10, 1, 1, 10, 10, 100, 100, 100};
	public int playerCardItemLevelIdx[] = {8, 8, 7, 8, 5, 8, 8, 8};
	public int playerCardItemSkill[] = {36100300, 0, 33200000, 0, 0, 0, 0, 0};
	public int playerAvatarEquipIdx[] = {-1, -1, -1, -1, -1, -1, -1}; //Idx not ItemId!!!
	public byte playerEventFlags[] = {0, 0, 0, 0, 0, 0, 0, 0}; //see debug mode
	
	public int playerChannelType = 3; //the channel type player is currently in
	
	public int playerInventorySlots = 24;
	public int playerType = 70; //set to 7 for GM...
	public int whiteCards[] = {200, 20, 30, 40};
	public int scrolls[] = {0, 0, 0};
	public int playerWins = 10;
	public int playerLoses = 20;
	public int playerKOs = 30;
	public int playerDowns = 40;
	
	public byte gender = 0; // 0 - male. 1 - female
	
	public int magicIndex, weaponIndex, accessoryIndex;
	
	public boolean isInRoom = false;
}
