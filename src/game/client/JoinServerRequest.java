package game.client;

import game.server.JoinServerResponse;
import net.GenericClientMessage;

public class JoinServerRequest extends GenericClientMessage {

	public JoinServerRequest(byte[] messageBytes) {
		super(messageBytes);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void interpretBytes(byte[] messageBytes) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processFields() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getResponse() {
		return new JoinServerResponse().getResponse();
	}

}