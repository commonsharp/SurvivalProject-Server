package net;

import java.io.IOException;

import tools.ExtendedByteBuffer;
import tools.HexTools;

public abstract class GenericHandler {
	protected int length;
	protected int messageID;
	protected int unknown1;
	protected int checksum;
	protected int state;
	private byte[] messageBytes;
	
	protected ExtendedByteBuffer input;
	
	protected UserTCPSession tcpServer;
	protected GenericUDPServer udpServer;
	
	public abstract void interpretBytes();
	public abstract byte[] getResponse();
	
	public abstract void afterSend() throws IOException;
	
	// Will be used in "fake" messages.
	public GenericHandler(UserTCPSession tcpServer) {
		this.tcpServer = tcpServer;
	}
	
	public GenericHandler(GenericUDPServer udpServer, byte[] messageBytes) {
		this(null, udpServer, messageBytes);
	}
	
	public GenericHandler(UserTCPSession tcpServer, byte[] messageBytes) {
		this(tcpServer, null, messageBytes);
	}
	
	public GenericHandler(UserTCPSession tcpServer, GenericUDPServer udpServer, byte[] messageBytes) {
		this.tcpServer = tcpServer;
		this.udpServer = udpServer;
		
		this.messageBytes = messageBytes;
		input = new ExtendedByteBuffer(messageBytes);
		
		length = input.getInt(0x0);
		messageID = input.getInt(0x4);
		unknown1 = input.getInt(0x8);
		checksum = input.getInt(0xC);
		state = input.getInt(0x10);
		
		interpretBytes();
	}
	
	public int getLength() {
		return length;
	}

	public int getMessageID() {
		return messageID;
	}

	public int getUnknown1() {
		return unknown1;
	}

	public int getChecksum() {
		return checksum;
	}

	public int getState() {
		return state;
	}
	
	public void printMessage() {
		HexTools.printHexArray(messageBytes, 0x14, false);
	}
	
	public void sendTCPMessage(byte[] messageBytes) throws IOException {
		tcpServer.sendMessage(messageBytes);
	}
}
