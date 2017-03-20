package lobby.handlers;

import java.io.IOException;

import lobby.LobbyServer;
import net.GenericHandler;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class RoomPlayersChangedHandler extends GenericHandler {
	public static final int REQUEST_ID = 0x4315;
	public static final int RESPONSE_ID = 0x4317;
	public static final int RESPONSE_LENGTH = 0x118;
	
    String netIP; // 16
    String localIP; // 16
    int level;
	byte gender;
    int magictype = -1;
    int weapontype = -1;
    int armortype = -1;
    int pettype; //maybe
    int magiclevel = -1;
    int weaponlevel = -1;
    int armorlevel = -1;
    int petlevel; //maybe
    int magicgf = -1;
    int weapongf = -1;
    int armorgf = -1;
    int petgf; //maybe
    int magicskill;
    int weaponskill;
    int armorskill;
    int petskill; //maybe
    int[] scroll = new int[3];
    int foot;
    int body;
    int hand1;
    int hand2;
    int face;
    int hair;
    int head;
    int unk20 = -1; //-1
    int unk21 = -1; //-1
    int mission;
    int missionlevel; //maybe
    int unk24;
    int unk25;
    int unk26 = -1; //-1
    
    int magic;
    int weapon;
    int accessory;
    int pet;
    
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
		tcpServer.getUser().roomSlot = input.getInt(0x14);
		tcpServer.getUser().roomCharacter = input.getInt(0x18);
		tcpServer.getUser().roomTeam = input.getInt(0x1C);
		tcpServer.getUser().roomReady = input.getByte(0x20);
		tcpServer.getUser().roomStart = input.getInt(0x24);
		unknown04 = input.getInt(0x28);
		unknown06 = input.getInt(0x2C);
		
		System.out.println("Ready: " + tcpServer.getUser().roomReady);
		System.out.println("Start: " + tcpServer.getUser().roomStart);
		
		System.out.println("Slot " + tcpServer.getUser().roomSlot);
//		System.out.println("Team : " + team);
		System.out.println(unknown01);
//		System.out.println(isReadyMaybe);
//		System.out.println(gameStart);
		System.out.println(unknown04);
		System.out.println(unknown06);
	}

	@Override
	public void afterSend() throws IOException {
		int roomID = tcpServer.getUser().roomIndex;
		lobby.broadcastMessage(tcpServer, new LobbyRoomsChangedHandler(tcpServer).getResponse(lobby.getRoom(roomID)));
		// this needs to be sent to everyone in the room, but not in the lobby...
		lobby.broadcastMessage(tcpServer, new RoomPlayersChangedHandler(lobby, tcpServer).getResponse(
				tcpServer.getUser().roomSlot, tcpServer.getUser().roomCharacter, tcpServer.getUser().roomTeam,
				tcpServer.getUser().roomReady, tcpServer.getUser().roomStart, tcpServer.getUser().roomFieldF4));
	}

	@Override
	public byte[] getResponse() {
		tcpServer.getUser().roomFieldF4 = -1;
		
		if (tcpServer.getUser().roomStart == 2) {
			tcpServer.getUser().roomFieldF4 = 0;
		}
		
		return getResponse(tcpServer.getUser().roomSlot, tcpServer.getUser().roomCharacter, tcpServer.getUser().roomTeam,
				tcpServer.getUser().roomReady, tcpServer.getUser().roomStart, tcpServer.getUser().roomFieldF4);
	}
	
	// field f4 must be set according to different rules.
	// the rules are described in "ItemsChangedResponse"
	public byte[] getResponse(int slot, int character, int team, byte ready, int start, int fieldF4) {
		int roomID = tcpServer.getUser().roomIndex;
		lobby.getRoom(roomID).setCharacter(slot, character);
		
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, RESPONSE_ID);
		output.putInt(0x14, slot); // slot. first player in the room = slot 0. next one = slot 1 and so on.
		output.putString(0x18, "100.0.0.2");
		output.putString(0x28, "100.0.0.2");
		output.putInt(0x38, tcpServer.getUser().playerLevel);
		output.putString(0x3C, tcpServer.getUser().username);
		output.putString(0x49, tcpServer.getUser().guildName);
		output.putString(0x56, tcpServer.getUser().guildDuty);
		output.putByte(0x63, (byte) 0);
		output.putInt(0x64, 0);
		output.putInt(0x70, ready);
		output.putInt(0x74, character);
		output.putInt(0x78, 0);
		output.putInt(0x7C, 0);
		output.putInt(0x80, team); // 20 for the other team
		output.putInt(0x84, 0);
		output.putInt(0x88, 0);
		
		System.out.println("Magic: " + tcpServer.getUser().playerCardItemId[tcpServer.getUser().magicIndex]);
		
		output.putInt(0x8C, tcpServer.getUser().playerCardItemId[tcpServer.getUser().magicIndex]);
		output.putInt(0x90, tcpServer.getUser().playerCardItemLevelIdx[tcpServer.getUser().weaponIndex]); // not sure...
		output.putInt(0x94, tcpServer.getUser().playerCardItemLevelIdx[tcpServer.getUser().accessoryIndex]); // not sure...
		output.putInt(0x98, 0);
		
		output.putInt(0x9C, tcpServer.getUser().playerCardItemLevelIdx[tcpServer.getUser().magicIndex]);
		output.putInt(0xA0, tcpServer.getUser().playerCardItemLevelIdx[tcpServer.getUser().weaponIndex]);
		output.putInt(0xA4, tcpServer.getUser().playerCardItemLevelIdx[tcpServer.getUser().accessoryIndex]);
		output.putInt(0xA8, 0);
		
		output.putInt(0xAC, tcpServer.getUser().playerCardItemDays[tcpServer.getUser().magicIndex]);
		output.putInt(0xB0, tcpServer.getUser().playerCardItemDays[tcpServer.getUser().weaponIndex]);
		output.putInt(0xB4, tcpServer.getUser().playerCardItemDays[tcpServer.getUser().accessoryIndex]);
		output.putInt(0xB8, 0);
		
		output.putInt(0xBC, tcpServer.getUser().playerCardItemSkill[tcpServer.getUser().magicIndex]);
		output.putInt(0xC0, tcpServer.getUser().playerCardItemSkill[tcpServer.getUser().weaponIndex]);
		output.putInt(0xC4, tcpServer.getUser().playerCardItemSkill[tcpServer.getUser().accessoryIndex]);
		
		output.putInts(0xCC, new int[] {0, 0, 0});
		output.putInt(0xD0, 0);
		output.putInt(0xD4, 0);
		output.putInt(0xD8, 0);
		output.putInt(0xDC, 0);
		output.putInt(0xE0, 0);
		output.putInt(0xE4, 0);
		output.putInt(0xE8, start);
		output.putInt(0xEC, 0);
		output.putInt(0xF0, 0);
		
		output.putInt(0xF4, fieldF4); // this one. set this to 0 and the game starts automatically.
		output.putInt(0xF8, 0);
		output.putInt(0xFC, 0);
		
		output.putByte(0x100, (byte) 1);
		output.putByte(0x101, (byte) 1);
		output.putByte(0x102, (byte) 1);
		output.putByte(0x103, (byte) 1);

		output.putInt(0x104, 0);
		output.putInt(0x108, 0);
		output.putInt(0x10C, 0);
		output.putInt(0x110, 0);
		output.putInt(0x114, 0);
		
		return output.toArray();
	}
}
