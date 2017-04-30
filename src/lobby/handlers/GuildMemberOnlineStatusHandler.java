package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class GuildMemberOnlineStatusHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x2E;
	public GuildMemberOnlineStatusHandler(LobbyServer lobbyServer, UserSession userSession) {
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
		return null;
	}
	
	public byte[] getResponse(UserSession userSession, boolean isConnected) {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.GUILD_MEMBER_ONLINE_STATE_HANDLER);
		
		output.putString(0x14, userSession.getUser().getUsername());
		output.putInt(0x24, userSession.getUser().getPlayerLevel());
		
		if (userSession.getUser().isInRoom()) {
			output.putInt(0x28, userSession.getUser().getRoomIndex() + 1);
		}
		
		output.putBoolean(0x2C, userSession.getUser().isMale());
		output.putBoolean(0x2D, isConnected);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
