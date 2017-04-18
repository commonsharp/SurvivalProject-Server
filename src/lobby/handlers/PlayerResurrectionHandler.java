package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class PlayerResurrectionHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x20;
	
	protected int slot;
	protected int x, y;
	
	public PlayerResurrectionHandler(LobbyServer lobbyServer, UserSession userSession) {
		super(lobbyServer, userSession);
	}
	
	public PlayerResurrectionHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
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
		lobbyServer.sendRoomMessage(userSession, getResponse(), false);
	}

	@Override
	public void processMessage() {
		// TODO Auto-generated method stub
		
	}
}
