package login;
import login.handlers.GetChannelUsersPercentageHandler;
import login.handlers.GuildMarkHandler;
import login.handlers.LoginHandler;
import login.handlers.ServerInfoHandler;
import login.handlers.SetActiveCharacterHandler;
import login.handlers.TutorialCompletedHandler;
import net.GenericMessage;
import net.GenericTCPServer;

public class LoginServer extends GenericTCPServer {
	public LoginServer(int port) {
		super("Login server", port);
	}
	
	@Override
	public GenericMessage processPacket(int messageID, byte[] messageBytes) {
		GenericMessage message = null;
		
		switch (messageID) {
		case LoginHandler.REQUEST_ID:
			message = new LoginHandler(messageBytes);
			break;
		case ServerInfoHandler.REQUEST_ID:
			message = new ServerInfoHandler(messageBytes);
			break;
		case SetActiveCharacterHandler.REQUEST_ID:
			message = new SetActiveCharacterHandler(messageBytes);
			break;
		case TutorialCompletedHandler.REQUEST_ID:
			message = new TutorialCompletedHandler(messageBytes);
			break;
		case GetChannelUsersPercentageHandler.REQUEST_ID:
			message = new GetChannelUsersPercentageHandler(messageBytes);
			break;
		case GuildMarkHandler.REQUEST_ID:
			message = new GuildMarkHandler(messageBytes);
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
