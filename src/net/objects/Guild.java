package net.objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

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
}
