package login;
import java.sql.SQLException;

import login.handlers.GetChannelActivityHandler;
import login.handlers.GuildMarkHandler;
import login.handlers.LoginCredentialsHandler;
import login.handlers.ReconnectHandler;
import login.handlers.ServersInfoHandler;
import login.handlers.SetMainCharacterHandler;
import login.handlers.TutorialCompletedHandler;
import net.GenericTCPServer;
import net.Messages;
import net.UserSession;
import net.handlers.GenericHandler;

public class LoginServer extends GenericTCPServer {
	public LoginServer(int port, int initialCapacity) {
		super("Login server", port, initialCapacity);
	}
	
	@Override
	public GenericHandler processPacket(UserSession userSession, int messageID, byte[] messageBytes) {
		GenericHandler message = null;
		
		switch (messageID) {
		case Messages.LOGIN_CREDENTIALS_REQUEST:
			message = new LoginCredentialsHandler(userSession, messageBytes);
			break;
		case Messages.SERVERS_INFO_REQUEST:
			message = new ServersInfoHandler(userSession, messageBytes);
			break;
		case Messages.SET_MAIN_CHARACTER_REQUEST:
			message = new SetMainCharacterHandler(userSession, messageBytes);
			break;
		case Messages.RECONNECT_REQUEST:
			message = new ReconnectHandler(userSession, messageBytes);
			break;
		case Messages.TUTORIAL_COMPLETED_REQUEST:
			message = new TutorialCompletedHandler(userSession, messageBytes);
			break;
		case Messages.GET_CHANNEL_USERS_PERCENTAGE_REQUEST:
			message = new GetChannelActivityHandler(userSession, messageBytes);
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
	public void onUserDisconnect(UserSession userSession) throws SQLException {
	}
}
