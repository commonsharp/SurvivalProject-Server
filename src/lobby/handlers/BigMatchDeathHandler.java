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
	
	protected int killedSlot;
	protected int killerSlot;
	
	public BigMatchDeathHandler(LobbyServer lobbyServer, UserTCPSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		killedSlot = input.getInt(0x18); // 0~7 for players. 8+ for npc
		killerSlot = input.getInt(0x20);
		System.out.println("14: " + input.getInt(0x14));
		System.out.println("18: " + input.getInt(0x18));
		System.out.println("1C: " + input.getInt(0x1C));
		System.out.println("20: " + input.getInt(0x20));
		System.out.println("24: " + input.getInt(0x24));
		/*
		 	14 - 0 for npc
			18 - slot. 0~7 for players. 8+ for npc.
			1C - another id
			20 - killer slot
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
		
		output.putInt(0x14, killedSlot); // id of death player
		output.putInt(0x18, killerSlot); // slot??
		
		// Those are the 4 best players/npcs in the game. it's sufficient to just change the first one.
		output.putInt(0x1C, 2); // lucky multiplier
		output.putInt(0x2C, 7); // experience and code
		output.putInt(0x3C, killerSlot); // slots. 0~7 for players. 8+ for npcs
		
		output.putInt(0x4C, 30); // new level
		output.putInt(0x50, 0);
		output.putInt(0x54, 0);
		output.putInt(0x58, 0);
		output.putInt(0x5C, 0);
		output.putInt(0x60, 0);
		output.putInt(0x64, 0);
		output.putInt(0x68, 0);
		output.putInt(0x6C, 0); // something with slots maybe.
		output.putInt(0x8C, 2); // element type
		output.putInt(0x90, 2); // element amount
		output.putInt(0x94, 23); // element multiplier
		output.putInt(0x98, 3); // npc points multiplier (not code/experience) (in big match survival). you get more points for killing those 
		output.putInt(0x9C, 0); // if this is 0, then the death time percentage can change. -1 = 100% blue. having 100% gives the entire team crit
		output.putInt(0xA0, 0);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
		lobbyServer.sendRoomMessage(userSession, getResponse(), false);
		
//		if (killedSlot < 8) {
			System.out.println("Restart");
			lobbyServer.sendRoomMessage(userSession, new RoundCompletedHandler(lobbyServer, userSession).getResponse(), true);
//			lobbyServer.sendRoomMessage(userSession, new SpawnHandler(lobbyServer, userSession).getResponse2(), true);
//		}
	}

	@Override
	public void processMessage() {
		// TODO Auto-generated method stub
		
	}

}
