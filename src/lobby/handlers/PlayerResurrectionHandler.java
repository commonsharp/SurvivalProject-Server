package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class PlayerResurrectionHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x20;
	
	protected int slot;
	protected int x, y;
	
	public PlayerResurrectionHandler(LobbyServer lobbyServer, UserTCPSession userSession) {
		super(lobbyServer, userSession);
	}
	
	public PlayerResurrectionHandler(LobbyServer lobbyServer, UserTCPSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		slot = input.getInt(0x14); // maybe
		x = input.getInt(0x18);
		y = input.getInt(0x1C);
	}

	@Override
	public byte[] getResponse() {
		return getResponse(slot, x, y);
	}
	
	public byte[] getResponse(int slot, int x, int y) {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.PLAYER_RESURRECTION_RESPONSE);
		output.putInt(0x14, slot);
		output.putInt(0x18, x);
		output.putInt(0x1C, y);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
		
	}

	@Override
	public void processMessage() {
		// TODO Auto-generated method stub
		
	}
}
