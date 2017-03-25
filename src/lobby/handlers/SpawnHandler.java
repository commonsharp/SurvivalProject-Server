package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class SpawnHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x38;

	public SpawnHandler(LobbyServer lobbyServer, UserTCPSession userSession) {
		super(lobbyServer, userSession);
	}

	@Override
	public void interpretBytes() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getResponse() {
		return null;
	}
	
	public byte[] getResponse(int unknown) {

		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.SPAWN_RESPONSE);
		
		int[] slots = new int[8];
		
		for (int i = 0; i < slots.length; i++) {
			slots[i] = i;
		}

		// each index should have its slot id
		output.putInts(0x14, slots);
		output.putInt(0x34, unknown); // this is not slot.
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processMessage() {
		// TODO Auto-generated method stub
		
	}

}
