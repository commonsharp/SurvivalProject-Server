package net;

import tools.HexTools;
import tools.output.ExtendedByteOutput;

public abstract class ServerGenericMessage extends GenericMessage {
	protected ExtendedByteOutput payload;
	
	public abstract void changeData();
	public abstract void addPayload();
	
	public ServerGenericMessage(int messageID) {
		payload = new ExtendedByteOutput();
		this.messageID = messageID;
	}
	
	public byte[] getResponse() {
		changeData();
		addPayload();
		
		int length = 20 + payload.getSize();
		
		byte[] response = new byte[length];
		
		HexTools.putIntegerInByteArray(response, 0, length);
		HexTools.putIntegerInByteArray(response, 4, messageID);
		HexTools.putIntegerInByteArray(response, 8, 11036); // This is fixed.

		payload.getBytes(response, 20);
		
		return response;
	}
}