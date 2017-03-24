package lobby.handlers;

import java.io.IOException;

import lobby.LobbyServer;
import net.GenericHandler;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class MissionInfoHandler extends GenericHandler {
	public static final int REQUEST_ID = 0x4458;
	public static final int RESPONSE_ID = 0x4458;
	
	byte[] requestBytes;
	
	LobbyServer lobby;
	public MissionInfoHandler(LobbyServer lobby, UserTCPSession userSession, byte[] messageBytes) {
		super(userSession, messageBytes);
		requestBytes = messageBytes;
		this.lobby = lobby;
	}

	@Override
	public void interpretBytes() {
		input.getInt(0x14); // unknown
		input.getInt(0x18); // unknown
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(requestBytes.length);
		output.putBytes(0x0, requestBytes);
		output.putInt(0x4, RESPONSE_ID);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
		lobby.roomMessage(userSession.getUser().roomIndex, getResponse());
	}

}
