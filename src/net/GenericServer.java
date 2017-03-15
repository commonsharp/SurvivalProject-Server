package net;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import login.client.ClientGenericMessage;

public abstract class GenericServer {
	protected int port;
	protected boolean isRunning = true;
	
	protected ArrayList<UserSession> usersSessions;
	
	public abstract ClientGenericMessage processPacket(int messageID, byte[] messageBytes);
	
	public GenericServer(int port) {
		this.port = port;
		
		usersSessions = new ArrayList<UserSession>();
	}
	
	public void startServer() throws IOException {
		ServerSocket server = new ServerSocket(port);
		UserSession currentSession;
		
		while (isRunning) {
			currentSession = new UserSession(this, server.accept());
			usersSessions.add(currentSession);
			new Thread(currentSession).start();
		}
		
		server.close();
	}
}
