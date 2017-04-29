package net.objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "guild_member")
public class GuildMember {
	private int id;
	private String guildName;
	private String username;

	public GuildMember() {
		
	}
	
	public GuildMember(String guildName, String username) {
		this.guildName = guildName;
		this.username = username;
	}
	
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	@Column(name = "id")
	public int getId() {
		return id;
	}

	@Column(name = "guild_name")
	public String getGuildName() {
		return guildName;
	}

	@Column(name = "username")
	public String getUsername() {
		return username;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setGuildName(String guildName) {
		this.guildName = guildName;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
