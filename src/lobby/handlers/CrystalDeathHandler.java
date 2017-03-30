package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserTCPSession;
import net.objects.Room;
import tools.ExtendedByteBuffer;

public class CrystalDeathHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x90;
	
	int questProgression;
	int unknown;
	
	public CrystalDeathHandler(LobbyServer lobbyServer, UserTCPSession tcpServer) {
		super(lobbyServer, tcpServer);
	}
	
	public CrystalDeathHandler(LobbyServer lobbyServer, UserTCPSession tcpServer, byte[] messageBytes) {
		super(lobbyServer, tcpServer, messageBytes);
	}

	@Override
	public void interpretBytes() {
		unknown = input.getInt(0x14); // 1, 2 or 3...
		questProgression = input.getInt(0x18); // 100% = quest completed
		System.out.println(unknown);
		System.out.println(questProgression);
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);

		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.CRYSTAL_DEATH_RESPONSE);
		
		int result = 3;
		if (questProgression == 100)
			result = 1;
		
		int[] slotResults = new int[8];
		
		for (int i = 0; i < 8; i++)
			slotResults[i] = result;
		
		return getResponse(slotResults);
	}
	
	public byte[] getResponse(int[] results) {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);

		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.CRYSTAL_DEATH_RESPONSE);
		
		int exp = 200;
		int code = 300;
		 // result array. 0 = resurrection. 1 = quest success. 2 = quest failed. 4 = player left game? others = nothing.
		output.putInts(0x14, results); // result (per slot)
		output.putInt(0x34, userSession.getUser().gameKO); //ko
		output.putInt(0x38, 15); // ? maybe guild exp?
		
		Room room = lobbyServer.getRoom(userSession.getUser().roomIndex);
		
		for (int i = 0; i < 8; i++) {
			output.putInt(0x3C + i * 4, exp);
			output.putInt(0x5C + i * 4, code);
			
			if (room.getUser(i) != null) {
				room.getUser(i).getUser().playerExperience += exp;
				room.getUser(i).getUser().playerCode += code;
			}
		}
		
		output.putLong(0x80, userSession.getUser().playerExperience); // new experience
		output.putLong(0x88, userSession.getUser().playerCode); // new code
		
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
