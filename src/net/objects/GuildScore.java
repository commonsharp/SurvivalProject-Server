package net.objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "guild_score")
public class GuildScore {
	private int id;
	private int serverID;
	private String guildName;
	private int score;

	public GuildScore() {
		super();
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
}
