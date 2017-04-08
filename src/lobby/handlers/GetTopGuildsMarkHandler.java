package lobby.handlers;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class GetTopGuildsMarkHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x1400;
	
	protected String guildName;
	protected int window;
	
	public GetTopGuildsMarkHandler(LobbyServer lobbyServer, UserSession tcpServer, byte[] messageBytes) {
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
		output.putInt(0x4, Messages.GET_TOP_GUILDS_MARK_RESPONSE);
		output.putString(0x14, "Guild3");
		byte[] pixels = new byte[12 * 13 * 2];
		
		// 16 bits per color. probably High Color.
		for (int i = 0; i < pixels.length; i += 2) {
			pixels[i] = (byte) 0; // color
			pixels[i + 1] = (byte) 255; // brightness
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
