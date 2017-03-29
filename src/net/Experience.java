package net;

public class Experience {
	// 50% to get 1. 25% to get 2. 12.5% to get 3... 100/2^k% to get k
	public static int getLuckyMultiplier() {
		int randomLucky = (int) Math.ceil(Math.log(Math.random()) / Math.log(0.5f));
		
		if (randomLucky == 0) {
			randomLucky = 1;
		}
		
		return randomLucky;
	}
	
	// 50% to get 0. 25% to get 1. 12.5% to get 2. 6.25% to get 3 and 6.25% to get 4.
	public static int getElementCount() {
		// returning amount - 1 because we should have a lot of 0's as well.
		int amount = (int) Math.ceil(Math.log(Math.random()) / Math.log(0.5f));
		
		if (amount == 0) {
			amount = 1;
		}
		
		if (amount > 5) {
			amount = 5;
		}
		
		return amount - 1;
	}
	
	public static int[] getExperience(int[] damageDone) {
		int totalDamage = 0;
		int[] experience = new int[damageDone.length];
		
		for (int i = 0; i < damageDone.length; i++) {
			totalDamage += damageDone[i];
		}
		
		if (totalDamage != 0) {
			int totalExperience = (int)(totalDamage / (float) ((int)(Math.random() * 3) + 4));
			
			for (int i = 0; i < damageDone.length; i++) {
				experience[i] = damageDone[i] * totalExperience / totalDamage;
			}
		}
		
		return experience;
	}
	
	public static int getLevel(long experience) {
		final int minPointForeveDword[] = {0, 1, 50, 100,
				200, 400, 800, 1600,
				2400, 3200, 6400, 12800,
				25600, 51200, 102400, 204800,
				409600, 819200, 1638400, 3276800,
				6553600, 13107200, 26214400, 52428800,
				104857600, 209715200, 419430400, 838860800,
				1677721600};
	    final long minPointForLevelQword[] = {3355443200L, 6710886400L, 13421772800L, 26843545600L};
	    
	    for (int i = minPointForLevelQword.length - 1; i >= 0; i--) {
	    	if (experience >= minPointForLevelQword[i]) {
	    		return i + minPointForeveDword.length;
	    	}
	    }
	    
	    for (int i = minPointForeveDword.length - 1; i >= 0; i--) {
	    	if (experience >= minPointForeveDword[i]) {
	    		return i;
	    	}
	    }
	    
	    return 0;
	}
	
	public static int[] getLevels(long[] experiences) {
		int[] levels = new int[experiences.length];
		
		for (int i = 0; i < levels.length; i++) {
			levels[i] = getLevel(experiences[i]);
		}
		
		return levels;
	}
}
