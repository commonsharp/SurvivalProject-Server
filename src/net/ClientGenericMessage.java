package net;

import tools.input.ExtendedByteBuffer;

public abstract class ClientGenericMessage extends GenericMessage {
	protected ExtendedByteBuffer buffer;
	
	public abstract void interpretBytes(byte[] messageBytes);
	public abstract void processFields();
	public abstract byte[] getResponse();
	
	public ClientGenericMessage(byte[] messageBytes) {
		buffer = new ExtendedByteBuffer(messageBytes);
		
		length = buffer.readInt();
		messageID = buffer.readInt();
		unknown1 = buffer.readInt();
		checksum = buffer.readInt();
		state = buffer.readInt();
		
		interpretBytes(messageBytes);
		processFields();
	}
	
	public ExtendedByteBuffer getBuffer() {
		return buffer;
	}
}
