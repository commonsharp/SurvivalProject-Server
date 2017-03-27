package login;
import login.handlers.GetChannelUsersPercentageHandler;
import login.handlers.GuildMarkHandler;
import login.handlers.LoginCredentialsHandler;
import login.handlers.ReconnectHandler;
import login.handlers.ServerInfoHandler;
import login.handlers.SetActiveCharacterHandler;
import login.handlers.TutorialCompletedHandler;
import net.GenericTCPServer;
import net.Messages;
import net.UserTCPSession;
import net.handlers.GenericHandler;

public class LoginServer extends GenericTCPServer {
	public LoginServer(int port) {
		super("Login server", port);
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
			message = new SetActiveCharacterHandler(userSession, messageBytes);
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
	
	//					Server responses... X_X
	//					case 0x2807:
	//						break;
	//					case 0x2908:
	//						break;
	//					case 0x2912:
	//						break;
	//					case 0x2916:
	//						break;
	//					case 0x2918:
	//						break;
	//					case 0x2919:
	//						break;
	//					case 0x2922:
	//						break;
	//					case 0x2923:
	//				        break;
	//				    case 0x2924:
	//				    	break;
	//				    case 0x2925:
	//				    	break;
}
