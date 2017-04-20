package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class Test4376Handler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x88;
	
	public Test4376Handler(LobbyServer lobbyServer, UserSession userSession) {
		super(lobbyServer, userSession);
	}

	@Override
	public void interpretBytes() {
		
	}

	@Override
	public void processMessage() throws SQLException {
		
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, 0x4376);
		output.putInt(0x14, 2);
		output.putString(0x18, "yoyyyyyyyyyyyyyyyyyyyy");
		output.putInt(0x7C, 2);
		output.putShort(0x80, (short) 10);
		output.putShort(0x84, (short) 1);
		output.putShort(0x86, (short) 1);
//		output.putString(0x14, "hey");
//		output.putString(0x34, "hey2");
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
