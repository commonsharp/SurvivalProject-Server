package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class UserShopActionHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x20;
	
	protected int requestType;
	protected String username;
	protected String shopName;
	
	byte[] bytes;
	
	public UserShopActionHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
		this.bytes = messageBytes;
	}

	@Override
	public void interpretBytes() {
		requestType = input.getInt(0x4C); // Action type. 0~7. 4 - create a new shop
		username = input.getString(0x14);
		shopName = input.getString(0x2E);
		
		System.out.println("User: " + input.getString(0x14));
		System.out.println("User again: " + input.getString(0x21));
		System.out.println("Shop name: " + input.getString(0x2E));
		System.out.println(input.getInt(0x4C));
		System.out.println(input.getInt(0x50));
		System.out.println("Item index 1: " + input.getInt(0x54));
		System.out.println("Item index 2: " + input.getInt(0x58));
		System.out.println("Item index 3: " + input.getInt(0x5C));
		System.out.println("Item index 4: " + input.getInt(0x60));
		System.out.println(input.getInt(0x64));
		System.out.println(input.getInt(0x68));
		System.out.println(input.getInt(0x6C));
		System.out.println(input.getInt(0x70));
		System.out.println(input.getInt(0x74));
		System.out.println(input.getInt(0x78));
		System.out.println(input.getInt(0x7C));
		System.out.println(input.getInt(0x80));
		System.out.println(input.getInt(0x84));
		System.out.println(input.getInt(0x88));
		System.out.println(input.getInt(0x8C));
		System.out.println(input.getInt(0x90));
		System.out.println(input.getInt(0x94));
		System.out.println(input.getInt(0x98));
		System.out.println(input.getInt(0x9C));
		System.out.println(input.getInt(0xA0));
		System.out.println("Code 1: " + input.getLong(0xA8)); // codes of the 4 items
		System.out.println("Code 2: " + input.getLong(0xB0));
		System.out.println("Code 3: " + input.getLong(0xB8));
		System.out.println("Code 4: " + input.getLong(0xC0));
	}

	@Override
	public void processMessage() {
		if (requestType == 4) {
		}
//		output.putInt(0x4, Messages.USER_SHOP_OPEN_RESPONSE);
//		User user = userSession.getUser();
//		if (itemType == 1) {
//			lobbyServer.addShop(user.username, user.items[itemIndex], 0, 0, code);
//		}
//		// itemType = 2. elements
//		else {
//			lobbyServer.addShop(user.username, null, itemIndex / 10000, itemIndex % 10000, code);
//		}
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(bytes.length);
		output.putBytes(0x0, bytes);
		output.putInt(0x4, Messages.USER_SHOP_ACTION_REQUEST);
		
		return output.toArray();
		/*
		 * this is message 0x4372
		 * 14
21-indexes?
84
204
384
504
688-code
68C
690-water
694-fire
698-earth
6A0-wind
6A4
		 */
	}

	@Override
	public void afterSend() throws IOException {
		if (requestType == 4) {
			lobbyServer.sendRoomMessage(userSession, new UserShopOpenHandler(lobbyServer, userSession).getResponse(username, shopName), true);
		}
	}
}
