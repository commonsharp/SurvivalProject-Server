package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class BigMatchInfo extends LobbyHandler {
	byte[] requestBytes;
	
	public BigMatchInfo(LobbyServer lobbyServer, UserTCPSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
		requestBytes = messageBytes;
	}

	@Override
	public void interpretBytes() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processMessage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(requestBytes.length);
		output.putBytes(0x0, requestBytes);
		
		output.putInt(0x4, Messages.BIG_MATCH_INFO_REQUEST);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
		lobbyServer.sendRoomMessage(userSession, getResponse(), false);
	}

}
