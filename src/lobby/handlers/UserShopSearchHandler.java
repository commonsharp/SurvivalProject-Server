package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserTCPSession;
import net.objects.UserShop;
import tools.ExtendedByteBuffer;

public class UserShopSearchHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x108;
	
	protected int elementType;
	protected int itemLevel;
	protected int itemType; // last two digits for normal items
	
	UserShop[] results;
	
	public UserShopSearchHandler(LobbyServer lobbyServer, UserTCPSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void interpretBytes() {
		// those fields can also be -1. in which case the server needs to ignore those fields in the search
		elementType = input.getInt(0x14);
		itemLevel = input.getInt(0x18);
		itemType = input.getInt(0x1C); // first digit - item type (weapon/accessory/magic/...). second digit - subtype (axe/sword/...)
	}

	@Override
	public void processMessage() {
		results = lobbyServer.findShops();
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.USER_SHOP_SEARCH_RESPONSE);
		
		// how many items were found. <= 0 - no items found.
		output.putInt(0x14, 0); // the amount is actually 5 + this value.
		
		for (int i = 0; i < results.length && i < 5; i++) {
			output.putInt(0x18 + i * 4, -1);
			output.putString(0x2C + i * 0xD, results[i].getUsername());
			output.putInt(0x70 + i * 4, lobbyServer.findUser(results[i].getUsername()).playerLevel);
			output.putBoolean(0x84 + i, lobbyServer.findUser(results[i].getUsername()).isMale);
			output.putLong(0x90 + i * 8, results[i].getCode());
			
			if (results[i].getItem() != null) {
				output.putInt(0xB8 + i * 4, results[i].getItem().getId());
				output.putInt(0xCC + i * 4, results[i].getItem().getPremiumDays());
				output.putInt(0xE0 + i * 4, results[i].getItem().getLevel());
				output.putInt(0xF4 + i * 4, results[i].getItem().getSkill());
			}
			else {
				output.putInt(0xB8 + i * 4, results[i].getElementType() * 10000 + results[i].getElementAmount());
			}
		}
		
//		output.putStrings(0x2C, roomNames, 0xD);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
