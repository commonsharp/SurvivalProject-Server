package database;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;

import net.UserSession;
import net.objects.Guild;
import net.objects.GuildMember;

public class GuildsHelper {
//	@SuppressWarnings("unchecked")
//	public static String guildIDToName(int id) {
//		Session session = Database.getSession();
//		session.beginTransaction();
//		List<String> names = session.createQuery("SELECT guildName FROM Guild WHERE id = " + id).list();
//		
//		if (names.isEmpty()) {
//			return null;
//		}
//		
//		return names.get(0);
//	}
	
	@SuppressWarnings("unchecked")
	public static boolean isGuildExists(String guildName) throws SQLException {
		Session session = Database.getSession();
		session.beginTransaction();
		List<Guild> list = session.createQuery("FROM Guild WHERE guildName = :guildName").setParameter("guildName", guildName).list();
		boolean result = !list.isEmpty();
		session.getTransaction().commit();
		session.close();
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static boolean isGuildMemberExists(String guildName, String username) throws SQLException {
		Session session = Database.getSession();
		session.beginTransaction();
		List<Guild> list = session.createQuery("from GuildMember where guildName = :guildName and username = :username").
				setParameter("guildName", guildName).setParameter("username", username).list();
		boolean result = !list.isEmpty();
		session.getTransaction().commit();
		session.close();
		
		return result;
	}
	
	public static void joinGuild(String guildName, UserSession userSession) throws SQLException {
		Session session = Database.getSession();
		session.beginTransaction();
		session.save(new GuildMember(guildName, userSession.getUser().username));
		session.getTransaction().commit();
		session.close();
		
		userSession.getUser().guildName = guildName;
		userSession.getUser().saveUser();
	}

	public static void leaveGuild(UserSession userSession) throws SQLException {
		Session session = Database.getSession();
		session.beginTransaction();
		session.createQuery("delete from GuildMember where username = :username").setParameter("username", userSession.getUser().username).executeUpdate();
		session.getTransaction().commit();
		session.close();
		
		userSession.getUser().guildName = null;
		userSession.getUser().guildDuty = null;
		userSession.getUser().saveUser();
	}
}