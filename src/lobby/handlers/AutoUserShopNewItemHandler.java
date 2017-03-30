package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserTCPSession;
import net.objects.User;
import tools.ExtendedByteBuffer;

public class AutoUserShopNewItemHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x20;
	
	protected int itemType; // 1 for items. 2 for elements. nothing else.
	protected int itemIndex;
	protected int code;
	
	public AutoUserShopNewItemHandler(LobbyServer lobbyServer, UserTCPSession userSession) {
		super(lobbyServer, userSession);
	}
	
	public AutoUserShopNewItemHandler(LobbyServer lobbyServer, UserTCPSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		/*
			14 - always 1?
			18 - type. 1 for items. 2 for elements. nothing else.
			1C - item index (NOT ID!)
			20 - code
			24 - always 0?
			
			when selling elements, 1C is as follows:
			water elements - 10000 + amount
			fire elements - 20000 + amount
			earth elements - 30000 + amount
			wind elements - 40000 + amount
		 */
		
		itemType = input.getInt(0x18);
		itemIndex = input.getInt(0x1C);
		code = input.getInt(0x20);
		
		System.out.println(code);
		System.out.println(input.getInt(0x14));
		System.out.println(input.getInt(0x24));
	}

	@Override
	public void processMessage() {
		User user = userSession.getUser();
		if (itemType == 1) {
			lobbyServer.addShop(user.username, user.cards[itemIndex], 0, 0, code);
		}
		// itemType = 2. elements
		else {
			lobbyServer.addShop(user.username, null, itemIndex / 10000, itemIndex % 10000, code);
		}
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.AUTO_USER_SHOP_NEW_ITEM_RESPONSE);
		
		 // 3 - server error. 4 - okay? 5 - the item is already sold and cannot be deleted?
		output.putInt(0x14, 4);
		output.putInt(0x18, itemType);
		output.putInt(0x1C, itemIndex);
		return output.toArray();
	}
	
	@Override
	public void afterSend() throws IOException {
		
	}
}
