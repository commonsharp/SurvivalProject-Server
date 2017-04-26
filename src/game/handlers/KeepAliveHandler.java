package game.handlers;

import java.io.IOException;
import java.sql.SQLException;

import game.GameHandler;
import game.GameServer;
import net.Messages;
import tools.ExtendedByteBuffer;

public class KeepAliveHandler extends GameHandler {
	public static final int RESPONSE_LENGTH = 0x40;
	
	protected String username;
	
	public KeepAliveHandler(GameServer gameServer, byte[] messageBytes) {
		super(gameServer, messageBytes);
	}

	@Override
	public void interpretBytes() {
		username = input.getString(0x14);
		// 0x14 - username
		// 0x24 - server time
		// 0x28 - interval time (time between this message and the last keep alive message. Around 9000 ms)
	}

	@Override
	public void processMessage() throws SQLException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getResponse() throws SQLException {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.GAME_KEEP_ALIVE);
		output.putString(0x14, username);
		output.putInt(0x24, (int) (System.currentTimeMillis() - gameServer.serverStartTime));
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
