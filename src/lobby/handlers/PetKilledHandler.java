package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class PetKilledHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x1C;
	
	protected int petID;
	protected int slot;
	
	public PetKilledHandler(LobbyServer lobbyServer, UserTCPSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		petID = input.getInt(0x14);
		slot = input.getInt(0x18);
	}

	@Override
	public void processMessage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.PET_KILLED_RESPONSE);
		output.putInt(0x14, petID);
		output.putInt(0x18, slot);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
		lobbyServer.sendRoomMessage(userSession, getResponse(), false);
	}

}
