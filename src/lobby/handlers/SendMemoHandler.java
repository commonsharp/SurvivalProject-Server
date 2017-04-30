package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import org.hibernate.Session;

import database.Database;
import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import net.objects.Memo;
import net.objects.User;
import tools.ExtendedByteBuffer;

public class SendMemoHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x28;
	
	protected String username;
	int messageType;
	int levelAndGender;
	int unknown2;
	String text;
	
	int response;
	
	public SendMemoHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		username = input.getString(0x14);
		messageType = input.getInt(0x24);
		levelAndGender = input.getInt(0x28);
		unknown2 = input.getInt(0x2C);
		System.out.println(messageType); // 5 or 14.
		System.out.println(levelAndGender); // 0 if 0x24=14.
		System.out.println(unknown2); // 0 if 0x24=14.
		text = input.getString(0x30);
	}

	@Override
	public void processMessage() throws SQLException {
	}

	@Override
	public byte[] getResponse() throws SQLException {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.SEND_MEMO_RESPONSE);
		
		/*
		 * 1 - Send memo complete!!&#xA;Player (%s) will be receiving &#xA;your memo.
		 * 2 - Send memo failed.&#xA;Because of an exceptional error.
		 * 3 - You do not have enough&#xA;Code(%d) to send memo.&#xA;
		 * 4 - Send memo failed.&#xA;Because the server is busy. &#xA;&#xA;Please try again.
		 * 5 - kill notice???
		 */
		
		if (lobbyServer.findUserSession(username) != null || User.isUserExists(username)) {
			response = 1;
		}
		else {
			response = 2;
		}
		
		output.putInt(0x14, response);
		output.putString(0x18, username);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		if (response == 1) {
			UserSession otherUserSession = lobbyServer.findUserSession(username);
			
			if (otherUserSession != null) {
				otherUserSession.sendMessage(new MemoArrivalHandler(lobbyServer, userSession).getResponse(new Memo(
						userSession.getUser().getUsername(), messageType, levelAndGender, unknown2, text)));
			}
			else {
				Session session = Database.getSession();
				session.beginTransaction();
				session.save(new Memo(userSession.getUser().getUsername(), username, messageType, levelAndGender, unknown2, text));
				session.getTransaction().commit();
				session.close();
			}
		}
	}
}
