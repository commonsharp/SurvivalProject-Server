package net.objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "gift")
public class Gift {
	private int id;
	private String fromUsername;
	private String toUsername;
	private int giftType;
	private long amount;
	private int cardID;
	private int cardPremiumDays;
	private int cardLevel;
	private int cardSkill;
	
	public Gift() {
	}

	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	@Column(name = "gift_id")
	public int getId() {
		return id;
	}

	@Column(name = "from_username")
	public String getFromUsername() {
		return fromUsername;
	}

	@Column(name = "to_username")
	public String getToUsername() {
		return toUsername;
	}

	@Column(name = "gift_type")
	public int getGiftType() {
		return giftType;
	}

	@Column(name = "amount")
	public long getAmount() {
		return amount;
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

	public void setId(int id) {
		this.id = id;
	}

	public void setFromUsername(String fromUsername) {
		this.fromUsername = fromUsername;
	}

	public void setToUsername(String toUsername) {
		this.toUsername = toUsername;
	}

	public void setGiftType(int giftType) {
		this.giftType = giftType;
	}

	public void setAmount(long amount) {
		this.amount = amount;
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
}
