package game.handlers;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.SQLException;

import game.GameHandler;
import game.GameServer;
import net.Messages;
import tools.ExtendedByteBuffer;

public class JoinServerHandler extends GameHandler {
	public static final int RESPONSE_LENGTH = 0x14;

	InetAddress ipAddress;
	int port;
	
	public JoinServerHandler(GameServer gameServer, byte[] messageBytes, InetAddress ipAddress, int port) {
		super(gameServer, messageBytes);
		this.ipAddress = ipAddress;
		this.port = port;
	}

	@Override
	public void interpretBytes() {
		
	}

	@Override
	public void afterSend() throws IOException, SQLException {
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.GAME_JOIN_LOBBY);
		
		return output.toArray();
	}

	@Override
	public void processMessage() {
		// TODO Auto-generated method stub
		
	}

}