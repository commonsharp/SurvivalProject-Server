package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class ChangeCharacterHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x1C;
	
	protected int character;
	
	public ChangeCharacterHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		character = input.getInt(0x14);
	}

	@Override
	public void processMessage() throws SQLException {
	}

	@Override
	public byte[] getResponse() throws SQLException {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.CHANGE_CHARACTER_RESPONSE);
		output.putInt(0x14, 1); // 1 or 2
		output.putInt(0x18, character);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		lobbyServer.sendBroadcastMessage(userSession, new LobbyRoomsChangedHandler(lobbyServer, userSession).getResponse(lobbyServer.getRoom(userSession.getUser().roomIndex)));
	}

}
