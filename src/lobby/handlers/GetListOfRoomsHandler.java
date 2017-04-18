package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import net.objects.Room;
import tools.ExtendedByteBuffer;

public class GetListOfRoomsHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0xC8E;
	
	public GetListOfRoomsHandler(LobbyServer lobbyServer, UserSession userSession) {
		super(lobbyServer, userSession);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void interpretBytes() {
		
	}

	@Override
	public void processMessage() throws SQLException {
		
	}

	public byte[] getResponse(int offset) {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.GET_LIST_OF_ROOMS_RESPONSE);
		
		/*
		14+EDX-int-roomIndex
		6C-roomName (0x1D length)
		2EC+EDX-int-gameType
		344+EDX-int-gameMap
		39C+EDX-int-numberOfPlayers
		3F4+EDX-int-maxNumberOfPlayers
		44C+EAX-BYTE-isWithPassword
		462+EAX-BYTE-isWithScrolls
		478+EAX-BYTE-isClosed
		48E+EAX-BYTE-isPremium
		4A4+EAX-BYTE-isAutoTeam
		4BC+EDX-int-roomCardsLimit
		514 - 64 bytes per room
		A94 - 16 bytes per room
		BF4-int - 4 bytes per room
		C4C+EAX-BYTE-roomIsFull
		C62+EAX-BYTE
		C78+EAX-BYTE
		
		total - 0xC8E
		 */
		
		Room room;
		for (int i = 0; i < 0x16 && i < JoinLobbyHandler.lobbyMaxRooms; i++) {
			room = lobbyServer.getRoom(i + offset);
			
			if (room == null) {
				output.putInt(0x14 + 4 * i, -1);
			}
			else {
				// for more fields, look at LobbyRoomsChangedHandler
				output.putInt(0x14 + 4 * i, i + offset);
				output.putString(0x6C + 0x1D * i, room.getRoomName());
				output.putInt(0x2EC + 4 * i, room.getGameMode().getValue());
				output.putInt(0x344 + 4 * i, room.getGameMap());
				output.putInt(0x39C + 4 * i, room.getNumberOfPlayers());
				output.putInt(0x3F4 + 4 * i, room.getMaxNumberOfPlayers());
				output.putBoolean(0x44C + i, room.password != null);
				output.putBoolean(0x462 + i, room.isWithScrolls());
				output.putBoolean(0x478 + i, false);
				output.putBoolean(0x48E + i, false);
				output.putBoolean(0x4A4 + i, room.isWithAutoTeams());
				output.putInt(0x4BC + 4 * i, room.getCardsLimit());
				output.putInts(0x514 + 64 * i, room.getCharacters());
				output.putInt(0xA94 + 16 * i, 0);
				output.putInt(0xBF4 + 4 * i, room.missionLevel); // mission
				output.putByte(0xC4C + i, (byte) 0); // can you get in or not
				output.putByte(0xC62 + i, (byte) 0); // boolean. 0 or anything else.
				output.putBoolean(0xC78 + i, room.isLimitAnger()); // is crit limit? a red shape near the room name
			}
		}
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		
	}

	@Override
	public byte[] getResponse() throws SQLException {
		return null;
	}

}
