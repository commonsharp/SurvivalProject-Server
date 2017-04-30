package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import game.handlers.GameNotificationHandler;
import game.handlers.GameNotificationHandler.GameAnnouncementResult;
import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import net.objects.Guild;
import net.objects.User;
import tools.ExtendedByteBuffer;

public class ChatMessageHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x979;
	
	protected int messageType;
	protected String username;
	protected String whisperUsername;
	protected String text;
	
	public ChatMessageHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
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
		lobbyServer.sendBroadcastGameMessage(userSession, new GameNotificationHandler(lobbyServer.gameServer).getResponse(
				GameAnnouncementResult.LEVEL_UP, userSession.getUser().getUsername(), 37, 0));
//		lobbyServer.sendRoomMessage(userSession, new Test4471Handler(lobbyServer, userSession).getResponse(), true);
//		sendTCPMessage(new Test4471Handler(lobbyServer, userSession).getResponse());
//		lobbyServer.sendRoomMessage(userSession, new RoomGameModeChangedHandler(lobbyServer, userSession).getResponse2(), true);
//		lobbyServer.sendRoomMessage(userSession, new ChangeMapHandler(lobbyServer, userSession).getResponse(), true);
		if (text.toLowerCase().startsWith("@gender")) {
			userSession.getUser().setMale(!userSession.getUser().isMale());
			User.saveUser(userSession.getUser());
			return;
		}
		else if (text.toLowerCase().startsWith("@guild-join")) {
			String guildName = text.substring("@guild-join".length() + 1);

			if (Guild.isGuildExists(guildName) && !Guild.isGuildMemberExists(guildName, userSession.getUser().getUsername())) {
				System.out.println("in");
				Guild.leaveGuild(userSession);
				Guild.joinGuild(guildName, userSession);
			}
			
			return;
		}
		else if (text.toLowerCase().startsWith("@guild-leave")) {
			if (Guild.isGuildMemberExists(userSession.getUser().getGuildName(), userSession.getUser().getUsername())) {
				Guild.leaveGuild(userSession);
			}
			
			return;
		}
		else if (text.toLowerCase().startsWith("@pw")) {
			String password = text.substring("@pw".length() + 1);
			
			if (password.length() < 13) {
				userSession.getUser().setPassword(password);
				User.saveUser(userSession.getUser());
			}
			
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
		// Guild chat
		else if (messageType == 3) {
			lobbyServer.sendGuildMessage(userSession, getResponse2(), false);
		}
	}
}
