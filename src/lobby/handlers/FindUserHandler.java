package lobby.handlers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.Database;
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
	public byte[] getResponse() throws SQLException {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);

		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.FIND_USER_RESPONSE);
		
		UserSession userSession = lobbyServer.findUserSession(username);
		
		int isConnected = -1;
		int channelType = -1;
		int roomIndex = -1;
		String serverName = null;
		
		if (userSession == null) {
			Connection con = Database.getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT server_hostname, server_port, is_connected FROM users WHERE username = ?");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				if (rs.getBoolean("is_connected")) {
					String hostname = rs.getString("server_hostname");
					int port = rs.getInt("server_port");
					rs.close();
					ps.close();
					
					ps = con.prepareStatement("SELECT name, channelType FROM servers WHERE hostname = ? AND port = ?;");
					ps.setString(1, hostname);
					ps.setInt(2, port);
					rs = ps.executeQuery();
					
					if (rs.next()) {
						serverName = rs.getString("name");
						channelType = rs.getInt("channelType") + 1;
						isConnected = 0;
					}
					
					rs.close();
					ps.close();
				}
			}
			
			con.close();
		}
		else {
			isConnected = 0;
			channelType = userSession.getUser().channelType;
			
			if (userSession.getUser().isInRoom) {
				roomIndex = userSession.getUser().roomIndex + 1;
			}
			else {
				roomIndex = 0;
			}
		}
		
		output.putString(0x14, username); // this value is not getting read by the client. it was probably the username in older versions.
		output.putString(0x21, serverName);
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
