package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import net.objects.GameMode;
import net.objects.Room;
import tools.ExtendedByteBuffer;

public class RoomGameModeChangedHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x40;
	
	protected int roomID;
	protected int newGameType;
	protected int newGameMap;
	
	public RoomGameModeChangedHandler(LobbyServer lobbyServer, UserSession userSession) {
		super(lobbyServer, userSession);
	}
	
	public RoomGameModeChangedHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
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
		lobbyServer.getRoom(roomID).setGameMode(GameMode.getMode(newGameType));
		lobbyServer.getRoom(roomID).setGameMap(newGameMap);
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);

		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.ROOM_GAME_MODE_CHANGED_RESPONSE);
		
		output.putInt(0x14, newGameType);
		output.putInt(0x18, newGameMap);
		// dunno:
//		output.putInt(0x1C, 1);
//		output.putInt(0x20, 2);
//		output.putInt(0x24, 3);
//		output.putInt(0x28, 4);
//		output.putInt(0x2C, 5);
//		output.putInt(0x30, 6);
//		output.putInt(0x34, 7);
//		output.putInt(0x38, 8);
//		output.putInt(0x3C, 9);
		
		return output.toArray();
	}
	
	public byte[] getResponse2() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);

		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.ROOM_GAME_MODE_CHANGED_RESPONSE);
		
		Room room = lobbyServer.getRoom(userSession.getUser().getRoomIndex());
		output.putInt(0x14, room.getGameMode().getValue());
		output.putInt(0x18, room.getGameMap());
		// dunno:
		
		for (int i = 0; i < 8; i++) {
			if (room.getUserSession(i) != null) {
				output.putInt(0x1C + 4 * i, room.getUserSession(i).getUser().getRoomTeam());
			}
		}
		output.putInt(0x3C, 2);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		lobbyServer.sendBroadcastMessage(userSession, new LobbyRoomsChangedHandler(lobbyServer, userSession).getResponse(lobbyServer.getRoom(roomID)));
//		lobbyServer.sendBroadcastMessage(userSession, getResponse());
		lobbyServer.sendRoomMessage(userSession, getResponse(), false);
	}

}
