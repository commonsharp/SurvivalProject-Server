package net.objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table (name = "user_shop")
public class UserShop {
	private int id;
	private String username;
	private int cardIndex;
	private int cardID;
	private int cardPremiumDays;
	private int cardLevel;
	private int cardSkill;
	private long code;
	
	public UserShop() {
		
	}
	
	public UserShop(String username, int cardID, int cardPremiumDays, int cardLevel, int cardSkill, long code) {
		this(username, 0, cardID, cardPremiumDays, cardLevel, cardSkill, code);
	}
	
	public UserShop(String username, int cardIndex, int cardID, int cardPremiumDays, int cardLevel, int cardSkill, long code) {
		this.username = username;
		this.cardID = cardID;
		this.cardIndex = cardIndex;
		this.cardPremiumDays = cardPremiumDays;
		this.cardLevel = cardLevel;
		this.cardSkill = cardSkill;
		this.code = code;
	}
	
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	@Column(name = "shop_id")
	public int getId() {
		return id;
	}
	
	@Column(name = "username")
	public String getUsername() {
		return username;
	}
	
	@Transient
	public int getCardIndex() {
		return cardIndex;
	}
	
	@Column(name = "code")
	public long getCode() {
		return code;
	}
	
	@Column(name = "card_id")
	public int getCardID() {
		return cardID;
	}

	@Column(name = "card_premium_days")
	public int getCardPremiumDays() {
		return cardPremiumDays;
	}

	@Column(name = "card_level")
	public int getCardLevel() {
		return cardLevel;
	}

	@Column(name = "card_skill")
	public int getCardSkill() {
		return cardSkill;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setCardIndex(int cardIndex) {
		this.cardIndex = cardIndex;
	}

	public void setCardID(int cardID) {
		this.cardID = cardID;
	}

	public void setCardPremiumDays(int cardPremiumDays) {
		this.cardPremiumDays = cardPremiumDays;
	}

	public void setCardLevel(int cardLevel) {
		this.cardLevel = cardLevel;
	}

	public void setCardSkill(int cardSkill) {
		this.cardSkill = cardSkill;
	}

	public void setCode(long code) {
		this.code = code;
	}

	public void setId(int id) {
		this.id = id;
	}
}
