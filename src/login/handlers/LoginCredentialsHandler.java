package login.handlers;

import login.LoginHandler;
import net.Messages;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class LoginCredentialsHandler extends LoginHandler {
	public static final int RESPONSE_LENGTH = 0xA8;
	
	int response;
    long unknown1; // probably cash (premium money)
    String unknown3; // 24+0
    String unknown4; // 10+0
    int unknown5;
    int unknown6;
    int unknown7;
	
	public LoginCredentialsHandler(UserTCPSession tcpServer, byte[] messageBytes) {
		super(tcpServer, messageBytes);
	}
	
	@Override
	public void interpretBytes() {
		printMessage();
		userSession.getUser().username = input.getString(0x3C);
		userSession.getUser().password = input.getString(0x49);
		
		System.out.println(input.getInt(0x38));
		System.out.println("Username: " + input.getString(0x3C));
		System.out.println("Password: " + input.getString(0x49));
		System.out.println(input.getString(0x58));
		System.out.println(input.getString(0x64)); // this is equals to unknown3 in the response (what3)
		System.out.println(input.getInt(0x80));
		System.out.println("Process ID: " + input.getInt(0x84));
	}

	@Override
	public void afterSend() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void processMessage() {
		response = 1;
		unknown1 = 1234;
		unknown3 = "what3";
		unknown4 = "what4";
		unknown5 = 560;
		unknown6 = 10;
		unknown7 = 10;
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.LOGIN_CREDENTIALS_RESPONSE);
		output.putInt(0x14, response);
		output.putInt(0x18, userSession.getUser().userType);
		output.putInt(0x1C, userSession.getUser().mainCharacter);
		output.putInt(0x20, userSession.getUser().playerLevel);
		output.putInt(0x24, userSession.getUser().usuableCharacterCount);
		output.putInt(0x28, userSession.getUser().isMuted);
		output.putInt(0x2C, userSession.getUser().daysToMute);
		output.putInt(0x30, userSession.getUser().ageRestriction);
		output.putLong(0x38, userSession.getUser().playerExperience);
		output.putLong(0x40, userSession.getUser().playerMoney);
		output.putString(0x48, userSession.getUser().guildName);
		output.putString(0x55, userSession.getUser().guildDuty);
		output.putLong(0x70, unknown1);
		output.putString(0x78, unknown3);
		output.putString(0x91, unknown4);
		output.putInt(0x9C, unknown5);
		output.putInt(0xA0, unknown6);
		output.putInt(0xA4, unknown7);
		
		return output.toArray();
	}
}
