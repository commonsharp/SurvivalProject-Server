package game;
import java.io.IOException;

import game.handlers.AttackUDP;
import game.handlers.JoinServerHandler;
import lobby.LobbyServer;
import net.GenericHandler;
import net.GenericUDPServer;
import net.UserTCPSession;
import tools.HexTools;

public class GameServer extends GenericUDPServer {
	public GameServer(LobbyServer lobby, int port) {
		super(lobby, "Game server", port);
	}
	
	@Override
	public GenericHandler processPacket(GenericUDPServer udpServer, int messageID, byte[] messageBytes) {
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
		case 0x1110: // constant update. it's a must.
		case 0x1113: // dunno. happens when you move
		case 0x1114: // picked an item
		case 0x1119: // soccer ball got hit
		case 0x1120: // soccer ball status maybe?
		case 0x1125: // hit monster/npc
		case 0x1127: // crit bar full
		case 0x1129: // electrocuted
		case 0x1130: // hit by snowball in dodge mode
//		case 0x1133: // maybe
		case 0x1134: // hokey too? not sure
		case 0x1135: // something with hokey
		case 0x1140: // hit by soccer ball and pushed back. maybe hit by anything and pushed back?
			message = new AttackUDP(this, this, messageBytes);
			break;
//		case 0x1110:
//			break;
		default:
			// print only the first player
//			if (HexTools.getIntegerInByteArray(messageBytes, 0x18) == 0) {
				System.out.println(HexTools.integerToHexString(messageID) + " received!");
				HexTools.printHexArray(messageBytes, 0x14, false);
//			}
		}
		
		return message;
	}
	
	public void roomMessage(GenericUDPServer udpServer, int roomID, int slot, byte[] message) throws IOException {
		for (UserTCPSession user : lobby.getRoom(roomID).getUsers()) {
			// If the user is not null
			if (user != null) {
				// If the user is someone else
				if (user.getUser().roomSlot != slot) {
					udpServer.sendMessage(user.getUser(), message);
					// We need to duplicate the array because message is getting changed (some fields are changing. also the message is encrypted)
					user.sendMessage(HexTools.duplicateArray(message));
				}
			}
		}
	}
}