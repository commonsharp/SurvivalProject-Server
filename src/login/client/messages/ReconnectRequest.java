package login.client.messages;

import login.client.ClientGenericMessage;
import login.server.messages.ServerInfoResponse;

public class ReconnectRequest extends ClientGenericMessage {
	protected String username;
	protected int unknown1;
	protected int unknown2;
	protected int unknown3;
	
	public ReconnectRequest(byte[] messageBytes) {
		super(messageBytes);
	}

	@Override
	public void interpretBytes(byte[] messageBytes) {
		username = buffer.readNullTerminatedString(12);
		unknown1 = buffer.readInt();
		unknown2 = buffer.readInt();
		unknown3 = buffer.readInt();
	}

	@Override
	public void processFields() {
		// Add to database and stuff
	}

	@Override
	public byte[] getResponse() {
		return null;
//		return new ServerInfoResponse().getResponse();
	}
}
