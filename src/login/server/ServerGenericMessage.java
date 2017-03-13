package login.server;

import net.MD5;
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
		int State = -1;
		int state2 = Math.abs(((State = (~State + 0x14fb) * 0x1f) >> 16) ^ State);
		HexTools.putIntegerInByteArray(response, 16, state2);
		payload.getBytes(response, 20);
		
		HexTools.putIntegerInByteArray(response, 12, MD5.getDigest(response));
		
		return response;
	}
	
	public abstract void addPayload();
}