package lobby.handlers;

import java.io.IOException;

import lobby.LobbyServer;
import net.GenericHandler;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

// change to join room (official name)
public class EnterExistingRoomHandler extends GenericHandler {
	public static final int REQUEST_ID = 0x4313;
	public static final int RESPONSE_ID = 0x4314;
	public static final int RESPONSE_LENGTH = 0x7A;
	
	protected int roomID;
	protected LobbyServer lobby;
	
	public EnterExistingRoomHandler(LobbyServer lobby, UserTCPSession tcpServer, byte[] messageBytes) {
		super(tcpServer, messageBytes);
		this.lobby = lobby;
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
		userSession.getUser().roomSlot = lobby.getRoom(roomID).getSlot();
		userSession.getUser().roomTeam = lobby.getRoom(roomID).getTeam();
		userSession.getUser().roomCharacter = 10;
		userSession.getUser().roomReady = 0;
		userSession.getUser().roomFieldF4 = 2;
		lobby.getRoom(roomID).setUserSession(userSession.getUser().roomSlot, userSession);
		
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, RESPONSE_ID);
		output.putInt(0x14, 0); //1,2,3,4,5,6 - errors possibly. others - good.
		output.putInt(0x18, roomID);
		output.putString(0x1C, lobby.getRoom(roomID).getRoomName());
		output.putInt(0x3C, lobby.getRoom(roomID).getGameType());
		output.putInt(0x40, lobby.getRoom(roomID).getGameMap());
		output.putInt(0x54, lobby.getRoom(roomID).getMaxNumberOfPlayers());
		output.putByte(0x58, lobby.getRoom(roomID).getIsWithScrolls());
		output.putInt(0x5C, 0); // character?
		output.putByte(0x60, (byte) 0); //this is like field F4. set to -1 to start automatically...
		output.putByte(0x61, (byte) 0);
		output.putInt(0x64, userSession.getUser().roomSlot); // this is the slot. -1 crashes the game (duh)
		output.putInt(0x68, userSession.getUser().roomTeam); // team
		output.putByte(0x6C, lobby.getRoom(roomID).getIsWithTeams());
		output.putInt(0x70, lobby.getRoom(roomID).getCardsLimit());
		output.putShort(0x74, (short) 2);
		output.putByte(0x78, lobby.getRoom(roomID).getIsLimitAnger());
		output.putByte(0x79, (byte) 0);
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
		UserTCPSession currentUserSession;
		
		// Go through each of the users in the room
		for (int i = 0; i < 8; i++) {
			currentUserSession = lobby.getRoom(roomID).getUser(i);
			
			// If that slot is used
			if (currentUserSession != null) {
				// Send the user
				sendTCPMessage(new RoomPlayersChangedHandler(lobby, currentUserSession).getResponse(currentUserSession.getUser()));
			}
		}
		
		lobby.roomMessage(userSession, roomID, new RoomPlayersChangedHandler(lobby, userSession).getResponse(userSession.getUser()));
	}
}
