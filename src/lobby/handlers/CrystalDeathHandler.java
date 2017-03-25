package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class CrystalDeathHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x90;
	
	int questProgression;
	int unknown;
	
	public CrystalDeathHandler(LobbyServer lobbyServer, UserTCPSession tcpServer, byte[] messageBytes) {
		super(lobbyServer, tcpServer, messageBytes);
	}

	@Override
	public void interpretBytes() {
		unknown = input.getInt(0x14); // 1, 2 or 3...
		questProgression = input.getInt(0x18); // 100% = quest completed
		System.out.println(unknown);
		System.out.println(questProgression);
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);

		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.CRYSTAL_DEATH_RESPONSE);
		
		int result = 3;
		if (questProgression == 100)
			result = 1;
		
		int[] slotResults = new int[8];
		
		for (int i = 0; i < 8; i++)
			slotResults[i] = result;
		
		 // result array. 0 = resurrection. 1 = quest success. 2 = quest failed. 4 = player left game? others = nothing.
		output.putInts(0x14, slotResults); // result (per slot)
		output.putInt(0x34, 13); //ko
		output.putInt(0x38, 15); // ? maybe guild exp?
		output.putInt(0x3C, 200); // exp array (per slot)
		output.putInt(0x5C, 300); // code array (per slot)
		output.putLong(0x80, 400); // new exp
		output.putLong(0x88, 500); // new code
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
//		lobby.roomMessage(userSession.getUser().roomIndex, getResponse());
	}

	@Override
	public void processMessage() {
		// TODO Auto-generated method stub
		
	}

}
