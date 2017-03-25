package net;

public class Messages {
	// Login messages
	public static final int LOGIN_CREDENTIALS_REQUEST = 0x2707;
	public static final int LOGIN_CREDENTIALS_RESPONSE = 0x2807;
	public static final int SERVERS_INFO_REQUEST = 0x2907;
	public static final int SERVERS_INFO_RESPONSE = 0x2908;
	public static final int SET_ACTIVE_CHARACTER_REQUEST = 0x2911;
	public static final int SET_ACTIVE_CHARACTER_RESPONSE = 0x2912;
	public static final int RECONNECT_REQUEST = 0x2913;
	public static final int TUTORIAL_REQUEST = 0x2915;
	public static final int UNKNOWN_LOGIN_RESPONSE_4 = 0x2916;
	public static final int GET_CHANNEL_USERS_PERCENTAGE_REQUEST = 0x2917;
	public static final int GET_CHANNEL_USERS_PERCENTAGE_RESPONSE = 0x2918;
	public static final int UNKNOWN_LOGIN_RESPONSE_3 = 0x2919;
	public static final int LOGIN_GUILD_MARK_REQUEST = 0x2921;
	public static final int LOGIN_GUILD_MARK_RESPONSE = 0x2922;
	public static final int TUTORIAL_RESPONSE = 0x2923;
	public static final int UNKNOWN_LOGIN_RESPONSE_1 = 0x2924;
	public static final int UNKNOWN_LOGIN_RESPONSE_2 = 0x2925;
	
	// Lobby messages
	public static final int JOIN_LOBBY_REQUEST = 0x4301;
	public static final int JOIN_LOBBY_RESPONSE = 0x4302;
	public static final int LOBBY_ROOMS_CHANGED_RESPONSE = 0x4304;
	public static final int UNKNOWN_LOBBY_RESPONSE_1 = 0x4305;
	public static final int UNKNOWN_LOBBY_RESPONSE_2 = 0x4306;
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
	public static final int UNKNOWN_LOBBY_RESPONSE_3 = 0x4321;
	public static final int LEAVE_GAME_REQUEST = 0x4322;
	public static final int LEAVE_GAME_RESPONSE = 0x4323;
	public static final int UNKNOWN_LOBBY_RESPONSE_4 = 0x4324;
	public static final int UNKNOWN_LOBBY_RESPONSE_5 = 0x4326;
	public static final int UNKNOWN_LOBBY_RESPONSE_6 = 0x4328;
	public static final int UNKNOWN_LOBBY_RESPONSE_7 = 0x4330;
	public static final int UNKNOWN_LOBBY_RESPONSE_8 = 0x4332;
	public static final int PLAYER_RESURRECTION_REQUEST = 0x4333;
	public static final int PLAYER_RESURRECTION_RESPONSE = 0x4334;
	public static final int PLAYER_DEATH_REQUEST = 0x4335;
	public static final int PLAYER_DEATH_RESPONSE = 0x4336;
	public static final int UNKNOWN_LOBBY_RESPONSE_9 = 0x4337;
	public static final int UNKNOWN_LOBBY_RESPONSE_18 = 0x4338;
	public static final int UNKNOWN_LOBBY_RESPONSE_10 = 0x4340;
	public static final int UNKNOWN_LOBBY_RESPONSE_11 = 0x4341;
	public static final int FUSION_REQUEST = 0x4342;
	public static final int FUSION_RESPONSE = 0x4343;
	public static final int SOCCER_GOAL_REQUEST = 0x4344;
	public static final int SOCCER_GOAL_RESPONSE = 0x4345;
	public static final int UNKNOWN_LOBBY_RESPONSE_12 = 0x4346;
	public static final int UNKNOWN_LOBBY_RESPONSE_13 = 0x4347;
	public static final int UNKNOWN_LOBBY_RESPONSE_14 = 0x4349;
	public static final int ROOM_NAME_CHANGED_REQUEST = 0x4350;
	public static final int UNKNOWN_LOBBY_RESPONSE_15 = 0x4352;
	public static final int UNKNOWN_LOBBY_RESPONSE_16 = 0x4354;
	public static final int UNKNOWN_LOBBY_RESPONSE_17 = 0x4356;
	public static final int QUEST_DEATH_REQUEST = 0x4358;
	public static final int QUEST_DEATH_RESPONSE = 0x4359;
	public static final int CRYSTAL_DEATH_REQUEST = 0x4360;
	public static final int CRYSTAL_DEATH_RESPONSE = 0x4361;
	public static final int QUEST_INFO_REQUEST = 0x4362;
	public static final int UNKNOWN_LOBBY_RESPONSE_19 = 0x4364;
	public static final int UNKNOWN_LOBBY_RESPONSE_20 = 0x4366;
	public static final int UNKNOWN_LOBBY_RESPONSE_21 = 0x4368;
	public static final int UNKNOWN_LOBBY_RESPONSE_22 = 0x4369;
	public static final int SPAWN_RESPONSE = 0x4370;
	public static final int UNKNOWN_LOBBY_RESPONSE_23 = 0x4371;
	public static final int UNKNOWN_LOBBY_RESPONSE_24 = 0x4372;
	public static final int UNKNOWN_LOBBY_RESPONSE_25 = 0x4373;
	public static final int UNKNOWN_LOBBY_RESPONSE_26 = 0x4376;
	public static final int UNKNOWN_LOBBY_RESPONSE_27 = 0x4377;
	public static final int UNKNOWN_LOBBY_RESPONSE_28 = 0x4378;
	public static final int UNKNOWN_LOBBY_RESPONSE_29 = 0x4379;
	public static final int UNKNOWN_LOBBY_RESPONSE_30 = 0x4380;
	public static final int UNKNOWN_LOBBY_RESPONSE_31 = 0x4381;
	public static final int UNKNOWN_LOBBY_RESPONSE_32 = 0x4382;
	public static final int UNKNOWN_LOBBY_RESPONSE_33 = 0x4384;
	public static final int UNKNOWN_LOBBY_RESPONSE_34 = 0x4385;
	public static final int UNKNOWN_LOBBY_RESPONSE_35 = 0x4387;
	public static final int GET_TOP_GUILDS_REQUEST = 0x4388;
	public static final int GET_TOP_GUILDS_RESPONSE = 0x4389;
	public static final int UNKNOWN_LOBBY_RESPONSE_36 = 0x4390;
	public static final int UNKNOWN_LOBBY_RESPONSE_37 = 0x4391;
	public static final int UNKNOWN_LOBBY_RESPONSE_38 = 0x4393;
	public static final int UNKNOWN_LOBBY_RESPONSE_39 = 0x4395;
	public static final int BIG_MATCH_DEATH_REQUEST = 0x4396;
	public static final int BIG_MATCH_DEATH_RESPONSE = 0x4397;
	public static final int GAME_MASTER_BAN_REQUEST = 0x4398;
	public static final int GAME_MASTER_BAN_RESPONSE = 0x4399;
	public static final int UNKNOWN_LOBBY_RESPONSE_41 = 0x4403;
	public static final int UNKNOWN_LOBBY_RESPONSE_42 = 0x4404;
	public static final int UNKNOWN_LOBBY_RESPONSE_43 = 0x4405;
	public static final int UNKNOWN_LOBBY_RESPONSE_44 = 0x4407;
	public static final int UNKNOWN_LOBBY_RESPONSE_45 = 0x4408;
	public static final int UNKNOWN_LOBBY_RESPONSE_46 = 0x4409;
	public static final int UNKNOWN_LOBBY_RESPONSE_47 = 0x4410;
	public static final int UNKNOWN_LOBBY_RESPONSE_48 = 0x4412;
	public static final int UNKNOWN_LOBBY_RESPONSE_49 = 0x4414;
	public static final int UNKNOWN_LOBBY_RESPONSE_50 = 0x4416;
	public static final int UNKNOWN_LOBBY_RESPONSE_51 = 0x4418;
	public static final int UNKNOWN_LOBBY_RESPONSE_52 = 0x4419;
	public static final int UNKNOWN_LOBBY_RESPONSE_53 = 0x4420;
	public static final int UNKNOWN_LOBBY_RESPONSE_54 = 0x4422;
	public static final int UNKNOWN_LOBBY_RESPONSE_55 = 0x4423;
	public static final int UNKNOWN_LOBBY_RESPONSE_56 = 0x4425;
	public static final int UNKNOWN_LOBBY_RESPONSE_57 = 0x4426;
	public static final int UNKNOWN_LOBBY_RESPONSE_58 = 0x4427;
	public static final int UNKNOWN_LOBBY_RESPONSE_59 = 0x4429;
	public static final int PET_KILLED_REQUEST = 0x4430;
	public static final int PET_KILLED_RESPONSE = 0x4431;
	public static final int UNKNOWN_LOBBY_RESPONSE_60 = 0x4433;
	public static final int UNKNOWN_LOBBY_RESPONSE_61 = 0x4436;
	public static final int UNKNOWN_LOBBY_RESPONSE_62 = 0x4439;
	public static final int UNKNOWN_LOBBY_RESPONSE_63 = 0x4441;
	public static final int UNKNOWN_LOBBY_RESPONSE_64 = 0x4442;
	public static final int UNKNOWN_LOBBY_RESPONSE_65 = 0x4443;
	public static final int UNKNOWN_LOBBY_RESPONSE_66 = 0x4445;
	public static final int UNKNOWN_LOBBY_RESPONSE_67 = 0x4447;
	public static final int UNKNOWN_LOBBY_RESPONSE_68 = 0x4448;
	public static final int UNKNOWN_LOBBY_RESPONSE_69 = 0x4450;
	public static final int UNKNOWN_LOBBY_RESPONSE_70 = 0x4452;
	public static final int UNKNOWN_LOBBY_RESPONSE_71 = 0x4453;
	public static final int MISSION_START_COUNTDOWN_REQUEST = 0x4454;
	public static final int MISSION_START_COUNTDOWN_RESPONSE = 0x4455;
	public static final int MISSION_COMPLETED_REQUEST = 0x4456;
	public static final int MISSION_COMPLETED_RESPONSE = 0x4457;
	public static final int MISSION_INFO_REQUEST = 0x4458;
	public static final int UNKNOWN_LOBBY_RESPONSE_72 = 0x4460;
	public static final int UNKNOWN_LOBBY_RESPONSE_73 = 0x4461;
	public static final int UNKNOWN_LOBBY_RESPONSE_74 = 0x4463;
	public static final int UNKNOWN_LOBBY_RESPONSE_75 = 0x4465;
	public static final int UNKNOWN_LOBBY_RESPONSE_76 = 0x4466;
	public static final int UNKNOWN_LOBBY_RESPONSE_77 = 0x4468;
	public static final int UNKNOWN_LOBBY_RESPONSE_78 = 0x4470;
	public static final int UNKNOWN_LOBBY_RESPONSE_79 = 0x4471;
	public static final int UNKNOWN_LOBBY_RESPONSE_80 = 0x4472;
	public static final int UNKNOWN_LOBBY_RESPONSE_81 = 0x4473;
	public static final int UNKNOWN_LOBBY_RESPONSE_82 = 0x4475;
	public static final int UNKNOWN_LOBBY_RESPONSE_83 = 0x4476;
	public static final int UNKNOWN_LOBBY_RESPONSE_84 = 0x4477;
	public static final int UNKNOWN_LOBBY_RESPONSE_85 = 0x4479;
	public static final int UNKNOWN_LOBBY_RESPONSE_86 = 0x4481;
	public static final int UNKNOWN_LOBBY_RESPONSE_87 = 0x4483;
	public static final int UNKNOWN_LOBBY_RESPONSE_88 = 0x4485;
	public static final int GET_TOP_GUILDS_MARK_REQUEST = 0x4486;
	public static final int GET_TOP_GUILDS_MARK_RESPONSE = 0x4487;
	public static final int UNKNOWN_LOBBY_RESPONSE_89 = 0x4488;
	public static final int UNKNOWN_LOBBY_RESPONSE_90 = 0x4490;
}
