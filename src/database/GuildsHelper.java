package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.UserSession;

public class GuildsHelper {
	public static boolean isGuildExists(String guildName) throws SQLException {
		Connection con = DatabaseConnection.getConnection();
		PreparedStatement ps = con.prepareStatement("SELECT * FROM guild WHERE guildName = ?");
		ps.setString(1, guildName);
		ResultSet rs = ps.executeQuery();

		boolean result = rs.next();
		
		rs.close();
		ps.close();
		con.close();
		
		return result;
	}
	
	public static boolean isGuildMemberExists(String guildName, String username) throws SQLException {
		Connection con = DatabaseConnection.getConnection();
		PreparedStatement ps = con.prepareStatement("SELECT * FROM guild_member WHERE guildName = ? AND username = ?;");
		ps.setString(1, guildName);
		ps.setString(2, username);
		ResultSet rs = ps.executeQuery();

		boolean result = rs.next();
		
		rs.close();
		ps.close();
		con.close();
		
		return result;
	}
	
	public static void joinGuild(String guildName, UserSession userSession) throws SQLException {
		Connection con = DatabaseConnection.getConnection();
		PreparedStatement ps = con.prepareStatement("INSERT INTO guild_member VALUES (?, ?);");
		ps.setString(1, guildName);
		ps.setString(2, userSession.getUser().username);
		ps.executeUpdate();
		ps.close();
		con.close();
		
		userSession.getUser().guildName = guildName;
		userSession.getUser().saveUser();
	}

	public static void leaveGuild(UserSession userSession) throws SQLException {
		Connection con = DatabaseConnection.getConnection();
		PreparedStatement ps = con.prepareStatement("DELETE FROM guild_member WHERE username=?;");
		ps.setString(1, userSession.getUser().username);
		ps.executeUpdate();
		ps.close();
		con.close();
		
		userSession.getUser().guildName = null;
		userSession.getUser().guildDuty = null;
	}
}