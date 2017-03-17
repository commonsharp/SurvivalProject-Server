package game.handlers;

import net.GenericHandler;
import net.GenericUDPServer;

public class JoinServerHandler extends GenericHandler {
	public static final int REQUEST_ID = 0x1101;
	public static final int RESPONSE_ID = 0x1101;
	public static final int RESPONSE_LENGTH = 0x14;

	public JoinServerHandler(GenericUDPServer udpServer, byte[] messageBytes) {
		super(udpServer, messageBytes, RESPONSE_LENGTH, RESPONSE_ID);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void interpretBytes() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processFields() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPayload() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterSend() {
		// TODO Auto-generated method stub
		
	}

}