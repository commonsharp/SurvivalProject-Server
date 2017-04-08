package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.UserSession;
import net.objects.Room;
import tools.ExtendedByteBuffer;

public class GetRoomPlayersGuildRankHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x24;
	
	public GetRoomPlayersGuildRankHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
		// TODO Auto-generated constructor stub
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
		output.putInt(0x4, 0x4403);
		
		Room room = lobbyServer.getRoom(userSession.getUser().roomIndex);
		
		for (int i = 0; i < 8; i++) {
			if (room.getUserSession(i) != null) {
				output.putShort(0x14 + 2 * i, (short) room.getUserSession(i).getUser().guildRank);
			}
		}
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		lobbyServer.sendRoomMessage(userSession, getResponse(), false);
	}

}
