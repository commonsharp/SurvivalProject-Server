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
	
    byte playerCardItemExist[] = {1, 1, 1, 1}; // if it's 0, this item is not going to be available (must be set to 1...)
	
	int playerCardItemId[] = {1111, 1122, 1133, 1144};
	int playerCardItemDays[] = {10, 1, 1, 1};
	int playerCardItemLevelIdx[] = {8, 8, 7, 8, 5};
	int playerCardItemSkill[] = {36100300, 0, 33200000, 0};
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
		super(0x4302);
	}
	
	@Override
	public void changeData() {
	}

	// 0x979 length
	@Override
	public void addPayload() {
		payload.putInteger(1); // 0x14
		payload.putString(guildName, 12); // 0x18
		payload.putString(guildTitle, 12); // 0x25
		payload.putByte((byte) 0); // 0x32
		payload.putByte((byte) 0); // 0x33 - not used 
		payload.putInteger(playerWins); // 0x34
		payload.putInteger(playerLoses); // 0x38
		payload.putInteger(0); // 0x3c
		payload.putInteger(0); // 0x40
		payload.putInteger(playerKOs); // 0x44
		payload.putInteger(playerDowns); // 0x48
		payload.putInteger(0); // 0x4c - not used
		payload.putInteger(0); // 0x50
		payload.putInteger(0); // 0x54
		payload.putInteger(0); // 0x58 - not used
		payload.putInteger(0); // 0x5c
		payload.putLong(playerPoint); // 0x60
		payload.putLong(playerCode); // 0x68
		payload.putLong(0); // 0x70
		payload.putInteger(playerLevel); // 0x78
		payload.putInteger(12); // 0x7c
		payload.putInteger(scrolls[0]); // 0x80
		payload.putInteger(scrolls[1]); // 0x84
		payload.putInteger(scrolls[2]); // 0x88
		
		/* white cards */
		payload.putInteger(whiteCards[0]); // 0x8c
		payload.putInteger(whiteCards[1]); // 0x90
		payload.putInteger(whiteCards[2]); // 0x94
		payload.putInteger(whiteCards[3]); // 0x98

		payload.putInteger(channelFlag); // 0x9c
		payload.putBytes(playerCardItemExist); // 0xA0
		payload.putBytes(new byte[0x1DC]); // 0xA4
		
		payload.putIntArray(playerCardItemId); // 0x280
		payload.putBytes(new byte[0x170]); // 0x290
		payload.putIntArray(playerCardItemDays); // 0x400
		payload.putBytes(new byte[0x170]); // 0x410
		payload.putIntArray(playerCardItemLevelIdx); // 0x580
		payload.putBytes(new byte[0x170]); // 0x590
		payload.putIntArray(playerCardItemSkill); // 0x700
		payload.putBytes(new byte[0x170]); // 0x710
		payload.putInteger(playerInventorySlots); // 0x880
		payload.putIntArray(minPointForeveDword); // 0x884
		payload.putLongArray(minPointForLevelQword); //0x8F8
		payload.putInteger(playerChannelType); // 0x918
		payload.putInteger(0); // 0x91c
		payload.putInteger(0); // 0x920
		payload.putInteger(0); // 0x924
		payload.putInteger(1); // 0x928
		payload.putInteger(1); // 0x92C // 1~4
		payload.putInteger(0); // 0x930
		payload.putInteger(0); // 0x934
		payload.putInteger(0); // 0x938
		payload.putBytes(playerEventFlags); // 0x93C
		payload.putBytes(new byte[1]); // 0x943
		payload.putInteger(0); // 0x944
		payload.putInteger(0); // 0x948 - not used
		payload.putInteger(lobbyMaxRooms); // 0x94c
		payload.putIntArray(playerAvatarEquipIdx); // 0x950
		payload.putInteger(0); // 0x96C
		payload.putInteger(0); // 0x970 = field_A0 in Login
		payload.putInteger(playerType); // 0x974
		payload.putByte((byte) 0); // 0x978
	}
}
