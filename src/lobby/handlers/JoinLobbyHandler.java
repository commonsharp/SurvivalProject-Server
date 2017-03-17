package lobby.handlers;

import net.GenericHandler;
import net.UserTCPSession;

public class JoinLobbyHandler extends GenericHandler {
	public static final int REQUEST_ID = 0x4301;
	public static final int RESPONSE_ID = 0x4302;
	public static final int RESPONSE_LENGTH = 0x979;
	
	protected byte[] versionHash; // 36 bytes
	protected int versionCode;
	String username; // 12 characters+0
	String password; // 12 characters+0
	
	final int minPointForeveDword[] = {-5, 1, 50, 100,
			200, 400, 800, 1600,
			2400, 3200, 6400, 12800,
			25600, 51200, 102400, 204800,
			409600, 819200, 1638400, 3276800,
			6553600, 13107200, 26214400, 52428800,
			104857600, 209715200, 419430400, 838860800,
			1677721600};
    final long minPointForLevelQword[] = {3355443200L, 6710886400L, 13421772800L, 26843545600L};
	
    // if it's 0, this item is not going to be available (must be set to 1...)
    byte playerCardItemExist[] = {1, 1, 1, 1, 1};
	
	int playerCardItemId[] = {1111, 1122, 1133, 1144, 1244};
	int playerCardItemDays[] = {10, 1, 1, 1, 10};
	int playerCardItemLevelIdx[] = {8, 8, 7, 8, 5, 6};
	int playerCardItemSkill[] = {36100300, 0, 33200000, 0, 0};
	int playerAvatarEquipIdx[] = {-1, -1, -1, -1, -1, -1, -1}; //Idx not ItemId!!!
	byte playerEventFlags[] = {0, 0, 0, 0, 0, 0, 0, 0}; //see debug mode
	
	String guildName = "barakguild";
	String guildDuty = "whatwhat";
	long playerCode = 1234567;
	long playerPoint = 7654321;
	long avatarMoney = 1234;
	
	int playerChannelType = 3; //the channel type player is currently in
	
	int playerLevel = 30;
	int playerInventorySlots = 24;
	int playerType = 0; //set to 7 for GM...
	int whiteCards[] = {200, 20, 30, 40};
	int scrolls[] = {0, 0, 0};
	int playerWins = 10;
	int playerLoses = 20;
	int playerKOs = 30;
	int playerDowns = 40;
	
	byte gender = 1; // 0 - male. 1 - female
	int usuableCharactersCount = 12;
	
	// when you log in you get a visit bonus
	int visitBonusMoney = 123456;
	int visitBonusElementsType = 2;
	int visitBonusElements = 5;
	int visitBonusElementsMultiplier = 4;
	int visitBonusAvatarMoney = 55;
	
	// 1 - highest level player in the game/server
	int playerRank = 10;
	
	int lobbyMaxRooms = 100;
	
	int response = 1;
	/* calculate channel flag from playerLevel */
	int channelFlag = (playerLevel == 0) ? 0 : (playerLevel >= 17) ? 30 : (playerLevel >= 13) ? 20 : 10;
	
	public JoinLobbyHandler(UserTCPSession tcpServer, byte[] messageBytes) {
		super(tcpServer, messageBytes, RESPONSE_LENGTH, RESPONSE_ID);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void interpretBytes() {
		// TODO ...
	}

	@Override
	public void processFields() {
		// Add to database and stuff
	}

	@Override
	public void changeData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPayload() {
		output.putInt(0x14, response); // 0x14
		output.putString(0x18, guildName); // 0x18
		output.putString(0x25, guildDuty); // 0x25
		output.putByte(0x32, gender); // 0x32
		output.putInt(0x34, playerWins); // 0x34
		output.putInt(0x38, playerLoses); // 0x38
		output.putInt(0x3c, 11); // 0x3c
		output.putInt(0x40, 22); // 0x40
		output.putInt(0x44, playerKOs); // 0x44
		output.putInt(0x48, playerDowns); // 0x48
		output.putInt(0x50, 33); // 0x50
		output.putInt(0x54, 44); // 0x54
		output.putInt(0x5c, 55); // 0x5c
		output.putLong(0x60, playerPoint); // 0x60
		output.putLong(0x68, playerCode); // 0x68
		output.putLong(0x70, avatarMoney); // 0x70
		output.putInt(0x78, playerLevel); // 0x78
		output.putInt(0x7C, usuableCharactersCount); // 0x7c
		output.putInt(0x80, scrolls[0]); // 0x80
		output.putInt(0x84, scrolls[1]); // 0x84
		output.putInt(0x88, scrolls[2]); // 0x88
		output.putInt(0x8C, whiteCards[0]); // 0x8c
		output.putInt(0x90, whiteCards[1]); // 0x90
		output.putInt(0x94, whiteCards[2]); // 0x94
		output.putInt(0x98, whiteCards[3]); // 0x98
		output.putInt(0x9C, channelFlag); // 0x9c
		output.putBytes(0xA0, playerCardItemExist); // 0xA0
		output.putInts(0x280, playerCardItemId); // 0x280
		output.putInts(0x400, playerCardItemDays); // 0x400
		output.putInts(0x580, playerCardItemLevelIdx); // 0x580
		output.putInts(0x700, playerCardItemSkill); // 0x700
		output.putInt(0x880, playerInventorySlots); // 0x880
		output.putInts(0x884, minPointForeveDword); // 0x884
		output.putLongs(0x8F8, minPointForLevelQword); //0x8F8
		output.putInt(0x918, playerChannelType); // 0x918
		output.putInt(0x91C, 88); // 0x91c
		output.putInt(0x920, 77); // 0x920
		output.putInt(0x924, 66); // 0x924
		output.putInt(0x928, visitBonusMoney);
		output.putInt(0x92C, visitBonusElementsType);
		output.putInt(0x930, visitBonusElements);
		output.putInt(0x934, visitBonusElementsMultiplier);
		output.putInt(0x938, visitBonusAvatarMoney);
		output.putBytes(0x93C, playerEventFlags); // 0x93C
		output.putInt(0x944, playerRank); // 0x944
		output.putInt(0x948, 33); // 0x948 - not used
		output.putInt(0x94C, lobbyMaxRooms); // 0x94c
		output.putInts(0x950, playerAvatarEquipIdx); // 0x950
		output.putInt(0x96C, 22); // 0x96C
		output.putInt(0x970, 11); // 0x970 = field_A0 in Login
		output.putInt(0x974, playerType); // 0x974
		output.putByte(0x978, (byte) 0); // 0x978
	}

	@Override
	public void afterSend() {
		// TODO Auto-generated method stub
		
	}
}
