package lobby.handlers;

import java.io.IOException;

import lobby.LobbyServer;
import net.GenericHandler;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class PlayerDeathHandler extends GenericHandler {
	public static final int REQUEST_ID = 0x4335;
	public static final int RESPONSE_ID = 0x4336;
	public static final int RESPONSE_LENGTH = 0x118;
	
	protected int killerIndex;
	protected LobbyServer lobby;
	
	public PlayerDeathHandler(LobbyServer lobby, UserTCPSession userSession, byte[] messageBytes) {
		super(userSession, messageBytes);
		this.lobby = lobby;
	}

	@Override
	public void interpretBytes() {
		killerIndex = input.getInt(0x18);
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, RESPONSE_ID);
		output.putInt(0x14, userSession.getUser().roomSlot); // killed slot
		output.putInt(0x18, killerIndex); // killer slot
		output.putInt(0x1C, 0); // code array possibly
		output.putInt(0x3C, 6); // exp array
		output.putInt(0x5C, 0);
		output.putInt(0x60, 0);
		output.putInt(0x64, 0);
		output.putInt(0x68, 0);
		output.putInt(0x6C, 0);
		output.putInt(0x70, 0);
		output.putInt(0x74, 0);
		output.putInt(0x78, 0);
		output.putInt(0x7C, 0); // another array 0x20
		output.putInt(0x9C, 2); // elements type
		output.putInt(0xA0, 5); // elements amount
		output.putInt(0xA4, 4); // elements multiplier
		output.putInt(0xA8, 0); // can resurrect
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
		lobby.roomMessage(userSession, userSession.getUser().roomIndex, getResponse());
		
		sendTCPMessage(new SpawnHandler(userSession).getResponse(userSession.getUser().roomSlot));
		lobby.roomMessage(userSession.getUser().roomIndex, new SpawnHandler(userSession).getResponse(userSession.getUser().roomSlot));
//		sendTCPMessage(new ResultsHandler(userSession).getResponse(0));
//		lobby.roomMessage(userSession.getUser().roomIndex, new ResultsHandler(userSession).getResponse(0));
//		sendTCPMessage(new PlayerResurrectionHandler(userSession).getResponse(userSession.getUser().roomSlot, 500, 500));
//		lobby.roomMessage(userSession, userSession.getUser().roomIndex,
//				new PlayerResurrectionHandler(userSession).getResponse(userSession.getUser().roomSlot, 500, 500));
	}

}
