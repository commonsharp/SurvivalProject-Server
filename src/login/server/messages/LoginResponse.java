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
    long unknown1;
//    int unknown1;
//    int unknown2;
    String unknown3; // 24+0
    String unknown4; // 10+0
    int unknown5;
    int unknown6;
    int unknown7;
    
	public LoginResponse() {
		super(0x2807);
	}
	
	@Override
	public void changeData() {
		response = 1;
		ageRestriction = -1;
//		unknown5 = ageRestriction;
		playerLevel = 25;
		playerMoney = 100;
		usuableCharacterCount = 12;
		playerExperience = 100;
		userType = 30;
		activeCharacter = 20;
		guildName = "barakguild";
//		unknown1 = 100;
//		unknown3 = "what";
//		unknown4 = "what2";
		isMuted = 1;
		daysToMute = 30;
		guildTitle = "whatwhat";
//		unknown6 = 580;
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
		payload.putLong(unknown1);
//		payload.putInteger(unknown1);
//		payload.putInteger(unknown2);
		payload.putString(unknown3, 24);
		payload.putString(unknown3, 10);
		// those need to be included, but it seems like we need to change more fields before that
//		payload.putInteger(unknown5);
//		payload.putInteger(unknown6);
//		payload.putInteger(unknown7);
	}
}
