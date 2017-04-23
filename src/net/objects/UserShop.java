package net.objects;

public class UserShop {
	String username;
	int cardIndex;
	int cardID;
	int cardPremiumDays;
	int cardLevel;
	int cardSkill;
	long code;
	
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
	
	public String getUsername() {
		return username;
	}
	
	public int getCardIndex() {
		return cardIndex;
	}
	
	public long getCode() {
		return code;
	}
	
	public int getCardID() {
		return cardID;
	}

	public int getCardPremiumDays() {
		return cardPremiumDays;
	}

	public int getCardLevel() {
		return cardLevel;
	}

	public int getCardSkill() {
		return cardSkill;
	}
}
