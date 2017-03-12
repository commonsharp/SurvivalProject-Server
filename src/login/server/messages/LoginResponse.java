package login.server.messages;

import login.server.ServerGenericMessage;

public class LoginResponse extends ServerGenericMessage {
	int response;
    int UserType; //Not sure, but It control/close servers 0x1E open all servers
    int defaultCharacter;
    int UserLevel1;
    int UserLevel2;
    int unk2;
    int unk3;
    int ageCheck; //1 is ok, other will give korean announcement
    int unk4; //Always 0x40000000, can be 0
    long Points;
    long Code;
    int unk5;
    int unk6;
    int unk7;
    int unk8; //5
    int unk9;
    int unk10;
    int unk11; //2
    int unk12;
    int unk13; //5
    int unk14; //2
    int unk15;
    int unk16;
    int unk17;
    int unk18;
    int unk19;
    int unk20;
    int unk21;
    int unk22;
    int unk23; //-1
    int unk24; //7
    int unk25; //64
    
	public LoginResponse() {
		super(10247, 0, 0);
		
		response = 0;
		UserType = 0x1E;
		UserLevel1 = 2;
		UserLevel2 = 2;
		ageCheck = 1;
		
		/*
		 * Login_Response.size = 0xA8;
		Login_Response.type = LOGIN_RESPONSE;
		Login_Response.unk1 = 11036;
		Login_Response.state = UpdateState();
		if (!IsCorrectLogin())
		{
			Login_Response.Response = WrongPasswd;
		}
		else
		{
			Login_Response.Response = CorrectPasswd;
			MyCharInfo UsrInfo;
			GetCharInfo(Login_Info->username,UsrInfo);
			Login_Response.UserType = UsrInfo.UserType;
			*(int*)&Login_Response.DefaultCharacter = UsrInfo.DefaultCharacter;
			Login_Response.UserLevel1 = UsrInfo.Level;
			Login_Response.UserLevel2 = UsrInfo.Level;
			Login_Response.AgeCheck = 1;
			Login_Response.Points = UsrInfo.Points;
			Login_Response.Code = UsrInfo.Code;
			memset(&Login_Response.unk5,0,20*4);
		}
		Login_Response.checksum = cIOSocket.MakeDigest((unsigned char*)&Login_Response);
		buffer = (unsigned char*)&Login_Response;
		 */
	}

	@Override
	public void addPayload() {
		payload.putInteger(response);
		payload.putInteger(UserType);
		payload.putInteger(defaultCharacter);
		payload.putInteger(UserLevel1);
		payload.putInteger(UserLevel2);
		payload.putInteger(unk2);
		payload.putInteger(unk3);
		payload.putInteger(ageCheck);
		payload.putInteger(unk4);
		payload.putLong(Points);
		payload.putLong(Code);
		payload.putInteger(unk5);
		payload.putInteger(unk6);
		payload.putInteger(unk7);
		payload.putInteger(unk8);
		payload.putInteger(unk9);
		payload.putInteger(unk10);
		payload.putInteger(unk11);
		payload.putInteger(unk12);
		payload.putInteger(unk13);
		payload.putInteger(unk14);
		payload.putInteger(unk15);
		payload.putInteger(unk16);
		payload.putInteger(unk17);
		payload.putInteger(unk18);
		payload.putInteger(unk19);
		payload.putInteger(unk20);
		payload.putInteger(unk21);
		payload.putInteger(unk22);
		payload.putInteger(unk23);
		payload.putInteger(unk24);
		payload.putInteger(unk25);
	}
}
