package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class NewMasterHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x1C;
	
	public NewMasterHandler(LobbyServer lobbyServer, UserSession userSession) {
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
		output.putInt(0x4, Messages.NEW_MASTER_RESPONSE);
//		output.putInt(0x14, lobbyServer.getRoom(userSession.getUser().roomIndex)); // this should be the new master slot.
		output.putInt(0x18, 1); // ?
		
		return output.toArray();
	}
	
	public byte[] getResponse(int master) {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.NEW_MASTER_RESPONSE);
		output.putInt(0x14, master); // this should be the new master slot.
		output.putInt(0x18, 1); // ?
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		
	}
}
