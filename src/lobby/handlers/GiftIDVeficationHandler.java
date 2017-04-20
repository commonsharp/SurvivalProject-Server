package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class GiftIDVeficationHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0xF0;
	
	int stringCompare;
	
	public GiftIDVeficationHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		stringCompare = input.getInt(0x14); // 0 when the ID verification is good
	}

	@Override
	public void processMessage() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getResponse() throws SQLException {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.GIFT_ID_VERIFY_RESPONSE);
		
		/*
		 * 1 - okay
		 * 2 - You cannot send the gift &#xA;because the ID you entered&#xA;is wrong.
		 */
		int response = stringCompare == 0 ? 1 : 2;
		output.putInt(0x14, response);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
