package login.client.messages;

import login.server.messages.ServerInfoResponse;
import net.GenericClientMessage;

public class ServerInfoRequest extends GenericClientMessage {
	protected int unknown1;
	protected int channelType;
	
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
