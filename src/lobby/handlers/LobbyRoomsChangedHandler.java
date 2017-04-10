package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import net.objects.Room;
import tools.ExtendedByteBuffer;

public class LobbyRoomsChangedHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x118;
	
	public LobbyRoomsChangedHandler(LobbyServer lobbyServer, UserSession tcpServer) {
		super(lobbyServer, tcpServer);
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
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.LOBBY_ROOMS_CHANGED_RESPONSE);
		
		// 0x14 field = 0 - no room. other - room. when it's 0, it can still show premium. need to set everything else to 0
		if (room.getNumberOfPlayers() == 0) {
			output.putByte(0x14, (byte) 0);
			output.putInt(0x18, room.getRoomID());
		}
		else {
			output.putByte(0x14, (byte) 1); // boolean
			output.putInt(0x18, room.getRoomID()); // room ID
			output.putString(0x1C,  room.getRoomName()); // room title
			output.putInt(0x3C, room.getGameMode().getValue()); // room type
			output.putInt(0x40, room.getGameMap()); // room map
			output.putInt(0x44, room.getNumberOfPlayers()); // number of players in the room. if it's equal to the max, the room is full (blacked out)
			output.putInt(0x48, room.getMaxNumberOfPlayers()); // max players
			output.putByte(0x4C, (byte) 0); // is password
			output.putByte(0x4D, (byte) 1); // is with scrolls
			output.putByte(0x4E, (byte) 0); // is closed. 0 = not closed. 1 = closed.
			output.putByte(0x4F, (byte) 1); // is premium
			output.putByte(0x50, (byte) 0); // is auto team
			output.putInt(0x54, room.getCardsLimit()); // cards limit.
			output.putInts(0x58, room.getCharacters()); // characters in the room
			output.putInt(0x98, 0); // -1 - everyone is random. 0 - normal. 1 - red team random. 2 - blue team random
			output.putInt(0xA8, 88); // this is big mission quest number. something with field 2E54 in MyInfo
			
			output.putByte(0xAC, (byte) 0); // can you get in or not
			output.putByte(0xAD, (byte) 0); // boolean. 0 or anything else.
			output.putByte(0xAE, (byte) 0); // is crit limit? a red shape near the room name
		}
		
		return output.toArray();
	}
	
	@Override
	public void afterSend() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processMessage() {
		// TODO Auto-generated method stub
		
	}

}
