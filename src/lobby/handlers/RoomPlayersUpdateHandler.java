package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import net.objects.GameMode;
import net.objects.Room;
import net.objects.User;
import tools.ExtendedByteBuffer;

public class RoomPlayersUpdateHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x118;
	
    int unknown04, unknown06;
    int action;
    
    public RoomPlayersUpdateHandler(LobbyServer lobbyServer, UserSession tcpServer) {
    	super(lobbyServer, tcpServer);
    }
    
	public RoomPlayersUpdateHandler(LobbyServer lobbyServer, UserSession tcpServer, byte[] messageBytes) {
		super(lobbyServer, tcpServer, messageBytes);
	}
	
	@Override
	public void interpretBytes() {
		userSession.getUser().roomSlot = input.getInt(0x14);
		userSession.getUser().roomCharacter = input.getInt(0x18);
		userSession.getUser().roomTeam = input.getInt(0x1C);
		userSession.getUser().roomReady = input.getByte(0x20);
		action = input.getInt(0x24);
		unknown04 = input.getInt(0x28);
		unknown06 = input.getByte(0x2C);
		
		System.out.println("Ready: " + userSession.getUser().roomReady);
		System.out.println("Action: " + action); // 0 - change character. 1 - change team. 2 - pressed ready
		
		System.out.println("Slot " + userSession.getUser().roomSlot);
//		System.out.println("Team : " + team);
//		System.out.println(isReadyMaybe);
//		System.out.println(gameStart);
		System.out.println(unknown04);
		System.out.println(unknown06);
	}

	@Override
	public void afterSend() throws IOException {
		Room room = lobbyServer.getRoom(userSession.getUser().roomIndex);
		lobbyServer.sendBroadcastMessage(userSession, new LobbyRoomsChangedHandler(lobbyServer, userSession).getResponse(room));
		// this needs to be sent to everyone in the room, but not in the lobby...
		
		lobbyServer.sendRoomMessage(userSession, getResponse(userSession.getUser()), false);
//		lobby.broadcastMessage(tcpServer, new RoomPlayersChangedHandler(lobby, tcpServer).getResponse(tcpServer.getUser()));
		
		if (room.isStart) {
			if (room.getGameMode() == GameMode.MISSION || room.getGameMode() == GameMode.BIG_MATCH_DEATH_MATCH) {
				lobbyServer.sendRoomMessage(userSession, new SpawnHandler(lobbyServer, userSession).getResponse2(), true);
			}
			if (room.getGameMode() == GameMode.HOKEY) {
				lobbyServer.sendRoomMessage(userSession, new SpawnHandler(lobbyServer, null).getResponse2(), true);
//				lobbyServer.sendRoomMessage(userSession, new SoccerGoalHandler(lobbyServer, null).getResponse(room, 3), true);
			}
			
			if (room.getGameMode() == GameMode.KING_SURVIVAL) {
				sendTCPMessage(new NewKingHandler(lobbyServer, userSession).getResponse());
			}
			
			for (int i = 0; i < 8; i++) {
				if (room.getUserSession(i) != null) {
					room.isAlive[i] = true;
				}
				else {
					room.isAlive[i] = false;
				}
			}
			
			for (int i = 8; i < 40; i++) {
				room.isAlive[i] = true;
			}
		}
	}

	byte b;
	
	@Override
	public byte[] getResponse() {
		userSession.getUser().roomFieldF4 = 2;
		
		userSession.getUser().lives = 5;
		userSession.getUser().gameKO = 0;
		
		b = 0;
		
		if (lobbyServer.getRoom(userSession.getUser().roomIndex).isStart) {
			b = 1;
		}
		
		if (lobbyServer.getRoom(userSession.getUser().roomIndex).shouldStart(userSession.getUser().roomSlot)) {
			lobbyServer.getRoom(userSession.getUser().roomIndex).isStart = true;
			userSession.getUser().roomFieldF4 = 0;
		}
		
		return getResponse(userSession.getUser());
	}
	

	public byte[] getResponse(User user) {
		int roomID = userSession.getUser().roomIndex;
		lobbyServer.getRoom(roomID).setCharacter(user.roomSlot, user.roomCharacter);
		
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.ROOM_PLAYERS_UPDATE_RESPONSE);
		output.putInt(0x14, user.roomSlot); // slot. first player in the room = slot 0. next one = slot 1 and so on.
		output.putString(0x18, "10.0.0.50");
		output.putString(0x28, "10.0.0.50");
		output.putInt(0x38, user.playerLevel);
		output.putString(0x3C, user.username);
		output.putString(0x49, user.guildName);
		output.putString(0x56, user.guildDuty);
		output.putByte(0x63, (byte) 0);
		output.putInt(0x64, 0);
		output.putInt(0x70, user.roomReady);
		output.putInt(0x74, user.roomCharacter);
		output.putInt(0x78, 0); // same as unknown04 in the request
		output.putInt(0x7C, 0); // is random
		output.putInt(0x80, user.roomTeam);
		output.putInt(0x84, 0); //ko
		output.putInt(0x88, 0); // round number. starts with 0.
		
		// If the item doesn't exist, this needs to be 0, and not -1.
		if (user.getItemID(user.magicIndex) != -1)
			output.putInt(0x8C, user.getItemID(user.magicIndex));
		
		if (user.getItemID(user.weaponIndex) != -1)
			output.putInt(0x90, user.getItemID(user.weaponIndex));
		
		if (user.getItemID(user.accessoryIndex) != -1)
			output.putInt(0x94, user.getItemID(user.accessoryIndex));
		
		if (user.getItemID(user.petIndex) != -1)
			output.putInt(0x98, user.getItemID(user.petIndex));

		output.putInt(0x9C, user.getItemLevel(user.magicIndex));
		output.putInt(0xA0, user.getItemLevel(user.weaponIndex));
		output.putInt(0xA4, user.getItemLevel(user.accessoryIndex));
		output.putInt(0xA8, user.getItemLevel(user.petIndex));
		
		output.putInt(0xAC, user.getItemPremiumDays(user.magicIndex));
		output.putInt(0xB0, user.getItemPremiumDays(user.weaponIndex));
		output.putInt(0xB4, user.getItemPremiumDays(user.accessoryIndex));
		output.putInt(0xB8, user.getItemPremiumDays(user.petIndex));
		
		output.putInt(0xBC, user.getItemSkill(user.magicIndex));
		output.putInt(0xC0, user.getItemSkill(user.weaponIndex));
		output.putInt(0xC4, user.getItemSkill(user.accessoryIndex));
		output.putInt(0xC8, user.getItemSkill(user.petIndex));
		
		output.putInts(0xCC, user.scrolls);
		output.putInt(0xD0, 0); // between 1 and 16
		output.putInt(0xD4, 0); // same ^
		output.putInt(0xD8, user.getItemID(user.footIndex));
		output.putInt(0xDC, user.getItemID(user.bodyIndex));
		output.putInt(0xE0, user.getItemID(user.hand1Index));
		output.putInt(0xE4, user.getItemID(user.hand2Index));
		output.putInt(0xE8, user.getItemID(user.faceIndex));
		output.putInt(0xEC, user.getItemID(user.hairIndex));
		output.putInt(0xF0, user.getItemID(user.headIndex));
		
		
		// Gamestates:
		// 3 - in room waiting?
		// 4 - started?
		
		/* this can be anything between 0 and 6 inclusive.
		 * 1 and 2 (exactly the same) - skips everything. go to the end of case 5. basically nothing.
		 * 3 - "Game has already started" message
		 * 4 - standby...
		 * 5 - tournament error (need 4 people+)
		 * 6 - card limit error.
		*/
//		output.putInt(0xF4, 0);
//		output.putInt(0xF4, user.roomFieldF4); // this one. set this to 0 and the game starts automatically.
		
		if (lobbyServer.getRoom(roomID).shouldStart(user.roomSlot))
			output.putInt(0xF4, 0);
		else
			output.putInt(0xF4, 1);
		
		output.putInt(0xF8, 0); // this one is used in mode 40 and mode 51
		output.putInt(0xFC, 0); // same ^
		
//		if (user.username.equals(lobbyServer.getRoom(roomID).roomCreator)) {
//			b = 0;
//		}
//		else {
//			b = 1;
//		}
		
		// this is for quests only. if it's 0, then the "monsters level increased" message can not appear.
		// that probably means that is something like isSoloQuest. also if it's set to 0, the game automatically start,
		// where if it's set to 1 the game waits for everyone but still doesn't do much afterwards.
		output.putByte(0x100, (byte) 1); // this is a boolean... setting it to 1 doesn't start for some reason :S
		output.putByte(0x101, (byte) 0); // heart near the player. extra life by super silver or so.
		output.putByte(0x102, (byte) 0); // 0x59 in create room. this is master card 1.3x exp/code bonus
		output.putByte(0x103, (byte) 0);

		output.putInt(0x104, user.missionLevel); // current mission level (you can play a level 20 mission with someone who's level 20 while you're level 40 for example)
		output.putInt(0x108, user.missionLevel); // my mission level
		output.putInt(0x10C, 0); // aura. 1 - aura. others - nothing?
		output.putInt(0x110, 0); // something with values 0x7D7 or 0x7D6 or 0x7D3? this is trail when the user moves
		output.putInt(0x114, 0); // something to do with guild mark
		
		return output.toArray();
	}

	@Override
	public void processMessage() {
		if (action == 1) {
			int team = userSession.getUser().roomTeam;
			Room room = lobbyServer.getRoom(userSession.getUser().roomIndex);
			
			if (team == 10) {
				room.bluePlayersCount++;
				room.redPlayersCount--;
			}
			else {
				room.bluePlayersCount--;
				room.redPlayersCount++;
			}
		}
	}
}
