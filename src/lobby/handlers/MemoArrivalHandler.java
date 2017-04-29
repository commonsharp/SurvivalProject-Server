package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import net.objects.Memo;
import tools.ExtendedByteBuffer;

public class MemoArrivalHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0xA8;
	
	public MemoArrivalHandler(LobbyServer lobbyServer, UserSession userSession) {
		super(lobbyServer, userSession);
	}

	@Override
	public void interpretBytes() {
		
	}

	@Override
	public void processMessage() throws SQLException {
		
	}

	@Override
	public byte[] getResponse() throws SQLException {
		return null;
	}
	
	public byte[] getResponse(Memo memo) {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.MEMO_ARRIVAL_RESPONSE);
		
		output.putString(0x14, memo.getFromUsername());
		output.putInt(0x24, memo.getMessageType());
		output.putInt(0x28, memo.getLevelAndGender());
		output.putInt(0x2C, memo.getUnknown2());
		output.putString(0x30, memo.getText());
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		
	}
}
