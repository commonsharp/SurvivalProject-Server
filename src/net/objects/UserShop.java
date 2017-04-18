package net.objects;

public class UserShop {
	String username;
	int cardID;
	int cardPremiumDays;
	int cardLevel;
	int cardSkill;
	long code;
	
	public UserShop(String username, int cardID, int cardPremiumDays, int cardLevel, int cardSkill, long code) {
		this.username = username;
		this.cardID = cardID;
		this.cardPremiumDays = cardPremiumDays;
		this.cardLevel = cardLevel;
		this.cardSkill = cardSkill;
		this.code = code;
	}
	
	public String getUsername() {
		return username;
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
