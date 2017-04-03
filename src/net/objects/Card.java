package net.objects;

public class Card {
	protected int id;
	protected int premiumDays;
	protected int level;
	protected int skill;
	
	int BootSkill1[] = {110,111,112,113,156,157,158,159,160,161,162,163,164,165,166,167,168,169,170,171};
	int ArmorSkill1[] = {102,103,104,105,106,107,108,109,142,143,144,145,146,147,148,149,150,151,152,153,154,155};
	int WeaponSkill1[] = {100,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,139,140,141};
	int ShieldSkill1[] = {102,103,104,105,106,107,108,109,142,143,144,145,146,147,148,149,150,151,152,153,154,155};
	int ShieldSkill2[] = {301,302,303,304,305,306,307,308,310,311,312,313,314,315,325,326,327,328,329,330,349,350,351,352,353,354,355,356,357,358,359,360,328,329,330,331,332,333,334,335,336,337,338,339,340,341,342,343,344,345,346,347,348,373,374,375,376,377,378,379,380,381,382,383,384,385,386,387,388,389,390,391,392,393,394,395,396,397,398,399,400,401,402,403,404,405,406,407,408,409,410,411,412,413,414,415,416,417,418,419,420,421,422,423,424,425,426,427,428,429,430,431,432,433,434,435};
	int PendantSkill1[] = {0,0,0,0,0};
	int PendantSkill2[] = {308,310,311,312,349,350,357,358,359,360};
	int BootSkill2[] = {310,311,312,313,349,350,351,352,361,362,363,364,365,366,367,368,369,370,371,372};
	int MeleeWpnSkill2[] = {200,201,300,301,302,303,304,306,314,315,316,317,318,319,320,321,322,323,324,325,326,327,328,329,330,331,332,333,334,335,336,337,338,339,340,341,342,343,344,345,346,347,348,373,374,375,376,377,378,379,380,381,382,383,384,385,386,387,388,389,390,391,392,393,394,395,396,397,398,399,400,401,402,403,404,405,406,407,408,409,410,411,412,413,414,415,416,417,418,419,420,421,422,423,424,425,426,427,428,429,430,431,432,433,434,435};
	int RangedWpnSkill2[] = {200,201,202,203,300,301,302,303,304,306,314,315,316,317,318,319,320,321,322,323,324,325,326,327,328,329,330,331,332,333,334,335,336,337,338,339,340,341,342,343,344,345,346,347,348,373,374,375,376,377,378,379,380,381,382,383,384,385,386,387,388,389,390,391,392,393,394,395,396,397,398,399,400,401,402,403,404,405,406,407,408,409,410,411,412,413,414,415,416,417,418,419,420,421,422,423,424,425,426,427,428,429,430,431,432,433,434,435};
	int MagicSkill[] = {300,301,302,303,304,306,314,315,316,317,318,319,320,321,322,323,324,325,326,327,328,329,330,331,332,333,334,335,336,337,338,339,340,341,342,343,344,345,346,347,348,373,374,375,376,377,378,379,380,381,382,383,384,385,386,387,388,389,390,391,392,393,394,395,396,397,398,399,400,401,402,403,404,405,406,407,408,409,410,411,412,413,414,415,416,417,418,419,420,421,422,423,424,425,426,427,428,429,430,431,432,433,434,435};
	
	public static final int WEAPON = 1;
	public static final int ACCESSORY = 2;
	public static final int MAGIC = 3;
	
	public static final int AXE = 1;
	public static final int STAFF = 2;
	public static final int SWORD = 3;
	public static final int BOW = 4;
	
	public static final int SHIELD = 1;
	public static final int PENDANT = 2;
	public static final int ARMOR = 3;
	public static final int BOOTS = 4;
	
	public enum CardType {
		WEAPON(1), ACCESSORY(2), MAGIC(3);
		
		private final int id;
		
		CardType(int id) {
			this.id = id;
		}
		
	    public int getValue() {
	    	return id;
	    }
	}
	
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
	
	public int getCardType() {
		return (id / 100) % 10;
	}
	
	public int getElement() {
		return (id / 10) % 10;
	}
	
	public int getSubType() {
		return id % 10;
	}

	public int getRandomSkill() {
		/*
		 * First 3 digits - x
		 * Next 3 digits - x
		 * Last 3 digits - skill activation success chance
		 */
		int chance = 100;
		int firstSkill = 200;
//		int firstSkill = ArmorSkill1[(int)(Math.random() * ArmorSkill1.length)];
		int secondSkill = 0;
		
//		return 100201000;
		// 033200000
		return chance * 1000000 + firstSkill * 1000 + secondSkill;
	}
}
