package net;

import net.objects.Card;

public class CurrencyHelper {
	public static int getLevelFusionSpirits(Card card) {
		if (card.isMagicCard()) {
			switch (card.getLevel()) {
			case 0: return 4;
			case 1: return 7;
			case 2: return 13;
			case 3: return 50;
			case 4: return 100;
			case 5: return 800;
			case 6: return 1600;
			case 7: return 3200;
			}
			
			return 3200;
		}
		else {
			switch (card.getLevel()) {
			case 0: return 1;
			case 1: return 2;
			case 2: return 4;
			case 3: return 13;
			case 4: return 25;
			case 5: return 200;
			case 6: return 400;
			case 7: return 800;
			}
			
			return 800;
		}
	}
	
	public static int getLevelFusionCode(Card card) {
		if (card.isMagicCard()) {
			switch (card.getLevel()) {
			case 0: return 1250;
			case 1: return 2500;
			case 2: return 5000;
			case 3: return 20000;
			case 4: return 40000;
			case 5: return 320000;
			case 6: return 640000;
			case 7: return 1280000;
			}
			
			return 3200;
		}
		else {
			switch (card.getLevel()) {
			case 0: return 300;
			case 1: return 600;
			case 2: return 1250;
			case 3: return 5000;
			case 4: return 10000;
			case 5: return 80000;
			case 6: return 160000;
			case 7: return 320000;
			}
			
			return 800;
		}
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
		if (card.isMagicCard()) {
			switch (card.getLevel()) {
			case 0: return 2;
			case 1: return 2;
			case 2: return 4;
			case 3: return 7;
			case 4: return 25;
			case 5: return 50;
			case 6: return 400;
			case 7: return 800;
			case 8: return 1600;
			}
			
			return 1600;
		}
		else {
			switch (card.getLevel()) {
			case 0: return 1;
			case 1: return 1;
			case 2: return 1;
			case 3: return 2;
			case 4: return 7;
			case 5: return 13;
			case 6: return 100;
			case 7: return 200;
			case 8: return 400;
			}
			
			return 800;
		}
	}
	
	public static int getSkillFusionCode(Card card) {
		if (card.isMagicCard()) {
			switch (card.getLevel()) {
			case 0: return 600;
			case 1: return 1250;
			case 2: return 2500;
			case 3: return 5000;
			case 4: return 10000;
			case 5: return 20000;
			case 6: return 160000;
			case 7: return 320000;
			case 8: return 640000;
			}
			
			return 3200;
		}
		else {
			switch (card.getLevel()) {
			case 0: return 150;
			case 1: return 300;
			case 2: return 600;
			case 3: return 1250;
			case 4: return 2500;
			case 5: return 5000;
			case 6: return 40000;
			case 7: return 80000;
			case 8: return 160000;
			}
			
			return 800;
		}
	}
}
