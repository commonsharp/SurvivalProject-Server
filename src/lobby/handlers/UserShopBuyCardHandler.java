package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.query.Query;

import database.Database;
import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import net.objects.Card;
import net.objects.User;
import tools.ExtendedByteBuffer;

public class UserShopBuyCardHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x694;
	
	protected String username;
	protected long code;
	protected Card card;
	
	int elementType;
	int elementCount;
	
	public UserShopBuyCardHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		System.out.println("0x14: " + input.getInt(0x14));
		username = input.getString(0x18);
		code = input.getLong(0x28);
		
		int id = input.getInt(0x30);
		
		if (id > 10000) {
			elementType = id / 10000;
			elementCount = id % 10000;
		}
		else {
			card = new Card(input.getInt(0x30), input.getInt(0x34), input.getInt(0x38), input.getInt(0x3C));
		}
	}

	@Override
	public void processMessage() throws SQLException {
		userSession.getUser().setPlayerCode(userSession.getUser().getPlayerCode() - code);
		
		int addedSlot = -1;
		// If you bought a card
		if (card != null) {
			addedSlot = userSession.getUser().addCard(card);
			
			if (card.getCardID() == 0x7D7 || card.getCardID() == 0x7D8 || card.getCardID() == 0x7DC) {
				userSession.getUser().setBooster(card.getCardID());
			}
		}
		// If you bought an element
		else {
			userSession.getUser().setWhiteCard(elementType - 1, userSession.getUser().getWhiteCard(elementType - 1) + elementCount);
		}
		
		User.saveUser(userSession.getUser());
		
		if (addedSlot != -1) {
			User.saveCards(userSession.getUser(), addedSlot);
		}
		
		// Change the owner's code
		UserSession ownerUserSession = lobbyServer.findUserSession(username);
		
		if (ownerUserSession != null) {
			ownerUserSession.getUser().setPlayerCode(ownerUserSession.getUser().getPlayerCode() - code);
		}
		
		User.saveUser(userSession.getUser());
		
		// Remove the card from the usershop
		Session session = Database.getSession();
		session.beginTransaction();
		Query<?> query = session.createQuery("delete from UserShop where username = :username and cardID = :cardID and cardPremiumDays = :cardPremiumDays and cardLevel = :cardLevel and cardSkill = :cardSkill and code = :code");
		query.setParameter("username", username);
		query.setParameter("code", code);
		
		if (card != null) {
			query.setParameter("cardID", card.getCardID());
			query.setParameter("cardPremiumDays", card.getCardPremiumDays());
			query.setParameter("cardLevel", card.getCardLevel());
			query.setParameter("cardSkill", card.getCardSkill());
		}
		else {
			query.setParameter("cardID", elementType * 10000 + elementCount);
			query.setParameter("cardPremiumDays", 0);
			query.setParameter("cardLevel", 0);
			query.setParameter("cardSkill", 0);
		}
		
		query.executeUpdate();
		
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public byte[] getResponse() throws SQLException {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.USER_SHOP_BUY_CARD_RESPONSE);
		
		/*
		 * response:
		 * add 2 to everything here:
		 * 2 - Item buying complete
		 * 3,6,8,10 - The item has been sold already or&#xA;not available to buy because of the sale information change&#xA;is the item.
		 * 12 - okay?
		 * 13 - demo version or something. can't buy cards in demo version
		 */
		
		int response = 2;
		
		User user = userSession.getUser();
		
		for (int i = 0; i < 96; i++) {
			if (user.getItemID(i) != -1) {
				output.putByte(0x14 + i, (byte) 1); // needs to be 1..
				output.putInt(0x74 + 4 * i, user.getItemID(i)); // id
				output.putInt(0x1F4 + 4 * i, user.getItemPremiumDays(i)); // premium days
				output.putInt(0x374 + 4 * i, user.getItemLevel(i)); // level
				output.putInt(0x4F4 + 4 * i, user.getItemSkill(i)); // skill
			}
		}
		
		output.putLong(0x678, userSession.getUser().getPlayerCode());
		output.putInts(0x680, userSession.getUser().getWhiteCards());
		
		output.putInt(0x690, response);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
