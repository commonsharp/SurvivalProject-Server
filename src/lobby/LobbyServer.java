package lobby;

import java.io.IOException;
import java.net.InetAddress;

import lobby.handlers.CreateRoomHandler;
import lobby.handlers.CrystalDeathHandler;
import lobby.handlers.JoinRoomHandler;
import lobby.handlers.PetKilledHandler;
import lobby.handlers.FusionHandler;
import lobby.handlers.GameMasterBanHandler;
import lobby.handlers.GetRoomInfoHandler;
import lobby.handlers.GetTopGuildsHandler;
import lobby.handlers.GetTopGuildsMarkHandler;
import lobby.handlers.GetUserInfoHandler;
import lobby.handlers.ItemsChangedHandler;
import lobby.handlers.JoinLobbyHandler;
import lobby.handlers.LeaveGameHandler;
import lobby.handlers.LeaveRoomHandler;
import lobby.handlers.MissionCompletedHandler;
import lobby.handlers.MissionInfoHandler;
import lobby.handlers.PlayerDeathHandler;
import lobby.handlers.PlayerResurrectionHandler;
import lobby.handlers.QuestDeathHandler;
import lobby.handlers.QuestInfoHandler;
import lobby.handlers.BigMatchDeathHandler;
import lobby.handlers.BigMatchInfo;
import lobby.handlers.RoomNameChangedHandler;
import lobby.handlers.RoomPlayersUpdateHandler;
import lobby.handlers.SoccerGoalHandler;
import lobby.handlers.StartCountdownHandler;
import net.GenericTCPServer;
import net.Messages;
import net.UserTCPSession;
import net.handlers.GenericHandler;
import net.objects.Room;
import net.objects.User;
import tools.HexTools;

public class LobbyServer extends GenericTCPServer {
	protected Room[] rooms;
	
	public LobbyServer(int port) {
		super("Lobby server", port);
		
		rooms = new Room[200]; // change....
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
		case Messages.LEAVE_GAME_REQUEST:
			message = new LeaveGameHandler(this, userSession, messageBytes);
			break;
		case Messages.PLAYER_RESURRECTION_REQUEST:
			message = new PlayerResurrectionHandler(this, userSession, messageBytes);
			break;
		case Messages.FUSION_REQUEST:
			message = new FusionHandler(this, userSession, messageBytes);
			break;
		case Messages.ROOM_NAME_CHANGED_REQUEST:
			message = new RoomNameChangedHandler(this, userSession, messageBytes);
			break;
		case Messages.PLAYER_DEATH_REQUEST:
			message = new PlayerDeathHandler(this, userSession, messageBytes);
			break;
		case Messages.SOCCER_GOAL_REQUEST:
			message = new SoccerGoalHandler(this, userSession, messageBytes);
			break;
		case Messages.QUEST_DEATH_REQUEST:
			message = new QuestDeathHandler(this, userSession, messageBytes);
			break;
		case Messages.CRYSTAL_DEATH_REQUEST:
			message = new CrystalDeathHandler(this, userSession, messageBytes);
			break;
		case Messages.QUEST_INFO_REQUEST:
			message = new QuestInfoHandler(this, userSession, messageBytes);
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
		case Messages.PET_KILLED_REQUEST:
			message = new PetKilledHandler(this, userSession, messageBytes);
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
		case Messages.GET_TOP_GUILDS_MARK_REQUEST:
			message = new GetTopGuildsMarkHandler(this, userSession, messageBytes);
			break;
		default:
			HexTools.printHexArray(messageBytes, 0x14, false);
		}
		
		return message;
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

	public User findUser(String username) {
		for (UserTCPSession userSession : usersSessions) {
			if (userSession.getUser().username.equals(username)) {
				return userSession.getUser();
			}
		}
		
		return null;
	}

	public User findUser(InetAddress ipAddress, int port) {
		for (UserTCPSession userSession : usersSessions) {
			if (userSession.getUser().udpIPAddress != null && userSession.getUser().udpIPAddress.equals(ipAddress) && userSession.getUser().udpPort == port) {
				return userSession.getUser();
			}
		}
		
		return null;
	}
}
