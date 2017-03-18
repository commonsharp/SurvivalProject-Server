package net;

public class User {
	public byte[] versionHash; // 36 bytes
	public int versionCode;
	public String username = "barak";
	public String password;
	
	public int userType = 30;
	public int activeCharacter = 20;
	public int playerLevel = 30;
	public int usuableCharacterCount = 12;
	public int isMuted;
	public int daysToMute;
	public int ageRestriction = 1;
	public long playerExperience = 1000;
	public long playerMoney = 2000;
	public String guildName = "Obamas";
	public String guildDuty = "MasterLOL";
	public long unknown1; // probably cash (premium money)
}
