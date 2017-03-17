package login.server.messages;

import net.GenericServerMessage;

public class SetActiveCharacterResponse extends GenericServerMessage {
	protected int unknown1;
	protected int character;
	
	public SetActiveCharacterResponse(int character) {
		super(0x1C, 0x2912);
		this.character = character;
	}

	@Override
	public void changeData() {
		unknown1 = 1; // ?
	}

	@Override
	public void addPayload() {
		buffer.putInt(0x14, unknown1);
		buffer.putInt(0x18, character);
	}
}
