package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserTCPSession;
import net.objects.User;
import tools.ExtendedByteBuffer;

public class FindUserHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x4C;
	
	protected String username;
	
	public FindUserHandler(LobbyServer lobbyServer, UserTCPSession userSession, byte[] messageBytes) {
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
		
		User user = lobbyServer.findUserSession(username).getUser();
		
		int isConnected = 0;
		int channelType = 0;
		int roomIndex = -1;
		
		if (user == null) {
			isConnected = -1;
		}
		else {
			channelType = user.playerChannelType;
			
			if (user.isInRoom) {
				roomIndex = user.roomIndex + 1;
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
