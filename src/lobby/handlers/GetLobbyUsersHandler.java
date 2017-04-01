package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class GetLobbyUsersHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x3C;
	
	public GetLobbyUsersHandler(LobbyServer lobbyServer, UserTCPSession userSession) {
		super(lobbyServer, userSession);
	}
	
	public GetLobbyUsersHandler(LobbyServer lobbyServer, UserTCPSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		// no fields
	}

	@Override
	public void processMessage() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.GET_LOBBY_USERS_RESPONSE);
		output.putString(0x14, "baraak"); // name
		output.putInt(0x24, 30); // level
		output.putInt(0x28, 2);
		output.putInt(0x2C, 2);
		output.putBoolean(0x30, true); // isMale
		output.putBoolean(0x31, true); // is online
		output.putInt(0x34, 2);
		output.putInt(0x38, 40); // mission level
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		// The friends list is not being sent to the room screen, so we have to send it
//		sendTCPMessage(new GetFriendsHandler(lobbyServer, userSession).getResponse());
	}

}
