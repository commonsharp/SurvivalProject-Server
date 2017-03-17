package lobby.server.messages;

import net.GenericServerMessage;

public class GetTopGuildsResponse extends GenericServerMessage {
	public GetTopGuildsResponse() {
		super(0xC4, 0x4389);
	}

	@Override
	public void changeData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPayload() {
		// If your clan is in this list, then 9 0x4486 requests are being sent to the server.
		// Otherwise, 10 0x4486 requests are being sent to the server...
		buffer.putString(0x14, "hello1");
		buffer.putString(0x21, "hello2");
		buffer.putString(0x2E, "hello3");
		buffer.putString(0x3B, "hello4");
		buffer.putString(0x48, "hello5");
		buffer.putString(0x55, "hello6");
		buffer.putString(0x62, "hello7");
		buffer.putString(0x6F, "hello8");
		buffer.putString(0x7C, "hello9");
		buffer.putString(0x89, "hello10");
		buffer.putInt(0x98, 10); // score1
		buffer.putInt(0x9C, 9); // score2
		buffer.putInt(0xA0, 8); // score3
		buffer.putInt(0xA4, 7); // score4
		buffer.putInt(0xA8, 6); // score5
		buffer.putInt(0xAC, 5); // score6
		buffer.putInt(0xB0, 4); // score7
		buffer.putInt(0xB4, 3); // score8
		buffer.putInt(0xB8, 2); // score9
		buffer.putInt(0xBC, 1); // score10
		buffer.putInt(0xC0, 0); // ?
//		buffer.putString(0xC0, "hello1");
//		buffer.putInt(0xD0, 5);
	}
	
}
