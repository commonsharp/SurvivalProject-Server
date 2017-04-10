package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class SellCardHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x30;
	
	protected int cardIndex;
	
	public SellCardHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		cardIndex = input.getInt(0x14);
	}

	@Override
	public void processMessage() throws SQLException {
		userSession.getUser().cards[cardIndex] = null;
		userSession.getUser().saveUser();
	}

	@Override
	public byte[] getResponse() throws SQLException {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.SELL_CARD_RESPONSE);
		output.putInt(0x14, 1); // must be 1. other values result in an assert fail
		output.putInt(0x18, cardIndex);
		output.putInt(0x1C, 0); // not sure if this should be here. probably NOT.
		output.putLong(0x20, userSession.getUser().playerCode);
		output.putLong(0x28, userSession.getUser().avatarMoney);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
