package login.server.messages;

import login.server.ServerGenericMessage;

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
    int unknown1;
    int unknown2;
    String unknown3; // 24+0
    String unknown4; // 10+0
    int unknown5;
    int unknown6;
    int unknown7;
    
	public LoginResponse() {
		super(0x2807, 0);
	}
	
	@Override
	public void changeData() {
		response = 1;
		ageRestriction = -1;
//		unknown5 = ageRestriction;
		playerLevel = 30;
//		playerMoney = 100;
		usuableCharacterCount = 10;
//		playerExperience = 100;
		userType = 30;
//		activeCharacter = 10;
		guildName = "barakguild";
//		isMuted = 1;
//		daysToMute = 30;
//		guildTitle = "whatwhat";
//		playerLevel = 5;
//		userType = 0x1E;
	}

	@Override
	public void addPayload() {
		payload.putInteger(response);
		payload.putInteger(userType);
		payload.putInteger(activeCharacter);
		payload.putInteger(playerLevel);
		payload.putInteger(usuableCharacterCount);
		payload.putInteger(isMuted);
		payload.putInteger(daysToMute);
		payload.putInteger(ageRestriction);
		payload.putInteger(notUsed);
		payload.putLong(playerExperience);
		payload.putLong(playerMoney);
		payload.putString(guildName, 12);
		payload.putString(guildTitle, 26);
		payload.putInteger(unknown1);
		payload.putInteger(unknown2);
		payload.putString(unknown3, 24);
		payload.putString(unknown3, 10);
		payload.putInteger(unknown5);
		payload.putInteger(unknown6);
		payload.putInteger(unknown7);
		
//		payload.putBytes(new byte[13]);
//		payload.putBytes(new byte[13]);
//		payload.putBytes(new byte[23]);
//		payload.putBytes(new byte[13]);
//		payload.putBytes(new byte[10]);
//		payload.putBytes(new byte[12]);
		// it should be: remove last line and add the next 3...
//		payload.putBytes(new byte[16]);
//		payload.putInteger(0);
//		payload.putInteger(0);
	}
}
