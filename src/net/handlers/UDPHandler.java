package net.handlers;

import net.GenericUDPServer;

public abstract class UDPHandler extends GenericHandler {
	protected GenericUDPServer udpServer;
	
	// Will be used in "fake" messages.
	public UDPHandler(GenericUDPServer udpServer) {
		super();
		this.udpServer = udpServer;
	}
	
	public UDPHandler(GenericUDPServer udpServer, byte[] messageBytes) {
		super(messageBytes);
		this.udpServer = udpServer;
	}
}
