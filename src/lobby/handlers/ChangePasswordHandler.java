package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class ChangePasswordHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x24;
	
	protected String newPassword;
	
	public ChangePasswordHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		newPassword = input.getString(0x14);
		System.out.println(newPassword);
	}

	@Override
	public void processMessage() throws SQLException {
		lobbyServer.getRoom(userSession.getUser().getRoomIndex()).password = newPassword;
	}

	@Override
	public byte[] getResponse() throws SQLException {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.CHANGE_PASSWORD_REQUEST);
		output.putString(0x14, newPassword);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		lobbyServer.sendBroadcastMessage(userSession, new LobbyRoomsChangedHandler(lobbyServer, userSession).getResponse(lobbyServer.getRoom(userSession.getUser().getRoomIndex())));
	}
}
