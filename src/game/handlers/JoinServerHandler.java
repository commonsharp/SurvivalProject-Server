package game.handlers;

import game.GameHandler;
import net.GenericUDPServer;
import tools.ExtendedByteBuffer;

public class JoinServerHandler extends GameHandler {
	public static final int REQUEST_ID = 0x1101;
	public static final int RESPONSE_ID = 0x1101;
	public static final int RESPONSE_LENGTH = 0x14;

	public JoinServerHandler(GenericUDPServer udpServer, byte[] messageBytes) {
		super(udpServer, messageBytes);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void interpretBytes() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterSend() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, RESPONSE_ID);
		
		return output.toArray();
	}

	@Override
	public void processMessage() {
		// TODO Auto-generated method stub
		
	}

}