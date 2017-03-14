package login.server;

import net.MD5;
import tools.HexTools;
import tools.output.ExtendedByteOutput;

public abstract class ServerGenericMessage {
	protected int messageID;
	protected int state;
	
	protected ExtendedByteOutput payload;
	
	public ServerGenericMessage(int messageID, int state) {
		payload = new ExtendedByteOutput();
		this.messageID = messageID;
		this.state = state;
		changeData();
	}
	
	public byte[] getResponse() {
		addPayload();
		
		int length = 20 + payload.getSize();
		
		byte[] response = new byte[length];
		
		HexTools.putIntegerInByteArray(response, 0, length);
		HexTools.putIntegerInByteArray(response, 4, messageID);
		HexTools.putIntegerInByteArray(response, 8, 11036); // TODO change? it's fixed...
		int oldState = -1;
		int newState = newState(oldState);
		
//		int state2 = Math.abs(((State = (~State + 0x14fb) * 0x1f) >> 16) ^ State);
		HexTools.putIntegerInByteArray(response, 16, newState);
		payload.getBytes(response, 20);
		
		HexTools.putIntegerInByteArray(response, 12, MD5.getDigest(response));
		
		return response;
	}
	
	public int newState(int oldState) {
		return -1;
//		return 31 * (~oldState) ^ (31 * (~oldState) >> 16);
	}
	
	public abstract void changeData();
	public abstract void addPayload();
}