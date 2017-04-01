package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class GuildMemberOnlineStatusHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x2E;
	public GuildMemberOnlineStatusHandler(LobbyServer lobbyServer, UserTCPSession userSession) {
		super(lobbyServer, userSession);
	}

	@Override
	public void interpretBytes() {
		
	}

	@Override
	public void processMessage() throws SQLException {
		
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.GUILD_MEMBER_ONLINE_STATE_HANDLER);
		
		output.putString(0x14, "obama");
		output.putInt(0x24, 10); // level
		output.putInt(0x28, 30); // rank in guild
		output.putByte(0x2C, (byte) 1); // gender
		output.putByte(0x2D, (byte) 1); // this one is probably "isConnected"
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
