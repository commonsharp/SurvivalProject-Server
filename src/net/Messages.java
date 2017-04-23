package net;

public class Messages {
	// NOTHING = the receive function doesn't do anything with the packet. The packet was probably used in older versions of the game.
	// "not" = not implemented yet
	// and of course every unknown is not implemented yet.
	
	// Game mesages
	public static final int GAME_UNKNOWN_1 = 0x1000; // used in sub_6FAE30. This one is used to send bug reports and hacking reports to the server.
													//	It sends them to IP: 211.233.70.51 port 9999.
	public static final int GAME_KEEP_ALIVE = 0x1100;
	public static final int GAME_JOIN_LOBBY = 0x1101;
	public static final int GAME_UNKNOWN_2 = 0x1102; // sends message 0x1103 lol.
	public static final int GAME_NOTHING = 0x1103; // doesn't do anything. might be used for testing
	public static final int GAME_AFTER_ACTION = 0x1104;
	public static final int GAME_STOPPED_MOVING = 0x1105;
	public static final int GAME_MOVE = 0x1106;
	public static final int GAME_ATTACK = 0x1107;
	public static final int GAME_DEFENSE = 0x1108;
	public static final int GAME_HIT = 0x1109;
	public static final int GAME_CONSTANT_UPDATE = 0x1110;
	public static final int GAME_CHAT = 0x1112;
	public static final int GAME_WHEN_YOU_MOVE = 0x1113;
	public static final int GAME_PICK_SCROLL = 0x1114;
	public static final int GAME_USE_SCROLL = 0x1115;
	public static final int GAME_TOUCH_ELEMENT = 0x1118;
	public static final int GAME_SOCCER_BALL_HIT = 0x1119;
	public static final int GAME_SOCCER_HOKEY_BALL_STATUS = 0x1120;
	public static final int GAME_UNKNOWN_4 = 0x1123; // get event flags
	public static final int GAME_EMOJI = 0x1124;
	public static final int GAME_HIT_MONSTER_NPC = 0x1125;
	public static final int GAME_UNKNOWN_5 = 0x1126; // CUDPNpcControlMsg
	public static final int GAME_CRIT_BAR_FULL = 0x1127;
	public static final int GAME_SERVER_NOTIFICATION = 0x1128;
	public static final int GAME_ELECTROCUTED = 0x1129;
	public static final int GAME_HIT_BY_SNOWBALL = 0x1130;
	public static final int GAME_BIG_MATCH_1 = 0x1131;
	public static final int GAME_UNKNOWN_6 = 0x1132; // something with the server recapturing
	public static final int GAME_BIG_MATCH_2 = 0x1133;
	public static final int GAME_HOKEY_1 = 0x1134;
	public static final int GAME_HOKEY_2 = 0x1135;
	public static final int GAME_UNKNOWN_7 = 0x1136; // new champion in champion title match
	public static final int GAME_UNKNOWN_8 = 0x1137; // sets byte_810DA8 to 1. i didn't find any references to this byte. force close by administrator??
	public static final int GAME_MISSION_AGGRESSION_INFO = 0x1138;
	public static final int GAME_MISSION_PORTAL = 0x1139;
	public static final int GAME_PUSHED_BACK = 0x1140;
	
	// Login messages
	public static final int LOGIN_CREDENTIALS_REQUEST = 0x2707;
	public static final int LOGIN_CREDENTIALS_RESPONSE = 0x2807;
	public static final int SERVERS_INFO_REQUEST = 0x2907;
	public static final int SERVERS_INFO_RESPONSE = 0x2908;
	public static final int SET_MAIN_CHARACTER_REQUEST = 0x2911;
	public static final int SET_MAIN_CHARACTER_RESPONSE = 0x2912;
	public static final int RECONNECT_REQUEST = 0x2913;
	public static final int TUTORIAL_COMPLETED_REQUEST = 0x2915;
	public static final int TUTORIAL_COMPLETED_RESPONSE = 0x2916;
	public static final int GET_CHANNEL_USERS_PERCENTAGE_REQUEST = 0x2917;
	public static final int GET_CHANNEL_ACTIVITY_RESPONSE = 0x2918;
	public static final int USER_ALREADY_CONNECTED_RESPONSE = 0x2919;
	public static final int LOGIN_GUILD_MARK_REQUEST = 0x2921;
	public static final int LOGIN_GUILD_MARK_RESPONSE = 0x2922;
	public static final int TUTORIAL_COMPLETED_NEW_VERSION_RESPONSE = 0x2923;
	public static final int UNKNOWN_LOGIN_RESPONSE_1 = 0x2924; // this one deletes config.ini and changes it. this is also a request.
	public static final int DISCONNECT_NIGHT_PLAY_RESPONSE = 0x2925; // this one disconnects the user because he was connected between 6pm and 6am. lol
	
	// Lobby messages
	public static final int JOIN_LOBBY_REQUEST = 0x4301;
	public static final int JOIN_LOBBY_RESPONSE = 0x4302;
	public static final int GET_LOBBY_USERS_RESPONSE = 0x4303;
	public static final int LOBBY_ROOMS_CHANGED_RESPONSE = 0x4304;
	public static final int NOTHING_1 = 0x4305;
	public static final int GUILD_MEMBER_ONLINE_STATE_HANDLER = 0x4306;
	public static final int CREATE_ROOM_REQUEST = 0x4307;
	public static final int CREATE_ROOM_RESPONSE = 0x4308;
	public static final int GET_USER_INFO_REQUEST = 0x4309;
	public static final int GET_USER_INFO_RESPONSE = 0x4310;
	public static final int GET_ROOM_INFO_REQUEST = 0x4311;
	public static final int GET_ROOM_INFO_RESPONSE = 0x4312;
	public static final int JOIN_ROOM_REQUEST = 0x4313;
	public static final int JOIN_ROOM_RESPONSE = 0x4314;
	public static final int ROOM_PLAYERS_UPDATE_REQUEST = 0x4315;
	public static final int ITEMS_CHANGED_REQUEST = 0x4316;
	public static final int ROOM_PLAYERS_UPDATE_RESPONSE = 0x4317;
	public static final int LEAVE_ROOM_REQUEST = 0x4318;
	public static final int LEAVE_ROOM_RESPONSE = 0x4319;
	public static final int KICK_PLAYER_REQUEST = 0x4320;
	public static final int KICK_PLAYER_RESPONSE = 0x4321;
	public static final int LEAVE_GAME_REQUEST = 0x4322;
	public static final int LEAVE_GAME_RESPONSE = 0x4323;
	public static final int NEW_MASTER_RESPONSE = 0x4324;
	public static final int FIND_USER_REQUEST = 0x4325;
	public static final int FIND_USER_RESPONSE = 0x4326;
	public static final int UNKNOWN_LOBBY_REQUEST_1 = 0x4327; // i don't think this is being used
	public static final int NOTHING_2 = 0x4328;
	public static final int BUY_SCROLL_REQUEST = 0x4329;
	public static final int BUY_SCROLL_RESPONSE = 0x4330;
	public static final int CHAT_MESSAGE_REQUEST = 0x4331;
	public static final int CHAT_MESSAGE_RESPONSE = 0x4332;
	public static final int PLAYER_RESURRECTION_REQUEST = 0x4333;
	public static final int PLAYER_RESURRECTION_RESPONSE = 0x4334;
	public static final int PLAYER_DEATH_REQUEST = 0x4335;
	public static final int PLAYER_DEATH_RESPONSE = 0x4336;
	public static final int ROUND_COMPLETED_RESPONSE = 0x4337;
	public static final int GAME_COMPLETED_RESPONSE = 0x4338;
	public static final int USE_ACTION_REQUEST = 0x4339;
	public static final int TRADE_REQUEST = 0x4340;
	public static final int TRADE_RESPONSE = 0x4341;
	public static final int FUSION_REQUEST = 0x4342;
	public static final int FUSION_RESPONSE = 0x4343;
	public static final int SOCCER_GOAL_REQUEST = 0x4344;
	public static final int SOCCER_GOAL_RESPONSE = 0x4345;
	public static final int UNKNOWN_LOBBY_RESPONSE_12 = 0x4346; // this one uses the code field and lots of fields including another long value. TODO
	public static final int UNKNOWN_LOBBY_RESPONSE_13 = 0x4347; // this one changes your items/elements/code it seems TODO maybe level up response according to b6oy
	public static final int UNKNOWN_LOBBY_REQUEST_2 = 0x4348;
	public static final int UNKNOWN_LOBBY_RESPONSE_14 = 0x4349;
	public static final int ROOM_NAME_CHANGED_REQUEST = 0x4350;
	public static final int INVITE_PLAYER_REQUEST = 0x4351;
	public static final int INVITE_PLAYER_RESPONSE = 0x4352;
	public static final int ADD_FRIEND_REQUEST = 0x4353;
	public static final int ADD_FRIEND_RESPONSE = 0x4354;
	public static final int GET_FRIENDS_REQUEST = 0x4355;
	public static final int GET_FRIENDS_RESPONSE = 0x4356;
	public static final int GET_LOBBY_USERS_REQUEST = 0x4357;
	public static final int MONSTER_DEATH_REQUEST = 0x4358;
	public static final int QUEST_DEATH_RESPONSE = 0x4359;
	public static final int CRYSTAL_DEATH_REQUEST = 0x4360;
	public static final int CRYSTAL_DEATH_RESPONSE = 0x4361;
	public static final int QUEST_INFO_REQUEST = 0x4362;
	public static final int ENTER_CARD_SHOP_REQUEST = 0x4363;
	public static final int ENTER_CARD_SHOP_RESPONSE = 0x4364;
	public static final int SELL_CARD_REQUEST = 0x4365;
	public static final int SELL_CARD_RESPONSE = 0x4366;
	public static final int BUY_CARD_OR_CHARGE_REQUEST = 0x4367;
	public static final int BUY_CARD_OR_CHARGE_RESPONSE = 0x4368;
	public static final int ROOM_NAME_CHANGED_RESPONSE = 0x4369;
	public static final int SPAWN_RESPONSE = 0x4370;
	public static final int USER_SHOP_I_ACTION_REQUEST = 0x4371;
	public static final int UNKNOWN_LOBBY_RESPONSE_25 = 0x4372;
	public static final int NEW_USER_SHOP_I_RESPONSE = 0x4373;
	public static final int UNKNOWN_LOBBY_REQUEST_3 = 0x4374; // training
	public static final int UNKNOWN_LOBBY_REQUEST_4 = 0x4375; // training
	public static final int UNKNOWN_LOBBY_RESPONSE_26 = 0x4376;
	public static final int UNKNOWN_LOBBY_RESPONSE_27 = 0x4377; // this one sets a lot of unknown fields in MyInfo
	public static final int SPAWN_ELEMENT_RESPONSE = 0x4378;
	public static final int PICK_BUBBLE_RESPONSE = 0x4379;
	public static final int SPAWN_CODE_RESPONSE = 0x4380;
	public static final int ENJOY_MODE_DEATH_RESPONSE = 0x4381;
	public static final int RACE_INFO_RESPONSE = 0x4382; // this is also a request
	public static final int GOLD_FORCE_CHARGE_REQUEST = 0x4383;
	public static final int GOLD_FORCE_CHARGE_RESPONSE = 0x4384;
	public static final int UNKNOWN_LOBBY_RESPONSE_34 = 0x4385; // you get a reward after purchase/sells during an event
	public static final int SYMBOL_STATE_CHANGED_REQUEST = 0x4386;
	public static final int UNKNOWN_LOBBY_RESPONSE_35 = 0x4387; // changes the teams
	public static final int GET_TOP_GUILDS_REQUEST = 0x4388;
	public static final int GET_TOP_GUILDS_RESPONSE = 0x4389;
	public static final int NEW_DOMINATING_GUILD = 0x4390; // not
	public static final int NEW_KING_RESPONSE = 0x4391;
	public static final int UNKNOWN_LOBBY_REQUEST_5 = 0x4392;
	public static final int UNKNOWN_LOBBY_RESPONSE_38 = 0x4393; // this is memo.
	// no 0x4394
	public static final int BIG_MATCH_INFO_REQUEST = 0x4395;
	public static final int BIG_MATCH_DEATH_REQUEST = 0x4396;
	public static final int BIG_MATCH_DEATH_RESPONSE = 0x4397;
	public static final int GAME_MASTER_BAN_REQUEST = 0x4398;
	public static final int GAME_MASTER_BAN_RESPONSE = 0x4399;
	// no 0x4400
	public static final int UNKNOWN_LOBBY_REQUEST_6 = 0x4401; // i don't think this is being sent
	public static final int GET_ROOM_PLAYERS_GUILD_RANK_REQUEST = 0x4402;
	public static final int GET_ROOM_PLAYERS_GUILD_RANK_RESPONSE = 0x4403;
	public static final int UNKNOWN_LOBBY_RESPONSE_42 = 0x4404;
	public static final int NOTHING_3 = 0x4405;
	public static final int UNKNOWN_LOBBY_REQUEST_7 = 0x4406;
	public static final int UNKNOWN_LOBBY_RESPONSE_44 = 0x4407;
	public static final int CRYSTALS_INFO_REQUEST = 0x4408;
	public static final int UNKNOWN_LOBBY_RESPONSE_46 = 0x4409;
	public static final int UNKNOWN_LOBBY_RESPONSE_47 = 0x4410;
	public static final int UNKNOWN_LOBBY_REQUEST_8 = 0x4411; // only in quests...
	public static final int UNKNOWN_LOBBY_RESPONSE_48 = 0x4412;
	public static final int ID_VERIFICATION_REQUEST = 0x4413;
	public static final int ID_VERIFICATION_RESPONSE = 0x4414;
	public static final int GIFT_ID_VERIFY_REQUEST = 0x4415;
	public static final int GIFT_ID_VERIFY_RESPONSE = 0x4416;
	public static final int SEND_GIFT_REQUEST = 0x4417;
	public static final int SEND_GIFT_RESPONSE = 0x4418;
	public static final int CARD_GIFT_RECEIVED_RESPONSE = 0x4419;
	public static final int ELEMENTS_OR_CODE_GIFT_RECEIVED_RESPONSE = 0x4420;
	public static final int HOKEY_GOAL_REQUEST = 0x4421;
	public static final int HOKEY_GOAL_RESPONSE = 0x4422;
	public static final int UNKNOWN_LOBBY_RESPONSE_55 = 0x4423; // TODO check
	public static final int SEND_MEMO_REQUEST = 0x4424;
	public static final int SEND_MEMO_RESPONSE = 0x4425;
	public static final int MEMO_ARRIVAL_RESPONSE = 0x4426;
	public static final int UNKNOWN_LOBBY_RESPONSE_58 = 0x4427; // something with Software\\IOSPK\\EVENT\\FREE811
	public static final int UNKNOWN_LOBBY_REQUEST_9 = 0x4428; // not sure if this is being sent
	public static final int UNKNOWN_LOBBY_RESPONSE_59 = 0x4429; // lottery
	public static final int PET_KILLED_REQUEST = 0x4430;
	public static final int PET_KILLED_RESPONSE = 0x4431;
	public static final int USER_SHOP_SEARCH_REQUEST = 0x4432;
	public static final int USER_SHOP_SEARCH_RESPONSE = 0x4433;
	public static final int USER_SHOP_EXTRA_SEARCH_REQUEST = 0x4434;
	public static final int USER_SHOP_BUY_CARD_REQUEST = 0x4435;
	public static final int USER_SHOP_BUY_CARD_RESPONSE = 0x4436;
	public static final int REMOVE_USER_SHOP_I_CARD_REQUEST = 0x4437;
	public static final int BUY_ELEMENTS_REQUEST = 0x4438;
	public static final int BUY_ELEMENTS_RESPONSE = 0x4439;
	// no 0x4440
	public static final int CHANGE_PASSWORD_REQUEST = 0x4441;
	public static final int SEND_ANY_MESSAGE_RESPONSE = 0x4442; // this is also a request
	public static final int NOTHING_4 = 0x4443;
	public static final int ROOM_GAME_MODE_CHANGED_REQUEST = 0x4444;
	public static final int ROOM_GAME_MODE_CHANGED_RESPONSE = 0x4445;
	public static final int GET_SCROLL_REQUEST = 0x4446;
	public static final int GET_SCROLL_RESPONSE = 0x4447;
	public static final int UNKNOWN_LOBBY_RESPONSE_68 = 0x4448; // this one gives you a card
	public static final int UNKNOWN_LOBBY_REQUEST_10 = 0x4449; // not sure if this is used
	public static final int UNKNOWN_LOBBY_RESPONSE_69 = 0x4450; // the response to 0x4449
	public static final int CHANGE_CHARACTER_REQUEST = 0x4451;
	public static final int CHANGE_CHARACTER_RESPONSE = 0x4452;
	public static final int GET_MISSION_LEVEL_RESPONSE = 0x4453;
	public static final int MISSION_START_COUNTDOWN_REQUEST = 0x4454;
	public static final int MISSION_START_COUNTDOWN_RESPONSE = 0x4455;
	public static final int MISSION_COMPLETED_REQUEST = 0x4456;
	public static final int MISSION_COMPLETED_RESPONSE = 0x4457;
	public static final int MISSION_INFO_REQUEST = 0x4458;
	public static final int UNKNOWN_LOBBY_REQUEST_11 = 0x4459; // not sure if this is being sent at all.
	public static final int UNKNOWN_LOBBY_RESPONSE_72 = 0x4460; // this sets a few fields which have no reference at all.
	public static final int UNKNOWN_LOBBY_RESPONSE_73 = 0x4461; // TODO bonus visit state response??  CHECK THIS...
	public static final int UNKNOWN_LOBBY_REQUEST_12 = 0x4462; // Sent by macro 12
	public static final int UNKNOWN_LOBBY_RESPONSE_74 = 0x4463; // the response to 0x4462
	public static final int UNKNOWN_LOBBY_REQUEST_13 = 0x4464; // Sent by macros 13, 15, 16, 17, 18, 21, 22, 24 and in different functions...
	public static final int UNKNOWN_LOBBY_RESPONSE_75 = 0x4465; // The response to 0x4464
	public static final int UNKNOWN_LOBBY_RESPONSE_76 = 0x4466; // list of kill standby users?? 50 users.
	public static final int INCREASE_CARD_SLOTS_REQUEST = 0x4467;
	public static final int INCREASE_CARD_SLOTS_RESPONSE = 0x4468;
	public static final int UNKNOWN_LOBBY_REQUEST_14 = 0x4469;
	public static final int UNKNOWN_LOBBY_RESPONSE_78 = 0x4470;
	public static final int INFINITY_KING_POINTS_RESPONSE = 0x4471; // infinity game modes points
	public static final int NEW_BOOSTERS_RESPONSE = 0x4472; // not
	public static final int UNKNOWN_LOBBY_RESPONSE_81 = 0x4473; // championship win packet
	public static final int AUTO_USER_SHOP_NEW_ITEM_REQUEST = 0x4474;
	public static final int AUTO_USER_SHOP_NEW_ITEM_RESPONSE = 0x4475;
	public static final int TREASURE_SPAWNED_REQUEST = 0x4476; // this is also a response
	public static final int INFINITY_INFO_REQUEST = 0x4477; // also a response
	public static final int UNKNOWN_LOBBY_REQUEST_15 = 0x4478; // the request of 0x4479
	public static final int CHANGE_MAP_RESPONSE = 0x4479; // this one seems to be only in infinity survival mode. it changes the map of the game
	public static final int UNKNOWN_LOBBY_REQUEST_16 = 0x4480; // seems to get sent after a 0x4481 message is received. probably "send another batch message". couldn't get it to send that message though
	public static final int GET_LIST_OF_ROOMS_RESPONSE = 0x4481;
	public static final int UNKNOWN_LOBBY_REQUEST_17 = 0x4482; // this is getting called by macro 19
	public static final int MESSAGE_4483 = 0x4483; // this one prints a message in korean about "kill mark" (only in rooms, not in the lobby). not sure what it means...
	public static final int UNKNOWN_LOBBY_REQUEST_18 = 0x4484; // this is getting called by macro 20
	public static final int UNKNOWN_LOBBY_RESPONSE_88 = 0x4485; // something with marks. probably the response to 0x4484
	public static final int GET_GUILD_MARK_REQUEST = 0x4486;
	public static final int GET_GUILD_MARK_RESPONSE = 0x4487;
	public static final int UNKNOWN_LOBBY_RESPONSE_89 = 0x4488; // ?
	public static final int UNKNOWN_LOBBY_REQUEST = 0x4489; // this is getting called by macro 26
	public static final int UNKNOWN_LOBBY_RESPONSE_90 = 0x4490; // this is probably the response to 0x4489
}