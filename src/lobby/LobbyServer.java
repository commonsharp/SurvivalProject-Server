package lobby;

import java.io.IOException;

import lobby.handlers.CreateRoomHandler;
import lobby.handlers.EnterExistingRoomHandler;
import lobby.handlers.FusionHandler;
import lobby.handlers.GetRoomInfoHandler;
import lobby.handlers.GetTopGuildsHandler;
import lobby.handlers.GetTopGuildsMarkHandler;
import lobby.handlers.ItemsChangedHandler;
import lobby.handlers.JoinLobbyHandler;
import lobby.handlers.LeaveRoomHandler;
import lobby.handlers.RoomNameChangedHandler;
import lobby.handlers.RoomPlayersChangedHandler;
import net.GenericHandler;
import net.GenericTCPServer;
import net.Room;
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
	public GenericHandler processPacket(UserTCPSession tcpServer, int messageID, byte[] messageBytes) {
		GenericHandler message = null;

		switch (messageID) {
		case JoinLobbyHandler.REQUEST_ID:
			message = new JoinLobbyHandler(tcpServer, messageBytes);
			break;
		case CreateRoomHandler.REQUEST_ID:
			message = new CreateRoomHandler(this, tcpServer, messageBytes);
			break;
		case GetRoomInfoHandler.REQUEST_ID:
			message = new GetRoomInfoHandler(tcpServer, messageBytes);
			break;
		case EnterExistingRoomHandler.REQUEST_ID:
			message = new EnterExistingRoomHandler(this, tcpServer, messageBytes);
			break;
		case RoomPlayersChangedHandler.REQUEST_ID:
			message = new RoomPlayersChangedHandler(this, tcpServer, messageBytes);
			break;
		case ItemsChangedHandler.REQUEST_ID:
			message = new ItemsChangedHandler(this, tcpServer, messageBytes);
			break;
		case LeaveRoomHandler.REQUEST_ID:
			message = new LeaveRoomHandler(this, tcpServer, messageBytes);
			break;
		case FusionHandler.REQUEST_ID:
			message = new FusionHandler(tcpServer, messageBytes);
			break;
		case RoomNameChangedHandler.REQUEST_ID:
			message = new RoomNameChangedHandler(this, tcpServer, messageBytes);
			break;
		case GetTopGuildsHandler.REQUEST_ID:
			message = new GetTopGuildsHandler(tcpServer, messageBytes);
			break;
		case GetTopGuildsMarkHandler.REQUEST_ID:
			message = new GetTopGuildsMarkHandler(tcpServer, messageBytes);
			break;
		default:
			HexTools.printHexArray(messageBytes, 0x14, false);
		}
		
		return message;
	}

	public void broadcastMessage(UserTCPSession tcpServer, byte[] message) throws IOException {
		for (UserTCPSession user : usersSessions) {
			// Send the message to everyone but yourself
			if (user.getUser().username != tcpServer.getUser().username) {
				user.sendMessage(message);
			}
		}
	}
}
