package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class QuestDeathHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0xB0;
	
	int monsterIndex;
	
	public QuestDeathHandler(LobbyServer lobbyServer, UserTCPSession tcpServer, byte[] messageBytes) {
		super(lobbyServer, tcpServer, messageBytes);
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
		output.putInt(0x4, Messages.QUEST_DEATH_RESPONSE);
		
		output.putInt(0x14, monsterIndex);
		output.putInt(0x18, userSession.getUser().roomSlot); // slot
		output.putInt(0x1C, 0); // byte
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
		lobbyServer.sendRoomMessage(userSession, getResponse(), true);
	}

	@Override
	public void processMessage() {
		// TODO Auto-generated method stub
		
	}
}
