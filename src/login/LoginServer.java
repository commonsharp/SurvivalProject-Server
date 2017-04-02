package login;
import java.sql.SQLException;

import login.handlers.GetChannelUsersPercentageHandler;
import login.handlers.GuildMarkHandler;
import login.handlers.LoginCredentialsHandler;
import login.handlers.ReconnectHandler;
import login.handlers.ServerInfoHandler;
import login.handlers.SetMainCharacterHandler;
import login.handlers.TutorialCompletedHandler;
import net.GenericTCPServer;
import net.Messages;
import net.UserTCPSession;
import net.handlers.GenericHandler;

public class LoginServer extends GenericTCPServer {
	public LoginServer(int port, int initialCapacity) {
		super("Login server", port, initialCapacity);
	}
	
	@Override
	public GenericHandler processPacket(UserTCPSession userSession, int messageID, byte[] messageBytes) {
		GenericHandler message = null;
		
		switch (messageID) {
		case Messages.LOGIN_CREDENTIALS_REQUEST:
			message = new LoginCredentialsHandler(userSession, messageBytes);
			break;
		case Messages.SERVERS_INFO_REQUEST:
			message = new ServerInfoHandler(userSession, messageBytes);
			break;
		case Messages.SET_MAIN_CHARACTER_REQUEST:
			message = new SetMainCharacterHandler(userSession, messageBytes);
			break;
		case Messages.RECONNECT_REQUEST:
			message = new ReconnectHandler(userSession, messageBytes);
			break;
		case Messages.TUTORIAL_REQUEST:
			message = new TutorialCompletedHandler(userSession, messageBytes);
			break;
		case Messages.GET_CHANNEL_USERS_PERCENTAGE_REQUEST:
			message = new GetChannelUsersPercentageHandler(userSession, messageBytes);
			break;
		case Messages.LOGIN_GUILD_MARK_REQUEST:
			message = new GuildMarkHandler(userSession, messageBytes);
			break;
		default:
//			HexTools.printHexArray(messageBytes, 20, false);
//			System.out.println();
//			HexTools.printHexArray(messageBytes, 20, true);
		}
		
		return message;
	}

	@Override
	public void onUserDisconnect(UserTCPSession userTCPSession) throws SQLException {
		userTCPSession.getUser().saveUser();
	}
}
