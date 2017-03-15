package game.server;

import net.ServerGenericMessage;

public class JoinServerResponse extends ServerGenericMessage {

	public JoinServerResponse() {
		super(0x1101);
	}

	@Override
	public void changeData() {
		
	}

	@Override
	public void addPayload() {
		
	}

}
