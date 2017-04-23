package net.objects;

public enum GameMode {
		FIRST_TRAINING(0), // unused
		COMMUNITY(1),
		AUTO_TEAM(2), // ?
		TEAMPLAY(3),
		SURVIVAL(4),
		DUEL(5),
		LUCKY_3(6),
		ASSAULT(7),
		SOCCER(8),
		KING_SLAYER(9),
		MAGIC_LUCKY_3(10),
		DUNGEON_QUEST_1(11),
		DUNGEON_QUEST_2(12),
		DUNGEON_QUEST_3(13),
		DUNGEON_QUEST_4(14),
		FIGHT_CLUB(15),
		TOURNAMENT(16),
		DUNGEON_QUEST_5(17),
		FOREST_QUEST_1(18),
		FOREST_QUEST_2(19),
		FOREST_QUEST_3(20),
		FOREST_QUEST_4(21),
		FOREST_QUEST_5(22),
		FLAME_QUEST_1(23),
		FLAME_QUEST_2(24),
		FLAME_QUEST_3(25),
		FLAME_QUEST_4(26),
		FLAME_QUEST_5(27),
		DODGE(28),
		MOLE(29),
		RACE(30),
		SYMBOL(31),
		KING_SURVIVAL(32),
		BIG_MATCH_SURVIVAL(33),
		BIG_MATCH_AUTO_TEAM(34),
		BIG_MATCH_DEATH_MATCH(35),
		CHAMP_TEAM(36),
		CHAMP_ASSAULT(37),
		CHAMP_KING_SLAYER(38),
		CHAMP_TOURNAMENT(39),
		CRYSTAL(40),
		HOKEY(41),
		TRAINING_1(42),
		TRAINING_2(43),
		TRAINING_3(44),
		TRAINING_4(45),
		TRAINING_5(46),
		MISSION(47),
		INFINITY_SURVIVAL(48),
		INFINITY_SYMBOL(49),
		INFINITY_KING(50),
		HERO(51);
		
		
		private final int id;
		
		GameMode(int id) {
			this.id = id;
		}
		
	    public int getValue() {
	    	return id;
	    }
	    
	    public static GameMode getMode(int id) {
	    	for (int i = 0; i < values().length; i++) {
	    		if (values()[i].id == id) {
	    			return values()[i];
	    		}
	    	}
	    	
	    	return null;
	    }
	}