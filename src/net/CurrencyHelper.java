package net;

import net.objects.Card;

public class CurrencyHelper {
	public static int getScrollCode(int scrollIndex) {
		int[] costs = {7, 7, 6, 6, 5, 7, 7, 4, 5, 7, 3, 4, 3, 4};
		
		return costs[scrollIndex];
	}
	
	public static int getLevelFusionSpirits(Card card) {
		int[] weapons = {2, 4, 7, 25, 50, 400, 800, 1600};
		int[] armor = {1, 2, 4, 13, 25, 200, 400, 800};
		int[] shield = {1, 1, 2, 7, 13, 100, 200, 400};
		int[] pendant = {1, 2, 4, 13, 25, 200, 400, 800};
		int[] boots = {2, 4, 7, 25, 50, 400, 800, 1600};
		int[] magic = {4, 7, 13, 50, 100, 800, 1600, 3200};
		
		if (card.getCardType() == Card.MAGIC) {
			return magic[card.getLevel()];
		}
		else if (card.getCardType() == Card.WEAPON) {
			return weapons[card.getLevel()];
		}
		else {
			switch (card.getSubType()) {
			case Card.SHIELD: return shield[card.getLevel()];
			case Card.PENDANT: return pendant[card.getLevel()];
			case Card.ARMOR: return armor[card.getLevel()];
			case Card.BOOTS: return boots[card.getLevel()];
			}
		}
		
		return 0;
	}
	
	public static int getLevelFusionCode(Card card) {
		int[] weapons = {600, 1250, 2500, 10000, 20000, 160000, 320000, 640000};
		int[] armor = {300, 600, 1250, 5000, 10000, 80000, 160000, 320000};
		int[] shield = {150, 300, 600, 2500, 5000, 40000, 80000, 160000};
		int[] pendant = {300, 600, 1250, 5000, 10000, 80000, 160000, 320000};
		int[] boots = {600, 1250, 2500, 10000, 20000, 160000, 320000, 640000};
		int[] magic = {1250, 2500, 5000, 20000, 40000, 320000, 640000, 1280000};
		
		if (card.getCardType() == Card.MAGIC) {
			return magic[card.getLevel()];
		}
		else if (card.getCardType() == Card.WEAPON) {
			return weapons[card.getLevel()];
		}
		else {
			switch (card.getSubType()) {
			case Card.SHIELD: return shield[card.getLevel()];
			case Card.PENDANT: return pendant[card.getLevel()];
			case Card.ARMOR: return armor[card.getLevel()];
			case Card.BOOTS: return boots[card.getLevel()];
			}
		}
		
		return 0;
	}
	
	public static int getLevelFusionLevelSuccess(Card card) {
		switch (card.getLevel()) {
		case 0: return 100;
		case 1: return 100;
		case 2: return 100;
		case 3: return 100;
		case 4: return 100;
		case 5: return 20;
		case 6: return 10;
		case 7: return 5;
		}
		
		return 5;
	}
	
	public static int getSkillFusionSpirits(Card card) {
		int[] weapons = {1, 1, 2, 4, 13, 25, 200, 400, 800};
		int[] armor = {1, 1, 1, 2, 7, 13, 100, 200, 400};
		int[] shield = {1, 1, 1, 1, 4, 7, 50, 100, 200};
		int[] pendant = {1, 1, 1, 2, 7, 13, 100, 200, 400};
		int[] boots = {1, 1, 2, 4, 13, 25, 200, 400, 800};
		int[] magic = {2, 2, 4, 7, 25, 50, 400, 800, 1600};
		
		if (card.getCardType() == Card.MAGIC) {
			return magic[card.getLevel()];
		}
		else if (card.getCardType() == Card.WEAPON) {
			return weapons[card.getLevel()];
		}
		else {
			switch (card.getSubType()) {
			case Card.SHIELD: return shield[card.getLevel()];
			case Card.PENDANT: return pendant[card.getLevel()];
			case Card.ARMOR: return armor[card.getLevel()];
			case Card.BOOTS: return boots[card.getLevel()];
			}
		}
		
		return 0;
	}
	
	public static int getSkillFusionCode(Card card) {
		int[] weapons = {300, 600, 1250, 2500, 5000, 10000, 80000, 160000, 320000};
		int[] armor = {150, 300, 600, 1250, 2500, 5000, 40000, 80000, 160000};
		int[] shield = {75, 150, 300, 600, 1250, 2500, 20000, 40000, 80000};
		int[] pendant = {150, 300, 600, 1250, 2500, 5000, 40000, 80000, 160000};
		int[] boots = {300, 600, 1250, 2500, 5000, 10000, 80000, 160000, 320000};
		int[] magic = {600, 1250, 2500, 5000, 10000, 20000, 160000, 320000, 640000};
		
		if (card.getCardType() == Card.MAGIC) {
			return magic[card.getLevel()];
		}
		else if (card.getCardType() == Card.WEAPON) {
			return weapons[card.getLevel()];
		}
		else {
			switch (card.getSubType()) {
			case Card.SHIELD: return shield[card.getLevel()];
			case Card.PENDANT: return pendant[card.getLevel()];
			case Card.ARMOR: return armor[card.getLevel()];
			case Card.BOOTS: return boots[card.getLevel()];
			}
		}
		
		return 0;
	}
}
