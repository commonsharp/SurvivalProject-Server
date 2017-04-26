package net.handlers;

import java.io.IOException;
import java.sql.SQLException;

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
	
	public abstract void interpretBytes();
	public abstract void processMessage() throws SQLException, IOException;
	public abstract byte[] getResponse() throws SQLException;
	public abstract void afterSend() throws IOException, SQLException;
	
	public GenericHandler() {
		
	}
	
	public GenericHandler(byte[] messageBytes) {
		this.messageBytes = messageBytes;
		input = new ExtendedByteBuffer(messageBytes);
		
		length = input.getInt(0x0);
		messageID = input.getInt(0x4);
		unknown1 = input.getInt(0x8);
		checksum = input.getInt(0xC);
		state = input.getInt(0x10);
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
}
