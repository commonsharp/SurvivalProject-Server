package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

// not sure if you need to send the mission info to everyone else
public class MissionInfoHandler extends LobbyHandler {
	byte[] requestBytes;
	
	public MissionInfoHandler(LobbyServer lobbyServer, UserTCPSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
		requestBytes = messageBytes;
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
		output.putInt(0x4, Messages.MISSION_INFO_REQUEST);
		
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
