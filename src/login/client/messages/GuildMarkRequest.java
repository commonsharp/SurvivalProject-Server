package login.client.messages;

import login.server.messages.GuildMarkResponse;
import net.GenericClientMessage;
import tools.HexTools;

public class GuildMarkRequest extends GenericClientMessage {
	
	public GuildMarkRequest(byte[] messageBytes) {
		super(messageBytes);
	}

	@Override
	public void interpretBytes(byte[] messageBytes) {
	}

	@Override
	public void processFields() {
		// Add to database and stuff
	}

	@Override
	public byte[] getResponse() {
		return new GuildMarkResponse().getResponse();
	}
}
