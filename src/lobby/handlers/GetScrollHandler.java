package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class GetScrollHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x20;
	
	protected int playerSlot;
	protected int scrollID;
	protected int scrollSlot;
	
	public GetScrollHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		playerSlot = input.getInt(0x14);
		scrollID = input.getInt(0x18);
		scrollSlot = input.getInt(0x1C);
	}

	@Override
	public void processMessage() throws SQLException {
		userSession.getUser().scrolls[scrollSlot] = scrollID;
		userSession.getUser().saveUser();
	}

	@Override
	public byte[] getResponse() throws SQLException {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.GET_SCROLL_RESPONSE);
		output.putInt(0x14, playerSlot);
		output.putInt(0x18, scrollID);
		output.putInt(0x1C, scrollSlot);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		lobbyServer.sendRoomMessage(userSession, getResponse(), false);
	}

}
