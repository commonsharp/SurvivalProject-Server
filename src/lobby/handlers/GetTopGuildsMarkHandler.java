package lobby.handlers;

import net.GenericHandler;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class GetTopGuildsMarkHandler extends GenericHandler {
	public static final int REQUEST_ID = 0x4486;
	public static final int RESPONSE_ID = 0x4487;
	public static final int RESPONSE_LENGTH = 0x1400;
	
	protected String guildName;
	protected int window;
	
	public GetTopGuildsMarkHandler(UserTCPSession tcpServer, byte[] messageBytes) {
		super(tcpServer, messageBytes);
	}

	@Override
	public void interpretBytes() {
		guildName = input.getString(0x14);
		window = input.getInt(0x24);
	}

	@Override
	public void afterSend() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, RESPONSE_ID);
		output.putString(0x14, "hello7");
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
}
