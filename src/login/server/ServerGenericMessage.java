package login.server;

import net.MD5;
import tools.HexTools;
import tools.output.ExtendedByteOutput;

public abstract class ServerGenericMessage {
	protected int messageID;
	
	protected ExtendedByteOutput payload;
	
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
		HexTools.putIntegerInByteArray(response, 8, 11036); // TODO change? it's fixed...

		payload.getBytes(response, 20);
		
		return response;
	}
	
	public abstract void changeData();
	public abstract void addPayload();
}