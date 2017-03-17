package lobby.handlers;

import net.GenericHandler;
import net.UserTCPSession;

public class RoomPlayersChangedHandler extends GenericHandler {
	public static final int REQUEST_ID = 0x4315;
	public static final int RESPONSE_ID = 0x4304;
	public static final int RESPONSE_LENGTH = 0xAF;
	
	int unknown01;
	int character;
	int unknown02;
	byte unknown03;
	int unknown04;
	int unknown05;
	int unknown06;
	
	public RoomPlayersChangedHandler(UserTCPSession tcpServer, byte[] messageBytes) {
		super(tcpServer, messageBytes, RESPONSE_LENGTH, RESPONSE_ID);
	}

	@Override
	public void interpretBytes() {
		unknown01 = input.getInt(0x14);
		character = input.getInt(0x18);
		unknown02 = input.getInt(0x1C);
		unknown03 = input.getByte(0x20);
		unknown04 = input.getInt(0x24);
		unknown05 = input.getInt(0x28);
		unknown06 = input.getInt(0x2C);
	}

	@Override
	public void processFields() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPayload() {
		output.putByte(0x14, (byte) 1); //create?
		output.putInt(0x18, 0); // room ID
		output.putString(0x1C, "barak's room"); // room title
		output.putInt(0x3C, 8); // room type
		output.putInt(0x40, 10); // room map
		output.putInt(0x44, 0); // unknown
		output.putInt(0x48, 8); // max players
		output.putByte(0x4C, (byte) 0); // is password
		output.putByte(0x4D, (byte) 0); // is potion
		output.putByte(0x4E, (byte) 0); // is closed
		output.putByte(0x4F, (byte) 0); // is premium
		output.putByte(0x50, (byte) 0);
		output.putInt(0x54, 0);
		output.putInt(0x58, 10);
//		output.putString(0x58, "");
		output.putInt(0x98, 0);
		output.putInt(0xA8, 0);
		output.putByte(0xAC, (byte) 0);
		output.putByte(0xAD, (byte) 1);
		output.putByte(0xAE, (byte) 0);
	}

	@Override
	public void afterSend() {
		// TODO Auto-generated method stub
		
	}
}
