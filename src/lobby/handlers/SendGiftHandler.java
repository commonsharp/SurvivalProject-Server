package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import org.hibernate.Session;

import database.Database;
import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import net.objects.Card;
import net.objects.Gift;
import net.objects.User;
import tools.ExtendedByteBuffer;

public class SendGiftHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x38;
	
	String toUsername;
	int giftType; // 1 - card. 2 - elements. 3 - code
	long amountOrIndex;
	
	int response;
	Card card;
	
	public SendGiftHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		toUsername = input.getString(0x14);
		giftType = input.getInt(0x24);
		amountOrIndex = input.getLong(0x28);
	}

	@Override
	public void processMessage() throws SQLException {
		if (lobbyServer.findUserSession(toUsername) != null || User.isUserExists(toUsername)) {
			response = 1;
			
			if (giftType == 1) {
				card = userSession.getUser().getCard((int) amountOrIndex);
				userSession.getUser().removeCard((int) amountOrIndex);
			}
			else if (giftType == 2) {
				userSession.getUser().setWhiteCard((int) amountOrIndex / 10000 - 1, userSession.getUser().getWhiteCard((int) amountOrIndex / 10000 - 1) - (int) amountOrIndex % 10000);
			}
			else if (giftType == 3) {
				userSession.getUser().setPlayerCode(userSession.getUser().getPlayerCode() - amountOrIndex);
			}
			
			// you always lose 19000 code per gift
			userSession.getUser().setPlayerCode(userSession.getUser().getPlayerCode() - 19000);
			
			User.saveUser(userSession.getUser());
			
			if (giftType == 1) {
				User.saveCards(userSession.getUser(), (int) amountOrIndex);
			}
		}
		else {
			response = 2;
		}
	}

	@Override
	public byte[] getResponse() throws SQLException {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.SEND_GIFT_RESPONSE);
		
		/*
		 * 1 - Gift send complete!!&#xA;Player (%s) will be receiving &#xA;your gift.
		 * 2 - Send gift failed &#xA;because of an exceptional error.
		 * 3 - You do not have enough &#xA;Code(%d) to send out gift.&#xA;
		 */
		output.putInt(0x14, response);
		output.putString(0x18, toUsername);
		output.putInt(0x28, giftType);
		
		// card
		if (giftType == 1) {
			output.putInt(0x30, (int) amountOrIndex);
		}
		// element
		else if (giftType == 2) {
			output.putLong(0x30, amountOrIndex);
		}
		// code
		else if (giftType == 3) {
			output.putLong(0x30, amountOrIndex);
		}
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		if (response == 1) {
			UserSession toUserSession = lobbyServer.findUserSession(toUsername);
			
			if (toUserSession != null) {
				if (giftType == 1) {
					toUserSession.sendMessage(new CardGiftReceivedHandler(lobbyServer, userSession).getResponse(toUserSession, card));
				}
				else if (giftType == 2 || giftType == 3) {
					toUserSession.sendMessage(new ElementsOrCodeGiftReceivedHandler(lobbyServer, userSession).getResponse(userSession.getUser().getUsername(), toUserSession, giftType, amountOrIndex));
				}
			}
			else {
				Session session = Database.getSession();
				session.beginTransaction();
				Gift gift = new Gift();
				gift.setFromUsername(userSession.getUser().getUsername());
				gift.setToUsername(toUsername);
				gift.setGiftType(giftType);
				gift.setAmount(amountOrIndex);
				
				if (giftType == 1) {
					gift.setCardID(card.getCardID());
					gift.setCardPremiumDays(card.getCardPremiumDays());
					gift.setCardLevel(card.getCardLevel());
					gift.setCardSkill(card.getCardSkill());
				}
				else {
					// Everything is 0
				}
				
				session.save(gift);
				session.getTransaction().commit();
				session.close();
			}
		}
	}
}
