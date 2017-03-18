package lobby;

import lobby.handlers.CreateRoomHandler;
import lobby.handlers.GetTopGuildsHandler;
import lobby.handlers.GetTopGuildsMarkHandler;
import lobby.handlers.ItemsChangedHandler;
import lobby.handlers.JoinLobbyHandler;
import lobby.handlers.LeaveRoomHandler;
import lobby.handlers.RoomPlayersChangedHandler;
import net.GenericHandler;
import net.GenericTCPServer;
import net.UserTCPSession;
import tools.HexTools;

public class LobbyServer extends GenericTCPServer {
	public LobbyServer(int port) {
		super("Lobby server", port);
	}

	@Override
	public GenericHandler processPacket(UserTCPSession tcpServer, int messageID, byte[] messageBytes) {
		GenericHandler message = null;

		switch (messageID) {
		case JoinLobbyHandler.REQUEST_ID:
			message = new JoinLobbyHandler(tcpServer, messageBytes);
			break;
		case CreateRoomHandler.REQUEST_ID:
			message = new CreateRoomHandler(tcpServer, messageBytes);
			break;
		case RoomPlayersChangedHandler.REQUEST_ID:
			message = new RoomPlayersChangedHandler(tcpServer, messageBytes);
			break;
		case ItemsChangedHandler.REQUEST_ID:
			message = new ItemsChangedHandler(tcpServer, messageBytes);
			break;
		case LeaveRoomHandler.REQUEST_ID:
			message = new LeaveRoomHandler(tcpServer, messageBytes);
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

}
