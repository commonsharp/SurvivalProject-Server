package net.objects;

public class Card {
	protected int id;
	protected int index;
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
	
	public static final int MASTER = 2000;
	public static final int USERSHOP = 2001;
	public static final int ANGEL_EYE = 2002;
	public static final int QUEST_LIFE = 2003;
	public static final int CARD_SLOT6 = 2004;
	public static final int CARD_SLOT12 = 2005;
	public static final int VISIT_BONUS = 2006;
	public static final int SKULL_BOOSTER = 2007;
	public static final int STAR_BOOSTER = 2008;
	public static final int USERSHOP2 = 2009;
	public static final int TIME_BONUS = 2010;
	public static final int USERSHOP2_AGAIN = 2011;
	public static final int MOON_BOOSTER = 2012;
	public static final int LEVEL_FUSION = 2013;
	public static final int SKILL_FUSION = 2014;
	public static final int SKILL1_FUSION = 2015;
	public static final int SKILL2_FUSION = 2016;
	public static final int ELEMENTAL_BONUS = 2017;
	public static final int SKILL_1_1_FUSION = 2018;
	public static final int SKILL_2_1_FUSION = 2019;
	public static final int SKILL_2_2_FUSION = 2020;
	public static final int PREMIUM_EVENT = 2021;
	
	public static final int PACKAGE_SKILL1_25 = 2500;
	public static final int PACKAGE_SKILL1_15 = 2501;
	public static final int PACKAGE_SKILL2_25 = 2502;
	public static final int PACKAGE_SKILL2_15 = 2503;
	public static final int PACKAGE_USERSHOP2_25 = 2504;
	public static final int PACKAGE_USERSHOP2_15 = 2505;
	public static final int PACKAGE_CARD_CARD_30 = 2506;
	public static final int PACKAGE_CARD_CARD_18 = 2507;
	
	
	public Card(int id, int premiumDays, int level, int skill) {
		this(0, id, premiumDays, level, skill);
	}
	
	public Card(int index, int id, int premiumDays, int level, int skill) {
		this.index = index;
		this.id = id;
		this.premiumDays = premiumDays;
		this.level = level;
		this.skill = skill;
	}

	public int getId() {
		return id;
	}
	
	public int getIndex() {
		return index;
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

	// 1 - only first skill. 2 - only second skill. 0 - anything
	public int getRandomSkill(int skill) {
		/*
		 * First 3 digits - x
		 * Next 3 digits - x
		 * Last 3 digits - skill activation success chance
		 */
		int chance = 100;
		int firstSkill = getSkill1();
		int secondSkill = getSkill2();
		
		int[] skill1Array = null;
		int[] skill2Array = null;
		
		if (getCardType() == MAGIC) {
			skill1Array = MagicSkill;
			skill2Array = MagicSkill;
		}
		else if (getCardType() == WEAPON) {
			skill1Array = WeaponSkill1;
			if (getSubType() == AXE || getSubType() == SWORD) {
				skill2Array = MeleeWpnSkill2;
			}
			else if (getSubType() == STAFF || getSubType() == BOW) {
				skill2Array = RangedWpnSkill2;
			}
		}
		else if (getCardType() == ACCESSORY) {
			switch (getSubType()) {
			case SHIELD:
				skill1Array = ShieldSkill1;
				skill2Array = ShieldSkill2;
				break;
			case PENDANT:
				skill1Array = PendantSkill1;
				skill2Array = PendantSkill2;
				break;
			case ARMOR:
				skill1Array = ArmorSkill1;
				skill2Array = null;
				break;
			case BOOTS:
				skill1Array = BootSkill1;
				skill2Array = BootSkill2;
				break;
			}
		}
		
		if (skill == 1 || skill == 0) {
			if (skill1Array != null) {
				firstSkill = skill1Array[(int)(Math.random() * skill1Array.length)];
			}
		}
		if (skill == 2 || skill == 0) {
			if (skill2Array != null) {
				secondSkill = skill2Array[(int)(Math.random() * skill2Array.length)];
				chance = (int) (Math.random() * 100) + 1;
			}
		}
		if (skill == 3 || skill == 0) {
			chance = (int) (Math.random() * 100) + 1;
		}
		
		return chance * 1000000 + firstSkill * 1000 + secondSkill;
	}
	
	public int getSkillSuccess() {
		return skill / 1000000;
	}
	
	public int getSkill1() {
		return (skill / 1000) % 1000;
	}
	
	public int getSkill2() {
		return skill % 1000;
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Index: ").append(index).append("\t");
		buffer.append("ID: ").append(id).append("\t");
		buffer.append("Premium days: ").append(premiumDays).append("\t");
		buffer.append("Level: ").append(level).append("\t");
		buffer.append("Skill: ").append(skill).append("\t");
		buffer.append("\n");
		
		return buffer.toString();
	}
}
