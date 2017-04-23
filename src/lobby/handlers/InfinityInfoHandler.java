package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class InfinityInfoHandler extends LobbyHandler {
	byte[] bytes;
	public InfinityInfoHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
		bytes = messageBytes;
	}

	@Override
	public void interpretBytes() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processMessage() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getResponse() throws SQLException {
		ExtendedByteBuffer output = new ExtendedByteBuffer(bytes.length);
		output.putBytes(0x0, bytes);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
