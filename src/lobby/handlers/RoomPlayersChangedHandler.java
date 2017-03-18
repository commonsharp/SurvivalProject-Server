package lobby.handlers;

import java.io.IOException;

import net.GenericHandler;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class RoomPlayersChangedHandler extends GenericHandler {
	public static final int REQUEST_ID = 0x4315;
	public static final int RESPONSE_ID = 0x4317;
	public static final int RESPONSE_LENGTH = 0x118;
	
	int Slot; //0
    String netIP; // 16
    String localIP; // 16
    int level;
	byte gender;
    int Ready;
    int Character;
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
    int Start; //2
    int unk20 = -1; //-1
    int unk21 = -1; //-1
    int mission;
    int missionlevel; //maybe
    int unk24;
    int unk25;
    int unk26 = -1; //-1
    
    int team; // 10 for blue team. 20 for red team.
    
    int magic;
    int weapon;
    int accessory;
    int pet;
    
    int unknown01, unknown04, unknown06;
	
	public RoomPlayersChangedHandler(UserTCPSession tcpServer, byte[] messageBytes) {
		super(tcpServer, messageBytes, RESPONSE_LENGTH, RESPONSE_ID);
	}
	
	public static byte[] generateFakeMessage(UserTCPSession tcpServer, int character, int team, byte ready, int start) {
//		ExtendedByteBuffer buf = new ExtendedByteBuffer(0x30);
//		buf.putInt(0x0, 0x30);
//		buf.putInt(0x4, REQUEST_ID);
//		buf.putInt(0x8, 11036);
//		buf.putInt(0xC, 0);
//		buf.putInt(0x10, tcpServer.clientState);
//		buf.putInt(0x14, 0);
//		buf.putInt(0x18, character);
//		buf.putInt(0x1C, team);
//		buf.putInt(0x20, ready);
//		buf.putInt(0x24, start);
//		buf.putInt(0x28, 0);
//		buf.putInt(0x2C, 0);
//		
//		return new RoomPlayersChangedHandler(tcpServer, buf.toArray()).getResponse();
		
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, RESPONSE_ID);
		output.putInt(0x8, 11036);
		output.putInt(0x14, 0); // slot. first player in the room = slot 0. next one = slot 1 and so on.
		output.putString(0x18, "10.0.0.2");
		output.putString(0x28, "10.0.0.2");
		output.putInt(0x38, 30); // user type
		output.putString(0x3C, "barak");
		output.putString(0x49, "Obamas");
		output.putString(0x56, "MasterLOL");
		output.putByte(0x63, (byte) 0);
		output.putInt(0x64, -1);
		output.putInt(0x70, -1);
		output.putInt(0x74, 20); // character
		output.putInt(0x78, -1);
		output.putInt(0x7C, -1);
		output.putInt(0x80, 20); // 20 for the other team
		output.putInt(0x84, -1);
		output.putInt(0x88, -1);
		
		output.putInt(0x8C, 0);
		output.putInt(0x90, 0);
		output.putInt(0x94, 0);
		output.putInt(0x98, 0);
		
		output.putInt(0x9C, 0);
		output.putInt(0xA0, 0);
		output.putInt(0xA4, 0);
		output.putInt(0xA8, 0);
		
		output.putInt(0xAC, 0);
		output.putInt(0xB0, 0);
		output.putInt(0xB4, 0);
		output.putInt(0xB8, 0);
		
		output.putInt(0xBC, 0);
		output.putInt(0xC0, 0);
		output.putInt(0xC4, 0);
		
		output.putInts(0xCC, new int[3]);
		output.putInt(0xD0, -1);
		output.putInt(0xD4, -1);
		output.putInt(0xD8, -1);
		output.putInt(0xDC, -1);
		output.putInt(0xE0, -1);
		output.putInt(0xE4, -1);
		output.putInt(0xE8, -1);
		output.putInt(0xEC, -1);
		output.putInt(0xF0, -1);
		
		output.putInt(0xF4, -1); // this one. set this to 0 and the game starts automatically.
		output.putInt(0xF8, 0);
		output.putInt(0xFC, 0);
		
		output.putByte(0x100, (byte) 0);
		output.putByte(0x101, (byte) 0);
		output.putByte(0x102, (byte) 0);
		output.putByte(0x103, (byte) 0);

		output.putInt(0x104, 0);
		output.putInt(0x108, 0);
		output.putInt(0x10C, 0);
		output.putInt(0x110, 0);
		output.putInt(0x114, 0);
		
		return output.toArray();
	}

	@Override
	public void interpretBytes() {
		unknown01 = input.getInt(0x14);
		Character = input.getInt(0x18);
		team = input.getInt(0x1C);
		Ready = input.getByte(0x20);
		Start = input.getInt(0x24);
		unknown04 = input.getInt(0x28);
		unknown06 = input.getInt(0x2C);
		
		System.out.println("Ready: " + Ready);
		System.out.println("Start: " + Start);
		
//		System.out.println("Team : " + team);
		System.out.println(unknown01);
//		System.out.println(isReadyMaybe);
//		System.out.println(gameStart);
		System.out.println(unknown04);
		System.out.println(unknown06);
	}

	@Override
	public void processFields() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPayload() {
//		output = null;
		System.out.println("Ready: " + Ready);
		System.out.println("Start: " + Start);
		output.putInt(0x14, 0); // slot. first player in the room = slot 0. next one = slot 1 and so on.
		output.putString(0x18, "10.0.0.2");
		output.putString(0x28, "10.0.0.2");
		output.putInt(0x38, 30);
		output.putString(0x3C, "barak");
		output.putString(0x49, "Obamas");
		output.putString(0x56, "MasterLOL");
		output.putByte(0x63, gender);
		output.putInt(0x64, 0);
		output.putInt(0x70, Ready);
		output.putInt(0x74, Character);
		output.putInt(0x78, 0);
		output.putInt(0x7C, 0);
		output.putInt(0x80, team); // 20 for the other team
		output.putInt(0x84, 0);;
		output.putInt(0x88, 0);
		
		output.putInt(0x8C, 0);
		output.putInt(0x90, 0);
		output.putInt(0x94, 0);
		output.putInt(0x98, 0);
		
		output.putInt(0x9C, 0);
		output.putInt(0xA0, 0);
		output.putInt(0xA4, 0);
		output.putInt(0xA8, 0);
		
		output.putInt(0xAC, 0);
		output.putInt(0xB0, 0);
		output.putInt(0xB4, 0);
		output.putInt(0xB8, 0);
		
		output.putInt(0xBC, 0);
		output.putInt(0xC0, 0);
		output.putInt(0xC4, 0);
		
		output.putInts(0xCC, scroll);
		output.putInt(0xD0, -1);
		output.putInt(0xD4, -1);
		output.putInt(0xD8, -1);
		output.putInt(0xDC, -1);
		output.putInt(0xE0, -1);
		output.putInt(0xE4, -1);
		output.putInt(0xE8, Start);
		output.putInt(0xEC, 0);
		output.putInt(0xF0, 0);
		
		output.putInt(0xF4, 0);
		output.putInt(0xF8, 0);
		output.putInt(0xFC, 0);
		
		output.putByte(0x100, (byte) 0);
		output.putByte(0x101, (byte) 0);
		output.putByte(0x102, (byte) 0);
		output.putByte(0x103, (byte) 0);

		output.putInt(0x104, 0);
		output.putInt(0x108, 0);
		output.putInt(0x10C, 0);
		output.putInt(0x110, 0);
		output.putInt(0x114, 0);
	}

	@Override
	public void afterSend() throws IOException {
		
	}
}
