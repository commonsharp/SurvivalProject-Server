package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

// This one sends every single message/announcement you can think of to the client
public class SendAnyMessageHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x1C;
	
	int requestType;
	
	public SendAnyMessageHandler(LobbyServer lobbyServer, UserSession userSession) {
		super(lobbyServer, userSession);
	}

	public SendAnyMessageHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}
	
	@Override
	public void interpretBytes() {
		requestType = input.getInt(0x14);
	}

	@Override
	public void processMessage() throws SQLException {
		
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.SEND_ANY_MESSAGE_RESPONSE);
		output.putInt(0x14, requestType); // message index
		output.putInt(0x18, 0); // parameter?
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
