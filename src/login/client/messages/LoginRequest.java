package login.client.messages;

import login.server.messages.LoginResponse;
import net.GenericClientMessage;

public class LoginRequest extends GenericClientMessage {
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
		
//		HexTools.printHexArray(buffer.peekNext(1000), false);
	}

	@Override
	public void processFields() {
		// Add to database and stuff
	}

	@Override
	public byte[] getResponse() {
		return new LoginResponse().getResponse();
	}
}
