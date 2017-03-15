package login.server.messages;

import login.server.ServerGenericMessage;

public class SetActiveCharacterResponse extends ServerGenericMessage {
	protected int unknown1;
	protected int character;
	
	public SetActiveCharacterResponse(int character) {
		super(0x2912);
		this.character = character;
	}

	@Override
	public void changeData() {
		unknown1 = 1;
	}

	@Override
	public void addPayload() {
		payload.putInteger(unknown1);
		payload.putInteger(character);
	}
}
