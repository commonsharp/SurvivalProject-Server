package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHelper {
	public static boolean isUserExists(String username) throws SQLException {
		Connection con = DatabaseConnection.getConnection();
		PreparedStatement ps = con.prepareStatement("Select * FROM users WHERE username = ?");
		ps.setString(1, username);
		ResultSet rs = ps.executeQuery();
		
		boolean isExists = rs.next();
		
		rs.close();
		ps.close();
		con.close();
		
		return isExists;
	}
	
	public static boolean isConnected(String username) throws SQLException {
		boolean isConnected = false;
		
		Connection con = DatabaseConnection.getConnection();
		PreparedStatement ps = con.prepareStatement("Select isConnected FROM users WHERE username = ?");
		ps.setString(1, username);
		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			isConnected = rs.getBoolean("isConnected");
		}
		
		rs.close();
		ps.close();
		con.close();
		
		return isConnected;
	}
}