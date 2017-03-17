package net;

import tools.output.ExtendedByteOutput;

public abstract class GenericMessage {
	protected int length;
	protected int messageID;
	protected int unknown1;
	protected int checksum;
	protected int state;
	
	protected ExtendedByteOutput inputBuffer;
	protected ExtendedByteOutput outputBuffer;
	
	public abstract void interpretBytes(byte[] messageBytes);
	public abstract void processFields();
	
	public abstract void changeData();
	public abstract void addPayload();
	
	public GenericMessage(byte[] messageBytes, int responseLength, int responseMessageID) {
		inputBuffer = new ExtendedByteOutput(messageBytes);
		
		length = inputBuffer.getInt(0x0);
		messageID = inputBuffer.getInt(0x4);
		unknown1 = inputBuffer.getInt(0x8);
		checksum = inputBuffer.getInt(0xC);
		state = inputBuffer.getInt(0x10);
		
		interpretBytes(messageBytes);
		processFields();
		
		outputBuffer = new ExtendedByteOutput(responseLength);
		
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
}
