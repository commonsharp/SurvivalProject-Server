package lobby.client.messages;

import lobby.server.messages.GetTopGuildsResponse;
import net.GenericClientMessage;

public class GetTopGuildsRequest extends GenericClientMessage {
	public GetTopGuildsRequest(byte[] messageBytes) {
		super(messageBytes);
	}

	@Override
	public void interpretBytes(byte[] messageBytes) {
	}

	@Override
	public void processFields() {
		
	}

	@Override
	public byte[] getResponse() {
		return new GetTopGuildsResponse().getResponse();
	}
}
