package login;
import login.handlers.GetChannelUsersPercentageHandler;
import login.handlers.GuildMarkHandler;
import login.handlers.LoginHandler;
import login.handlers.ReconnectHandler;
import login.handlers.ServerInfoHandler;
import login.handlers.SetActiveCharacterHandler;
import login.handlers.TutorialCompletedHandler;
import net.GenericHandler;
import net.GenericTCPServer;
import net.UserTCPSession;

public class LoginServer extends GenericTCPServer {
	public LoginServer(int port) {
		super("Login server", port);
	}
	
	@Override
	public GenericHandler processPacket(UserTCPSession tcpServer, int messageID, byte[] messageBytes) {
		GenericHandler message = null;
		
		switch (messageID) {
		case LoginHandler.REQUEST_ID:
			message = new LoginHandler(tcpServer, messageBytes);
			break;
		case ServerInfoHandler.REQUEST_ID:
			message = new ServerInfoHandler(tcpServer, messageBytes);
			break;
		case SetActiveCharacterHandler.REQUEST_ID:
			message = new SetActiveCharacterHandler(tcpServer, messageBytes);
			break;
		case ReconnectHandler.REQUEST_ID:
			message = new ReconnectHandler(tcpServer, messageBytes);
			break;
		case TutorialCompletedHandler.REQUEST_ID:
			message = new TutorialCompletedHandler(tcpServer, messageBytes);
			break;
		case GetChannelUsersPercentageHandler.REQUEST_ID:
			message = new GetChannelUsersPercentageHandler(tcpServer, messageBytes);
			break;
		case GuildMarkHandler.REQUEST_ID:
			message = new GuildMarkHandler(tcpServer, messageBytes);
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
