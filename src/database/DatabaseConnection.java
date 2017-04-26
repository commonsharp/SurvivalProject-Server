package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import main.Main;

public class DatabaseConnection {
	private static final String USERNAME;
	private static final String PASSWORD;
	
	static {
		if (Main.IS_RELEASE) {
			USERNAME = "bak";
			PASSWORD = "spgame101";
		}
		else {
			USERNAME = "root";
			PASSWORD = "1234";
		}
	}
	   
	public static Connection getConnection() {
		Connection conn = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			try {
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/spgame?autoReconnect=true&useSSL=false", USERNAME, PASSWORD);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;
	}
}
