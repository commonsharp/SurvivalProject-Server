package net.objects;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.annotations.GenericGenerator;

import database.Database;
import net.UserSession;

@Entity
@Table(name = "guild")
public class Guild {
	private int guildID;
	private String guildName;

	public Guild() {
	}

	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	@Column(name = "guild_id")
	public int getGuildID() {
		return guildID;
	}

	@Column(name = "guild_name")
	public String getGuildName() {
		return guildName;
	}

	public void setGuildID(int guildID) {
		this.guildID = guildID;
	}

	public void setGuildName(String guildName) {
		this.guildName = guildName;
	}
	
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
		session.save(new GuildMember(guildName, userSession.getUser().getUsername()));
		session.getTransaction().commit();
		session.close();
		
		userSession.getUser().setGuildName(guildName);
		User.saveUser(userSession.getUser());
	}

	public static void leaveGuild(UserSession userSession) throws SQLException {
		Session session = Database.getSession();
		session.beginTransaction();
		session.createQuery("delete from GuildMember where username = :username").setParameter("username", userSession.getUser().getUsername()).executeUpdate();
		session.getTransaction().commit();
		session.close();
		
		userSession.getUser().setGuildName(null);
		userSession.getUser().setGuildDuty(null);
		User.saveUser(userSession.getUser());
	}
}
