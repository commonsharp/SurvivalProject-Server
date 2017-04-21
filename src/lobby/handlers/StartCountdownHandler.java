package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class StartCountdownHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x118;
	
	public StartCountdownHandler(LobbyServer lobbyServer, UserSession userSession) {
		super(lobbyServer, userSession);
	}
	
	public StartCountdownHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		input.getInt(0x14);
		input.getInt(0x18);
		input.getInt(0x1C);
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.MISSION_START_COUNTDOWN_RESPONSE);
		
		output.putInt(0x14, 0);
		output.putInt(0x18, 0);
		output.putInt(0x1C, 600000);
		output.putInt(0x20, 0);
		
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
