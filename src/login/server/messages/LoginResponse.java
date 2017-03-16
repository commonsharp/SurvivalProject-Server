package login.server.messages;

import net.ServerGenericMessage;

public class LoginResponse extends ServerGenericMessage {
	int response;
    int userType; //Not sure, but It control/close servers 0x1E open all servers
    int activeCharacter;
    int playerLevel;
    int usuableCharacterCount; // 1 - 12?
    int isMuted;
    int daysToMute;
    int ageRestriction; //1 is ok, other will give korean announcement
    int notUsed; //Always 0x40000000, can be 0 // not used maybe
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
    
	public LoginResponse() {
		super(0xA8, 0x2807);
	}
	
	@Override
	public void changeData() {
		response = 1;
		ageRestriction = 1;
		playerLevel = 0;
		playerMoney = 1234567;
		usuableCharacterCount = 12;
		playerExperience = 7654321;
		userType = 0;
		activeCharacter = 0;
		guildName = "barakguild";
		isMuted = 0;
		daysToMute = 0;
		guildTitle = "whatwhat";
//		unknown1 = 1234;
//		unknown3 = "what3";
//		unknown4 = "what4";
//		unknown5 = 560;
//		unknown6 = 10;
//		unknown7 = 10;
	}

	@Override
	public void addPayload() {
		buffer.putInt(0x14, response);
		buffer.putInt(0x18, userType);
		buffer.putInt(0x1C, activeCharacter);
		buffer.putInt(0x20, playerLevel);
		buffer.putInt(0x24, usuableCharacterCount);
		buffer.putInt(0x28, isMuted);
		buffer.putInt(0x2C, daysToMute);
		buffer.putInt(0x30, ageRestriction);
		buffer.putLong(0x38, playerExperience);
		buffer.putLong(0x40, playerMoney);
		buffer.putString(0x48, guildName);
		buffer.putString(0x55, guildTitle);
		buffer.putLong(0x70, unknown1);
		buffer.putString(0x78, unknown3);
		buffer.putString(0x91, unknown4);
		buffer.putInt(0x9C, unknown5);
		buffer.putInt(0xA0, unknown6);
		buffer.putInt(0xA4, unknown7);
	}
}
