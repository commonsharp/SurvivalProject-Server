package login.handlers;

import net.GenericMessage;

public class GetChannelUsersPercentageHandler extends GenericMessage {
	public static final int REQUEST_ID = 0x2917;
	public static final int RESPONSE_ID = 0x2918;
	public static final int RESPONSE_LENGTH = 0x1000;
	
	public GetChannelUsersPercentageHandler(byte[] messageBytes) {
		super(messageBytes, RESPONSE_LENGTH, RESPONSE_ID);
	}

	@Override
	public void interpretBytes() {
	}

	@Override
	public void processFields() {
		// Add to database and stuff
	}

	@Override
	public void changeData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPayload() {
		outputBuffer.putInt(0x14, 0); // tutorial channel
		outputBuffer.putInt(0x18, 33); // beginner channel
		outputBuffer.putInt(0x1C, 66); // hero channel
		outputBuffer.putInt(0x20, 100); // epic channel
	}
}
