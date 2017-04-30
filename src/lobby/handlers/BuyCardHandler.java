package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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
		
		ArrayList<Integer> changedIndexes = new ArrayList<Integer>();
		
		if (isBuy) {
			if (cardID == Card.PACKAGE_SKILL1_25) {
				changedIndexes.add(user.addCard(new Card(Card.SKILL1_FUSION, premiumDays, 25, 0)));
				changedIndexes.add(user.addCard(new Card(Card.SKILL1_FUSION, premiumDays, 25, 0)));
				changedIndexes.add(user.addCard(new Card(Card.SKILL_1_1_FUSION, premiumDays, 1, 0)));
				changedIndexes.add(user.addCard(new Card(Card.SKILL_2_1_FUSION, premiumDays, 1, 0)));
				changedIndexes.add(user.addCard(new Card(Card.SKILL_2_2_FUSION, premiumDays, 1, 0)));
			}
			else if (cardID == Card.PACKAGE_SKILL1_15) {
				changedIndexes.add(user.addCard(new Card(Card.SKILL1_FUSION, premiumDays, 16, 0)));
				changedIndexes.add(user.addCard(new Card(Card.SKILL2_FUSION, premiumDays, 1, 0)));
			}
			else if (cardID == Card.PACKAGE_SKILL2_25) {
				changedIndexes.add(user.addCard(new Card(Card.SKILL2_FUSION, premiumDays, 25, 0)));
				changedIndexes.add(user.addCard(new Card(Card.SKILL_1_1_FUSION, premiumDays, 1, 0)));
				changedIndexes.add(user.addCard(new Card(Card.SKILL_2_1_FUSION, premiumDays, 1, 0)));
				changedIndexes.add(user.addCard(new Card(Card.SKILL_2_2_FUSION, premiumDays, 1, 0)));
			}
			else if (cardID == Card.PACKAGE_SKILL2_15) {
				changedIndexes.add(user.addCard(new Card(Card.SKILL2_FUSION, premiumDays, 16, 0)));
				changedIndexes.add(user.addCard(new Card(Card.SKILL1_FUSION, premiumDays, 1, 0)));
			}
			else if (cardID == Card.PACKAGE_USERSHOP2_25) {
				changedIndexes.add(user.addCard(new Card(Card.USERSHOP2, premiumDays, 25, 0)));
				changedIndexes.add(user.addCard(new Card(Card.SKILL_FUSION, premiumDays, 2, 0)));
			}
			else if (cardID == Card.PACKAGE_USERSHOP2_15) {
				changedIndexes.add(user.addCard(new Card(Card.USERSHOP2, premiumDays, 15, 0)));
				changedIndexes.add(user.addCard(new Card(Card.LEVEL_FUSION, premiumDays, 2, 0)));
			}
			else if (cardID == Card.PACKAGE_CARD_CARD_30) {
				changedIndexes.add(user.addCard(new Card(Card.CARD_SLOT12, premiumDays, 0, 0)));
				changedIndexes.add(user.addCard(new Card(Card.CARD_SLOT12, premiumDays, 0, 0)));
				changedIndexes.add(user.addCard(new Card(Card.CARD_SLOT6, premiumDays, 0, 0)));
				changedIndexes.add(user.addCard(new Card(Card.SKILL_1_1_FUSION, premiumDays, 1, 0)));
				changedIndexes.add(user.addCard(new Card(Card.SKILL_2_1_FUSION, premiumDays, 1, 0)));
				changedIndexes.add(user.addCard(new Card(Card.SKILL_2_2_FUSION, premiumDays, 1, 0)));
			}
			else if (cardID == Card.PACKAGE_CARD_CARD_18) {
				changedIndexes.add(user.addCard(new Card(Card.CARD_SLOT12, premiumDays, 0, 0)));
				changedIndexes.add(user.addCard(new Card(Card.CARD_SLOT6, premiumDays, 0, 0)));
				changedIndexes.add(user.addCard(new Card(Card.SKILL1_FUSION, premiumDays, 1, 0)));
				changedIndexes.add(user.addCard(new Card(Card.SKILL2_FUSION, premiumDays, 1, 0)));
			}
			else {
				changedIndexes.add(user.addCard(new Card(cardID, premiumDays, amount, 0)));
			}
			
			if (cardID == 0x7D7 || cardID == 0x7D8 || cardID == 0x7DC) {
				user.setBooster(cardID);
			}
			else if (cardID == Card.QUEST_LIFE) {
				user.setExtraLife(true);
			}
			else if (cardID == Card.TIME_BONUS) {
				user.setTimeBonus(true);
			}
		}
		else {
			changedIndexes.add(cardIndex);
			user.getCard(cardIndex).setCardPremiumDays(user.getCard(cardIndex).getCardPremiumDays() + premiumDays);
		}
		
		User.saveUser(user);
		
		int[] indexes = new int[changedIndexes.size()];
		for (int i = 0; i < indexes.length; i++) {
			indexes[i] = changedIndexes.get(i);
		}
		
		User.saveCards(userSession.getUser(), indexes);
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
		
		output.putInt(0x678, user.getPlayerInventorySlots()); // new inventory slots
		output.putLong(0x680, user.getPlayerCode()); // code
		output.putLong(0x688, user.getCash()); // cash
		output.putLong(0x690, user.getAvatarMoney()); // coin (avatar money)
		
//		user.saveUser();
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
	}
}
