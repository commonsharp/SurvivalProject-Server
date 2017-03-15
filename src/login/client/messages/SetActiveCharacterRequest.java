package login.client.messages;

import login.client.ClientGenericMessage;
import login.server.messages.SetActiveCharacterResponse;

public class SetActiveCharacterRequest extends ClientGenericMessage {
	protected String username;
	protected int character;
	
	public SetActiveCharacterRequest(byte[] messageBytes) {
		super(messageBytes);
	}

	@Override
	public void interpretBytes(byte[] messageBytes) {
		username = buffer.readNullTerminatedString(15);
		character = buffer.readInt();
	}

	@Override
	public void processFields() {
		// Add to database and stuff
	}

	@Override
	public byte[] getResponse() {
		return new SetActiveCharacterResponse(character).getResponse();
	}
}
