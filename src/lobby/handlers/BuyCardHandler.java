package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import net.objects.Card;
import net.objects.User;
import tools.ExtendedByteBuffer;

public class BuyCardHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x698;
	
	protected int cardIndex;
	protected int cardID;
	protected int premiumDays;
	protected boolean isBuy;
	protected int amount;
	
	public BuyCardHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
		printMessage();
	}

	@Override
	public void interpretBytes() {
		cardIndex = input.getInt(0x14);
		cardID = input.getInt(0x18);
		premiumDays = input.getInt(0x1C);
		isBuy = input.getInt(0x20) == 0;
		amount = input.getInt(0x24); // this one is only used for skill fusion cards where you get 10 per purchase
//		input.getInt(0x14); // item index to charge (-1 if you're just buying an item)
//		input.getInt(0x18); // item id
//		input.getInt(0x1C); // premium days
//		input.getInt(0x20); // 0 for buy. 1 for charge
//		input.getInt(0x24);
//		input.getInt(0x28); // always 1? - this is probably the currency type (you can buy with avatar coins and with cash!)
	}

	@Override
	public void processMessage() throws SQLException {
		User user = userSession.getUser();
		
		if (isBuy) {
			int newSlot = userSession.getUser().getEmptyCardSlot();
			
			user.cards[newSlot] = new Card(cardID, premiumDays, amount, 0);
		}
		else {
			user.cards[cardIndex].setPremiumDays(user.cards[cardIndex].getPremiumDays() + premiumDays);
		}
		
		user.saveUser();
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.BUY_CARD_OR_CHARGE_RESPONSE);
		
		/*
			responses:
			1 - good
			2 - An error  has occurred. &#xA;Please inquire the support team.
			3 - Passed the maximum card number.
			4 - The card you want to charge does not exist.
			5 - You can only charge up to %d days
			6 - The card you want to charge is different
			7 - Not enough Cash
			8 - Not enough Code
			9 - Cannot charge PC room cards
			10 - prints something
			11 - prints something
			
			
			14 - response
			18 - item probably
			78- 4 bytes per item (total: 96 items)
			1F8 - 4 bytes per item - id
			378 - 4 bytes per item - premium days
			4F8 - 4 bytes per item - level
			678 - inventory slots
			680+684 - money
			688 - myinfo+DC8 - might be cash/avatar money
			690+694 - cash (or avatar money)

		 */
		
		int response = 1;
		output.putInt(0x14, response);
		
		User user = userSession.getUser();
		
		for (int i = 0; i < 96; i++) {
			if (user.getItemID(i) != -1) {
				output.putByte(0x18 + i, (byte) 1); // needs to be 1..
				output.putInt(0x78 + 4 * i, user.getItemID(i)); // id
				output.putInt(0x1F8 + 4 * i, user.getItemPremiumDays(i)); // premium days
				output.putInt(0x378 + 4 * i, user.getItemLevel(i)); // level
				output.putInt(0x4F8 + 4 * i, user.getItemSkill(i)); // skill
			}
		}
		
		output.putInt(0x678, user.playerInventorySlots); // new inventory slots
		output.putLong(0x680, user.playerCode); // code
		output.putLong(0x688, user.cash); // cash
		output.putLong(0x690, user.avatarMoney); // coin (avatar money)
		
//		user.saveUser();
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
