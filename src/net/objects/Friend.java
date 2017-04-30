package net.objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "friend")
public class Friend {
	private int id;
	private String username;
	private String friendName;
	
	public Friend() {
		
	}
	
	public Friend(String username, String friend) {
		this.username = username;
		this.friendName = friend;
	}

	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	public int getId() {
		return id;
	}
	
	@Column(name = "username")
	public String getUsername() {
		return username;
	}
	
	@Column(name = "friend")
	public String getFriendName() {
		return friendName;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setFriendName(String friend) {
		this.friendName = friend;
	}
}
