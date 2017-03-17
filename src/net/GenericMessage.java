package net;

import tools.ExtendedByteBuffer;
import tools.HexTools;

public abstract class GenericMessage {
	protected int length;
	protected int messageID;
	protected int unknown1;
	protected int checksum;
	protected int state;
	private byte[] messageBytes;
	
	protected ExtendedByteBuffer inputBuffer;
	protected ExtendedByteBuffer outputBuffer;
	
	public abstract void interpretBytes();
	public abstract void processFields();
	
	public abstract void changeData();
	public abstract void addPayload();
	
	public GenericMessage(byte[] messageBytes, int responseLength, int responseMessageID) {
		this.messageBytes = messageBytes;
		inputBuffer = new ExtendedByteBuffer(messageBytes);
		
		length = inputBuffer.getInt(0x0);
		messageID = inputBuffer.getInt(0x4);
		unknown1 = inputBuffer.getInt(0x8);
		checksum = inputBuffer.getInt(0xC);
		state = inputBuffer.getInt(0x10);
		
		interpretBytes();
		processFields();
		
		outputBuffer = new ExtendedByteBuffer(responseLength);
		
		outputBuffer.putInt(0x0, responseLength);
		outputBuffer.putInt(0x4, responseMessageID);
		outputBuffer.putInt(0x8, 11036);
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
		
		return outputBuffer.toArray();
	}
	
	public void printMessage() {
		HexTools.printHexArray(messageBytes, false);
	}
}
