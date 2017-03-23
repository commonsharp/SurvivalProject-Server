package lobby.handlers;

import java.io.IOException;

import net.GenericHandler;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class JoinLobbyHandler extends GenericHandler {
	public static final int REQUEST_ID = 0x4301;
	public static final int RESPONSE_ID = 0x4302;
	public static final int RESPONSE_LENGTH = 0x979;
	
	final int minPointForeveDword[] = {-5, 1, 50, 100,
			200, 400, 800, 1600,
			2400, 3200, 6400, 12800,
			25600, 51200, 102400, 204800,
			409600, 819200, 1638400, 3276800,
			6553600, 13107200, 26214400, 52428800,
			104857600, 209715200, 419430400, 838860800,
			1677721600};
    final long minPointForLevelQword[] = {3355443200L, 6710886400L, 13421772800L, 26843545600L};
	
	long avatarMoney = 1234;
	
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
	int channelFlag = (userSession.getUser().playerLevel == 0) ? 0 : (userSession.getUser().playerLevel >= 17) ? 30 : (userSession.getUser().playerLevel >= 13) ? 20 : 10;
	
	// Registry/IOSPK/Version
	int spVersion = 11;
	int ioProtectVersion = 11;
	int survivalprojectVersion = 11;
	
	public JoinLobbyHandler(UserTCPSession tcpServer, byte[] messageBytes) {
		super(tcpServer, messageBytes);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void interpretBytes() {
		userSession.getUser().username = input.getString(0x14);
		System.out.println(input.getString(0x14));
		System.out.println(input.getString(0x21));
		System.out.println(input.getString(0x3A));
		System.out.println(input.getInt(0x44));
		System.out.println(input.getInt(0x48));
		System.out.println(input.getInt(0x4C));
		System.out.println(input.getInt(0x50));
		System.out.println(input.getInt(0x54));
		System.out.println(input.getInt(0x58));
	}

	@Override
	public void afterSend() throws IOException {
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, RESPONSE_ID);
		output.putInt(0x14, response); // 0x14
		output.putString(0x18, userSession.getUser().guildName); // 0x18
		output.putString(0x25, userSession.getUser().guildDuty); // 0x25
		output.putByte(0x32, userSession.getUser().gender); // 0x32
		output.putInt(0x34, userSession.getUser().playerWins); // 0x34
		output.putInt(0x38, userSession.getUser().playerLoses); // 0x38
		output.putInt(0x3c, 11); // 0x3c - something with wins maybe...
		output.putInt(0x40, userSession.getUser().playerLevel);
		output.putInt(0x44, userSession.getUser().playerKOs); // 0x44
		output.putInt(0x48, userSession.getUser().playerDowns); // 0x48
		output.putInt(0x50, 33); // 0x50
		output.putInt(0x54, 44); // 0x54
		output.putInt(0x5c, 55); // 0x5c
		output.putLong(0x60, userSession.getUser().playerExperience); // 0x60
		output.putLong(0x68, userSession.getUser().playerMoney); // 0x68
		output.putLong(0x70, avatarMoney); // 0x70
		output.putInt(0x78, userSession.getUser().playerLevel); // 0x78
		output.putInt(0x7C, userSession.getUser().usuableCharacterCount); // 0x7c
		output.putInt(0x80, userSession.getUser().scrolls[0]); // 0x80
		output.putInt(0x84, userSession.getUser().scrolls[1]); // 0x84
		output.putInt(0x88, userSession.getUser().scrolls[2]); // 0x88
		output.putInt(0x8C, userSession.getUser().whiteCards[0]); // 0x8c
		output.putInt(0x90, userSession.getUser().whiteCards[1]); // 0x90
		output.putInt(0x94, userSession.getUser().whiteCards[2]); // 0x94
		output.putInt(0x98, userSession.getUser().whiteCards[3]); // 0x98
		output.putInt(0x9C, channelFlag); // 0x9c
		
		for (int i = 0; i < 96; i++) {
			if (userSession.getUser().getItemID(i) != -1) {
				// If the item exists, put 1 to mark it as "exists"
				output.putByte(0xA0 + i, (byte) 1);
			}
		}
		
		for (int i = 0; i < 96; i++) {
			output.putInt(0x280 + i * 4, userSession.getUser().getItemID(i));
		}
		
		for (int i = 0; i < 96; i++) {
			output.putInt(0x400 + i * 4, userSession.getUser().getItemPremiumDays(i));
		}
		
		for (int i = 0; i < 96; i++) {
			output.putInt(0x580 + i * 4, userSession.getUser().getItemLevel(i));
		}
		
		for (int i = 0; i < 96; i++) {
			output.putInt(0x700 + i * 4, userSession.getUser().getItemSkill(i));
		}
		
		output.putInt(0x880, userSession.getUser().playerInventorySlots); // 0x880
		output.putInts(0x884, minPointForeveDword); // 0x884
		output.putLongs(0x8F8, minPointForLevelQword); //0x8F8
		output.putInt(0x918, userSession.getUser().playerChannelType); // 0x918
		output.putInt(0x91C, spVersion); // 0x91c
		output.putInt(0x920, ioProtectVersion); // 0x920
		output.putInt(0x924, survivalprojectVersion); // 0x924
		output.putInt(0x928, visitBonusMoney);
		output.putInt(0x92C, visitBonusElementsType);
		output.putInt(0x930, visitBonusElements);
		output.putInt(0x934, visitBonusElementsMultiplier);
		output.putInt(0x938, visitBonusAvatarMoney);
		output.putBytes(0x93C, userSession.getUser().playerEventFlags); // 0x93C
		output.putInt(0x944, playerRank); // 0x944
		output.putByte(0x948, (byte) 1);
		output.putInt(0x94C, lobbyMaxRooms); // 0x94c
		output.putInt(0x950, userSession.getUser().getAvatarItemID(userSession.getUser().footIndex));
		output.putInt(0x954, userSession.getUser().getAvatarItemID(userSession.getUser().bodyIndex));
		output.putInt(0x958, userSession.getUser().getAvatarItemID(userSession.getUser().hand1Index));
		output.putInt(0x95C, userSession.getUser().getAvatarItemID(userSession.getUser().hand2Index));
		output.putInt(0x960, userSession.getUser().getAvatarItemID(userSession.getUser().faceIndex));
		output.putInt(0x964, userSession.getUser().getAvatarItemID(userSession.getUser().hairIndex));
		output.putInt(0x968, userSession.getUser().getAvatarItemID(userSession.getUser().headIndex));
		output.putInt(0x96C, 1); // something with guild mark
		output.putInt(0x970, 11); // 0x970 = field_A0 in Login
		output.putInt(0x974, userSession.getUser().playerType); // 0x974
		output.putByte(0x978, (byte) 0); // 0x978
		
		return output.toArray();
	}
}
