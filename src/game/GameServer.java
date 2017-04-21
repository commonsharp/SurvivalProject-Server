package game;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.SQLException;

import game.handlers.ForwardMessageHandler;
import game.handlers.GameStartedHandler;
import game.handlers.JoinServerHandler;
import lobby.LobbyServer;
import lobby.handlers.SpawnCodeHandler;
import lobby.handlers.SpawnElementHandler;
import net.GenericUDPServer;
import net.Messages;
import net.UserSession;
import net.handlers.GenericHandler;
import net.objects.Room;
import tools.HexTools;

public class GameServer extends GenericUDPServer {
	public GameServer(LobbyServer lobby, int port) {
		super(lobby, "Game server", port);
	}
	
	@Override
	public GenericHandler processPacket(GenericUDPServer udpServer, int messageID, byte[] messageBytes, InetAddress ipAddress, int port) throws IOException, SQLException {
		GenericHandler message = null;
		
		switch (messageID) {
		case Messages.GAME_JOIN_LOBBY:
			message = new JoinServerHandler(udpServer, messageBytes, ipAddress, port);
			break;
		case Messages.GAME_KEEP_ALIVE:
			UserSession userSession = lobby.findUserSession(ipAddress, port);
			
			if (userSession.getUser().isInGame) {
				Room room = lobby.getRoom(userSession.getUser().roomIndex);
				
				room.totalTicks++;
				
				if (room.totalTicks % 10 == 0) {
					lobby.sendRoomMessage(userSession, new SpawnElementHandler(lobby, userSession).getResponse(), true);
				}
				if (room.totalTicks % 1 == 0) {
					lobby.sendRoomMessage(userSession, new SpawnCodeHandler(lobby, userSession).getResponse(), true);
				}
			}
			userSession.getUser().totalTicks++;
			
			if (userSession.getUser().totalTicks % 200 == 0) {
				int times = userSession.getUser().totalTicks / 200;
				
				if (times >= 4) {
					times = 4;
				}
				
				int amount = (int) Math.pow(2, times);
				
				if (userSession.getUser().timeBonus) {
					amount *= 2;
				}
				
				userSession.getUser().whiteCards[userSession.getUser().getElementType() - 1] += amount;
				userSession.getUser().saveUser();
			}
			break;
		case Messages.GAME_AFTER_ACTION: // after action (after attack/move/defense/...)
		case Messages.GAME_STOPPED_MOVING: // stopped moving
		case Messages.GAME_MOVE: // move
		case Messages.GAME_ATTACK: // attack
		case Messages.GAME_DEFENSE: // defense
		case Messages.GAME_HIT: // hit by something
		case Messages.GAME_CHAT: // chatting in game
		case Messages.GAME_WHEN_YOU_MOVE: // dunno. happens when you move
		case Messages.GAME_PICK_SCROLL: // picked a scroll
		case Messages.GAME_USE_SCROLL: // use a scroll
		case Messages.GAME_TOUCH_ELEMENT: // touch an element
		case Messages.GAME_SOCCER_BALL_HIT: // soccer ball got hit
		case Messages.GAME_SOCCER_HOKEY_BALL_STATUS: // soccer/hockey ball/disk status
		case Messages.GAME_EMOJI: // emoji
		case Messages.GAME_HIT_MONSTER_NPC: // hit monster/npc
		case Messages.GAME_CRIT_BAR_FULL: // crit bar full
		case Messages.GAME_ELECTROCUTED: // electrocuted
		case Messages.GAME_HIT_BY_SNOWBALL: // hit by snowball in dodge mode
		case Messages.GAME_BIG_MATCH_1: // something with big match
		case Messages.GAME_BIG_MATCH_2: // another something with big match
		case Messages.GAME_HOKEY_1: // hokey too? not sure
		case Messages.GAME_HOKEY_2: // something with hokey
		case Messages.GAME_MISSION_AGGRESSION_INFO: // mission aggression info
		case Messages.GAME_MISSION_PORTAL: // went through the portal in a mission
		case Messages.GAME_PUSHED_BACK: // hit by soccer ball and pushed back. maybe hit by anything and pushed back?
			message = new ForwardMessageHandler(this, this, messageBytes);
			break;
		case Messages.GAME_CONSTANT_UPDATE: // constant update. it's a must.
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
	
	long time;
	
	public void sendToUser(GenericUDPServer udpServer, int roomID, int fromSlot, int toSlot, byte[] message, boolean sendInRoom) throws IOException {
		for (UserSession user : lobby.getRoom(roomID).getUsers()) {
			// If the user is not null
			if (user != null) {
				// If the user is someone else
				if (user.getUser().roomSlot == toSlot && (user.getUser().isInGame || sendInRoom)) {
					// We need to duplicate the array because message is getting changed (some fields are changing. also the message is encrypted)
					udpServer.sendMessage(lobby.findUserSession(lobby.getRoom(roomID).getUsers()[fromSlot].getUser().username), user.getUser(), HexTools.duplicateArray(message));
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