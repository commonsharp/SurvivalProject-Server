package net;

import java.io.IOException;
import java.sql.SQLException;

public class IsAliveTimer implements Runnable {
	GenericTCPServer server;
	
	public IsAliveTimer(GenericTCPServer server) {
		this.server = server;
	}
	
	@Override
	public void run() {
		try {
			for (UserSession userSession : server.userSessions) {
				if (System.currentTimeMillis() - userSession.timeSinceLastActivity > 10000) {
					server.userSessions.remove(this);
					server.onUserDisconnect(userSession);
					System.out.println("User disconnected");
				}
			}
			
			Thread.sleep(8000);
		} catch (SQLException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
