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
	protected ExtendedByteBuffer output;
	
	protected UserTCPSession tcpServer;
	protected GenericUDPServer udpServer;
	
	public abstract void interpretBytes();
	public abstract void processFields();
	
	public abstract void changeData();
	public abstract void addPayload();
	
	public abstract void afterSend() throws IOException;
	
	public GenericHandler(GenericUDPServer udpServer, byte[] messageBytes, int responseLength, int responseMessageID) {
		this(null, udpServer, messageBytes, responseLength, responseMessageID);
	}
	
	public GenericHandler(UserTCPSession tcpServer, byte[] messageBytes, int responseLength, int responseMessageID) {
		this(tcpServer, null, messageBytes, responseLength, responseMessageID);
	}
	
	public GenericHandler(UserTCPSession tcpServer, GenericUDPServer udpServer, byte[] messageBytes, int responseLength, int responseMessageID) {
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
		processFields();
		
		output = new ExtendedByteBuffer(responseLength);
		
		output.putInt(0x0, responseLength);
		output.putInt(0x4, responseMessageID);
		output.putInt(0x8, 11036);
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
	
	public byte[] getResponse() {
		changeData();
		addPayload();
		
		if (output == null) {
			return null;
		}
		
		return output.toArray();
	}
	
	public void printMessage() {
		HexTools.printHexArray(messageBytes, false);
	}
	
	public void sendTCPMessage(byte[] messageBytes) throws IOException {
		tcpServer.sendMessage(messageBytes);
	}
}
