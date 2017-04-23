package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class TreasureSpawnedHandler extends LobbyHandler{
	public static final int RESPONSE_LENGTH = 0x20;
	
	int monsterIndex;
	int x, y;
	public TreasureSpawnedHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void interpretBytes() {
		monsterIndex = input.getInt(0x14);
		x = input.getInt(0x18);
		y = input.getInt(0x1C);
	}

	@Override
	public void processMessage() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getResponse() throws SQLException {
		return null;
	}
	
	public byte[] getResponse2() throws SQLException {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);

		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.TREASURE_SPAWNED_REQUEST);
		output.putInt(0x14, monsterIndex);
		output.putInt(0x18, x);
		output.putInt(0x1C, y);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		lobbyServer.sendRoomMessage(userSession, getResponse2(), false);
	}

}
