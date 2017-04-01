package game;
import java.io.IOException;

import game.handlers.ForwardMessageHandler;
import game.handlers.GameStartedHandler;
import game.handlers.JoinServerHandler;
import lobby.LobbyServer;
import net.GenericUDPServer;
import net.UserTCPSession;
import net.handlers.GenericHandler;
import tools.HexTools;

public class GameServer extends GenericUDPServer {
	public GameServer(LobbyServer lobby, int port) {
		super(lobby, "Game server", port);
	}
	
	@Override
	public GenericHandler processPacket(GenericUDPServer udpServer, int messageID, byte[] messageBytes) throws IOException {
		GenericHandler message = null;
		
		switch (messageID) {
		case JoinServerHandler.REQUEST_ID:
			message = new JoinServerHandler(udpServer, messageBytes);
			break;
		case 0x1100:
			break;
		case 0x1104: // after action (after attack/move/defense/...)
		case 0x1105: // stopped moving
		case 0x1106: // move
		case 0x1107: // attack
		case 0x1108: // defense
		case 0x1109: // hit by something
		case 0x1112: // chatting in game
		case 0x1113: // dunno. happens when you move
		case 0x1114: // picked an item
		case 0x1119: // soccer ball got hit
		case 0x1120: // soccer/hockey ball/disk status
		case 0x1124: // emoji
		case 0x1125: // hit monster/npc
		case 0x1127: // crit bar full
		case 0x1129: // electrocuted
		case 0x1130: // hit by snowball in dodge mode
		case 0x1131: // something with big match
		case 0x1133: // another something with big match
		case 0x1134: // hokey too? not sure
		case 0x1135: // something with hokey
		case 0x1138: // hit by mission monsters? something in mission...
		case 0x1139: // went through the portal in a mission
		case 0x1140: // hit by soccer ball and pushed back. maybe hit by anything and pushed back?
			message = new ForwardMessageHandler(this, this, messageBytes);
			break;
		case 0x1110: // constant update. it's a must.
			message = new GameStartedHandler(this, this, messageBytes);
			break;
		default:
			// print only the first player
			if (HexTools.getIntegerInByteArray(messageBytes, 0x18) == 0) {
				System.out.println(HexTools.integerToHexString(messageID) + " received!");
				HexTools.printHexArray(messageBytes, 0x14, false);
			}
		}
		
		return message;
	}
	
	public void sendToUser(GenericUDPServer udpServer, int roomID, int toSlot, byte[] message, boolean sendInRoom) throws IOException {
		for (UserTCPSession user : lobby.getRoom(roomID).getUsers()) {
			// If the user is not null
			if (user != null) {
				// If the user is someone else
				if (user.getUser().roomSlot == toSlot && (user.getUser().isInGame || sendInRoom)) {
					// We need to duplicate the array because message is getting changed (some fields are changing. also the message is encrypted)
					udpServer.sendMessage(user.getUser(), HexTools.duplicateArray(message));
					break;
				}
			}
		}
	}
	
//	public void roomMessage(GenericUDPServer udpServer, int roomID, int slot, byte[] message) throws IOException {
//		for (UserTCPSession user : lobby.getRoom(roomID).getUsers()) {
//			// If the user is not null
//			if (user != null) {
//				// If the user is someone else
//				if (user.getUser().roomSlot != slot && user.getUser().isInGame) {
//					// We need to duplicate the array because message is getting changed (some fields are changing. also the message is encrypted)
//					udpServer.sendMessage(user.getUser(), HexTools.duplicateArray(message));
//				}
//			}
//		}
//	}
}