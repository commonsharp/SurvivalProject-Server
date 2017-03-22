package lobby.handlers;

import java.io.IOException;

import lobby.LobbyServer;
import net.GenericHandler;
import net.User;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class RoomPlayersChangedHandler extends GenericHandler {
	public static final int REQUEST_ID = 0x4315;
	public static final int RESPONSE_ID = 0x4317;
	public static final int RESPONSE_LENGTH = 0x118;
	
    int unknown01, unknown04, unknown06;
    
    protected LobbyServer lobby;
	
    public RoomPlayersChangedHandler(LobbyServer lobby, UserTCPSession tcpServer) {
    	super(tcpServer);
    	this.lobby = lobby;
    }
    
	public RoomPlayersChangedHandler(LobbyServer lobby, UserTCPSession tcpServer, byte[] messageBytes) {
		super(tcpServer, messageBytes);
		this.lobby = lobby;
	}
	
	@Override
	public void interpretBytes() {
		userSession.getUser().roomSlot = input.getInt(0x14);
		userSession.getUser().roomCharacter = input.getInt(0x18);
		userSession.getUser().roomTeam = input.getInt(0x1C);
		userSession.getUser().roomReady = input.getByte(0x20);
		userSession.getUser().roomStart = input.getInt(0x24);
		unknown04 = input.getInt(0x28);
		unknown06 = input.getInt(0x2C);
		
		System.out.println("Ready: " + userSession.getUser().roomReady);
		System.out.println("Start: " + userSession.getUser().roomStart);
		
		System.out.println("Slot " + userSession.getUser().roomSlot);
//		System.out.println("Team : " + team);
		System.out.println(unknown01);
//		System.out.println(isReadyMaybe);
//		System.out.println(gameStart);
		System.out.println(unknown04);
		System.out.println(unknown06);
	}

	@Override
	public void afterSend() throws IOException {
		int roomID = userSession.getUser().roomIndex;
		lobby.broadcastMessage(userSession, new LobbyRoomsChangedHandler(userSession).getResponse(lobby.getRoom(roomID)));
		// this needs to be sent to everyone in the room, but not in the lobby...
		
		lobby.roomMessage(userSession, roomID, new RoomPlayersChangedHandler(lobby, userSession).getResponse(userSession.getUser()));
//		lobby.broadcastMessage(tcpServer, new RoomPlayersChangedHandler(lobby, tcpServer).getResponse(tcpServer.getUser()));
		
		// soccer
		if (lobby.getRoom(roomID).getGameType() == 8 && lobby.getRoom(roomID).isStart) {
			byte[] response = new SoccerGoalHandler(lobby, userSession).getResponse(3);
			System.out.println("hi soccer");
			sendTCPMessage(response);
			lobby.roomMessage(userSession, roomID, response);
		}
	}

	@Override
	public byte[] getResponse() {
		userSession.getUser().roomFieldF4 = 2;

		if (lobby.getRoom(userSession.getUser().roomIndex).shouldStart(userSession.getUser().roomSlot)) {
			lobby.getRoom(userSession.getUser().roomIndex).isStart = true;
			userSession.getUser().roomFieldF4 = 0;
		}
		
		return getResponse(userSession.getUser());
	}
	
	// field f4 must be set according to different rules.
	// the rules are described in "ItemsChangedResponse"
	public byte[] getResponse(User user) {
		int roomID = userSession.getUser().roomIndex;
		lobby.getRoom(roomID).setCharacter(user.roomSlot, user.roomCharacter);
		
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, RESPONSE_ID);
		output.putInt(0x14, user.roomSlot); // slot. first player in the room = slot 0. next one = slot 1 and so on.
//		output.putString(0x18, "100.0.0.2");
//		output.putString(0x28, "100.0.0.2");
		output.putInt(0x38, user.playerLevel);
		output.putString(0x3C, user.username);
		output.putString(0x49, user.guildName);
		output.putString(0x56, user.guildDuty);
		output.putByte(0x63, (byte) 0);
		output.putInt(0x64, 0);
		output.putInt(0x70, user.roomReady);
		output.putInt(0x74, user.roomCharacter);
		output.putInt(0x78, 0);
		output.putInt(0x7C, 0); // is random
		output.putInt(0x80, user.roomTeam);
		output.putInt(0x84, 0); //ko
		output.putInt(0x88, 0);
		
		output.putInt(0x8C, user.playerCardItemId[user.magicIndex]);
		output.putInt(0x90, user.playerCardItemId[user.weaponIndex]); // not sure...
		output.putInt(0x94, user.playerCardItemId[user.accessoryIndex]); // not sure...
		output.putInt(0x98, 0);
		
		output.putInt(0x9C, user.playerCardItemLevelIdx[user.magicIndex]);
		output.putInt(0xA0, user.playerCardItemLevelIdx[user.weaponIndex]);
		output.putInt(0xA4, user.playerCardItemLevelIdx[user.accessoryIndex]);
		output.putInt(0xA8, 0);
		
		output.putInt(0xAC, user.playerCardItemDays[user.magicIndex]);
		output.putInt(0xB0, user.playerCardItemDays[user.weaponIndex]);
		output.putInt(0xB4, user.playerCardItemDays[user.accessoryIndex]);
		output.putInt(0xB8, 0);
		
		output.putInt(0xBC, user.playerCardItemSkill[user.magicIndex]);
		output.putInt(0xC0, user.playerCardItemSkill[user.weaponIndex]);
		output.putInt(0xC4, user.playerCardItemSkill[user.accessoryIndex]);
		
		output.putInts(0xCC, new int[] {0, 0, 0});
		output.putInt(0xD0, 0);
		output.putInt(0xD4, 0);
		output.putInt(0xD8, 0);
		output.putInt(0xDC, 0);
		output.putInt(0xE0, 0);
		output.putInt(0xE4, 0);
		output.putInt(0xE8, 0); // used to be room start but it seems like this is not.
		output.putInt(0xEC, 0);
		output.putInt(0xF0, 0);
		
		
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
		if (lobby.getRoom(roomID).shouldStart(user.roomSlot))
			output.putInt(0xF4, 0);
		else
			output.putInt(0xF4, 1);
		
		output.putInt(0xF8, 0);
		output.putInt(0xFC, 0);
		
		// this is for quests only. if it's 0, then the "monsters level increased" message can not appear.
		// that probably means that is something like isSoloQuest. also if it's set to 0, the game automatically start,
		// where if it's set to 1 the game waits for everyone but still doesn't do much afterwards.
		output.putByte(0x100, (byte) 0); // this is a boolean... setting it to 1 doesn't start for some reason :S
		output.putByte(0x101, (byte) 0); // heart near the player. extra life by super silver or so.
		output.putByte(0x102, (byte) 0); // 0x59 in create room
		output.putByte(0x103, (byte) 0);

		output.putInt(0x104, 0);
		output.putInt(0x108, 0);
		output.putInt(0x10C, 0); // aura. 1 - aura. others - nothing?
		output.putInt(0x110, 0x7D6); // something with values 0x7D7 or 0x7D6 or 0x7D3? this is trail when the user moves
		output.putInt(0x114, 0); // something to do with guild mark
		
		return output.toArray();
	}
}
