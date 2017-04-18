package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class UserShopOpenHandler extends LobbyHandler {
	// THERE ARE MORE FIELDS IN THIS MESSAGE!!!
	public static final int RESPONSE_LENGTH = 0x2E;
	
	public UserShopOpenHandler(LobbyServer lobbyServer, UserSession userSession) {
		super(lobbyServer, userSession);
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
		return null;
	}
	
	public byte[] getResponse(String username, String shopName) {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.USER_SHOP_OPEN_RESPONSE);
		output.putString(0x14, username);
		output.putString(0x21, shopName);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
