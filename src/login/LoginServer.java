package login;
import login.client.messages.LoginRequest;
import login.client.messages.ServerInfoRequest;
import login.client.messages.SetActiveCharacterRequest;
import login.client.messages.TutorialCompletedNotification;
import net.ClientGenericMessage;
import net.GenericTCPServer;
import tools.HexTools;

public class LoginServer extends GenericTCPServer {
	public LoginServer(int port) {
		super("Login server", port);
	}
	
	@Override
	public ClientGenericMessage processPacket(int messageID, byte[] messageBytes) {
		ClientGenericMessage message = null;
		
		switch (messageID) {
		case 0x2707:
			message = new LoginRequest(messageBytes);
			break;
		case 0x2907:
			message = new ServerInfoRequest(messageBytes);
			break;
		case 0x2911:
			message = new SetActiveCharacterRequest(messageBytes);
			break;
		case 0x2915:
			message = new TutorialCompletedNotification(messageBytes);
			break;
//		case 0x2917:
//			message = new GuildMarkRequest(messageBytes);
//			break;
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
