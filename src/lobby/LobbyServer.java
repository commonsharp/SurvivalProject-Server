package lobby;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.SQLException;

import lobby.handlers.AddFriend;
import lobby.handlers.AutoUserShopNewItemHandler;
import lobby.handlers.BigMatchDeathHandler;
import lobby.handlers.BigMatchInfo;
import lobby.handlers.BuyCardHandler;
import lobby.handlers.BuyElementHandler;
import lobby.handlers.BuyScrollHandler;
import lobby.handlers.ChatMessageHandler;
import lobby.handlers.CreateRoomHandler;
import lobby.handlers.CrystalDeathHandler;
import lobby.handlers.EnterCardShopHandler;
import lobby.handlers.FindUserHandler;
import lobby.handlers.FusionHandler;
import lobby.handlers.GameMasterBanHandler;
import lobby.handlers.GetFriendsHandler;
import lobby.handlers.GetLobbyUsersHandler;
import lobby.handlers.GetRoomInfoHandler;
import lobby.handlers.GetTopGuildsHandler;
import lobby.handlers.GetTopGuildsMarkHandler;
import lobby.handlers.GetUserInfoHandler;
import lobby.handlers.HokeyGoalHandler;
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
import lobby.handlers.RoomNameChangedHandler;
import lobby.handlers.RoomPlayersUpdateHandler;
import lobby.handlers.RoomTypeChangedHandler;
import lobby.handlers.SoccerGoalHandler;
import lobby.handlers.StartCountdownHandler;
import lobby.handlers.SymbolStateChanged;
import lobby.handlers.TradeHandler;
import lobby.handlers.UseScrollHandler;
import lobby.handlers.UserShopNewItemHandler;
import lobby.handlers.UserShopSearchHandler;
import net.GenericTCPServer;
import net.Messages;
import net.UserTCPSession;
import net.handlers.GenericHandler;
import net.objects.Card;
import net.objects.Room;
import net.objects.UserShop;
import tools.HexTools;

public class LobbyServer extends GenericTCPServer {
	protected Room[] rooms;
	protected UserShop[] userShops;
	
	public LobbyServer(int port, int initialCapacity) {
		super("Lobby server", port, initialCapacity);
		
		rooms = new Room[200]; // change....
		userShops = new UserShop[200]; // change...
	}
	
	public Room getRoom(int index) {
		return rooms[index];
	}
	
	public void setRoom(int index, Room room) {
		rooms[index] = room;
	}

	@Override
	public GenericHandler processPacket(UserTCPSession userSession, int messageID, byte[] messageBytes) {
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
		case Messages.GET_FRIENDS_REQUEST:
			message = new GetFriendsHandler(this, userSession, messageBytes);
			break;
		case Messages.ADD_FRIEND_REQUEST:
			message = new AddFriend(this, userSession, messageBytes);
			break;
		case Messages.PLAYER_DEATH_REQUEST:
			message = new PlayerDeathHandler(this, userSession, messageBytes);
			break;
		case Messages.USE_SCROLL_REQUEST:
			message = new UseScrollHandler(this, userSession, messageBytes);
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
		case Messages.BUY_CARD_OR_CHARGE_REQUEST:
			message = new BuyCardHandler(this, userSession, messageBytes);
			break;
		case Messages.USER_SHOP_NEW_ITEM_REQUEST:
			message = new UserShopNewItemHandler(this, userSession, messageBytes);
			break;
		case Messages.RACE_INFO_RESPONSE:
			message = new RaceInfoHandler(this, userSession, messageBytes);
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
		case Messages.HOKEY_GOAL_REQUEST:
			message = new HokeyGoalHandler(this, userSession, messageBytes);
			break;
		case Messages.PET_KILLED_REQUEST:
			message = new PetKilledHandler(this, userSession, messageBytes);
			break;
		case Messages.USER_SHOP_SEARCH_REQUEST:
			message = new UserShopSearchHandler(this, userSession, messageBytes);
			break;
		case Messages.BUY_ELEMENTS_REQUEST:
			message = new BuyElementHandler(this, userSession, messageBytes);
			break;
		case Messages.ROOM_TYPE_CHANGED_REQUEST:
			message = new RoomTypeChangedHandler(this, userSession, messageBytes);
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
		case Messages.AUTO_USER_SHOP_NEW_ITEM_REQUEST:
			message = new AutoUserShopNewItemHandler(this, userSession, messageBytes);
			break;
		case Messages.GET_TOP_GUILDS_MARK_REQUEST:
			message = new GetTopGuildsMarkHandler(this, userSession, messageBytes);
			break;
		default:
			HexTools.printHexArray(messageBytes, 0x14, false);
		}
		
		return message;
	}
	
	public void sendToUserMessage(String username, byte[] message) throws IOException {
		UserTCPSession userSession = findUserSession(username);
		
		if (userSession != null) {
			userSession.sendMessage(HexTools.duplicateArray(message));
		}
	}

	public void sendBroadcastMessage(UserTCPSession userSession, byte[] message) throws IOException {
		for (UserTCPSession currentUserSession : usersSessions) {
			// Send the message to everyone but yourself
			
			if (!currentUserSession.getUser().username.equals(userSession.getUser().username)) {
				// We need to duplicate the array because message is getting changed (some fields are changing. also the message is encrypted)
				currentUserSession.sendMessage(HexTools.duplicateArray(message));
			}
		}
	}
	
	public void sendFriendsMessage(UserTCPSession userSession, byte[] message) throws IOException {
		for (String friend : userSession.getUser().friends) {
			UserTCPSession friendSession = findUserSession(friend);
			
			if (friendSession != null) {
				// We need to duplicate the array because message is getting changed (some fields are changing. also the message is encrypted)
				friendSession.sendMessage(HexTools.duplicateArray(message));
			}
		}
	}
	
	public void sendRoomMessage(UserTCPSession userSession, byte[] message, boolean sendToSelf) throws IOException {
		int roomID = userSession.getUser().roomIndex;
		
		for (UserTCPSession currentUserSession : getRoom(roomID).getUsers()) {
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
	

	public UserTCPSession findUserSession(String username) {
		for (UserTCPSession userSession : usersSessions) {
			if (userSession.getUser().username.equals(username)) {
				return userSession;
			}
		}
		
		return null;
	}

	public UserTCPSession findUserSession(InetAddress ipAddress, int port) {
		for (UserTCPSession userSession : usersSessions) {
			if (userSession.getUser().udpIPAddress != null && userSession.getUser().udpIPAddress.equals(ipAddress) && userSession.getUser().udpPort == port) {
				return userSession;
			}
		}
		
		return null;
	}
	
	// need to change to add parameters
	public UserShop[] findShops() {
		int to = findEmptyShop();
		
		UserShop[] result = new UserShop[to];
		
		for (int i = 0; i < to; i++) {
			result[i] = userShops[i];
		}
		
		return result;
	}
	
	public void addShop(String username, Card item, int elementType, int elementCount, int code) {
//		UserShop shop = new UserShop(username, itemID, code);
		UserShop shop;
		
		if (item == null) {
			shop = new UserShop(username, elementType, elementCount, code);
		}
		else {
			shop = new UserShop(username, item, code);
		}
		
		userShops[findEmptyShop()] = shop;
	}
	
	public int findEmptyShop() {
		for (int i = 0; i < userShops.length; i++) {
			if (userShops[i] == null) {
				return i;
			}
		}
		
		return -1;
	}

	@Override
	public void onUserDisconnect(UserTCPSession userTCPSession) throws SQLException {
		userTCPSession.getUser().saveUser();
	}
}
