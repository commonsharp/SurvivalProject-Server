package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class MemoArrivalHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0xA8;
	
	public MemoArrivalHandler(LobbyServer lobbyServer, UserSession userSession) {
		super(lobbyServer, userSession);
	}

	@Override
	public void interpretBytes() {
		
	}

	@Override
	public void processMessage() throws SQLException {
		
	}

	@Override
	public byte[] getResponse() throws SQLException {
		return null;
	}
	
	public byte[] getResponse(String username, int messageType, int levelAndGender, int unknown2, String text) {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.MEMO_ARRIVAL_RESPONSE);
		
		output.putString(0x14, username);
		output.putInt(0x24, messageType);
		output.putInt(0x28, levelAndGender);
		output.putInt(0x2C, unknown2);
		output.putString(0x30, text);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		
	}
}
