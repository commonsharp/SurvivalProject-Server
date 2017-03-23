package net;

import java.net.InetAddress;

public class User {
	public byte[] versionHash; // 36 bytes
	public int versionCode;
	public String username;
	public String password;
	
	public int userType = 30;
	public int activeCharacter = 20;
	public int playerLevel = 1;
	public int usuableCharacterCount = 12;
	public int isMuted;
	public int daysToMute;
	public int ageRestriction = 1;
	public long playerExperience = 0;
	public long playerMoney = 200000000;
	public long avatarMoney = 1234;
	public String guildName = "Obamas";
	public String guildDuty = "MasterLOL";
	public long unknown1; // probably cash (premium money)
	
	public Item[] items;
	
	public byte playerEventFlags[] = {0, 0, 0, 0, 0, 0, 0, 0}; //see debug mode
	
	public int playerChannelType = 3; //the channel type player is currently in
	
	public int playerInventorySlots = 96;
	public int playerType = 7; //set to 7 for GM...
	public int whiteCards[] = {200000, 200000, 200000, 200000};
	public int scrolls[] = {0, 0, 0};
	public int playerWins = 10;
	public int playerLoses = 20;
	public int playerKOs = 30;
	public int playerDowns = 40;
	
	public byte gender = 0; // 0 - male. 1 - female
	
	public int magicIndex, weaponIndex, accessoryIndex, petIndex;
	public int footIndex = -1, bodyIndex = -1, hand1Index = -1, hand2Index = -1, faceIndex = -1, hairIndex = -1, headIndex = -1;
	public int missionLevel = 88;
	
	public boolean isInRoom = false;
	public boolean isInGame = false;
	public int roomIndex;
	public int roomSlot;
	public int roomTeam;
	public int roomCharacter;
	public byte roomReady;
	public int roomStart;
	public int roomFieldF4;
	
	public int udpState;
	public InetAddress udpIPAddress;
	public int udpPort;
	
	public User() {
		items = new Item[96];
		
		/*
		 * public int playerCardItemId[] = {1211, 1131, 1211, 1212, 1331, 1123, 1321, 1224, 5003, 4101, 4201, 4301, 4401, 4501, 4601, 4701};
	public int playerCardItemDays[] = {10, 1, 1, 10, 10, 100, 100, 100, 5, 0, 0, 0, 0, 0, 0, 0};
	public int playerCardItemLevelIdx[] = {8, 8, 7, 8, 5, 8, 8, 8, 5, 0, 0, 0, 0, 0, 0, 0};
	public int playerCardItemSkill[] = {36100300, 0, 33200000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		 */
		// 33200000 and 36100300
		
		// weapons
		items[0] = new Item(1111, 365, 8, 36100300);
		items[1] = new Item(1112, 365, 8, 36100300);
		items[2] = new Item(1113, 365, 8, 36100300);
		items[3] = new Item(1114, 365, 8, 36100300);
		items[4] = new Item(1121, 365, 8, 36100300);
		items[5] = new Item(1122, 365, 8, 36100300);
		items[6] = new Item(1123, 365, 8, 36100300);
		items[7] = new Item(1124, 365, 8, 36100300);
		items[8] = new Item(1131, 365, 8, 36100300);
		items[9] = new Item(1132, 365, 8, 36100300);
		items[10] = new Item(1133, 365, 8, 36100300);
		items[11] = new Item(1134, 365, 8, 36100300);
		items[12] = new Item(1141, 365, 8, 36100300);
		items[13] = new Item(1142, 365, 8, 36100300);
		items[14] = new Item(1143, 365, 8, 36100300);
		items[15] = new Item(1144, 365, 8, 36100300);
		
		// accessory
		items[16] = new Item(1211, 365, 8, 36100300);
		items[17] = new Item(1212, 365, 8, 36100300);
		items[18] = new Item(1213, 365, 8, 36100300);
		items[19] = new Item(1214, 365, 8, 36100300);
		items[20] = new Item(1221, 365, 8, 36100300);
		items[21] = new Item(1222, 365, 8, 36100300);
		items[22] = new Item(1223, 365, 8, 36100300);
		items[23] = new Item(1224, 365, 8, 36100300);
		items[24] = new Item(1231, 365, 8, 36100300);
		items[25] = new Item(1232, 365, 8, 36100300);
		items[26] = new Item(1233, 365, 8, 36100300);
		items[27] = new Item(1234, 365, 8, 36100300);
		items[28] = new Item(1241, 365, 8, 36100300);
		items[29] = new Item(1242, 365, 8, 36100300);
		items[30] = new Item(1243, 365, 8, 36100300);
		items[31] = new Item(1244, 365, 8, 36100300);
		
		// magic
		items[32] = new Item(1311, 365, 8, 36100300);
		items[33] = new Item(1312, 365, 8, 36100300);
		items[34] = new Item(1321, 365, 8, 36100300);
		items[35] = new Item(1322, 365, 8, 36100300);
		items[36] = new Item(1331, 365, 8, 36100300);
		items[37] = new Item(1332, 365, 8, 36100300);
		items[38] = new Item(1341, 365, 8, 36100300);
		items[39] = new Item(1342, 365, 8, 36100300);
		
		// avatar boots
		items[40] = new Item(4101, 0, 0, 0);
		items[41] = new Item(4102, 0, 0, 0);
		items[42] = new Item(4103, 0, 0, 0);
		
		// avatar body
		items[43] = new Item(4201, 0, 0, 0);
		items[44] = new Item(4202, 0, 0, 0);
		items[45] = new Item(4203, 0, 0, 0);
		
		// avatar hand 1
		items[46] = new Item(4301, 0, 0, 0);
		items[47] = new Item(4302, 0, 0, 0);
		items[48] = new Item(4303, 0, 0, 0);
		
		// avatar hand 2
		items[49] = new Item(4401, 0, 0, 0);
		items[50] = new Item(4402, 0, 0, 0);
		items[51] = new Item(4403, 0, 0, 0);
		
		// avatar face
		items[52] = new Item(4501, 0, 0, 0);
		items[53] = new Item(4502, 0, 0, 0);
		items[54] = new Item(4503, 0, 0, 0);
		
		// avatar hair
		items[55] = new Item(4601, 0, 0, 0);
		items[56] = new Item(4602, 0, 0, 0);
		items[57] = new Item(4603, 0, 0, 0);
		
		// avatar helmet
		items[58] = new Item(4701, 0, 0, 0);
		items[59] = new Item(4702, 0, 0, 0);
		items[60] = new Item(4703, 0, 0, 0);
		items[61] = new Item(4704, 0, 0, 0);
		items[62] = new Item(4705, 0, 0, 0);
		items[63] = new Item(4706, 0, 0, 0);
		
		// pet
		items[64] = new Item(5000, 0, 0, 0);
		items[65] = new Item(5001, 0, 0, 0);
		items[66] = new Item(5002, 0, 0, 0);
		items[67] = new Item(5003, 0, 0, 0);
		items[68] = new Item(5004, 0, 0, 0);
		items[69] = new Item(5005, 0, 0, 0);
		items[70] = new Item(5006, 0, 0, 0);
		items[71] = new Item(5007, 0, 0, 0);
		items[72] = new Item(5008, 0, 0, 0);
		items[73] = new Item(5009, 0, 0, 0);
		items[74] = new Item(5010, 0, 0, 0);
		items[75] = new Item(5011, 0, 0, 0);
		items[76] = new Item(5012, 0, 0, 0);
		items[77] = new Item(5013, 0, 0, 0);
		items[78] = new Item(5014, 0, 0, 0);
		
		items[79] = new Item(2900, 365, 0, 0);
	}
	
	public int getAvatarItemID(int index) {
		if (index == -1 || items[index] == null) {
			return -1;
		}
		
		return items[index].getId();
	}
	
	public int getItemID(int index) {
		if (index == -1 || items[index] == null) {
			return -1;
		}
		
		return items[index].getId();
	}
	
	public int getItemPremiumDays(int index) {
		if (index == -1 || items[index] == null) {
			return 0;
		}
		
		return items[index].getPremiumDays();
	}
	
	public int getItemLevel(int index) {
		if (index == -1 || items[index] == null) {
			return 0;
		}
		
		return items[index].getLevel();
	}
	
	public int getItemSkill(int index) {
		if (index == -1 || items[index] == null) {
			return 0;
		}
		
		return items[index].getSkill();
	}
}
