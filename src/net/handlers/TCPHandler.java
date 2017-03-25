package net.handlers;

import java.io.IOException;

import net.UserTCPSession;

public abstract class TCPHandler extends GenericHandler {
	protected UserTCPSession userSession;
	
	public TCPHandler(UserTCPSession userSession) {
		super();
		this.userSession = userSession;
	}
	
	public TCPHandler(UserTCPSession userSession, byte[] messageBytes) {
		super(messageBytes);
		this.userSession = userSession;
	}
	
	public void sendTCPMessage(byte[] messageBytes) throws IOException {
		userSession.sendMessage(messageBytes);
	}
}