package net;

import tools.output.ExtendedByteOutput;

public abstract class ServerGenericMessage extends GenericMessage {
	protected ExtendedByteOutput buffer;
	
	public abstract void changeData();
	public abstract void addPayload();
	
	public ServerGenericMessage(int length, int messageID) {
		buffer = new ExtendedByteOutput(length);
		
		buffer.putInt(0x0, length);
		buffer.putInt(0x4, messageID);
		buffer.putInt(0x8, 11036);
		this.messageID = messageID;
	}
	
	public byte[] getResponse() {
		changeData();
		addPayload();
		
		return buffer.toArray();
	}
}