package login.server.messages;

import net.ServerGenericMessage;

public class GuildMarkResponse extends ServerGenericMessage {

	public GuildMarkResponse() {
		super(1, 0x2922); // NOT GOOD.
	}

	@Override
	public void changeData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPayload() {
//		buffer.putString("barakguild", 16);
//		buffer.putBytes(new byte[79]);
	}
}
