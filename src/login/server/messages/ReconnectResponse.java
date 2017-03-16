package login.server.messages;

import net.ServerGenericMessage;

public class ReconnectResponse extends ServerGenericMessage {
	public ReconnectResponse() {
		super(0x14, 0);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void changeData() {
		// TODO Auto-generated method stub
	}

	@Override
	public void addPayload() {
		// TODO Auto-generated method stub
	}
}
