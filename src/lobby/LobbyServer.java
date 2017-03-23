package lobby;

import java.io.IOException;
import java.net.InetAddress;

import lobby.handlers.CreateRoomHandler;
import lobby.handlers.CrystalDeathHandler;
import lobby.handlers.JoinRoomHandler;
import lobby.handlers.FusionHandler;
import lobby.handlers.GetRoomInfoHandler;
import lobby.handlers.GetTopGuildsHandler;
import lobby.handlers.GetTopGuildsMarkHandler;
import lobby.handlers.GetUserInfoHandler;
import lobby.handlers.ItemsChangedHandler;
import lobby.handlers.JoinLobbyHandler;
import lobby.handlers.LeaveGameHandler;
import lobby.handlers.LeaveRoomHandler;
import lobby.handlers.PlayerDeathHandler;
import lobby.handlers.PlayerResurrectionHandler;
import lobby.handlers.QuestDeathHandler;
import lobby.handlers.QuestInfoHandler;
import lobby.handlers.BigMatchDeathHandler;
import lobby.handlers.RoomNameChangedHandler;
import lobby.handlers.RoomPlayersChangedHandler;
import lobby.handlers.SoccerGoalHandler;
import net.GenericHandler;
import net.GenericTCPServer;
import net.Room;
import net.User;
import net.UserTCPSession;
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
		case JoinLobbyHandler.REQUEST_ID:
			message = new JoinLobbyHandler(userSession, messageBytes);
			break;
		case CreateRoomHandler.REQUEST_ID:
			message = new CreateRoomHandler(this, userSession, messageBytes);
			break;
		case GetUserInfoHandler.REQUEST_ID:
			message = new GetUserInfoHandler(this, userSession, messageBytes);
			break;
		case GetRoomInfoHandler.REQUEST_ID:
			message = new GetRoomInfoHandler(userSession, messageBytes);
			break;
		case JoinRoomHandler.REQUEST_ID:
			message = new JoinRoomHandler(this, userSession, messageBytes);
			break;
		case RoomPlayersChangedHandler.REQUEST_ID:
			message = new RoomPlayersChangedHandler(this, userSession, messageBytes);
			break;
		case ItemsChangedHandler.REQUEST_ID:
			message = new ItemsChangedHandler(this, userSession, messageBytes);
			break;
		case LeaveRoomHandler.REQUEST_ID:
			message = new LeaveRoomHandler(this, userSession, messageBytes);
			break;
		case LeaveGameHandler.REQUEST_ID:
			message = new LeaveGameHandler(this, userSession, messageBytes);
			break;
		case PlayerResurrectionHandler.REQUEST_ID:
			message = new PlayerResurrectionHandler(userSession, messageBytes);
			break;
		case FusionHandler.REQUEST_ID:
			message = new FusionHandler(userSession, messageBytes);
			break;
		case RoomNameChangedHandler.REQUEST_ID:
			message = new RoomNameChangedHandler(this, userSession, messageBytes);
			break;
		case PlayerDeathHandler.REQUEST_ID:
			message = new PlayerDeathHandler(this, userSession, messageBytes);
			break;
		case SoccerGoalHandler.REQUEST_ID:
			message = new SoccerGoalHandler(this, userSession, messageBytes);
			break;
		case QuestDeathHandler.REQUEST_ID:
			message = new QuestDeathHandler(userSession, messageBytes);
			break;
		case CrystalDeathHandler.REQUEST_ID:
			message = new CrystalDeathHandler(userSession, messageBytes);
			break;
		case QuestInfoHandler.REQUEST_ID:
			message = new QuestInfoHandler(this, userSession, messageBytes);
			break;
		case GetTopGuildsHandler.REQUEST_ID:
			message = new GetTopGuildsHandler(userSession, messageBytes);
			break;
		case BigMatchDeathHandler.REQUEST_ID:
			message = new BigMatchDeathHandler(userSession, messageBytes);
			break;
		case GetTopGuildsMarkHandler.REQUEST_ID:
			message = new GetTopGuildsMarkHandler(userSession, messageBytes);
			break;
		default:
			HexTools.printHexArray(messageBytes, 0x14, false);
		}
		
		return message;
	}

	public void broadcastMessage(UserTCPSession userSession, byte[] message) throws IOException {
		for (UserTCPSession currentUserSession : usersSessions) {
			// Send the message to everyone but yourself
			
			if (currentUserSession.getUser().username != userSession.getUser().username) {
				// We need to duplicate the array because message is getting changed (some fields are changing. also the message is encrypted)
				currentUserSession.sendMessage(HexTools.duplicateArray(message));
			}
		}
	}
	
	// this sends to everyone in the room
	public void roomMessage(int roomID, byte[] message) throws IOException {
		for (UserTCPSession currentUserSession : getRoom(roomID).getUsers()) {
			// If the user is not null
			if (currentUserSession != null) {
				// We need to duplicate the array because message is getting changed (some fields are changing. also the message is encrypted)
				currentUserSession.sendMessage(HexTools.duplicateArray(message));
			}
		}
	}

	// this sends to everyone but yourself
	public void roomMessage(UserTCPSession userSession, int roomID, byte[] message) throws IOException {
		for (UserTCPSession currentUserSession : getRoom(roomID).getUsers()) {
			// If the user is not null
			if (currentUserSession != null) {
				// If the user is someone else
				if (currentUserSession.getUser().username != userSession.getUser().username) {
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
			if (userSession.getUser().udpIPAddress.equals(ipAddress) && userSession.getUser().udpPort == port) {
				return userSession.getUser();
			}
		}
		
		return null;
	}
}
