package login.handlers;

import net.GenericHandler;
import net.UserTCPSession;

public class GuildMarkHandler extends GenericHandler {
	public static final int REQUEST_ID = 0x2921;
	public static final int RESPONSE_ID = 0x2922;
	public static final int RESPONSE_LENGTH = 0x1000;
	
	public GuildMarkHandler(UserTCPSession tcpServer, byte[] messageBytes) {
		super(tcpServer, messageBytes, RESPONSE_LENGTH, RESPONSE_ID);
	}

	@Override
	public void interpretBytes() {
	}

	@Override
	public void processFields() {
		// Add to database and stuff
	}

	@Override
	public void changeData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPayload() {
		output.putString(0x14, "barakguild"); // TODO - change to the player's guild
		byte[] pixels = new byte[12 * 13 * 2];
		
		// 16 bits per color. probably High Color.
		for (int i = 0; i < pixels.length; i += 2) {
			pixels[i] = (byte) 0; // color
			pixels[i + 1] = (byte) 255; // brightness
		}
		output.putInt(0x24, 1); // 0 = nothing. other = mark??
		output.putBytes(0x28, pixels); // the colors. 2 bytes per pixel
	}

	@Override
	public void afterSend() {
		// TODO Auto-generated method stub
		
	}
}
