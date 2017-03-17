package game.server;

import net.GenericServerMessage;

public class JoinServerResponse extends GenericServerMessage {

	public JoinServerResponse() {
		super(0x14, 0x1101);
	}

	@Override
	public void changeData() {
		
	}

	@Override
	public void addPayload() {
		
	}

}
