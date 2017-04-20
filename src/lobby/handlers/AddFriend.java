package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import database.DatabaseHelper;
import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class AddFriend extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x28;
	
	protected String friendName;
	protected boolean isAdd;
	
	protected int response;
	
	public AddFriend(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		friendName = input.getString(0x14);
		isAdd = input.getInt(0x24) == 1; // 2 for delete
	}

	@Override
	public void processMessage() throws SQLException {
		if (isAdd) {
			if (DatabaseHelper.isUserExists(friendName)) {
				userSession.getUser().addFriend(friendName);
				response = 1;
			}
			else {
				response = 2;
			}
		}
		else {
			// I could verify if the friend exists, but if for some reason it got deleted, the user won't be able to delete this user
//			if (DatabaseHelper.isUserExists(friendName)) {
				userSession.getUser().removeFriend(friendName);
				response = 3;
//			}
//			else {
//				response = 4;
//			}
		}
		
		userSession.getUser().saveUser();
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.ADD_FRIEND_RESPONSE);
		output.putString(0x14, friendName);
		
		/*
		1 - Player %s has been &#xA;registered in the friend list.
		2 - Failed to register friend.
		3 - Player %s has been &#xA;deleted from friend list.
		4 - Failed to delete friend.
		 */
		output.putInt(0x24, response);
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		sendTCPMessage(new GetFriendsHandler(lobbyServer, userSession).getResponse());
	}

}
