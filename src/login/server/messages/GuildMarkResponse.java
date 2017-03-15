package login.server.messages;

import login.server.ServerGenericMessage;

public class GuildMarkResponse extends ServerGenericMessage {

	public GuildMarkResponse() {
		super(0x2922);
	}

	@Override
	public void changeData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPayload() {
		payload.putString("barakguild", 16);
		payload.putBytes(new byte[79]);
	}
}
