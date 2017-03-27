package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class EnjoyModeDeathHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0xB4;
	
	public EnjoyModeDeathHandler(LobbyServer lobbyServer, UserTCPSession tcpServer) {
		super(lobbyServer, tcpServer);
	}

	@Override
	public void interpretBytes() {
	}

	public byte[] getResponse(int monsterIndex, int molePoints) {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);

		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.ENJOY_MODE_DEATH_RESPONSE);
		
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
		output.putInt(0x84, 0); // another array. unknown yet.
		output.putInt(0xA4, 2); // element type
		output.putInt(0xA8, 5); // element count
		output.putInt(0xAC, 4); // element multiplier
		output.putInt(0xB0, molePoints); // mole points
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
	}

	@Override
	public void processMessage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getResponse() {
		// TODO Auto-generated method stub
		return null;
	}
}
