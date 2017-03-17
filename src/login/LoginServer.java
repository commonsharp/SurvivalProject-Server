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
		case 0x2707:
			message = new LoginHandler(messageBytes);
			break;
		case 0x2907:
			message = new ServerInfoHandler(messageBytes);
			break;
		case 0x2911:
			message = new SetActiveCharacterHandler(messageBytes);
			break;
		case 0x2915:
			message = new TutorialCompletedHandler(messageBytes);
			break;
		case 0x2917:
			message = new GetChannelUsersPercentageHandler(messageBytes);
			break;
		case 0x2921:
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
