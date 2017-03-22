package lobby.handlers;

import java.io.IOException;

import net.GenericHandler;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class QuestDeathHandler extends GenericHandler {
	public static final int REQUEST_ID = 0x4358;
	public static final int RESPONSE_ID = 0x4359;
	public static final int RESPONSE_LENGTH = 0xB0;
	
	int monsterIndex;
	public QuestDeathHandler(UserTCPSession tcpServer, byte[] messageBytes) {
		super(tcpServer, messageBytes);
	}

	@Override
	public void interpretBytes() {
		monsterIndex = input.getInt(0x14);
		System.out.println("Monster index: " + monsterIndex);
		/*
		 	14 - monster index
			18
			1C
			20
			24
			28
			2C
			30
			34
			38
			3C
			40 - monster type
			42 - monster level
			44 - integer
		 */
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);

		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, RESPONSE_ID);
		
		output.putInt(0x14, monsterIndex);
		output.putInt(0x18, 0); // slot
		output.putInt(0x1C, 10000);
		output.putInt(0x20, 10000);
		output.putInt(0x24, 20); // lucky multiplier (exp and code multiplier)
		output.putInt(0x44, 69999); // exp and code. can be between 0 and 70000 (exclusive). something happens after 33 monsters
		output.putInt(0x48, 10);
		output.putInt(0x4C, 10);
		output.putInt(0xA4, 2); // element type
		output.putInt(0xA8, 5); // element count
		output.putInt(0xAC, 4); // element multiplier
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
