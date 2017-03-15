package login.client.messages;

import login.client.ClientGenericMessage;
import login.server.messages.TutorialCompletedResponse;

public class TutorialCompletedNotification extends ClientGenericMessage {
	public TutorialCompletedNotification(byte[] messageBytes) {
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
		return new TutorialCompletedResponse().getResponse();
	}
}
