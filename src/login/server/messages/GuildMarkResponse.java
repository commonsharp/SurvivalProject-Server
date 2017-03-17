package login.server.messages;

import net.GenericServerMessage;

public class GuildMarkResponse extends GenericServerMessage {

	public GuildMarkResponse() {
		super(1000, 0x2922);
	}

	@Override
	public void changeData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPayload() {
		buffer.putString(0x14, "barakguild"); // TODO - change to the player's guild
		byte[] pixels = new byte[12 * 13 * 2];
		
		// 16 bits per color. probably High Color.
		for (int i = 0; i < pixels.length; i += 2) {
			pixels[i] = (byte) 0; // color
			pixels[i + 1] = (byte) 255; // brightness
		}
		buffer.putInt(0x24, 1); // 0 = nothing. other = mark??
		buffer.putBytes(0x28, pixels); // the colors. 2 bytes per pixel
	}
}
