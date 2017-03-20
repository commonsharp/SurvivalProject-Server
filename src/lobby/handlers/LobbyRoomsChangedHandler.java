package lobby.handlers;

import java.io.IOException;

import net.GenericHandler;
import net.Room;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class LobbyRoomsChangedHandler extends GenericHandler {
	public static final int RESPONSE_ID = 0x4304;
	public static final int RESPONSE_LENGTH = 0x118;
	
	public LobbyRoomsChangedHandler(UserTCPSession tcpServer) {
		super(tcpServer);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void interpretBytes() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getResponse() {
		return null;
	}
	
	public byte[] getResponse(Room room) {
		return getResponse(room.getRoomID(), room.getRoomName(), room.getGameType(),
				room.getGameMap(), room.getMaxNumberOfPlayers(), room.getIsWithScrolls(), room.getIsWithTeams(), room.getCharacters());
	}
	
	public byte[] getResponse(int roomID, String roomName, int gameType, int gameMap, int maxNumberOfPlayers, byte isWithScrolls, byte isWithTeams, int[] characters) {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, RESPONSE_ID);
		output.putByte(0x14, (byte) 1); //create?
		output.putInt(0x18, roomID); // room ID
		output.putString(0x1C,  roomName); // room title
		output.putInt(0x3C, gameType); // room type
		output.putInt(0x40, gameMap); // room map
		output.putInt(0x44, 1); // number of players in the room. if it's equal to the max, the room is full (blacked out)
		output.putInt(0x48, maxNumberOfPlayers); // max players
		output.putByte(0x4C, (byte) 0); // is password
		output.putByte(0x4D, (byte) 1); // is with scrolls
		output.putByte(0x4E, (byte) 0); // is closed. 0 = not closed. 1 = closed.
		output.putByte(0x4F, (byte) 1); // is premium
		output.putByte(0x50, (byte) 1); // is auto team
		output.putInt(0x54, -1); // cards limit.
		output.putInts(0x58, characters); // characters in the room
		output.putInt(0x98, 0); // -1 - everyone is random. 0 - normal. 1 - red team random. 2 - blue team random
		output.putInt(0xA8, 88); // this is big mission quest number. something with field 2E54 in MyInfo
		
		output.putByte(0xAC, (byte) 0); // isfull. 0 = not full. 1 = full.
		output.putByte(0xAD, (byte) 0); // boolean. 0 or anything else.
		output.putByte(0xAE, (byte) 0); // is crit limit? a red shape near the room name
		
		return output.toArray();
	}
	
	@Override
	public void afterSend() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
