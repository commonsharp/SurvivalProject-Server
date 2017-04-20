package lobby.handlers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DatabaseConnection;
import database.DatabaseHelper;
import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
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
		
		if (lobbyServer.findUserSession(username) != null || DatabaseHelper.isUserExists(username)) {
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
				otherUserSession.sendMessage(new MemoArrivalHandler(lobbyServer, userSession).getResponse(
						userSession.getUser().username, messageType, levelAndGender, unknown2, text));
			}
			else {
				Connection con = DatabaseConnection.getConnection();
				PreparedStatement ps = con.prepareStatement("INSERT INTO memo (from_username, to_username, message_type, level_and_gender, unknown2, message_text) VALUES (?, ?, ?, ?, ?, ?);");
				ps.setString(1, userSession.getUser().username);
				ps.setString(2, username);
				ps.setInt(3, messageType);
				ps.setInt(4, levelAndGender);
				ps.setInt(5, unknown2);
				ps.setString(6, text);
				ps.executeUpdate();
				ps.close();
				con.close();
			}
		}
	}
}
