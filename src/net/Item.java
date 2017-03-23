package net;

public class Item {
	protected int id;
	protected int premiumDays;
	protected int level;
	protected int skill;
	
	public Item(int id, int premiumDays, int level, int skill) {
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
}
