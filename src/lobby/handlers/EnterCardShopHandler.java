package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class EnterCardShopHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x1C;
	
	public EnterCardShopHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void interpretBytes() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processMessage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.ENTER_CARD_SHOP_RESPONSE);
		output.putLong(0x14, userSession.getUser().cash); // cash
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
