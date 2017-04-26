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
	public byte[] getResponse() throws SQLException {
		return null;
	}
	
	public byte[] getResponse(int i) {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, 0x4376);
		output.putInt(0x14, i); // 2 or 3
		output.putString(0x18, "baraklevy");
		output.putInt(0x7C, 3); // element type
		output.putShort(0x80, (short) 10); // element count
		output.putShort(0x84, (short) 0);
		output.putShort(0x86, (short) 0);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
	}

}
