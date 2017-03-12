package login.client;

import tools.input.ExtendedByteBuffer;

public abstract class ClientGenericMessage {
	protected int length;
	protected int messageID;
	protected int unknown1;
	protected int checksum;
	protected int state;
	
	protected ExtendedByteBuffer buffer;
	
	public ClientGenericMessage(byte[] messageBytes) {
		buffer = new ExtendedByteBuffer(messageBytes);
		
		length = buffer.readInt();
		messageID = buffer.readInt();
		unknown1 = buffer.readInt();
		checksum = buffer.readInt();
		state = buffer.readInt();
		
		interpretBytes(messageBytes);
		processFields();
		respond();
	}
	
	public abstract void interpretBytes(byte[] messageBytes);
	public abstract void processFields();
	public abstract void respond();
}
