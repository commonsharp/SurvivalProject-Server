package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import net.objects.User;
import net.objects.UserShop;
import tools.ExtendedByteBuffer;

public class AutoUserShopNewItemHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x20;
	
	protected boolean isBuy;
	protected int cardType; // 1 for items. 2 for elements. nothing else.
	protected int cardIndex;
	protected long code;
	
	public AutoUserShopNewItemHandler(LobbyServer lobbyServer, UserSession userSession) {
		super(lobbyServer, userSession);
	}
	
	public AutoUserShopNewItemHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		/*
			18 - type. 1 for items. 2 for elements. nothing else.
			1C - item index (NOT ID!)
			
			when selling elements, 1C is as follows:
			water elements - 10000 + amount
			fire elements - 20000 + amount
			earth elements - 30000 + amount
			wind elements - 40000 + amount
		 */
		
		isBuy = input.getInt(0x14) == 1; // 1 - buy. 2 - sell.
		cardType = input.getInt(0x18);
		cardIndex = input.getInt(0x1C);
		code = input.getLong(0x20);
		
		printMessage();
		
		System.out.println("Type : " + cardType);
		System.out.println("Item index: " + cardIndex);
		System.out.println("Code : " + code);
	}

	@Override
	public void processMessage() throws SQLException {
		User user = userSession.getUser();
		
		if (cardType == 1) {
			if (isBuy) {
				lobbyServer.addShop(new UserShop(user.getUsername(), user.getCard(cardIndex).getCardID(),
						user.getCard(cardIndex).getCardPremiumDays(), user.getCard(cardIndex).getCardLevel(), user.getCard(cardIndex).getCardSkill(), code));
				
				
				int id = user.getCard(cardIndex).getCardID();
				user.removeCard(cardIndex);
				
				if (id == 0x7D7 || id == 0x7D8 || id == 0x7DC) {
					user.setBooster(0);
					
					for (int i = 0; i < 96; i++) {
						if (user.getCard(i) != null && (user.getCard(i).getCardID() == 0x7D7 || user.getCard(i).getCardID() == 0x7D8 || user.getCard(i).getCardID() == 0x7DC)) {
							user.setBooster(user.getCard(i).getCardID());
							break;
						}
					}
				}
			}
			else {
////				user.cards[user.getEmptyCardSlot()] = d
//				// Remove the card from the usershop
//				Card card = new Card();
//				
//				Connection con = DatabaseConnection.getConnection();
//				PreparedStatement ps = con.prepareStatement("DELETE FROM user_shop WHERE username = ? AND card_id = ? AND card_premium_days = ? AND card_level = ? AND card_skill = ? AND code = ?;");
//				ps.setString(1, user.username);
//				
//				
//				if (card != null) {
//					ps.setInt(2, card.getId());
//					ps.setInt(3, card.getPremiumDays());
//					ps.setInt(4, card.getLevel());
//					ps.setInt(5, card.getSkill());
//				}
//				else {
//					ps.setInt(2, elementType * 10000 + elementCount);
//				}
			}
		}
		// itemType = 2. elements
		else {
			if (isBuy) {
				lobbyServer.addShop(new UserShop(user.getUsername(), cardIndex, 0, 0, 0, code));
			}
		}
		
		if (isBuy) {
			int i;
			for (i = 0; i < 96; i++) {
				if (user.getCard(i) != null && user.getCard(i).getCardID() == 2009) {
					user.getCard(i).setCardLevel(user.getCard(i).getCardLevel() - 1);
					
					if (user.getCard(i).getCardLevel() == 0) {
						user.removeCard(i);
					}
					break;
				}
			}
			
			if (cardIndex < 10000) {
				User.saveCards(userSession.getUser(), cardIndex, i);
			}
			else {
				User.saveCards(userSession.getUser(), i);
			}
		}
		
		User.saveUser(userSession.getUser());
		
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.AUTO_USER_SHOP_NEW_ITEM_RESPONSE);
		
		 // 3 - server error. 4 - okay? 5 - the item is already sold and cannot be deleted?
		output.putInt(0x14, 4);
		output.putInt(0x18, cardType);
		output.putInt(0x1C, cardIndex);
		return output.toArray();
	}
	
	@Override
	public void afterSend() throws IOException {
		
	}
}
