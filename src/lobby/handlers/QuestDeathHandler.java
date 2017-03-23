package lobby.handlers;

import java.io.IOException;

import lobby.LobbyServer;
import net.GenericHandler;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class QuestDeathHandler extends GenericHandler {
	public static final int REQUEST_ID = 0x4358;
	public static final int RESPONSE_ID = 0x4359;
	public static final int RESPONSE_LENGTH = 0xB0;
	
	int monsterIndex;
	
	protected LobbyServer lobby;
	
	public QuestDeathHandler(LobbyServer lobby, UserTCPSession tcpServer, byte[] messageBytes) {
		super(tcpServer, messageBytes);
		this.lobby = lobby;
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
		output.putInt(0x18, userSession.getUser().roomSlot); // slot
		output.putInt(0x1C, 0);
		output.putInt(0x20, 0);
		
		 // lucky multiplier (exp and code multiplier)
		int[] luckyMultiplier = new int[8];
		for (int i = 0; i < 8; i++) {
			luckyMultiplier[i] = 1;
		}
		
		int[] baseExpAndCode = new int[8];
		for (int i = 0; i < 8; i++) {
			baseExpAndCode[i] = 1;
		}
		
		int[] levels = new int[8];
		for (int i = 0; i < 8; i++) {
			levels[i] = 10;
		}
		
		output.putInts(0x24, luckyMultiplier);
		output.putInts(0x44, baseExpAndCode);
		output.putInts(0x64, levels);
		output.putInt(0x84, 200); // another array. unknown yet.
		output.putInt(0xA4, 2); // element type
		output.putInt(0xA8, 5); // element count
		output.putInt(0xAC, 4); // element multiplier
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
		lobby.roomMessage(userSession.getUser().roomIndex, getResponse());
	}
}
