package login.handlers;

import net.GenericHandler;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class LoginHandler extends GenericHandler {
	public static final int REQUEST_ID = 0x2707;
	public static final int RESPONSE_ID = 0x2807;
	public static final int RESPONSE_LENGTH = 0xA8;
	
	int response;
    long unknown1; // probably cash (premium money)
    String unknown3; // 24+0
    String unknown4; // 10+0
    int unknown5;
    int unknown6;
    int unknown7;
	
	public LoginHandler(UserTCPSession tcpServer, byte[] messageBytes) {
		super(tcpServer, messageBytes);
	}
	
	@Override
	public void interpretBytes() {
		printMessage();
		tcpServer.getUser().versionHash = input.getBytes(0x14, 36);
		tcpServer.getUser().versionCode = input.getInt(0x38);
		
		tcpServer.getUser().username = input.getString(0x3C);
		tcpServer.getUser().password = input.getString(0x49);
		
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
	public byte[] getResponse() {
		response = 1;
		unknown1 = 1234;
		unknown3 = "what3";
		unknown4 = "what4";
		unknown5 = 560;
		unknown6 = 10;
		unknown7 = 10;
		
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, RESPONSE_ID);
		output.putInt(0x14, response);
		output.putInt(0x18, tcpServer.getUser().userType);
		output.putInt(0x1C, tcpServer.getUser().activeCharacter);
		output.putInt(0x20, tcpServer.getUser().playerLevel);
		output.putInt(0x24, tcpServer.getUser().usuableCharacterCount);
		output.putInt(0x28, tcpServer.getUser().isMuted);
		output.putInt(0x2C, tcpServer.getUser().daysToMute);
		output.putInt(0x30, tcpServer.getUser().ageRestriction);
		output.putLong(0x38, tcpServer.getUser().playerExperience);
		output.putLong(0x40, tcpServer.getUser().playerMoney);
		output.putString(0x48, tcpServer.getUser().guildName);
		output.putString(0x55, tcpServer.getUser().guildDuty);
		output.putLong(0x70, unknown1);
		output.putString(0x78, unknown3);
		output.putString(0x91, unknown4);
		output.putInt(0x9C, unknown5);
		output.putInt(0xA0, unknown6);
		output.putInt(0xA4, unknown7);
		
		return output.toArray();
	}
}
