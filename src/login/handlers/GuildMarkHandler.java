package login.handlers;

import login.LoginHandler;
import net.Messages;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class GuildMarkHandler extends LoginHandler {
	public static final int RESPONSE_LENGTH = 0x1000;
	
	public GuildMarkHandler(UserTCPSession tcpServer, byte[] messageBytes) {
		super(tcpServer, messageBytes);
	}

	@Override
	public void interpretBytes() {
	}

	@Override
	public void afterSend() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.LOGIN_GUILD_MARK_RESPONSE);
		
		output.putString(0x14, "barakguild"); // TODO - change to the player's guild
		byte[] pixels = new byte[12 * 13 * 2];
		
		// 16 bits per color. probably High Color.
		for (int i = 0; i < pixels.length; i += 2) {
			pixels[i] = (byte) 0; // color
			pixels[i + 1] = (byte) 255; // brightness
		}
		output.putInt(0x24, 1); // 0 = nothing. other = mark??
		output.putBytes(0x28, pixels); // the colors. 2 bytes per pixel
		
		return output.toArray();
	}

	@Override
	public void processMessage() {
		// TODO Auto-generated method stub
		
	}
}
