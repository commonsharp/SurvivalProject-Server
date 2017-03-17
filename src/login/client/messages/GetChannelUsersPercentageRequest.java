package login.client.messages;

import login.server.messages.GetChannelUsersPercentageResponse;
import net.GenericClientMessage;

public class GetChannelUsersPercentageRequest extends GenericClientMessage {
	
	public GetChannelUsersPercentageRequest(byte[] messageBytes) {
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
		return new GetChannelUsersPercentageResponse().getResponse();
	}
}
