package login.client.messages;

import login.client.ClientGenericMessage;
import tools.HexTools;

public class LoginRequest extends ClientGenericMessage {
	protected byte[] versionHash; // 36 bytes
	protected int versionCode;
	String username; // 12 characters+0
	String password; // 12 characters+0
	
	public LoginRequest(byte[] messageBytes) {
		super(messageBytes);
	}

	@Override
	public void interpretBytes(byte[] messageBytes) {
		versionHash = buffer.readBytes(36);
		versionCode = buffer.readInt();
		
		username = buffer.readNullTerminatedString(12);
		password = buffer.readNullTerminatedString(12);
		
		System.out.println("Version code " + versionCode);
		System.out.println("Username " + username);
		System.out.println("Password " + password);
		
		HexTools.printHexArray(buffer.peekNext(1000), false);
	}

	@Override
	public void processFields() {
		// Add to database and stuff
	}

	@Override
	public void respond() {
		
	}
}
