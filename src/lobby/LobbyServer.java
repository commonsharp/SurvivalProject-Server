package lobby;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DatabaseConnection;
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
import lobby.handlers.StartCountdownHandler;
import lobby.handlers.SymbolStateChanged;
import lobby.handlers.TradeHandler;
import lobby.handlers.UseActionHandler;
import lobby.handlers.UserShopBuyCardHandler;
import lobby.handlers.UserShopExtraSearchHandler;
import lobby.handlers.UserShopIActionHandler;
import lobby.handlers.UserShopSearchHandler;
import net.GenericTCPServer;
import net.Messages;
import net.UserSession;
import net.handlers.GenericHandler;
import net.objects.Room;
import net.objects.UserShop;
import tools.HexTools;

public class LobbyServer extends GenericTCPServer {
	protected Room[] rooms;
	
	public static final int MAX_ROOMS = 300;
	
	public String hostname = "10.0.0.50";
	public int port = 21001;
	
	public GameServer gameServer;
	
	public LobbyServer(String hostname, int port, int initialCapacity) {
		super("Lobby server", port, initialCapacity);
		this.hostname = hostname;
		this.port = port;
		
		rooms = new Room[MAX_ROOMS]; // change....
	}
	
	public Room getRoom(int index) {
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
			
			if (!currentUserSession.getUser().username.equals(userSession.getUser().username)) {
				// We need to duplicate the array because message is getting changed (some fields are changing. also the message is encrypted)
				currentUserSession.sendMessage(HexTools.duplicateArray(message));
			}
		}
	}
	
	public void sendBroadcastGameMessage(UserSession userSession, byte[] message) throws IOException {
		for (UserSession currentUserSession : userSessions) {
			if (currentUserSession.getUser().udpIPAddress != null) {
				// We need to duplicate the array because message is getting changed (some fields are changing. also the message is encrypted)
				gameServer.sendMessage(userSession, currentUserSession.getUser(), HexTools.duplicateArray(message));
			}
		}
	}
	
	public void sendFriendsMessage(UserSession userSession, byte[] message) throws IOException {
		for (String friend : userSession.getUser().friends) {
			UserSession friendSession = findUserSession(friend);
			
			if (friendSession != null) {
				// We need to duplicate the array because message is getting changed (some fields are changing. also the message is encrypted)
				friendSession.sendMessage(HexTools.duplicateArray(message));
			}
		}
	}
	
	public void sendGuildMessage(UserSession userSession, byte[] message, boolean sendToSelf) throws IOException, SQLException {
		Connection con = DatabaseConnection.getConnection();
		PreparedStatement ps = con.prepareStatement("SELECT * FROM guild_member WHERE guildName=?");
		ps.setString(1, userSession.getUser().guildName);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			UserSession guildMemberSession = findUserSession(rs.getString("username"));
			
			if (guildMemberSession != null && (guildMemberSession != userSession || sendToSelf)) {
				// We need to duplicate the array because message is getting changed (some fields are changing. also the message is encrypted)
				guildMemberSession.sendMessage(HexTools.duplicateArray(message));
			}
		}
		
		rs.close();
		ps.close();
		con.close();
	}
	
	public void sendRoomMessage(UserSession userSession, byte[] message, boolean sendToSelf) throws IOException {
		int roomID = userSession.getUser().roomIndex;
		
		if (getRoom(roomID) != null) {
			for (UserSession currentUserSession : getRoom(roomID).getUsers()) {
				// If the user is not null
				if (currentUserSession != null) {
					// If the user is someone else
					if (sendToSelf || currentUserSession.getUser().roomSlot != userSession.getUser().roomSlot) {
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
	        if (username.compareTo(userSessions.get(middle).getUser().username) > 0) {
	        	low = middle + 1;
	        }
	        else if (username.compareTo(userSessions.get(middle).getUser().username) < 0) {
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
			if (userSession.getUser().udpIPAddress != null && userSession.getUser().udpIPAddress.equals(ipAddress) && userSession.getUser().udpPort == port) {
				return userSession;
			}
		}
		
		return null;
	}
	
	public ArrayList<UserShop> findShops(int elementType, int cardLevel, int cardType) throws SQLException {
		String query;
		
		// premium cards + pets
		if (cardType == 90) {
			query = "SELECT * FROM user_shop WHERE (card_id >= 2000 AND card_id < 3000) OR (card_id >= 5000 AND card_id < 6000);";
		}
		// avatar cards
		else if (cardType == 91) {
			query = "SELECT * FROM user_shop WHERE (card_id >= 4000 AND card_id < 5000)";
		}
		// gold force
		else if (cardType == 92) {
			query = "SELECT * FROM user_shop WHERE (card_id >= 3000 AND card_id < 4000)";
		}
		// normal cards (black cards)
		else if (cardType != 0) {
			if (elementType == -1) {
				query = "SELECT * FROM user_shop WHERE card_id LIKE \'" + "_" + (cardType / 10) + "_" + (cardType % 10) + "\'";
			}
			else {
				query = "SELECT * FROM user_shop WHERE card_id LIKE \'" + "_" + (cardType / 10) + "" + elementType + "" + (cardType % 10) + "\'";
			}
			
			if (cardLevel != -1) {
				query += " AND card_level = " + cardLevel;
			}
		}
		// elements (white cards)
		else {
			if (elementType != -1) {
				query = "SELECT * FROM user_shop WHERE floor(card_id / 10000) = " + elementType;
			}
			else {
				query = "SELECT * FROM user_shop WHERE card_id >= 10000";
			}
		}
		
		Connection con = DatabaseConnection.getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		
		ArrayList<UserShop> result = new ArrayList<UserShop>();
		
		while (rs.next()) {
			result.add(new UserShop(rs.getString("username"), rs.getInt("card_id"), rs.getInt("card_premium_days"),
					rs.getInt("card_level"), rs.getInt("card_skill"), rs.getInt("code")));
		}
		
		
		return result;
	}
	
	public void addShop(String username, int cardID, int cardPremiumDays, int cardLevel, int cardSkill, long code) throws SQLException {
		Connection con = DatabaseConnection.getConnection();
		PreparedStatement ps = con.prepareStatement("INSERT INTO user_shop (username, card_id, card_premium_days, card_level, card_skill, code) VALUES (?, ?, ?, ?, ?, ?);");
		ps.setString(1, username);
		ps.setInt(2, cardID);
		ps.setInt(3, cardPremiumDays);
		ps.setInt(4, cardLevel);
		ps.setInt(5, cardSkill);
		ps.setLong(6, code);
		ps.executeUpdate();
		ps.close();
		con.close();
	}
	
	@Override
	public void onUserDisconnect(UserSession userSession) throws SQLException, IOException {
		userSession.getUser().saveUser();
		
		// Send a leave lobby message
		sendBroadcastMessage(userSession, new GetLobbyUsersHandler(this, userSession).getResponse(userSession, false));
		
		// Leave a game/room
		if (userSession.getUser().isInRoom) {
			LeaveRoomHandler leaveRoomHandler = new LeaveRoomHandler(this, userSession);
			leaveRoomHandler.processMessage(userSession);
			sendRoomMessage(userSession, leaveRoomHandler.getResponse(userSession), false);
		}
		else if (userSession.getUser().isInGame) {
			LeaveGameHandler leaveGameHandler = new LeaveGameHandler(this, userSession);
			leaveGameHandler.processMessage(userSession);
			sendRoomMessage(userSession, leaveGameHandler.getResponse(userSession), false);
		}
		
		// Update the population
		Connection con = DatabaseConnection.getConnection();
		PreparedStatement ps = con.prepareStatement("UPDATE servers SET population=? WHERE hostname=? AND port=?");
		ps.setInt(1, userSessions.size());
		ps.setString(2, hostname);
		ps.setInt(3, port);
		ps.executeUpdate();
		ps.close();
		
		// Set server hostname, server port and isConnected
		ps = con.prepareStatement("UPDATE users SET is_connected = false WHERE username = ?;");
		ps.setString(1, userSession.getUser().username);
		ps.executeUpdate();
		ps.close();
		
		con.close();
		
		// Send your connectivity to anyone else in your guild
		sendGuildMessage(userSession, new GuildMemberOnlineStatusHandler(this, userSession).getResponse(userSession, false), false);
	}

	public ArrayList<UserSession> getUserSessions() {
		return userSessions;
	}
	
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
		Connection con = DatabaseConnection.getConnection();
		PreparedStatement ps = con.prepareStatement("SELECT * FROM guild_member WHERE guildName=?");
		ps.setString(1, userSession.getUser().guildName);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			UserSession guildMemberSession = findUserSession(rs.getString("username"));

			if (guildMemberSession != null) {
				userSession.sendMessage(new GuildMemberOnlineStatusHandler(this, userSession).getResponse(guildMemberSession, true));
			}
		}
		
		rs.close();
		ps.close();
		
		// Set server hostname, server port and isConnected
		ps = con.prepareStatement("UPDATE users SET server_hostname = ?, server_port = ?, is_connected = true WHERE username = ?;");
		ps.setString(1, hostname);
		ps.setInt(2, port);
		ps.setString(3, userSession.getUser().username);
		ps.executeUpdate();
		
		con.close();
	}
	
	public void setGameServer(GameServer gameServer) {
		this.gameServer = gameServer;
	}
}
