package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class GetRoomInfoHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0xCC;
	
	protected int roomID;
	
	public GetRoomInfoHandler(LobbyServer lobbyServer, UserSession tcpServer, byte[] messageBytes) {
		super(lobbyServer, tcpServer, messageBytes);
	}

	@Override
	public void interpretBytes() {
		roomID = input.getInt(0x14);
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.GET_ROOM_INFO_RESPONSE);
		output.putInt(0x14, 0); // result. must NOT be 1. otherwise there's an error. any other value is fine.
		output.putInts(0x18, new int[] {1, 2, 3, 4, 5, 6, 7, 8}); // level -1 = no player
		output.putBytes(0x38, new byte[] {1, 1, 0, 0, 1, 1, 0, 0}); // genders
		output.putStrings(0x40, new String[] {"b1", "b2", "b3", "b4", "b5", "b6", "b7", "b8"}, 0xD);
		output.putInts(0xA8, new int[] {1, 2, 3, 4, 5, 0, 6, 7}); // ping. 0 - nothing. i=1~5 - i lines. i>5 - 5 lines
		output.putInt(0xC8, 1); // master (the one with the key) index
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
		
	}

	@Override
	public void processMessage() {
		// TODO Auto-generated method stub
		
	}

}
