package login.handlers;

import net.GenericMessage;

public class SetActiveCharacterHandler extends GenericMessage {
	public static final int REQUEST_ID = 0x2911;
	public static final int RESPONSE_ID = 0x2912;
	public static final int RESPONSE_LENGTH = 0x1C;

	protected String username;
	protected int character;
	
	protected int unknown1;
	
	public SetActiveCharacterHandler(byte[] messageBytes) {
		super(messageBytes, RESPONSE_LENGTH, RESPONSE_ID);
	}

	@Override
	public void interpretBytes() {
		username = inputBuffer.getString(0x14);
		character = inputBuffer.getInt(0x24);
	}

	@Override
	public void processFields() {
		// Add to database and stuff
	}

	@Override
	public void changeData() {
		unknown1 = 1; // ?
	}

	@Override
	public void addPayload() {
		outputBuffer.putInt(0x14, unknown1);
		outputBuffer.putInt(0x18, character);
	}
}
