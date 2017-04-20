package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class TradeHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x88; // maybe change according to the last field. it could be an array...
	
	protected String fromUsername;
	protected String toUsername;
	
	byte[] bytes;
	
	public TradeHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
		bytes = messageBytes;
	}

	@Override
	public void interpretBytes() {
		fromUsername = input.getString(0x14);
		input.getInt(0x24); // ?
		toUsername = input.getString(0x28);
		// there are more fields!
	}

	@Override
	public void processMessage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(bytes.length);
		
		output.putBytes(0x0, bytes);
		output.putInt(0x0, bytes.length);
//		output.putInt(0x0, RESPONSE_LENGTH);
//		output.putInt(0x4, Messages.TRADE_REQUEST);
//		output.putInt(0x14, 1);
//		output.putString(0x14, toUsername);
//		output.putInt(0x24, 1);
//		output.putString(0x28, fromUsername);
//		output.putInt(0x38, -1);
//		output.putInt(0x3C, -1);
//		output.putInt(0x40, -1);
//		output.putInt(0x44, -1);
//		output.putLong(0x48, 5369532);
//		output.putInt(0x50, 4);
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
		lobbyServer.sendRoomMessage(userSession, getResponse(), false);
	}
}
