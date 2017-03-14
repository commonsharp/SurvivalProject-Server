package login.client.messages;

import login.client.ClientGenericMessage;
import login.server.messages.ServerInfoResponse;
import tools.HexTools;

public class ServerInfoRequest extends ClientGenericMessage {
	protected int unknown1;
	protected int channelType = 3;
	
	public ServerInfoRequest(byte[] messageBytes) {
		super(messageBytes);
	}

	@Override
	public void interpretBytes(byte[] messageBytes) {
		unknown1 = buffer.readInt();
		channelType = buffer.readInt();
//		System.out.println("NOTTIME!!!Time: " + unknown1);
	}

	@Override
	public void processFields() {
		// Add to database and stuff
	}

	@Override
	public byte[] getResponse() {
		return new ServerInfoResponse((short) channelType).getResponse();
	}
}
