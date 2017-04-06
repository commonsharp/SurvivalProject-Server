package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class ChatMessageHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x979;
	
	protected int messageType;
	protected String username;
	protected String whisperUsername;
	protected String text;
	
	public ChatMessageHandler(LobbyServer lobbyServer, UserTCPSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void interpretBytes() {
		messageType = input.getInt(0x14);
		username = input.getString(0x18);
		whisperUsername = input.getString(0x25);
		text = input.getString(0x32);
		
		System.out.println("Messagetype in chat: " + messageType);
	}

	@Override
	public void processMessage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getResponse() {
		return null;
	}
	
	public byte[] getResponse2() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.CHAT_MESSAGE_RESPONSE);
		output.putInt(0x14, messageType);
		output.putString(0x18, username);
		output.putString(0x25, whisperUsername);
		output.putString(0x32, text);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		if (text.toLowerCase().startsWith("@gender")) {
			userSession.getUser().isMale = !userSession.getUser().isMale;
			userSession.getUser().saveUser();
			return;
		}
		
		// All chat or trade chat - send to everyone
		if (messageType == 0 || messageType == 7) {
			lobbyServer.sendBroadcastMessage(userSession, getResponse2());
		}
		// Whisper
		else if (messageType == 1) {
			lobbyServer.sendToUserMessage(whisperUsername, getResponse2());
		}
		// Friends chat
		else if (messageType == 2) {
			lobbyServer.sendFriendsMessage(userSession, getResponse2());
		}
	}
}
