package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import net.objects.BigUserShop;
import net.objects.Card;
import net.objects.UserShop;
import tools.ExtendedByteBuffer;

public class UserShopIActionHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0xC8;
	
	protected int requestType;
	protected String fromUsername;
	protected String toUsername;
	protected String shopName;
	
	byte[] bytes;
	
	BigUserShop bigUserShop;
	
	public UserShopIActionHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
		this.bytes = messageBytes;
	}

	@Override
	public void interpretBytes() {
		/*
		 * 2 - view shop
		 * 3 - buy card
		 * 4 - create a new shop
		 * 5 - edit shop
		 * 6 - user is not connected
		 * 7 - not enough slots
		 */
		requestType = input.getInt(0x4C); // Action type. 0~7. 4 - create a new shop
		fromUsername = input.getString(0x14);
		toUsername = input.getString(0x21);
		shopName = input.getString(0x2E);
		
		if (requestType == 4) {
			bigUserShop = new BigUserShop(fromUsername, shopName);
			
			for (int i = 0; i < 4; i++) {
				int cardIndex = input.getInt(0x54 + i * 4);
				
				if (cardIndex != -1) {
					Card card = userSession.getUser().cards[cardIndex];
					bigUserShop.addShop(new UserShop(fromUsername, cardIndex, card.getId(), card.getPremiumDays(), card.getLevel(), card.getSkill(), input.getLong(0xA8 + 8 * i)), i);
				}
			}
			
			userSession.getUser().bigUserShop = bigUserShop;
		}
		else if (requestType == 2 || requestType == 3) {
			bigUserShop = lobbyServer.findUserSession(toUsername).getUser().bigUserShop;
		}
		else if (requestType == 5) {
			bigUserShop = userSession.getUser().bigUserShop;
		}
		
		System.out.println("From User: " + input.getString(0x14));
		System.out.println("To user: " + input.getString(0x21));
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
		if (requestType == 3) {
			
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
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.USER_SHOP_I_ACTION_REQUEST);
		
		output.putString(0x14, fromUsername);
		output.putString(0x21, toUsername);
		output.putString(0x2E, bigUserShop.shopName);
		
		int response = requestType;
		
		output.putInt(0x4C, response);
		output.putInt(0x50, -1);
		
		UserShop currentShop;
		for (int i = 0; i < 4; i++) {
			currentShop = bigUserShop.getShop(i);
			
			if (currentShop != null) {
				output.putInt(0x54 + 4 * i, currentShop.getCardIndex());
				output.putInt(0x64 + 4 * i, currentShop.getCardID());
				output.putInt(0x74 + 4 * i, currentShop.getCardLevel());
				output.putInt(0x84 + 4 * i, currentShop.getCardSkill());
				output.putInt(0x94 + 4 * i, currentShop.getCardPremiumDays());
				output.putLong(0xA8 + 8 * i, currentShop.getCode());
			}
			else {
				output.putInt(0x54 + 4 * i, -1);
				output.putInt(0x64 + 4 * i, -1);
				output.putInt(0x74 + 4 * i, -1);
				output.putInt(0x84 + 4 * i, -1);
				output.putInt(0x94 + 4 * i, -1);
				output.putLong(0xA8 + 8 * i, -1);
			}
		}
		
		output.putInt(0xA4, -1);
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
		lobbyServer.sendRoomMessage(userSession, getResponse(), false);
		
		if (requestType == 4) {
			lobbyServer.sendRoomMessage(userSession, new UserShopOpenHandler(lobbyServer, userSession).getResponse(fromUsername, shopName), false);
		}
		else if (requestType == 3) {
			
		}
	}
}
