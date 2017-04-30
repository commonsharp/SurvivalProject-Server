package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import net.objects.GameMode;
import net.objects.Room;
import tools.ExtendedByteBuffer;

public class RoomPlayersUpdateHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x118;
	
    int unknown04;
    int action;
    
    public RoomPlayersUpdateHandler(LobbyServer lobbyServer, UserSession tcpServer) {
    	super(lobbyServer, tcpServer);
    }
    
	public RoomPlayersUpdateHandler(LobbyServer lobbyServer, UserSession tcpServer, byte[] messageBytes) {
		super(lobbyServer, tcpServer, messageBytes);
	}
	
	@Override
	public void interpretBytes() {
		userSession.getUser().setRoomSlot(input.getInt(0x14));
		userSession.getUser().setRoomCharacter(input.getInt(0x18));
		System.out.println("character: " + userSession.getUser().getRoomCharacter());
		userSession.getUser().setRoomTeam(input.getInt(0x1C));
		userSession.getUser().setRoomReady(input.getByte(0x20));
		action = input.getInt(0x24);
		unknown04 = input.getInt(0x28);
		userSession.getUser().setRoomRandom(input.getByte(0x2C));
		
		System.out.println("Ready: " + userSession.getUser().getRoomReady());
		System.out.println("Action: " + action); // 0 - change character. 1 - change team. 2 - pressed ready
		
		System.out.println("Slot " + userSession.getUser().getRoomSlot());
//		System.out.println("Team : " + team);
//		System.out.println(isReadyMaybe);
//		System.out.println(gameStart);
		System.out.println(unknown04);
		System.out.println(userSession.getUser().getRoomRandom());
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		Room room = lobbyServer.getRoom(userSession.getUser().getRoomIndex());
		lobbyServer.sendBroadcastMessage(userSession, new LobbyRoomsChangedHandler(lobbyServer, userSession).getResponse(room));
		lobbyServer.sendRoomMessage(userSession, getResponse(userSession), false);
		
		if (room.isStarted) {
			if (room.roomStartTime == 0) {
				System.out.println("in");
				room.roomStartTime = System.currentTimeMillis();
			}
			if (room.getGameMode() == GameMode.HERO) {
				int oneTeam = room.bluePlayersCount == 1 ? 10 : 20;
				
				for (int i = 0; i < 8; i++) {
					if (room.getUserSession(i) != null && room.getUserSession(i).getUser().getRoomTeam() == oneTeam) {
						room.heroSlot = i;
						break;
					}
				}
			}
			
//			if (room.getGameMode() == GameMode.SOCCER) {
//				lobbyServer.sendRoomMessage(userSession, new SoccerGoalHandler(lobbyServer, null).getResponse(room, 3), true);
//			}
			
			if (room.getGameMode() == GameMode.MISSION || room.getGameMode() == GameMode.BIG_MATCH_DEATH_MATCH || room.getGameMode() == GameMode.FIGHT_CLUB || room.getGameMode() == GameMode.CRYSTAL) {
				lobbyServer.sendRoomMessage(userSession, new SpawnHandler(lobbyServer, userSession).getResponse2(), true);
			}
			
			if (room.getGameMode() == GameMode.HOKEY) {
				lobbyServer.sendRoomMessage(userSession, new SpawnHandler(lobbyServer, null).getResponse2(), true);
//				lobbyServer.sendRoomMessage(userSession, new SoccerGoalHandler(lobbyServer, null).getResponse(room, 3), true);
			}
			
			if (room.getGameMode() == GameMode.KING_SURVIVAL) {
				if (room.blueKingIndex == -1) {
					room.blueKingIndex = userSession.getUser().getRoomSlot();
				}
				sendTCPMessage(new NewKingHandler(lobbyServer, userSession).getResponse());
			}
			if (room.getGameMode() == GameMode.INFINITY_KING) {
				if (userSession.getUser().getRoomTeam() == 10) {
					if (room.blueKingIndex == -1 || room.infinityPoints[userSession.getUser().getRoomSlot()] >= room.infinityPoints[room.blueKingIndex]) {
						room.blueKingIndex = userSession.getUser().getRoomSlot();
					}
				}
				
				if (userSession.getUser().getRoomTeam() == 20) {
					if (room.redKingIndex == -1 || room.infinityPoints[userSession.getUser().getRoomSlot()] >= room.infinityPoints[room.redKingIndex]) {
						room.redKingIndex = userSession.getUser().getRoomSlot();
					}
				}
				
				System.out.println("Infinity king: " + room.blueKingIndex);
				lobbyServer.sendRoomMessage(userSession, new RoomGameModeChangedHandler(lobbyServer, userSession).getResponse2(), true);
				lobbyServer.sendRoomMessage(userSession, new InfinityKingPointsHandler(lobbyServer, userSession).getResponse(room.infinityPoints, 1, 0), true);
				sendTCPMessage(new StartCountdownHandler(lobbyServer, userSession).getResponse());
			}
			if (room.getGameMode() == GameMode.INFINITY_SURVIVAL) {
				lobbyServer.sendRoomMessage(userSession, new RoomGameModeChangedHandler(lobbyServer, userSession).getResponse2(), true);
				lobbyServer.sendRoomMessage(userSession, new StartCountdownHandler(lobbyServer, userSession).getResponse(), true);
			}
			if (room.getGameMode() == GameMode.INFINITY_SYMBOL) {
				lobbyServer.sendRoomMessage(userSession, new RoomGameModeChangedHandler(lobbyServer, userSession).getResponse2(), true);
				lobbyServer.sendRoomMessage(userSession, new InfinityKingPointsHandler(lobbyServer, userSession).getResponse(room.infinityPoints, 1, 0), true);
				sendTCPMessage(new StartCountdownHandler(lobbyServer, userSession).getResponse());
			}
			
			if (room.getGameMode() == GameMode.KING_SLAYER) {
				lobbyServer.sendRoomMessage(userSession, new SpawnHandler(lobbyServer, userSession).getResponse2(), true);
//				room.kingIndex = 0;
//				lobbyServer.sendRoomMessage(userSession, new NewKingHandler(lobbyServer, userSession).getResponse(), true);
//				
//				room.kingIndex = 1;
//				lobbyServer.sendRoomMessage(userSession, new NewKingHandler(lobbyServer, userSession).getResponse(), true);
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
		
		System.out.println("room time: " + room.roomStartTime);
	}

	byte b;
	
	@Override
	public byte[] getResponse() {
		userSession.getUser().setLives(5);
		userSession.getUser().setGameKO(0);
		userSession.getUser().setGameExtraLife(userSession.getUser().isExtraLife());
		
		b = 0;
		
		if (lobbyServer.getRoom(userSession.getUser().getRoomIndex()).isStarted) {
			b = 1;
		}
		
		if (!lobbyServer.getRoom(userSession.getUser().getRoomIndex()).isStarted && lobbyServer.getRoom(userSession.getUser().getRoomIndex()).shouldStart(userSession.getUser().getRoomSlot())) {
			lobbyServer.getRoom(userSession.getUser().getRoomIndex()).isRoomCreatedMessageSent = false;
			lobbyServer.getRoom(userSession.getUser().getRoomIndex()).isStarted = true;
			userSession.getUser().setInGame(true);
		}
		
		return getResponse(userSession);
	}

	public byte[] getResponse(UserSession user) {
		Room room = lobbyServer.getRoom(userSession.getUser().getRoomIndex());
		
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.ROOM_PLAYERS_UPDATE_RESPONSE);
		output.putInt(0x14, user.getUser().getRoomSlot()); // slot. first player in the room = slot 0. next one = slot 1 and so on.
//		output.putString(0x18, "10.0.0.50"); // IP!!!
		output.putString(0x18, user.udpIPAddress.getHostAddress()); // IP!!!
		output.putString(0x28, user.udpIPAddress.getHostAddress()); // not sure lol
		output.putInt(0x38, user.getUser().getPlayerLevel());
		output.putString(0x3C, user.getUser().getUsername());
		output.putString(0x49, user.getUser().getGuildName());
		output.putString(0x56, user.getUser().getGuildDuty());
		output.putByte(0x63, (byte) 0);
		output.putInt(0x64, 0);
		output.putInt(0x70, user.getUser().getRoomReady());
		output.putInt(0x74, user.getUser().getRoomCharacter());
		output.putInt(0x78, 0); // same as unknown04 in the request. if this is -1, there are no characters?
		output.putInt(0x7C, userSession.getUser().getRoomRandom()); // is random
		output.putInt(0x80, user.getUser().getRoomTeam());
		output.putInt(0x84, 0); //ko
		output.putInt(0x88, 0); // round number. starts with 0.
		
		// If the item doesn't exist, this needs to be 0, and not -1.
		if (user.getUser().getItemID(user.getUser().getMagicIndex()) != -1)
			output.putInt(0x8C, user.getUser().getItemID(user.getUser().getMagicIndex()));
		
		if (user.getUser().getItemID(user.getUser().getWeaponIndex()) != -1)
			output.putInt(0x90, user.getUser().getItemID(user.getUser().getWeaponIndex()));
		
		if (user.getUser().getItemID(user.getUser().getAccessoryIndex()) != -1)
			output.putInt(0x94, user.getUser().getItemID(user.getUser().getAccessoryIndex()));
		
		if (user.getUser().getItemID(user.getUser().getPetIndex()) != -1)
			output.putInt(0x98, user.getUser().getItemID(user.getUser().getPetIndex()));

		output.putInt(0x9C, user.getUser().getItemLevel(user.getUser().getMagicIndex()));
		output.putInt(0xA0, user.getUser().getItemLevel(user.getUser().getWeaponIndex()));
		output.putInt(0xA4, user.getUser().getItemLevel(user.getUser().getAccessoryIndex()));
		output.putInt(0xA8, user.getUser().getItemLevel(user.getUser().getPetIndex()));
		
		output.putInt(0xAC, user.getUser().getItemPremiumDays(user.getUser().getMagicIndex()));
		output.putInt(0xB0, user.getUser().getItemPremiumDays(user.getUser().getWeaponIndex()));
		output.putInt(0xB4, user.getUser().getItemPremiumDays(user.getUser().getAccessoryIndex()));
		output.putInt(0xB8, user.getUser().getItemPremiumDays(user.getUser().getPetIndex()));
		
		output.putInt(0xBC, user.getUser().getItemSkill(user.getUser().getMagicIndex()));
		output.putInt(0xC0, user.getUser().getItemSkill(user.getUser().getWeaponIndex()));
		output.putInt(0xC4, user.getUser().getItemSkill(user.getUser().getAccessoryIndex()));
		output.putInt(0xC8, user.getUser().getItemSkill(user.getUser().getPetIndex()));
		
		output.putInts(0xCC, user.getUser().getScrolls());
		output.putInt(0xD0, 0); // between 1 and 16
		output.putInt(0xD4, 0); // same ^
		output.putInt(0xD8, user.getUser().getItemID(user.getUser().getFootIndex()));
		output.putInt(0xDC, user.getUser().getItemID(user.getUser().getBodyIndex()));
		output.putInt(0xE0, user.getUser().getItemID(user.getUser().getHand1Index()));
		output.putInt(0xE4, user.getUser().getItemID(user.getUser().getHand2Index()));
		output.putInt(0xE8, user.getUser().getItemID(user.getUser().getFaceIndex()));
		output.putInt(0xEC, user.getUser().getItemID(user.getUser().getHairIndex()));
		output.putInt(0xF0, user.getUser().getItemID(user.getUser().getHeadIndex()));
		
		
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
		
		System.out.println("Slot " + user.getUser().getRoomSlot() + " Should start: " + room.shouldStart(user.getUser().getRoomSlot()));
		if (room.shouldStart(user.getUser().getRoomSlot())) {
//			if (user.getUser().roomSlot == 2) {
//				output.putInt(0xF4, 0);
//			}
//			else
			output.putInt(0xF4, 0);
		}
		else
			output.putInt(0xF4, 2);
		
		output.putInt(0xF8, 0); // this one is used in modes where you can spectate (teams). this one is compared with 0~7 (each slot)
		output.putInt(0xFC, 0); // same ^ this one is also compared with 0~7...
		
//		if (user.getUser().username.equals(lobbyServer.getRoom(roomID).roomCreator)) {
//			b = 0;
//		}
//		else {
//			b = 1;
//		}
		
		System.out.println("is joined: " + user.getUser().isJoined());
		
		// this is for quests only. if it's 0, then the "monsters level increased" message can not appear.
		// that probably means that is something like isSoloQuest. also if it's set to 0, the game automatically start,
		// where if it's set to 1 the game waits for everyone but still doesn't do much afterwards.
		output.putBoolean(0x100, user.getUser().isJoined()); // this is a boolean... setting it to 1 doesn't start for some reason :S
		output.putBoolean(0x101, user.getUser().isExtraLife()); // heart near the player. extra life by super silver or so.
		output.putByte(0x102, (byte) 0); // 0x59 in create room. this is master card 1.3x exp/code bonus
		output.putByte(0x103, (byte) 0);

		output.putInt(0x104, room.missionLevel); // current mission level (you can play a level 20 mission with someone who's level 20 while you're level 40 for example)
		output.putInt(0x108, user.getUser().getMissionLevel()); // my mission level
		output.putInt(0x10C, 1); // aura. 1 - aura. others - nothing?
		output.putInt(0x110, user.getUser().getBooster());
		output.putInt(0x114, 0); // something to do with guild mark
		
		return output.toArray();
	}

	@Override
	public void processMessage() {
		Room room = lobbyServer.getRoom(userSession.getUser().getRoomIndex());
//		int slot = userSession.getUser().roomSlot;
		
		if (action == 1) {
			int team = userSession.getUser().getRoomTeam();
			
			if (team == 10) {
				room.bluePlayersCount++;
				room.redPlayersCount--;
			}
			else {
				room.bluePlayersCount--;
				room.redPlayersCount++;
			}
		}
		if (room.getGameMode() == GameMode.FIGHT_CLUB) {
			if (userSession.getUser().getRoomReady() == 1) {
				room.fightClubOrder.add(userSession.getUser().getRoomSlot());
			}
		}
	}
}
