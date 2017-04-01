package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class JoinRoomHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x7A;
	
	protected int roomID;
	
	public JoinRoomHandler(LobbyServer lobbyServer, UserTCPSession tcpServer, byte[] messageBytes) {
		super(lobbyServer, tcpServer, messageBytes);
	}

	@Override
	public void interpretBytes() {
		roomID = input.getInt(0x14);
		input.getString(0x18); // always 0. must have been used in older versions.
		input.getByte(0x25); // ?
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		userSession.getUser().isInRoom = true;
		userSession.getUser().roomIndex = roomID;
		userSession.getUser().roomSlot = lobbyServer.getRoom(roomID).getSlot();
		userSession.getUser().roomTeam = lobbyServer.getRoom(roomID).getTeam();
		userSession.getUser().roomCharacter = userSession.getUser().mainCharacter;
		userSession.getUser().roomReady = 0;
		userSession.getUser().roomFieldF4 = 2;
		lobbyServer.getRoom(roomID).setUserSession(userSession.getUser().roomSlot, userSession);
		
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.JOIN_ROOM_RESPONSE);
		output.putInt(0x14, 0); //1,2,3,4,5,6 - errors possibly. others - good.
		output.putInt(0x18, roomID);
		output.putString(0x1C, lobbyServer.getRoom(roomID).getRoomName());
		output.putInt(0x3C, lobbyServer.getRoom(roomID).getGameMode().getValue());
		output.putInt(0x40, lobbyServer.getRoom(roomID).getGameMap());
		output.putInt(0x54, lobbyServer.getRoom(roomID).getMaxNumberOfPlayers());
		output.putByte(0x58, lobbyServer.getRoom(roomID).getIsWithScrolls());
		output.putInt(0x5C, 0); // character?
		output.putByte(0x60, (byte) 0); //this is like field F4. set to -1 to start automatically...
		output.putByte(0x61, (byte) 0);
		output.putInt(0x64, userSession.getUser().roomSlot);
		output.putInt(0x68, userSession.getUser().roomTeam);
		output.putByte(0x6C, lobbyServer.getRoom(roomID).getIsWithTeams());
		output.putInt(0x70, lobbyServer.getRoom(roomID).getCardsLimit());
		output.putShort(0x74, (short) 2);
		output.putByte(0x78, lobbyServer.getRoom(roomID).getIsLimitAnger());
		output.putByte(0x79, (byte) 0);
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
		UserTCPSession currentUserSession;
		
		// Go through each of the users in the room
		for (int i = 0; i < 8; i++) {
			currentUserSession = lobbyServer.getRoom(roomID).getUser(i);
			
			// If that slot is used
			if (currentUserSession != null) {
				// Send the user
				sendTCPMessage(new RoomPlayersUpdateHandler(lobbyServer, currentUserSession).getResponse(currentUserSession.getUser()));
			}
		}
		
		lobbyServer.sendRoomMessage(userSession, new RoomPlayersUpdateHandler(lobbyServer, userSession).getResponse(userSession.getUser()), false);
		lobbyServer.sendBroadcastMessage(userSession, new LobbyRoomsChangedHandler(lobbyServer, userSession).getResponse(lobbyServer.getRoom(roomID)));
		
		sendTCPMessage(new NewMasterHandler(lobbyServer, userSession).getResponse());
	}

	@Override
	public void processMessage() {
	}
}
