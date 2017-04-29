package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import main.Main;

public class Database {
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
	
	private static SessionFactory sessionFactory;
	
	public static Session getSession() {
//		Session session = Database.getSession();
//		session.beginTransaction();
//		session.getTransaction().commit();
//		session.close();
		
		if (sessionFactory == null) {
			return null;
		}
		
		return sessionFactory.openSession();
	}
	
	public static void setupDatabase() {
		if (sessionFactory == null) {
			final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
			
			try {
				sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
			}
			catch (Exception e) {
				StandardServiceRegistryBuilder.destroy(registry);
			}
		}
	}
	
	public static void closeDatabase() {
		if (sessionFactory != null) {
			sessionFactory.close();
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
