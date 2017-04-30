package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class QuestInfoHandler extends LobbyHandler {
	public static final int REQUEST_ID = 0x4362;
//	public static final int RESPONSE_ID = 0x4409; // this is the correct one. not 0x4362
//	public static final int RESPONSE_LENGTH = 0x6C;
	
	byte[] bytes;
	boolean firstTime;
	
	public QuestInfoHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
		printMessage();
		bytes = messageBytes;
//		firstTime = false;
//		if (lobbyServer.getRoom(userSession.getUser().roomIndex).questInfo == null) {
//			lobbyServer.getRoom(userSession.getUser().roomIndex).questInfo = messageBytes;
//			firstTime = true;
//		}
	}

	@Override
	public void interpretBytes() {
		System.out.println("In slot " + userSession.getUser().getRoomSlot());
		input.getInt(0x14); // unknown
		input.getInt(0x18); // unknown
	}

	@Override
	public byte[] getResponse() {
		return null;

	}
	
	public byte[] getResponse2() {
//		byte[] questInfo = lobbyServer.getRoom(userSession.getUser().roomIndex).questInfo;
		ExtendedByteBuffer output = new ExtendedByteBuffer(bytes.length);
		output.putBytes(0x0, bytes);
		
//		output.putInt(0x4, 0x4409);
//		output.putInt(0x4, Messages.QUEST_INFO_REQUEST);
		
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
//		if (firstTime) {
			lobbyServer.sendRoomMessage(userSession, getResponse2(), false);
//		}
	}

	@Override
	public void processMessage() {
		// TODO Auto-generated method stub
		
	}

}
