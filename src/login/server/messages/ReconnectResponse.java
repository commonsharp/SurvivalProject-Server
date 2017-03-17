package login.server.messages;

import net.GenericServerMessage;

public class ReconnectResponse extends GenericServerMessage {
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
