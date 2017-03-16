package lobby.server.messages;

import net.ServerGenericMessage;

public class JoinLobbyResponse extends ServerGenericMessage {
    final int minPointForeveDword[] = {-5, 1, 50, 100,
			200, 400, 800, 1600,
			2400, 3200, 6400, 12800,
			25600, 51200, 102400, 204800,
			409600, 819200, 1638400, 3276800,
			6553600, 13107200, 26214400, 52428800,
			104857600, 209715200, 419430400, 838860800,
			1677721600};
    final long minPointForLevelQword[] = {3355443200L, 6710886400L, 13421772800L, 26843545600L};
	
    byte playerCardItemExist[] = {1, 1, 1, 1, 1}; // if it's 0, this item is not going to be available (must be set to 1...)
	
	int playerCardItemId[] = {1111, 1122, 1133, 1144, 1244};
	int playerCardItemDays[] = {10, 1, 1, 1, 10};
	int playerCardItemLevelIdx[] = {8, 8, 7, 8, 5, 6};
	int playerCardItemSkill[] = {36100300, 0, 33200000, 0, 0};
	int playerAvatarEquipIdx[] = {-1, -1, -1, -1, -1, -1, -1}; //Idx not ItemId!!!
	byte playerEventFlags[] = {0, 0, 0, 0, 0, 0, 0}; //see debug mode
	
	String guildName = "barakguild";
	String guildTitle = "whatwhat";
	long playerCode = 1234567;
	long playerPoint = 7654321;
	
	int playerChannelType = 3; //the channel type player is currently in
	
	int playerLevel = 30;
	int playerInventorySlots = 24;
	int playerType = 0; //set to 7 for GM...
	int whiteCards[] = {200, 20, 30, 40};
	int scrolls[] = {0, 0, 0};
	int playerWins = 0;
	int playerLoses = 0;
	int playerKOs = 0;
	int playerDowns = 0;
	//
	int lobbyMaxRooms = 100;
	/* calculate channel flag from playerLevel */
	int channelFlag = (playerLevel == 0) ? 0 : (playerLevel >= 17) ? 30 : (playerLevel >= 13) ? 20 : 10;
	public JoinLobbyResponse() {
		super(0x979, 0x4302);
	}
	
	@Override
	public void changeData() {
	}

	// 0x979 length
	@Override
	public void addPayload() {
		buffer.putInt(0x14, 1); // 0x14
		buffer.putString(0x18, guildName); // 0x18
		buffer.putString(0x25, guildTitle); // 0x25
		buffer.putByte(0x32, (byte) 0); // 0x32
		buffer.putByte(0x33, (byte) 0); // 0x33 - not used 
		buffer.putInt(0x34, playerWins); // 0x34
		buffer.putInt(0x38, playerLoses); // 0x38
		buffer.putInt(0x3c, 0); // 0x3c
		buffer.putInt(0x40, 0); // 0x40
		buffer.putInt(0x44, playerKOs); // 0x44
		buffer.putInt(0x48, playerDowns); // 0x48
		buffer.putInt(0x4c, 0); // 0x4c - not used
		buffer.putInt(0x50, 0); // 0x50
		buffer.putInt(0x54, 0); // 0x54
		buffer.putInt(0x58, 0); // 0x58 - not used
		buffer.putInt(0x5c, 0); // 0x5c
		buffer.putLong(0x60, playerPoint); // 0x60
		buffer.putLong(0x68, playerCode); // 0x68
		buffer.putLong(0x70, 0); // 0x70
		buffer.putInt(0x78, playerLevel); // 0x78
		buffer.putInt(0x7C, 12); // 0x7c
		buffer.putInt(0x80, scrolls[0]); // 0x80
		buffer.putInt(0x84, scrolls[1]); // 0x84
		buffer.putInt(0x88, scrolls[2]); // 0x88
		
		/* white cards */
		buffer.putInt(0x8C, whiteCards[0]); // 0x8c
		buffer.putInt(0x90, whiteCards[1]); // 0x90
		buffer.putInt(0x94, whiteCards[2]); // 0x94
		buffer.putInt(0x98, whiteCards[3]); // 0x98

		buffer.putInt(0x9C, channelFlag); // 0x9c
		buffer.putBytes(0xA0, playerCardItemExist); // 0xA0
		
		buffer.putInts(0x280, playerCardItemId); // 0x280
		buffer.putInts(0x400, playerCardItemDays); // 0x400
		buffer.putInts(0x580, playerCardItemLevelIdx); // 0x580
		buffer.putInts(0x700, playerCardItemSkill); // 0x700
		buffer.putInt(0x880, playerInventorySlots); // 0x880
		buffer.putInts(0x884, minPointForeveDword); // 0x884
		buffer.putLongs(0x8F8, minPointForLevelQword); //0x8F8
		buffer.putInt(0x918, playerChannelType); // 0x918
		buffer.putInt(0x91C, 0); // 0x91c
		buffer.putInt(0x920, 0); // 0x920
		buffer.putInt(0x924, 0); // 0x924
		buffer.putInt(0x928, 1); // 0x928
		buffer.putInt(0x92C, 1); // 0x92C // 1~4
		buffer.putInt(0x930, 0); // 0x930
		buffer.putInt(0x934, 0); // 0x934
		buffer.putInt(0x938, 0); // 0x938
		buffer.putBytes(0x93C, playerEventFlags); // 0x93C
		buffer.putBytes(0x943, new byte[1]); // 0x943
		buffer.putInt(0x944, 0); // 0x944
		buffer.putInt(0x948, 0); // 0x948 - not used
		buffer.putInt(0x94C, lobbyMaxRooms); // 0x94c
		buffer.putInts(0x950, playerAvatarEquipIdx); // 0x950
		buffer.putInt(0x96C, 0); // 0x96C
		buffer.putInt(0x970, 0); // 0x970 = field_A0 in Login
		buffer.putInt(0x974, playerType); // 0x974
		buffer.putByte(0x978, (byte) 0); // 0x978
	}
}
