package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import net.objects.Room;
import net.objects.User;
import tools.ExtendedByteBuffer;

public class KickPlayerHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x18;
	
	protected int playerSlot;
	
	public KickPlayerHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		playerSlot = input.getInt(0x14);
	}

	@Override
	public void processMessage() throws SQLException {
		Room room = lobbyServer.getRoom(userSession.getUser().getRoomIndex());
		User user = room.getUserSession(playerSlot).getUser();
		
		room.setNumberOfUsers(room.getNumberOfPlayers() - 1);
		
		if (user.getRoomTeam() == 10) {
			room.bluePlayersCount--;
		}
		else {
			room.redPlayersCount--;
		}
		
		user.setInRoom(false);
		user.setInGame(false);
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.KICK_PLAYER_RESPONSE);
		output.putInt(0x14, playerSlot);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		Room room = lobbyServer.getRoom(userSession.getUser().getRoomIndex());
		lobbyServer.sendRoomMessage(userSession, getResponse(), false);
		room.setUserSession(playerSlot, null);
		lobbyServer.sendBroadcastMessage(userSession, new LobbyRoomsChangedHandler(lobbyServer, userSession).getResponse(room));
		lobbyServer.onJoinLobby(userSession);
	}
}
