package lobby.handlers;

import java.io.IOException;

import lobby.LobbyServer;
import net.GenericHandler;
import net.User;
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
		tcpServer.getUser().isInRoom = true;
		tcpServer.getUser().roomIndex = roomID;
		tcpServer.getUser().roomSlot = 1;
		tcpServer.getUser().roomTeam = 20;
		tcpServer.getUser().roomCharacter = 10;
		tcpServer.getUser().roomReady = 0;
		tcpServer.getUser().roomStart = 0;
		tcpServer.getUser().roomFieldF4 = -1;
		lobby.getRoom(roomID).setUser(tcpServer.getUser().roomSlot, tcpServer.getUser());
		
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
		output.putInt(0x64, tcpServer.getUser().roomSlot); // this is the slot. -1 crashes the game (duh)
		output.putInt(0x68, tcpServer.getUser().roomTeam); // team
		output.putByte(0x6C, lobby.getRoom(roomID).getIsWithTeams());
		output.putInt(0x70, lobby.getRoom(roomID).getCardsLimit());
		output.putShort(0x74, (short) 0);
		output.putByte(0x78, (byte) lobby.getRoom(roomID).getIsLimitAnger());
		output.putByte(0x79, (byte) 0);
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
		User u;
		
		// Go through each of the users in the room
		for (int i = 0; i < 8; i++) {
			u = lobby.getRoom(roomID).getUser(i);
			
			// If that slot is used
			if (u != null) {
				// Send the user
				sendTCPMessage(new RoomPlayersChangedHandler(lobby, tcpServer).getResponse(
						u.roomSlot, u.roomCharacter, u.roomTeam, u.roomReady, u.roomStart, u.roomFieldF4));
			}
		}
		
//		sendTCPMessage(new RoomPlayersChangedHandler(lobby, tcpServer).getResponse(tcpServer.getUser().roomSlot, 10, tcpServer.getUser().roomTeam, (byte) 0, 0, -1));
		lobby.broadcastMessage(tcpServer, new RoomPlayersChangedHandler(lobby, tcpServer).getResponse(
				tcpServer.getUser().roomSlot, tcpServer.getUser().roomCharacter, tcpServer.getUser().roomTeam,
				(byte) tcpServer.getUser().roomReady, tcpServer.getUser().roomStart, tcpServer.getUser().roomFieldF4));
	}
}
