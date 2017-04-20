package login.handlers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DatabaseConnection;
import login.LoginHandler;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

// request - V
public class SetMainCharacterHandler extends LoginHandler {
	public static final int RESPONSE_LENGTH = 0x1C;

	protected String username;
	
	public SetMainCharacterHandler(UserSession tcpServer, byte[] messageBytes) {
		super(tcpServer, messageBytes);
	}

	@Override
	public void interpretBytes() {
		username = input.getString(0x14);
		userSession.getUser().mainCharacter = input.getInt(0x24);
	}

	@Override
	public void afterSend() throws IOException {
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.SET_MAIN_CHARACTER_RESPONSE);
		output.putInt(0x14, 1);
		output.putInt(0x18, userSession.getUser().mainCharacter);
		
		return output.toArray();
	}

	@Override
	public void processMessage() throws SQLException {
		Connection conn = DatabaseConnection.getConnection();
		PreparedStatement ps = conn.prepareStatement("Update users SET mainCharacter = ? WHERE username = ?");
		ps.setInt(1, userSession.getUser().mainCharacter);
		ps.setString(2, username);
		
		ps.executeUpdate();
		
		ps.close();
		conn.close();
	}
}
