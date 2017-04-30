package net.objects;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.query.Query;

import database.Database;
import lobby.LobbyServer;
import net.ExperienceHelper;

@Entity
@Table(name = "guild_score")
public class GuildScore {
	private int id;
	private int serverID;
	private String guildName;
	private int score;

	public GuildScore() {
	}
	
	public GuildScore(int serverID, String guildName, int score) {
		this.serverID = serverID;
		this.guildName = guildName;
		this.score = score;
	}
	
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	@Column(name = "id")
	public int getId() {
		return id;
	}

	@Column(name = "server_id")
	public int getServerID() {
		return serverID;
	}

	@Column(name = "guild_name")
	public String getGuildName() {
		return guildName;
	}

	@Column(name = "guild_score")
	public int getScore() {
		return score;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setServerID(int serverID) {
		this.serverID = serverID;
	}
	
	public void setGuildName(String guildName) {
		this.guildName = guildName;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	@SuppressWarnings("unchecked")
	public static void updateGuildPoints(LobbyServer lobbyServer, Room room, int[] slots, int[] experienceGained, int[] luckyMultiplier) {
		Session session = Database.getSession();
		session.beginTransaction();
		Query<GuildScore> query = session.createQuery("from GuildScore where serverID = :serverID and guildName = :guildName");
		
		int guildPoints;
		for (int i = 0; i < slots.length; i++) {
			if (slots[i] < 8 && room.getUserSession(slots[i]) != null) {
				guildPoints = ExperienceHelper.experienceToGuildPoints(experienceGained[i] * luckyMultiplier[i], room.getGameMode());
				
				if (guildPoints != 0) {
					query.setParameter("serverID", lobbyServer.server.getId());
					query.setParameter("guildName", room.getUserSession(slots[i]).getUser().getGuildName());
					List<GuildScore> guildScores = query.list();
					
					if (guildScores.isEmpty()) {
						session.save(new GuildScore(lobbyServer.server.getId(), room.getUserSession(slots[i]).getUser().getGuildName(), guildPoints));
					}
					else {
						guildScores.get(0).setScore(guildScores.get(0).getScore() + guildPoints);
						session.update(guildScores.get(0));
					}
				}
			}
		}
		
		session.getTransaction().commit();
		session.close();
	}
	
	@SuppressWarnings("unchecked")
	public static void updateGuildPoints(LobbyServer lobbyServer, Room room, int[] experienceGained, int[] luckyMultiplier) {
		Session session = Database.getSession();
		session.beginTransaction();
		Query<GuildScore> query = session.createQuery("from GuildScore where serverID = :serverID and guildName = :guildName");
		
		int guildPoints;
		for (int i = 0; i < experienceGained.length; i++) {
			if (room.getUserSession(i) != null) {
				guildPoints = ExperienceHelper.experienceToGuildPoints(experienceGained[i] * luckyMultiplier[i], room.getGameMode());
				
				if (guildPoints != 0) {
					query.setParameter("serverID", lobbyServer.server.getId());
					query.setParameter("guildName", room.getUserSession(i).getUser().getGuildName());
					List<GuildScore> guildScores = query.list();
					
					if (guildScores.isEmpty()) {
						session.save(new GuildScore(lobbyServer.server.getId(), room.getUserSession(i).getUser().getGuildName(), guildPoints));
					}
					else {
						guildScores.get(0).setScore(guildScores.get(0).getScore() + guildPoints);
						session.update(guildScores.get(0));
					}
				}
			}
		}
		
		session.getTransaction().commit();
		session.close();
	}
}
