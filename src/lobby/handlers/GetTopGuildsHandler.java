package lobby.handlers;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class GetTopGuildsHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0xF0;
	
	public GetTopGuildsHandler(LobbyServer lobbyServer, UserSession tcpServer, byte[] messageBytes) {
		super(lobbyServer, tcpServer, messageBytes);
	}

	@Override
	public void interpretBytes() {
	}

	@Override
	public void afterSend() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.GET_TOP_GUILDS_RESPONSE);
		// If your clan is in this list, then 9 0x4486 requests are being sent to the server.
		// Otherwise, 10 0x4486 requests are being sent to the server...
		output.putString(0x14, "Guild3");
		output.putString(0x21, "hello2");
		output.putString(0x2E, "hello3");
		output.putString(0x3B, "hello4");
		output.putString(0x48, "hello5");
		output.putString(0x55, "hello6");
		output.putString(0x62, "hello7");
		output.putString(0x6F, "hello8");
		output.putString(0x7C, "hello9");
		output.putString(0x89, "hello10");
		output.putInt(0x98, 10); // score1
		output.putInt(0x9C, 9); // score2
		output.putInt(0xA0, 8); // score3
		output.putInt(0xA4, 7); // score4
		output.putInt(0xA8, 6); // score5
		output.putInt(0xAC, 5); // score6
		output.putInt(0xB0, 4); // score7
		output.putInt(0xB4, 3); // score8
		output.putInt(0xB8, 2); // score9
		output.putInt(0xBC, 1); // score10
		output.putInt(0xC0, 0); // another array (10)
		output.putInt(0xE8, 5); // my rank (0 based)
		output.putInt(0xEC, 10); // my points
//		output.putInt(0xC0, 1); // ?
//				outputBuffer.putString(0xC0, "hello1");
//				outputBuffer.putInt(0xD0, 5);
		
		return output.toArray();
	}

	@Override
	public void processMessage() {
		// TODO Auto-generated method stub
		
	}
}
