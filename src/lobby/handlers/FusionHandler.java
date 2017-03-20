package lobby.handlers;

import java.io.IOException;

import net.GenericHandler;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

//change to card fusion
public class FusionHandler extends GenericHandler {
	public static final int REQUEST_ID = 0x4342;
	public static final int RESPONSE_ID = 0x4343;
	public static final int RESPONSE_LENGTH = 0x979;
	
	protected int itemID;
	protected int fusionType; // 1 - level. 2 - skill
	protected int unknown;
	
	public FusionHandler(UserTCPSession tcpServer, byte[] messageBytes) {
		super(tcpServer, messageBytes);
	}

	@Override
	public void interpretBytes() {
		itemID = input.getInt(0x14);
		fusionType = input.getInt(0x18);
		unknown = input.getInt(0x1C);
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, RESPONSE_ID);
		// 0x1C = result.
		// 1 - level up fusion successful
		// 4 - korean message. maybe not enough elements or something...
		// 5 - skill fusion successful
		// 7 - "can't combine items in the chatting room".
		// 8 - "can't fuse PC room cards"
		// 9 - "Current event has been exited so there is not enough Codes"
		// others - something else
		
		int result = 7;
		output.putInt(0x14, 4); // item ID
		output.putInt(0x1C, result);
		output.putInt(0x20, 6); // new level
		output.putInt(0x24, 33200000); // new skill
		output.putInt(0x28, 40);
		output.putInt(0x2C, 50);
		output.putInt(0x30, 60);
		output.putInt(0x34, 7); // level probably. if you're fusing a skill, this cannot be set to 1.
		output.putInt(0x38, 80);
		output.putLong(0x40, 99999999); // new money amount
		output.putInt(0x48, 20000); // new water elements amount
		output.putInt(0x4C, 20000); // new fire elements amount
		output.putInt(0x50, 20000); // new earth elements amount
		output.putInt(0x54, 20000); // new wind elements amount
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
