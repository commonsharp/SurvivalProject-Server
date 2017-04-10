package lobby.handlers;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class GetGuildMarkHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x1400;
	
	protected String guildName;
	protected int window;
	
	public GetGuildMarkHandler(LobbyServer lobbyServer, UserSession tcpServer, byte[] messageBytes) {
		super(lobbyServer, tcpServer, messageBytes);
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
		output.putInt(0x4, Messages.GET_GUILD_MARK_RESPONSE);
		output.putString(0x14, guildName);
		byte[] pixels = new byte[12 * 13 * 2];
		
		// 16 bits per color. probably High Color.
		byte color = (byte)(Math.random() * 256);
		byte brightness = (byte)(Math.random() * 256);
		for (int i = 0; i < pixels.length; i += 2) {
			pixels[i] = (byte) color; // color
			pixels[i + 1] = (byte) brightness; // brightness
		}
		output.putInt(0x24, 15); // 0 = nothing. other = mark??
		output.putBytes(0x28, pixels); // the colors. 2 bytes per pixel
		
		return output.toArray();
	}

	@Override
	public void processMessage() {
		// TODO Auto-generated method stub
		
	}
}
