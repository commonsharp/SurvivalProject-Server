package net.objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "memo")
public class Memo {
	private int id;
	private String fromUsername;
	private String toUsername;
	private int messageType;
	private int levelAndGender;
	private int unknown2;
	private String text;

	public Memo() {

	}
	
	public Memo(String toUsername, int messageType, int levelAndGender, int unknown2, String text) {
		this(null, toUsername, messageType, levelAndGender, unknown2, text);
	}
	
	public Memo(String fromUsername, String toUsername, int messageType, int levelAndGender, int unknown2, String text) {
		this.fromUsername = fromUsername;
		this.toUsername = toUsername;
		this.messageType = messageType;
		this.levelAndGender = levelAndGender;
		this.unknown2 = unknown2;
		this.text = text;
	}

	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	@Column(name = "memo_id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "from_username")
	public String getFromUsername() {
		return fromUsername;
	}

	public void setFromUsername(String fromUsername) {
		this.fromUsername = fromUsername;
	}

	@Column(name = "to_username")
	public String getToUsername() {
		return toUsername;
	}

	public void setToUsername(String toUsername) {
		this.toUsername = toUsername;
	}

	@Column(name = "message_type")
	public int getMessageType() {
		return messageType;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	@Column(name = "level_and_gender")
	public int getLevelAndGender() {
		return levelAndGender;
	}

	public void setLevelAndGender(int levelAndGender) {
		this.levelAndGender = levelAndGender;
	}

	@Column(name = "unknown2")
	public int getUnknown2() {
		return unknown2;
	}

	public void setUnknown2(int unknown2) {
		this.unknown2 = unknown2;
	}

	@Column(name = "message_text")
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
