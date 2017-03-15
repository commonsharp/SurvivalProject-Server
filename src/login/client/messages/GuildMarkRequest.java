package login.client.messages;

import login.server.messages.GuildMarkResponse;
import login.server.messages.ServerInfoResponse;
import net.ClientGenericMessage;

public class GuildMarkRequest extends ClientGenericMessage {
	
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
