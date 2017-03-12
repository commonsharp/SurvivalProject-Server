package login.server;

import tools.HexTools;
import tools.output.ExtendedByteOutput;

public abstract class ServerGenericMessage {
	protected int messageID;
	protected int unknown1;
	protected int state;
	
	protected ExtendedByteOutput payload;
	
	public ServerGenericMessage(int messageID, int unknown1, int state) {
		payload = new ExtendedByteOutput();
		this.messageID = messageID;
		this.unknown1 = unknown1;
		this.state = state;
		addPayload();
	}
	
	public byte[] getResponse() {
		int length = 20 + payload.getSize();
		
		byte[] response = new byte[length];
		
		HexTools.putIntegerInByteArray(response, 0, length);
		HexTools.putIntegerInByteArray(response, 4, messageID);
		HexTools.putIntegerInByteArray(response, 8, 11036); // TODO change
		HexTools.putIntegerInByteArray(response, 16, state);
		payload.getBytes(response, 20);
		
		return response;
	}
	
	public abstract void addPayload();
}