package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class BigMatchDeathHandler extends LobbyHandler {
	public static final int REQUEST_ID = Messages.BIG_MATCH_DEATH_REQUEST;
	public static final int RESPONSE_ID = Messages.BIG_MATCH_DEATH_RESPONSE;
	public static final int RESPONSE_LENGTH = 0xA4;
	
	public BigMatchDeathHandler(LobbyServer lobbyServer, UserTCPSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		/*
		 	14 - 0 for npc
			18 - slot. 0~7 for players. 8+ for npc.
			1C - another id
			20 - killer id maybe?
			24
			28 is a structure with 3 fields. 28 = 500d for npc. was 297. now 150. time maybe?
			2C - zero for npc. A4 for warren. was A4, now 8C
			30 - zero for npc. 62h for warren - was 62h, now 67h
			34 - 34 is a structure with 3 fields. same value as field 20.
			38 - zero for npc. 28h for warren.
			3C - zero for npc. 10h for warren
		 */
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);

		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, RESPONSE_ID);
		
		output.putInt(0x14, 1); // id of death player
		output.putInt(0x18, 1); // killer ID
		output.putInt(0x1C, 2);
		output.putInt(0x2C, 3);
		output.putInt(0x3C, 4);
		output.putInt(0x4C, 5);
		output.putInt(0x50, 6);
		output.putInt(0x54, 7);
		output.putInt(0x58, 8);
		output.putInt(0x5C, 9);
		output.putInt(0x60, 10);
		output.putInt(0x64, 11);
		output.putInt(0x68, 12);
		output.putInt(0x6C, 13);
		output.putInt(0x8C, 14);
		output.putInt(0x90, 15);
		output.putInt(0x94, 16);
		output.putInt(0x98, 17);
		output.putInt(0x9C, 18);
		output.putInt(0xA0, 19);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processMessage() {
		// TODO Auto-generated method stub
		
	}

}
