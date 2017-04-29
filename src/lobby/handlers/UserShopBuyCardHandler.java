package lobby.handlers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
		userSession.getUser().playerCode -= code;
		
		// If you bought a card
		if (card != null) {
			int newSlot = userSession.getUser().getEmptyCardSlot();
			userSession.getUser().cards[newSlot] = card;
			
			if (card.getId() == 0x7D7 || card.getId() == 0x7D8 || card.getId() == 0x7DC) {
				userSession.getUser().booster = card.getId();
			}
		}
		// If you bought an element
		else {
			userSession.getUser().whiteCards[elementType - 1] += elementCount;
		}
		
		userSession.getUser().saveUser();
		
		// Change the owner's code
		UserSession ownerUserSession = lobbyServer.findUserSession(username);
		
		if (ownerUserSession != null) {
			ownerUserSession.getUser().playerCode -= code;
			ownerUserSession.getUser().saveUser();
		}
		else {
			Connection con = Database.getConnection();
			PreparedStatement ps = con.prepareStatement("UPDATE users SET code = code - ? WHERE username = ?;");
			ps.setLong(1, code);
			ps.setString(2, username);
			ps.executeUpdate();
			ps.close();
			con.close();
		}
		
		
		// Remove the card from the usershop
		Connection con = Database.getConnection();
		PreparedStatement ps = con.prepareStatement("DELETE FROM user_shop WHERE username = ? AND card_id = ? AND card_premium_days = ? AND card_level = ? AND card_skill = ? AND code = ?;");
		ps.setString(1, username);
		
		if (card != null) {
			ps.setInt(2, card.getId());
			ps.setInt(3, card.getPremiumDays());
			ps.setInt(4, card.getLevel());
			ps.setInt(5, card.getSkill());
		}
		else {
			ps.setInt(2, elementType * 10000 + elementCount);
		}
		
		ps.setLong(6, code);
		ps.executeUpdate();
		ps.close();
		con.close();
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
		
		output.putLong(0x678, userSession.getUser().playerCode);
		output.putInts(0x680, userSession.getUser().whiteCards);
		
		output.putInt(0x690, response);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
