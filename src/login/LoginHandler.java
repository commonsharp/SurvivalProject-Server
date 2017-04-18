package login;

import net.UserSession;
import net.handlers.TCPHandler;

public abstract class LoginHandler extends TCPHandler {
	public LoginHandler(UserSession userSession, byte[] messageBytes) {
		super(userSession, messageBytes);
	}
}