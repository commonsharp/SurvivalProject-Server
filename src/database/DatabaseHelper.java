package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHelper {
	public static boolean isUserExists(String username) throws SQLException {
		Connection con = Database.getConnection();
		PreparedStatement ps = con.prepareStatement("Select * FROM users WHERE username = ?");
		ps.setString(1, username);
		ResultSet rs = ps.executeQuery();
		
		boolean isExists = rs.next();
		
		rs.close();
		ps.close();
		con.close();
		
		return isExists;
	}
}