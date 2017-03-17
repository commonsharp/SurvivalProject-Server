package lobby.handlers;

import net.GenericMessage;

public class GetTopGuildsMarkHandler extends GenericMessage {
	public static final int RESPONSE_ID = 0x4487;
	
	protected String guildName;
	protected int window;
	
	public GetTopGuildsMarkHandler(byte[] messageBytes) {
		super(messageBytes, 1000, RESPONSE_ID);
	}

	@Override
	public void interpretBytes(byte[] messageBytes) {
		guildName = inputBuffer.getString(0x14);
		window = inputBuffer.getInt(0x24);
	}

	@Override
	public void processFields() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPayload() {
		outputBuffer.putString(0x14, "hello7");
		byte[] pixels = new byte[12 * 13 * 2];
		
		// 16 bits per color. probably High Color.
		for (int i = 0; i < pixels.length; i += 2) {
			pixels[i] = (byte) 0; // color
			pixels[i + 1] = (byte) 255; // brightness
		}
		outputBuffer.putInt(0x24, 1); // 0 = nothing. other = mark??
		outputBuffer.putBytes(0x28, pixels); // the colors. 2 bytes per pixel
	}
}
