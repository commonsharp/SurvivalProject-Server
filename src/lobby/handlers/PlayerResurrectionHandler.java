package lobby.handlers;

import java.io.IOException;

import net.GenericHandler;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class PlayerResurrectionHandler extends GenericHandler {
	public static final int REQUEST_ID = 0x4333;
	public static final int RESPONSE_ID = 0x4334;
	public static final int RESPONSE_LENGTH = 0x20;
	
	protected int slot;
	protected int x, y;
	
	public PlayerResurrectionHandler(UserTCPSession userSession) {
		super(userSession);
	}
	
	public PlayerResurrectionHandler(UserTCPSession userSession, byte[] messageBytes) {
		super(userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		slot = input.getInt(0x14); // maybe
		x = input.getInt(0x18);
		y = input.getInt(0x1C);
	}

	@Override
	public byte[] getResponse() {
		return getResponse(slot, x, y);
	}
	
	public byte[] getResponse(int slot, int x, int y) {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, RESPONSE_ID);
		output.putInt(0x14, slot);
		output.putInt(0x18, x);
		output.putInt(0x1C, y);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
		
	}
}
