package net.objects;

public class Card {
	protected int id;
	protected int premiumDays;
	protected int level;
	protected int skill;
	
	public Card(int id, int premiumDays, int level, int skill) {
		this.id = id;
		this.premiumDays = premiumDays;
		this.level = level;
		this.skill = skill;
	}

	public int getId() {
		return id;
	}
	
	public int getPremiumDays() {
		return premiumDays;
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getSkill() {
		return skill;
	}

	public void setPremiumDays(int premiumDays) {
		this.premiumDays = premiumDays;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setSkill(int skill) {
		this.skill = skill;
	}
}
