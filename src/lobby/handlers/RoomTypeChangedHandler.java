package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class RoomTypeChangedHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x40;
	
	protected int roomID;
	protected int newGameType;
	protected int newGameMap;
	
	public RoomTypeChangedHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		roomID = input.getInt(0x14);
		newGameType = input.getInt(0x18);
		newGameMap = input.getInt(0x1C);
	}

	@Override
	public void processMessage() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);

		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.CREATE_ROOM_RESPONSE);
		
		output.putInt(0x14, newGameType);
		output.putInt(0x18, newGameMap);
		output.putInt(0x1C, -1);
		output.putInt(0x20, -1);
		output.putInt(0x24, -1);
		output.putInt(0x28, -1);
		output.putInt(0x2C, -1);
		output.putInt(0x30, -1);
		output.putInt(0x34, -1);
		output.putInt(0x38, -1);
		output.putInt(0x3C, -1);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
