package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class ChatMessageHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x979;
	
	protected int messageType;
	protected String username;
	protected String unknown;
	protected String text;
	
	public ChatMessageHandler(LobbyServer lobbyServer, UserTCPSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void interpretBytes() {
		messageType = input.getInt(0x14);
		username = input.getString(0x18);
		unknown = input.getString(0x25);
		text = input.getString(0x32);
		
		System.out.println("Unknown in chat message: " + unknown);
	}

	@Override
	public void processMessage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getResponse() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public byte[] getResponse2() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.CHAT_MESSAGE_RESPONSE);
		output.putInt(0x14, messageType);
		output.putString(0x18, username);
		output.putString(0x25, unknown);
		output.putString(0x32, text);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
		lobbyServer.sendBroadcastMessage(userSession, getResponse2());
	}
}
