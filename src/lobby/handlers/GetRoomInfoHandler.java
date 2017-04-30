package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import net.objects.Room;
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
		
		Room room = lobbyServer.getRoom(roomID);
		UserSession currentUserSession;
		
		for (int i = 0; i < 8; i++) {
			currentUserSession = room.getUserSession(i);
			
			if (currentUserSession != null) {
				output.putInt(0x18 + 4 * i, currentUserSession.getUser().getPlayerLevel());
				output.putBoolean(0x38 + i, currentUserSession.getUser().isMale());
				output.putString(0x40 + 0xD * i, currentUserSession.getUser().getUsername());
				output.putInt(0xA8 + 4 * i, 1); // ping. 0 - nothing. i=1~5 - i lines. i>5 - 5 lines
			}
			else {
				output.putInt(0x18 + 4 * i, -1);
			}
		}
		
		output.putInt(0xC8, 0); // master (the one with the key) index
		
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
