package lobby.handlers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DatabaseConnection;
import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class GetTopGuildsHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0xF0;
	
	public GetTopGuildsHandler(LobbyServer lobbyServer, UserSession tcpServer, byte[] messageBytes) {
		super(lobbyServer, tcpServer, messageBytes);
	}

	@Override
	public void interpretBytes() {
	}

	@Override
	public void afterSend() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getResponse() throws SQLException {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.GET_TOP_GUILDS_RESPONSE);
		// If your clan is in this list, then 9 0x4486 requests are being sent to the server.
		// Otherwise, 10 0x4486 requests are being sent to the server...
		
		Connection con = DatabaseConnection.getConnection();
		PreparedStatement ps = con.prepareStatement("SELECT * FROM guild_score WHERE server_hostname=? AND server_port=? ORDER BY guild_score DESC LIMIT 10;");
		ps.setString(1, LobbyServer.HOSTNAME);
		ps.setInt(2, LobbyServer.PORT);
		ResultSet rs = ps.executeQuery();
		
		for (int i = 0; i < 10 && rs.next(); i++) {
			output.putString(0x14 + 0xD * i, rs.getString("guild_name"));
			output.putInt(0x98 + 4 * i, rs.getInt("guild_score"));
		}
		
		rs.close();
		ps.close();
		
		if (userSession.getUser().guildName != null) {
			int myScore = 0;
			int myRank = 0;
			
			ps = con.prepareStatement("SELECT guild_score FROM guild_score WHERE server_hostname = ? AND server_port = ? AND guild_name = ?");
			ps.setString(1, LobbyServer.HOSTNAME);
			ps.setInt(2, LobbyServer.PORT);
			ps.setString(3, userSession.getUser().guildName);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				myScore = rs.getInt("guild_score");
				rs.close();
				
				ps = con.prepareStatement("SELECT Count(*) FROM guild_score WHERE server_hostname = ? AND server_port = ? AND guild_score > ?");
				ps.setString(1, LobbyServer.HOSTNAME);
				ps.setInt(2, LobbyServer.PORT);
				ps.setInt(3, myScore);
				
				rs = ps.executeQuery();
				
				if (rs.next()) {
					myRank = rs.getInt(1) + 1;
				}
				
				rs.close();
			}
			
			ps.close();
			con.close();
			
			userSession.getUser().guildRank = myRank - 1;
			output.putInt(0xE8, myRank - 1);
			output.putInt(0xEC, myScore);
		}
		
		output.putInt(0xC0, 0); // another array (10 integers)
		
		rs.close();
		ps.close();
		
		return output.toArray();
	}

	@Override
	public void processMessage() {
		// TODO Auto-generated method stub
		
	}
}
