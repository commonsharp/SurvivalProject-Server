package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class FindUserHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x4C;
	
	protected String username;
	
	public FindUserHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		username = input.getString(0x14);
	}

	@Override
	public void processMessage() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);

		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.FIND_USER_RESPONSE);
		
		UserSession userSession = lobbyServer.findUserSession(username);
		
		int isConnected = 0;
		int channelType = 0;
		int roomIndex = -1;
		
		if (userSession == null) {
			isConnected = -1;
		}
		else {
			channelType = userSession.getUser().userType / 10;
			
			if (userSession.getUser().isInRoom) {
				roomIndex = userSession.getUser().roomIndex + 1;
			}
			else {
				roomIndex = 0;
			}
		}
		
		output.putString(0x14, username); // this value is not getting read by the client. it was probably the username in older versions.
		output.putString(0x21, "some random channel");
		output.putInt(0x40, roomIndex); // -1 = not in this server. 0 = in lobby. > 0 = the room number
		output.putInt(0x44, channelType); // 1 = beginner. 2 = hero. 3 = epic. you can also set this to -1 for a not connected message
		output.putInt(0x48, isConnected); // is connected. -1 = not connected. other values = connected
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
