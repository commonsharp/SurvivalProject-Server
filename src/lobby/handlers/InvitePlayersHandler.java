package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import net.objects.Room;
import tools.ExtendedByteBuffer;

public class InvitePlayersHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x5D;
	
	String fromUsername;
	String toUsername;
	int roomID;
	
	int inviteType;
	
	public InvitePlayersHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		fromUsername = input.getString(0x14); // my username
		toUsername = input.getString(0x21);
		roomID = input.getInt(0x30);
		inviteType = input.getInt(0x34); // 1 - invite someone. 2 - rejected
		
		System.out.println("from : " + fromUsername + " to " + toUsername);
	}

	@Override
	public void processMessage() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getResponse() throws SQLException {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		
		/*
		 * 1 - okay
		 * 2 - Player %s has &#xA;denied invitation.
		 * 3 - Player %s is not&#xA;connected.
		 * 4 - Players %s is not in &#xA;the lobby. Try using &#xA;search function.
		 * 5 - You can not invite users that &#xA;are 2 ranks higher.
		 * 6 - Cannot invite observer
		 */
		int response = inviteType;
		Room room = lobbyServer.getRoom(roomID);
		
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.INVITE_PLAYER_RESPONSE);
		output.putString(0x14, fromUsername);
		output.putString(0x21, toUsername);
		output.putInt(0x40, roomID);
		output.putInt(0x44, room.getGameMode().getValue());
		output.putInt(0x48, room.getGameMap());
		output.putInt(0x4C, response);
//		output.putString(0x50, "hello"); // this is another string. possibly the "reject message". it's blacked out though.
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		lobbyServer.findUserSession(toUsername).sendMessage(getResponse());
	}

}
