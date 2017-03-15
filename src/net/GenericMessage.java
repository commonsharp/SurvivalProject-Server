package net;

public class GenericMessage {
	protected int length;
	protected int messageID;
	protected int unknown1;
	protected int checksum;
	protected int state;
	
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
}
