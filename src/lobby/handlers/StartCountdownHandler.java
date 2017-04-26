package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import net.objects.Room;
import tools.ExtendedByteBuffer;

public class StartCountdownHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x118;
	
	boolean isChangeType;
	
	public StartCountdownHandler(LobbyServer lobbyServer, UserSession userSession) {
		super(lobbyServer, userSession);
		isChangeType = false;
	}
	
	public StartCountdownHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		isChangeType = input.getInt(0x14) == 2; // 2 if we change the timer to different time
		int time = input.getInt(0x18);
		System.out.println("Start countdown - 0x14: " + input.getInt(0x14));
		System.out.println("Start countdown - 0x18: " + input.getInt(0x18));
		System.out.println("Start countdown - 0x1C: " + input.getInt(0x1C));
		
		Room room = lobbyServer.getRoom(userSession.getUser().roomIndex);
		
		if (time != 0) {
			room.roomTimerTime = time;
		}
	}

	@Override
	public byte[] getResponse() {
		Room room = lobbyServer.getRoom(userSession.getUser().roomIndex);
		
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.MISSION_START_COUNTDOWN_RESPONSE);
		System.out.println("in start countdown");
		
		if (isChangeType) {
			output.putInt(0x14, 2); // 2 is for changing the time of the timer
		}
		else {
			output.putInt(0x14, 0); // 0 and 1 show the timer.
		}
		
		output.putInt(0x18, (int)(System.currentTimeMillis() - lobbyServer.gameServer.serverStartTime)); // server time
		output.putInt(0x1C, room.roomTimerTime);
		
		if (room.roomStartTime != 0) {
			output.putInt(0x20, (int) (System.currentTimeMillis() - room.roomStartTime)); // delay
		}
		
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
