package login.client.messages;

import login.server.messages.TutorialCompletedResponse;
import net.GenericClientMessage;

public class TutorialCompletedNotification extends GenericClientMessage {
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
