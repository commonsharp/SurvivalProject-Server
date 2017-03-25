package login;

import net.UserTCPSession;
import net.handlers.TCPHandler;

public abstract class LoginHandler extends TCPHandler {
	public LoginHandler(UserTCPSession userSession, byte[] messageBytes) {
		super(userSession, messageBytes);
	}
}