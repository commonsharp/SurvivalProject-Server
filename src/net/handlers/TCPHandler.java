package net.handlers;

import java.io.IOException;

import net.UserSession;

public abstract class TCPHandler extends GenericHandler {
	protected UserSession userSession;
	
	public TCPHandler(UserSession userSession) {
		super();
		this.userSession = userSession;
	}
	
	public TCPHandler(UserSession userSession, byte[] messageBytes) {
		super(messageBytes);
		this.userSession = userSession;
	}
	
	public void sendTCPMessage(byte[] messageBytes) throws IOException {
		userSession.sendMessage(messageBytes);
	}
}