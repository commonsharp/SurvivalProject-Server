package lobby.handlers;

import net.GenericHandler;
import net.UserTCPSession;

public class EquipChangedHandler extends GenericHandler {
	public static final int REQUEST_ID = 0x4316;
	public static final int RESPONSE_ID = 0x4317;
	public static final int RESPONSE_LENGTH = 0x118;
	
    int Slot; //0
    String netIP; // 16
    String localIP; // 16
    int level = 25;
    String username = "just_a_name";
	byte gender;
    int Ready = 0;
    int Character = 20;
    int magictype = 1;
    int weapontype = 1;
    int armortype = 1;
    int pettype; //maybe
    int magiclevel = 1;
    int weaponlevel = 1;
    int armorlevel = 1;
    int petlevel; //maybe
    int magicgf = 1;
    int weapongf = 1;
    int armorgf = 1;
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
    int Start = 0; //2
    int unk20; //-1
    int unk21; //-1
    int mission;
    int missionlevel; //maybe
    int unk24;
    int unk25;
    int unk26; //-1
    
    int team = 10; // 10 for blue team. 20 for red team.
    
    int magic;
    int weapon;
    int accessory;
    int pet;
    
    boolean send = true;
	public EquipChangedHandler(UserTCPSession tcpServer, byte[] messageBytes) {
		super(tcpServer, messageBytes, RESPONSE_LENGTH, RESPONSE_ID);
	}

	@Override
	public void interpretBytes() {
		magic = input.getInt(0x14);
		weapon = input.getInt(0x18);
		accessory = input.getInt(0x1C);
		pet = input.getInt(0x20);
		foot = input.getInt(0x24);
		body = input.getInt(0x28);
		hand1 = input.getInt(0x2C);
		hand2 = input.getInt(0x30);
		face = input.getInt(0x34);
		hair = input.getInt(0x38);
		head = input.getInt(0x3C);
	}

	@Override
	public void processFields() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeData() {
	}

	@Override
	public void addPayload() {
		if (CreateRoomHandler.state == 2) {
			output.putInt(0x14, 0);
			output.putString(0x18, "10.0.0.2");
			output.putString(0x28, "10.0.0.2");
			output.putInt(0x38, level);
			output.putString(0x3C, username);
			output.putString(0x49, "hello");
			output.putString(0x56, "hello2");
			output.putByte(0x63, gender);
			output.putInt(0x64, 0);
			output.putInt(0x70, Ready);
			output.putInt(0x74, Character);
			output.putInt(0x78, 0);
			output.putInt(0x7C, 0);
			output.putInt(0x80, team); // 20 for the other team
			output.putInt(0x84, 0);;
			output.putInt(0x88, 0);
			
			output.putInt(0x8C, magictype);
			output.putInt(0x90, weapontype);
			output.putInt(0x94, armortype);
			output.putInt(0x98, pettype);
			
			output.putInt(0x9C, magiclevel);
			output.putInt(0xA0, weaponlevel);
			output.putInt(0xA4, armorlevel);
			output.putInt(0xA8, petlevel);
			
			output.putInt(0xAC, magicgf);
			output.putInt(0xB0, weapongf);
			output.putInt(0xB4, armorgf);
			output.putInt(0xB8, petgf);
			
			output.putInt(0xBC, magicskill);
			output.putInt(0xC0, weaponskill);
			output.putInt(0xC4, armorskill);
			
			output.putInts(0xCC, scroll);
			output.putInt(0xD0, -1);
			output.putInt(0xD4, -1);
			output.putInt(0xD8, -1);
			output.putInt(0xDC, -1);
			output.putInt(0xE0, -1);
			output.putInt(0xE4, -1);
			output.putInt(0xE8, Start);
			output.putInt(0xEC, -1);
			output.putInt(0xF0, -1);
			
			output.putInt(0xF4, 80);
			output.putInt(0xF8, 70);
			output.putInt(0xFC, 60);
			
			output.putByte(0x100, (byte) 1);
			output.putByte(0x101, (byte) 1);
			output.putByte(0x102, (byte) 1);
			output.putByte(0x103, (byte) 1);
	
			output.putInt(0x104, 10);
			output.putInt(0x108, 20);
			output.putInt(0x10C, 30);
			output.putInt(0x110, 40);
			output.putInt(0x114, 50);
		}
		else {
			output = null;
		}
	}

	@Override
	public void afterSend() {
		// TODO Auto-generated method stub
		
	}
}