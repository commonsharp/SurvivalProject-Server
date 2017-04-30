package lobby;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;

import database.Database;
import game.GameServer;
import lobby.handlers.AddFriend;
import lobby.handlers.AutoUserShopNewItemHandler;
import lobby.handlers.BigMatchDeathHandler;
import lobby.handlers.BigMatchInfo;
import lobby.handlers.BuyCardHandler;
import lobby.handlers.BuyElementHandler;
import lobby.handlers.BuyScrollHandler;
import lobby.handlers.ChangeCharacterHandler;
import lobby.handlers.ChangePasswordHandler;
import lobby.handlers.ChatMessageHandler;
import lobby.handlers.CreateRoomHandler;
import lobby.handlers.CrystalDeathHandler;
import lobby.handlers.CrystalsInfoHandler;
import lobby.handlers.EnterCardShopHandler;
import lobby.handlers.FindUserHandler;
import lobby.handlers.FusionHandler;
import lobby.handlers.GameMasterBanHandler;
import lobby.handlers.GetFriendsHandler;
import lobby.handlers.GetGuildMarkHandler;
import lobby.handlers.GetListOfRoomsHandler;
import lobby.handlers.GetLobbyUsersHandler;
import lobby.handlers.GetMissionLevelHandler;
import lobby.handlers.GetRoomInfoHandler;
import lobby.handlers.GetRoomPlayersGuildRankHandler;
import lobby.handlers.GetScrollHandler;
import lobby.handlers.GetTopGuildsHandler;
import lobby.handlers.GetUserInfoHandler;
import lobby.handlers.GiftIDVeficationHandler;
import lobby.handlers.GoldForceChargeHandler;
import lobby.handlers.GuildMemberOnlineStatusHandler;
import lobby.handlers.HokeyGoalHandler;
import lobby.handlers.IDVerificationHandler;
import lobby.handlers.IncreaseCardSlotsHandler;
import lobby.handlers.InfinityInfoHandler;
import lobby.handlers.InfinityPointsHandler;
import lobby.handlers.InvitePlayersHandler;
import lobby.handlers.ItemsChangedHandler;
import lobby.handlers.JoinLobbyHandler;
import lobby.handlers.JoinRoomHandler;
import lobby.handlers.KickPlayerHandler;
import lobby.handlers.LeaveGameHandler;
import lobby.handlers.LeaveRoomHandler;
import lobby.handlers.MissionCompletedHandler;
import lobby.handlers.MissionInfoHandler;
import lobby.handlers.MonsterDeathHandler;
import lobby.handlers.PetKilledHandler;
import lobby.handlers.PlayerDeathHandler;
import lobby.handlers.PlayerResurrectionHandler;
import lobby.handlers.QuestInfoHandler;
import lobby.handlers.RaceInfoHandler;
import lobby.handlers.RemoveUserShopICardHandler;
import lobby.handlers.RoomGameModeChangedHandler;
import lobby.handlers.RoomNameChangedHandler;
import lobby.handlers.RoomPlayersUpdateHandler;
import lobby.handlers.SellCardHandler;
import lobby.handlers.SendGiftHandler;
import lobby.handlers.SendMemoHandler;
import lobby.handlers.SoccerGoalHandler;
import lobby.handlers.SpawnCodeHandler;
import lobby.handlers.SpawnElementHandler;
import lobby.handlers.StartCountdownHandler;
import lobby.handlers.SymbolStateChanged;
import lobby.handlers.TradeHandler;
import lobby.handlers.TreasureSpawnedHandler;
import lobby.handlers.UseActionHandler;
import lobby.handlers.UserShopBuyCardHandler;
import lobby.handlers.UserShopExtraSearchHandler;
import lobby.handlers.UserShopIActionHandler;
import lobby.handlers.UserShopSearchHandler;
import net.ExperienceHelper;
import net.GenericTCPServer;
import net.Messages;
import net.UserSession;
import net.handlers.GenericHandler;
import net.objects.Friend;
import net.objects.GameMode;
import net.objects.GuildMember;
import net.objects.Room;
import net.objects.Server;
import net.objects.Trade;
import net.objects.User;
import net.objects.UserShop;
import tools.HexTools;

public class LobbyServer extends GenericTCPServer {
	protected Room[] rooms;
	
	public static final int MAX_ROOMS = 300;
	
	public Server server;
	
	public GameServer gameServer;
	
	public ArrayList<Trade> ongoingTrades;
	
	public LobbyServer(Server server) {
		super(server.getName(), server.getPort(), server.getMaxPopulation() / 2);
		this.server = server;
		
		rooms = new Room[MAX_ROOMS];
		ongoingTrades = new ArrayList<Trade>();
		startLobbyThread();
	}
	
	public Room getRoom(int index) {
		if (index < 0) {
			return null;
		}
		
		return rooms[index];
	}
	
	public void setRoom(int index, Room room) {
		rooms[index] = room;
	}

	@Override
	public GenericHandler processPacket(UserSession userSession, int messageID, byte[] messageBytes) {
		GenericHandler message = null;

		switch (messageID) {
		case Messages.JOIN_LOBBY_REQUEST:
			message = new JoinLobbyHandler(this, userSession, messageBytes);
			break;
		case Messages.CREATE_ROOM_REQUEST:
			message = new CreateRoomHandler(this, userSession, messageBytes);
			break;
		case Messages.GET_USER_INFO_REQUEST:
			message = new GetUserInfoHandler(this, userSession, messageBytes);
			break;
		case Messages.GET_ROOM_INFO_REQUEST:
			message = new GetRoomInfoHandler(this, userSession, messageBytes);
			break;
		case Messages.JOIN_ROOM_REQUEST:
			message = new JoinRoomHandler(this, userSession, messageBytes);
			break;
		case Messages.ROOM_PLAYERS_UPDATE_REQUEST:
			message = new RoomPlayersUpdateHandler(this, userSession, messageBytes);
			break;
		case Messages.ITEMS_CHANGED_REQUEST:
			message = new ItemsChangedHandler(this, userSession, messageBytes);
			break;
		case Messages.LEAVE_ROOM_REQUEST:
			message = new LeaveRoomHandler(this, userSession, messageBytes);
			break;
		case Messages.KICK_PLAYER_REQUEST:
			message = new KickPlayerHandler(this, userSession, messageBytes);
			break;
		case Messages.LEAVE_GAME_REQUEST:
			message = new LeaveGameHandler(this, userSession, messageBytes);
			break;
		case Messages.FIND_USER_REQUEST:
			message = new FindUserHandler(this, userSession, messageBytes);
			break;
		case Messages.BUY_SCROLL_REQUEST:
			message = new BuyScrollHandler(this, userSession, messageBytes);
			break;
		case Messages.CHAT_MESSAGE_REQUEST:
			message = new ChatMessageHandler(this, userSession, messageBytes);
			break;
		case Messages.PLAYER_RESURRECTION_REQUEST:
			message = new PlayerResurrectionHandler(this, userSession, messageBytes);
			break;
		case Messages.TRADE_REQUEST:
			message = new TradeHandler(this, userSession, messageBytes);
			break;
		case Messages.FUSION_REQUEST:
			message = new FusionHandler(this, userSession, messageBytes);
			break;
		case Messages.ROOM_NAME_CHANGED_REQUEST:
			message = new RoomNameChangedHandler(this, userSession, messageBytes);
			break;
		case Messages.INVITE_PLAYER_REQUEST:
			message = new InvitePlayersHandler(this, userSession, messageBytes);
			break;
		case Messages.GET_FRIENDS_REQUEST:
			message = new GetFriendsHandler(this, userSession, messageBytes);
			break;
		case Messages.ADD_FRIEND_REQUEST:
			message = new AddFriend(this, userSession, messageBytes);
			break;
		case Messages.PLAYER_DEATH_REQUEST:
			message = new PlayerDeathHandler(this, userSession, messageBytes);
			break;
		case Messages.USE_ACTION_REQUEST:
			message = new UseActionHandler(this, userSession, messageBytes);
			break;
		case Messages.SOCCER_GOAL_REQUEST:
			message = new SoccerGoalHandler(this, userSession, messageBytes);
			break;
		case Messages.GET_LOBBY_USERS_REQUEST:
			message = new GetLobbyUsersHandler(this, userSession, messageBytes);
			break;
		case Messages.MONSTER_DEATH_REQUEST:
			message = new MonsterDeathHandler(this, userSession, messageBytes);
			break;
		case Messages.CRYSTAL_DEATH_REQUEST:
			message = new CrystalDeathHandler(this, userSession, messageBytes);
			break;
		case Messages.QUEST_INFO_REQUEST:
			message = new QuestInfoHandler(this, userSession, messageBytes);
			break;
		case Messages.ENTER_CARD_SHOP_REQUEST:
			message = new EnterCardShopHandler(this, userSession, messageBytes);
			break;
		case Messages.SELL_CARD_REQUEST:
			message = new SellCardHandler(this, userSession, messageBytes);
			break;
		case Messages.BUY_CARD_OR_CHARGE_REQUEST:
			message = new BuyCardHandler(this, userSession, messageBytes);
			break;
		case Messages.USER_SHOP_I_ACTION_REQUEST:
			message = new UserShopIActionHandler(this, userSession, messageBytes);
			break;
		case Messages.RACE_INFO_RESPONSE:
			message = new RaceInfoHandler(this, userSession, messageBytes);
			break;
		case Messages.GOLD_FORCE_CHARGE_REQUEST:
			message = new GoldForceChargeHandler(this, userSession, messageBytes);
			break;
		case Messages.SYMBOL_STATE_CHANGED_REQUEST:
			message = new SymbolStateChanged(this, userSession, messageBytes);
			break;
		case Messages.GET_TOP_GUILDS_REQUEST:
			message = new GetTopGuildsHandler(this, userSession, messageBytes);
			break;
		case Messages.BIG_MATCH_INFO_REQUEST:
			message = new BigMatchInfo(this, userSession, messageBytes);
			break;
		case Messages.BIG_MATCH_DEATH_REQUEST:
			message = new BigMatchDeathHandler(this, userSession, messageBytes);
			break;
		case Messages.GAME_MASTER_BAN_REQUEST:
			message = new GameMasterBanHandler(this, userSession, messageBytes);
			break;
		case Messages.GET_ROOM_PLAYERS_GUILD_RANK_REQUEST:
			message = new GetRoomPlayersGuildRankHandler(this, userSession, messageBytes);
			break;
		case Messages.CRYSTALS_INFO_REQUEST:
			message = new CrystalsInfoHandler(this, userSession, messageBytes);
			break;
		case Messages.ID_VERIFICATION_REQUEST:
			message = new IDVerificationHandler(this, userSession, messageBytes);
			break;
		case Messages.GIFT_ID_VERIFY_REQUEST:
			message = new GiftIDVeficationHandler(this, userSession, messageBytes);
			break;
		case Messages.SEND_GIFT_REQUEST:
			message = new SendGiftHandler(this, userSession, messageBytes);
			break;
		case Messages.HOKEY_GOAL_REQUEST:
			message = new HokeyGoalHandler(this, userSession, messageBytes);
			break;
		case Messages.SEND_MEMO_REQUEST:
			message = new SendMemoHandler(this, userSession, messageBytes);
			break;
		case Messages.PET_KILLED_REQUEST:
			message = new PetKilledHandler(this, userSession, messageBytes);
			break;
		case Messages.USER_SHOP_SEARCH_REQUEST:
			message = new UserShopSearchHandler(this, userSession, messageBytes);
			break;
		case Messages.USER_SHOP_EXTRA_SEARCH_REQUEST:
			message = new UserShopExtraSearchHandler(this, userSession, messageBytes);
			break;
		case Messages.USER_SHOP_BUY_CARD_REQUEST:
			message = new UserShopBuyCardHandler(this, userSession, messageBytes);
			break;
		case Messages.REMOVE_USER_SHOP_I_CARD_REQUEST:
			message = new RemoveUserShopICardHandler(this, userSession, messageBytes);
			break;
		case Messages.BUY_ELEMENTS_REQUEST:
			message = new BuyElementHandler(this, userSession, messageBytes);
			break;
		case Messages.CHANGE_PASSWORD_REQUEST:
			message = new ChangePasswordHandler(this, userSession, messageBytes);
			break;
//		case Messages.SEND_ANY_MESSAGE_RESPONSE:
//			message = new SendAnyMessageHandler(this, userSession, messageBytes);
//			break;
		case Messages.ROOM_GAME_MODE_CHANGED_REQUEST:
			message = new RoomGameModeChangedHandler(this, userSession, messageBytes);
			break;
		case Messages.GET_SCROLL_REQUEST:
			message = new GetScrollHandler(this, userSession, messageBytes);
			break;
		case Messages.CHANGE_CHARACTER_REQUEST:
			message = new ChangeCharacterHandler(this, userSession, messageBytes);
			break;
		case Messages.MISSION_START_COUNTDOWN_REQUEST:
			message = new StartCountdownHandler(this, userSession, messageBytes);
			break;
		case Messages.MISSION_COMPLETED_REQUEST:
			message = new MissionCompletedHandler(this, userSession, messageBytes);
			break;
		case Messages.MISSION_INFO_REQUEST:
			message = new MissionInfoHandler(this, userSession, messageBytes);
			break;
		case Messages.INCREASE_CARD_SLOTS_REQUEST:
			message = new IncreaseCardSlotsHandler(this, userSession, messageBytes);
			break;
		case Messages.AUTO_USER_SHOP_NEW_ITEM_REQUEST:
			message = new AutoUserShopNewItemHandler(this, userSession, messageBytes);
			break;
		case Messages.TREASURE_SPAWNED_REQUEST:
			message = new TreasureSpawnedHandler(this, userSession, messageBytes);
			break;
		case Messages.INFINITY_INFO_REQUEST:
			message = new InfinityInfoHandler(this, userSession, messageBytes);
			break;
		case Messages.GET_GUILD_MARK_REQUEST:
			message = new GetGuildMarkHandler(this, userSession, messageBytes);
			break;
		default:
			HexTools.printHexArray(messageBytes, 0x14, false);
		}
		
		return message;
	}
	
	public void sendToUserMessage(String username, byte[] message) throws IOException {
		UserSession userSession = findUserSession(username);
		
		if (userSession != null) {
			userSession.sendMessage(HexTools.duplicateArray(message));
		}
	}

	public void sendBroadcastMessage(UserSession userSession, byte[] message) throws IOException {
		for (UserSession currentUserSession : userSessions) {
			// Send the message to everyone but yourself
			
			if (!currentUserSession.getUser().getUsername().equals(userSession.getUser().getUsername())) {
				// We need to duplicate the array because message is getting changed (some fields are changing. also the message is encrypted)
				currentUserSession.sendMessage(HexTools.duplicateArray(message));
			}
		}
	}
	
	public void sendBroadcastGameMessage(UserSession userSession, byte[] message) throws IOException {
		for (UserSession currentUserSession : userSessions) {
			if (currentUserSession.udpIPAddress != null) {
				// We need to duplicate the array because message is getting changed (some fields are changing. also the message is encrypted)
				gameServer.sendMessage(userSession, currentUserSession, HexTools.duplicateArray(message));
			}
		}
	}
	
	public void sendFriendsMessage(UserSession userSession, byte[] message) throws IOException {
		for (Friend friend : userSession.getUser().getFriends()) {
			UserSession friendSession = findUserSession(friend.getFriendName());
			
			if (friendSession != null) {
				// We need to duplicate the array because message is getting changed (some fields are changing. also the message is encrypted)
				friendSession.sendMessage(HexTools.duplicateArray(message));
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void sendGuildMessage(UserSession userSession, byte[] message, boolean sendToSelf) throws IOException, SQLException {
		Session session = Database.getSession();
		session.beginTransaction();
		List<GuildMember> guildMembers = session.createQuery("from GuildMember where guildName = :guildName").setParameter("guildName", userSession.getUser().getGuildName()).list();
		
		for (GuildMember member : guildMembers) {
			UserSession guildMemberSession = findUserSession(member.getUsername());
			
			if (guildMemberSession != null && (guildMemberSession != userSession || sendToSelf)) {
				// We need to duplicate the array because message is getting changed (some fields are changing. also the message is encrypted)
				guildMemberSession.sendMessage(HexTools.duplicateArray(message));
			}
		}
		
		session.getTransaction().commit();
		session.close();
	}
	
	public void sendRoomMessage(UserSession userSession, byte[] message, boolean sendToSelf) throws IOException {
		int roomID = userSession.getUser().getRoomIndex();
		
		if (getRoom(roomID) != null) {
			for (UserSession currentUserSession : getRoom(roomID).getUsers()) {
				// If the user is not null
				if (currentUserSession != null) {
					// If the user is someone else
					if (sendToSelf || currentUserSession.getUser().getRoomSlot() != userSession.getUser().getRoomSlot()) {
						// We need to duplicate the array because message is getting changed (some fields are changing. also the message is encrypted)
						currentUserSession.sendMessage(HexTools.duplicateArray(message));
					}
				}
			}
		}
	}
	
	public UserSession findUserSession(String username) {
		if (username == null) {
			return null;
		}
		
		int low = 0;
		int high = userSessions.size() - 1;
		
		while (low <= high) {
	        int middle = (low + high) / 2;
	        if (username.compareTo(userSessions.get(middle).getUser().getUsername()) > 0) {
	        	low = middle + 1;
	        }
	        else if (username.compareTo(userSessions.get(middle).getUser().getUsername()) < 0) {
	        	high = middle - 1;
	        }
	        else {
	        	return userSessions.get(middle);
	        }
		}
		
		return null;
	}

//	public UserTCPSession findUserSession(String username) {
//		for (UserTCPSession userSession : usersSessions) {
//			if (userSession.getUser().username.equals(username)) {
//				return userSession;
//			}
//		}
//		
//		return null;
//	}

	public UserSession findUserSession(InetAddress ipAddress, int port) {
		for (UserSession userSession : userSessions) {
			if (userSession.udpIPAddress != null && userSession.udpIPAddress.equals(ipAddress) && userSession.udpPort == port) {
				return userSession;
			}
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<UserShop> findShops(int elementType, int cardLevel, int cardType) throws SQLException {
		String query;
		
		// premium cards + pets
		if (cardType == 90) {
			query = "FROM UserShop WHERE (cardID >= 2000 AND cardID < 3000) OR (cardID >= 5000 AND cardID < 6000)";
		}
		// gold force
		else if (cardType == 91) {
			query = "FROM UserShop WHERE (cardID >= 3000 AND cardID < 4000)";
		}
		// avatar cards
		else if (cardType == 92) {
			query = "FROM UserShop WHERE (cardID >= 4000 AND cardID < 5000)";
		}
		// normal cards (black cards)
		else if (cardType != 0) {
			if (elementType == -1) {
				query = "FROM UserShop WHERE cardID LIKE \'_" + (cardType / 10) + "_" + (cardType % 10) + "\'";
			}
			else {
				query = "FROM UserShop WHERE cardID LIKE \'_" + (cardType / 10) + "" + elementType + "" + (cardType % 10) + "\'";
			}
			
			if (cardLevel != -1) {
				query += " AND cardLevel = " + cardLevel;
			}
		}
		// elements (white cards)
		else {
			if (elementType != -1) {
				query = "FROM UserShop WHERE floor(cardID / 10000) = " + elementType;
			}
			else {
				query = "FROM UserShop WHERE cardID >= 10000";
			}
		}
		
		Session session = Database.getSession();
		session.beginTransaction();
		List<UserShop> result = session.createQuery(query).list();
		session.getTransaction().commit();
		session.close();
		
		return result;
	}
	
	public void addShop(UserShop shop) throws SQLException {
		Session session = Database.getSession();
		session.beginTransaction();
		session.save(shop);
		session.getTransaction().commit();
		session.close();
	}
	
	@Override
	public void onUserDisconnect(UserSession userSession) throws SQLException, IOException {
		// Send a leave lobby message
		sendBroadcastMessage(userSession, new GetLobbyUsersHandler(this, userSession).getResponse(userSession, false));
		
		// Leave a game/room
		if (userSession.getUser().isInRoom()) {
			LeaveRoomHandler leaveRoomHandler = new LeaveRoomHandler(this, userSession);
			leaveRoomHandler.processMessage(userSession);
			sendRoomMessage(userSession, leaveRoomHandler.getResponse(userSession), false);
			leaveRoomHandler.isDisconnected = true;
			leaveRoomHandler.afterSend();
		}
		else if (userSession.getUser().isInGame()) {
			LeaveGameHandler leaveGameHandler = new LeaveGameHandler(this, userSession);
			leaveGameHandler.processMessage(userSession);
			sendRoomMessage(userSession, leaveGameHandler.getResponse(userSession), false);
			leaveGameHandler.isDisconnected = true;
			leaveGameHandler.afterSend();
		}
		
		// Update the population
		Session session = Database.getSession();
		session.beginTransaction();
		server.setPopulation(userSessions.size());
		session.update(server);
		session.getTransaction().commit();
		session.close();
		
		// Set server ID and isConnected
		session = Database.getSession();
		session.beginTransaction();
		userSession.getUser().setConnected(false);
		userSession.getUser().setServer(null);
		session.update(userSession.getUser());
		session.getTransaction().commit();
		session.close();
				
		// Send your connectivity to anyone else in your guild
		sendGuildMessage(userSession, new GuildMemberOnlineStatusHandler(this, userSession).getResponse(userSession, false), false);
	}

	public ArrayList<UserSession> getUserSessions() {
		return userSessions;
	}
	
	@SuppressWarnings("unchecked")
	public void onJoinLobby(UserSession userSession) throws IOException, SQLException {
		// Get list of rooms
		for (int i = 0; i < MAX_ROOMS; i += 22) {
			userSession.sendMessage(new GetListOfRoomsHandler(this, userSession).getResponse(i));
		}
		
		// Get list of users
		GetLobbyUsersHandler getLobbyUsersHandler = new GetLobbyUsersHandler(this, userSession);
		
		for (UserSession currentUserSession : userSessions) {
			userSession.sendMessage(getLobbyUsersHandler.getResponse(currentUserSession, true));
		}
		
		// Send everyone that you got connected
		sendBroadcastMessage(userSession, new GetLobbyUsersHandler(this, userSession).getResponse(userSession, true));
		
		// Get friends
		userSession.sendMessage(new GetFriendsHandler(this, userSession).getResponse());
		
		// Get mission level
		userSession.sendMessage(new GetMissionLevelHandler(this, userSession).getResponse());
		
		// Send your connectivity to anyone else in your guild
		sendGuildMessage(userSession, new GuildMemberOnlineStatusHandler(this, userSession).getResponse(userSession, true), false);
		
		// Get connectivities from anyone who's connected in the guild
		Session session = Database.getSession();
		session.beginTransaction();
		List<GuildMember> guildMembers = session.createQuery("from GuildMember where guildName = :guildName").setParameter("guildName", userSession.getUser().getGuildName()).list();
		
		for (GuildMember member : guildMembers) {
			UserSession guildMemberSession = findUserSession(member.getUsername());

			if (guildMemberSession != null) {
				userSession.sendMessage(new GuildMemberOnlineStatusHandler(this, userSession).getResponse(guildMemberSession, true));
			}
		}
		
		// Set server ID and isConnected
		session = Database.getSession();
		session.beginTransaction();
		userSession.getUser().setConnected(true);
		userSession.getUser().setServer(server);
		session.update(userSession.getUser());
		session.getTransaction().commit();
		session.close();
	}
	
	public void setGameServer(GameServer gameServer) {
		this.gameServer = gameServer;
	}

	public void onKeepAlive(InetAddress ipAddress, int port) throws IOException, SQLException {
		UserSession userSession = findUserSession(ipAddress, port);
		
		if (userSession != null) {
			if (userSession.getUser().isInGame()) {
				Room room = getRoom(userSession.getUser().getRoomIndex());
				
				room.totalTicks++;
				
				if (room.totalTicks % 10 == 0) {
					sendRoomMessage(userSession, new SpawnElementHandler(this, userSession).getResponse(), true);
				}
				if (room.totalTicks % 1 == 0) {
					sendRoomMessage(userSession, new SpawnCodeHandler(this, userSession).getResponse(), true);
				}
			}
			userSession.getUser().setTotalTicks(userSession.getUser().getTotalTicks() + 1);
			
			if (userSession.getUser().getTotalTicks() % 200 == 0) {
				int times = userSession.getUser().getTotalTicks() / 200;
				
				if (times >= 4) {
					times = 4;
				}
				
				int amount = (int) Math.pow(2, times);
				
				if (userSession.getUser().isTimeBonus()) {
					amount *= 2;
				}
				
				userSession.getUser().setWhiteCard(userSession.getUser().getElementType() - 1, userSession.getUser().getWhiteCard(userSession.getUser().getElementType() - 1) + amount);
				User.saveUser(userSession.getUser());
			}
		}
	}
	
	public void startLobbyThread() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						for (int i = 0; i < rooms.length; i++) {
							if (rooms[i] != null) {
								if (rooms[i].getGameMode() == GameMode.INFINITY_SURVIVAL || rooms[i].getGameMode() == GameMode.INFINITY_KING || rooms[i].getGameMode() == GameMode.INFINITY_SYMBOL) {
									if (rooms[i].roomStartTime != 0 && (System.currentTimeMillis() - rooms[i].roomStartTime) >= rooms[i].roomTimerTime) {
										showInfinityScore(rooms[i]);
									}
								}
							}
						}
						
						Thread.sleep(500);
					}
				} catch (IOException | SQLException | InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		}).start();
	}
	
	public void showInfinityScore(Room room) throws IOException, SQLException {
		System.out.println("in start countdown");
		
		int[] luckyMultiplier = new int[8];
		int[] elements = new int[8];
		int[] levels = new int[8];
		
		int[] slots = new int[8];
		
		for (int i = 0; i < 8; i++) {
			slots[i] = i;
		}
		
		int minimumIndex;
		for (int i = 0; i < 8; i++) {
			minimumIndex = -1;
			if (room.getUserSession(i) != null) {
				minimumIndex = i;
				for (int j = i; j < 8; j++) {
					if (room.getUserSession(j) != null && room.infinityPoints[minimumIndex] > room.infinityPoints[j]) {
						minimumIndex = j;
					}
				}
				
				int temp = room.infinityPoints[minimumIndex];
				room.infinityPoints[minimumIndex] = room.infinityPoints[i];
				room.infinityPoints[i] = temp;
				
				temp = slots[minimumIndex];
				slots[minimumIndex] = slots[i];
				slots[i] = temp;
			}
		}
		
		System.out.println(Arrays.toString(slots));
		int[] coins = new int[8];
		int k = 0;
		for (int i = 0; i < 8; i++) {
			if (room.getUserSession(slots[i]) != null) {
				coins[slots[i]] = 3 + k;
				luckyMultiplier[slots[i]] = 2 + k;
				k++;
			}
		}
		
		// If we have 8 players, the first one gets 11 coins, not 10.
		// also, the lucky multiplier is 10, not 9.
		if (k == 8) {
			coins[slots[7]] = 11;
			luckyMultiplier[slots[7]] = 10;
		}
		
		for (int i = 0; i < 8; i++) {
			UserSession currentUserSession = room.getUserSession(i);
			
			if (currentUserSession != null) {
				elements[i] = (int) (Math.random() * 4) + 1;
				levels[i] = ExperienceHelper.getLevel(currentUserSession.getUser().getPlayerExperience() + 8000 * luckyMultiplier[i]);
			}
		}
		
		for (int i = 0; i < 8; i++) {
			UserSession currentUserSession = room.getUserSession(i);
			
			if (currentUserSession != null) {
				InfinityPointsHandler iph = new InfinityPointsHandler(this, currentUserSession);
				currentUserSession.sendMessage(iph.getResponse(currentUserSession.getUser(), luckyMultiplier, levels, elements, coins));
				iph.afterSend();
			}
		}
	}
	
	public void addTrade(Trade trade) {
		ongoingTrades.add(trade);
	}
	
	public Trade findTrade(String username) {
		for (Trade trade : ongoingTrades) {
			if (trade.firstUsername.equals(username) || trade.secondUsername.equals(username)) {
				return trade;
			}
		}
		
		return null;
	}
	
	public void removeTrade(Trade trade) {
		ongoingTrades.remove(trade);
	}
}
