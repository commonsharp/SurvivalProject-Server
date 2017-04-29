package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import net.objects.Friend;
import tools.ExtendedByteBuffer;

public class GetFriendsHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x1AC;
	
	public GetFriendsHandler(LobbyServer lobbyServer, UserSession userSession) {
		super(lobbyServer, userSession);
	}
	
	public GetFriendsHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		
	}

	@Override
	public void processMessage() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getResponse() throws SQLException {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.GET_FRIENDS_RESPONSE);
		
		List<Friend> friends = userSession.getUser().friends;
		
		int i = 0;
		for (Friend friend : friends) {
			output.putString(0x14 + 0xD * i, friend.getFriendName());
			
			if (lobbyServer.findUserSession(friend.getFriendName()) != null) {
				output.putInt(0x14C + 4 * i, 0); // connected
			}
			else {
				output.putInt(0x14C + 4 * i, -1); // disconnected
			}
			
			i++;
		}
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
	}
}
