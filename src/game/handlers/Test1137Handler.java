package game.handlers;

import java.io.IOException;
import java.sql.SQLException;

import game.GameHandler;
import game.GameServer;
import tools.ExtendedByteBuffer;

public class Test1137Handler extends GameHandler {
	public static final int RESPONSE_LENGTH = 0x14;
	
	public Test1137Handler(GameServer gameServer) {
		super(gameServer);
	}

	@Override
	public void interpretBytes() {
		
	}

	@Override
	public void processMessage() throws SQLException {
		
	}

	@Override
	public byte[] getResponse() throws SQLException {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, 0x1137);
		
		return output.toArray();
	}
	
	@Override
	public void afterSend() throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
