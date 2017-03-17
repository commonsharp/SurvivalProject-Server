package login.server.messages;

import net.GenericServerMessage;

public class GetChannelUsersPercentageResponse extends GenericServerMessage {

	public GetChannelUsersPercentageResponse() {
		super(1000, 0x2918);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void changeData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPayload() {
		buffer.putInt(0x14, 0); // tutorial channel
		buffer.putInt(0x18, 33); // beginner channel
		buffer.putInt(0x1C, 66); // hero channel
		buffer.putInt(0x20, 100); // epic channel
	}

}
