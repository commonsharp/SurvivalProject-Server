package lobby.handlers;

import net.GenericMessage;

public class GetTopGuildsHandler extends GenericMessage {
	public static final int REQUEST_ID = 0x4388;
	public static final int RESPONSE_ID = 0x4389;
	public static final int RESPONSE_LENGTH = 0xC4;
	
	public GetTopGuildsHandler(byte[] messageBytes) {
		super(messageBytes, RESPONSE_LENGTH, RESPONSE_ID);
	}

	@Override
	public void interpretBytes() {
	}

	@Override
	public void processFields() {
		
	}

	@Override
	public void changeData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPayload() {
		// If your clan is in this list, then 9 0x4486 requests are being sent to the server.
		// Otherwise, 10 0x4486 requests are being sent to the server...
		outputBuffer.putString(0x14, "hello1");
		outputBuffer.putString(0x21, "hello2");
		outputBuffer.putString(0x2E, "hello3");
		outputBuffer.putString(0x3B, "hello4");
		outputBuffer.putString(0x48, "hello5");
		outputBuffer.putString(0x55, "hello6");
		outputBuffer.putString(0x62, "hello7");
		outputBuffer.putString(0x6F, "hello8");
		outputBuffer.putString(0x7C, "hello9");
		outputBuffer.putString(0x89, "hello10");
		outputBuffer.putInt(0x98, 10); // score1
		outputBuffer.putInt(0x9C, 9); // score2
		outputBuffer.putInt(0xA0, 8); // score3
		outputBuffer.putInt(0xA4, 7); // score4
		outputBuffer.putInt(0xA8, 6); // score5
		outputBuffer.putInt(0xAC, 5); // score6
		outputBuffer.putInt(0xB0, 4); // score7
		outputBuffer.putInt(0xB4, 3); // score8
		outputBuffer.putInt(0xB8, 2); // score9
		outputBuffer.putInt(0xBC, 1); // score10
		outputBuffer.putInt(0xC0, 0); // ?
//		outputBuffer.putString(0xC0, "hello1");
//		outputBuffer.putInt(0xD0, 5);
	}
}
