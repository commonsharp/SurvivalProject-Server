package login.handlers;

import net.GenericHandler;
import net.UserTCPSession;

public class LoginHandler extends GenericHandler {
	public static final int REQUEST_ID = 0x2707;
	public static final int RESPONSE_ID = 0x2807;
	public static final int RESPONSE_LENGTH = 0xA8;
	
	protected byte[] versionHash; // 36 bytes
	protected int versionCode;
	String username; // 12 characters+0
	String password; // 12 characters+0
	
	
	int response;
    int userType; //Not sure, but It control/close servers 0x1E open all servers
    int activeCharacter;
    int playerLevel;
    int usuableCharacterCount; // 1 - 12?
    int isMuted;
    int daysToMute;
    int ageRestriction;
    long playerExperience;
    long playerMoney; // money?
    String guildName; // 12+0
    String guildTitle; // 26+0
    long unknown1; // probably cash (premium money)
    String unknown3; // 24+0
    String unknown4; // 10+0
    int unknown5;
    int unknown6;
    int unknown7;
	
	public LoginHandler(UserTCPSession tcpServer, byte[] messageBytes) {
		super(tcpServer, messageBytes, RESPONSE_LENGTH, RESPONSE_ID);
	}
	
	@Override
	public void interpretBytes() {
		versionHash = input.getBytes(0x14, 36);
		versionCode = input.getInt(0x38);
		
		username = input.getString(0x3C);
		password = input.getString(0x49);
	}

	@Override
	public void processFields() {
		// Add to database and stuff
	}

	@Override
	public void changeData() {
		response = 1;
		ageRestriction = 1;
		playerLevel = 30;
		playerMoney = 1234567;
		usuableCharacterCount = 12;
		playerExperience = 7654321;
		userType = 30;
		activeCharacter = 40;
		guildName = "barakguild";
		isMuted = 12;
		daysToMute = 34;
		guildTitle = "whatwhat";
		unknown1 = 1234;
		unknown3 = "what3";
		unknown4 = "what4";
		unknown5 = 560;
		unknown6 = 10;
		unknown7 = 10;
	}

	@Override
	public void addPayload() {
		output.putInt(0x14, response);
		output.putInt(0x18, userType);
		output.putInt(0x1C, activeCharacter);
		output.putInt(0x20, playerLevel);
		output.putInt(0x24, usuableCharacterCount);
		output.putInt(0x28, isMuted);
		output.putInt(0x2C, daysToMute);
		output.putInt(0x30, ageRestriction);
		output.putLong(0x38, playerExperience);
		output.putLong(0x40, playerMoney);
		output.putString(0x48, guildName);
		output.putString(0x55, guildTitle);
		output.putLong(0x70, unknown1);
		output.putString(0x78, unknown3);
		output.putString(0x91, unknown4);
		output.putInt(0x9C, unknown5);
		output.putInt(0xA0, unknown6);
		output.putInt(0xA4, unknown7);
	}

	@Override
	public void afterSend() {
		// TODO Auto-generated method stub
		
	}
}
