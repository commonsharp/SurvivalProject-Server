package database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Database {
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
				e.printStackTrace();
				StandardServiceRegistryBuilder.destroy(registry);
			}
		}
	}
	
	public static void closeDatabase() {
		if (sessionFactory != null) {
			sessionFactory.close();
		}
	}
}
