package lobby.handlers;

import java.io.IOException;

import lobby.LobbyServer;
import net.GenericHandler;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class LeaveGameHandler extends GenericHandler {
	public static final int REQUEST_ID = 0x4322;
	public static final int RESPONSE_ID = 0x4323;
	public static final int RESPONSE_LENGTH = 0x31;
	
	protected LobbyServer lobby;
	
	public LeaveGameHandler(LobbyServer lobby, UserTCPSession userSession, byte[] messageBytes) {
		super(userSession, messageBytes);
		this.lobby = lobby;
	}

	@Override
	public void interpretBytes() {
		
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, RESPONSE_ID);
		output.putInt(0x14, userSession.getUser().roomSlot);
		output.putString(0x18, userSession.getUser().username);
		output.putInt(0x28, 0);
		output.putInt(0x2C, 0);
		output.putByte(0x30, (byte) 0);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
		lobby.broadcastMessage(userSession, getResponse());
	}

}
