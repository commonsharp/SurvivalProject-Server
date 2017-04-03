package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class BuyElementHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x30;
	
	protected int elementType;
	protected int amount;
	
	public BuyElementHandler(LobbyServer lobbyServer, UserTCPSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		elementType = input.getInt(0x14) % 6000;
		amount = input.getInt(0x18);
	}

	@Override
	public void processMessage() throws SQLException {
		userSession.getUser().whiteCards[elementType] += amount;
		userSession.getUser().saveUser();
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.BUY_ELEMENTS_RESPONSE);
		/*
		 * 1 - Item buying complete
		 * 2 - An error  has occurred. &#xA;Please inquire the support team.
		 * 8 - Not enough Code
		 */
		output.putInt(0x14, 1); // response
		output.putLong(0x18, userSession.getUser().playerCode);
		output.putInts(0x20, userSession.getUser().whiteCards);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

}