package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class QuestInfoHandler extends LobbyHandler {
	public static final int REQUEST_ID = 0x4362;
//	public static final int RESPONSE_ID = 0x4409; // this is the correct one. not 0x4362
//	public static final int RESPONSE_LENGTH = 0x6C;
	
	byte[] requestBytes;
	
	private class NpcData {
		short xPos;
		short yPos;
		short unk11;
		short unk12;
		short unk21;
		short unk22;
		short unk3;
		short unk4;
		short one;
		short npcType;
		short one2;
		short level;
	};
	
	NpcData[] monsters;
	
	public QuestInfoHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
		requestBytes = messageBytes;
	}

	@Override
	public void interpretBytes() {
		input.getInt(0x14); // unknown
		input.getInt(0x18); // unknown
		
		monsters = new NpcData[33];
		for (int i = 0; i < monsters.length; i++) {
			monsters[i] = new NpcData();
			// each monster is of size 0x18 (24 bytes).
			monsters[i].xPos = input.getShort(0x1C + i * 0x18 + 0x0);
			monsters[i].yPos = input.getShort(0x1C + i * 0x18 + 0x2);
			monsters[i].unk11 = input.getShort(0x1C + i * 0x18 + 0x4);
			monsters[i].unk12 = input.getShort(0x1C + i * 0x18 + 0x6);
			monsters[i].unk21 = input.getShort(0x1C + i * 0x18 + 0x8);
			monsters[i].unk22 = input.getShort(0x1C + i * 0x18 + 0xA);
			monsters[i].unk3 = input.getShort(0x1C + i * 0x18 + 0xC);
			monsters[i].unk4 = input.getShort(0x1C + i * 0x18 + 0xE);
			monsters[i].one = input.getShort(0x1C + i * 0x18 + 0x10);
			monsters[i].npcType = input.getShort(0x1C + i * 0x18 + 0x12);
			monsters[i].one2 = input.getShort(0x1C + i * 0x18 + 0x14);
			monsters[i].level = input.getShort(0x1C + i * 0x18 + 0x16);
			monsters[i].level = 10;
		}
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(requestBytes.length);
		output.putBytes(0x0, requestBytes);
		
//		output.putInt(0x4, 0x4409);
		output.putInt(0x4, Messages.QUEST_INFO_REQUEST);
		
//		int[] levels = new int[200];
//		for (int i = 0; i < levels.length; i++) {
//			levels[i] = 0;
//		}
		
//		output.putInts(0x14, levels);
//		for (int i = 0; i < monsters.length; i++) {
//			output.putShort(0x1C + i * 0x18 + 0x0, monsters[i].xPos);
//			output.putShort(0x1C + i * 0x18 + 0x2, monsters[i].yPos);
//			output.putShort(0x1C + i * 0x18 + 0x4, monsters[i].unk11);
//			output.putShort(0x1C + i * 0x18 + 0x6, monsters[i].unk12);
//			output.putShort(0x1C + i * 0x18 + 0x8, monsters[i].unk21);
//			output.putShort(0x1C + i * 0x18 + 0xA, monsters[i].unk22);
//			output.putShort(0x1C + i * 0x18 + 0xC, monsters[i].unk3);
//			output.putShort(0x1C + i * 0x18 + 0xE, monsters[i].unk4);
//			output.putShort(0x1C + i * 0x18 + 0x10, monsters[i].one);
//			output.putShort(0x1C + i * 0x18 + 0x12, monsters[i].npcType);
//			output.putShort(0x1C + i * 0x18 + 0x14, monsters[i].one2);
//			output.putShort(0x1C + i * 0x18 + 0x16, monsters[i].level);
//		}
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
		lobbyServer.sendRoomMessage(userSession, getResponse(), false);
	}

	@Override
	public void processMessage() {
		// TODO Auto-generated method stub
		
	}

}
