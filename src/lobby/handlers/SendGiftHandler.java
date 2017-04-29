package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import org.hibernate.Session;

import database.Database;
import database.DatabaseHelper;
import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import net.objects.Card;
import net.objects.Gift;
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
		if (lobbyServer.findUserSession(toUsername) != null || DatabaseHelper.isUserExists(toUsername)) {
			response = 1;
			
			if (giftType == 1) {
				card = userSession.getUser().cards[(int) amountOrIndex];
				userSession.getUser().cards[(int) amountOrIndex] = null;
			}
			else if (giftType == 2) {
				userSession.getUser().whiteCards[(int) amountOrIndex / 10000 - 1] -= (int) amountOrIndex % 10000;
			}
			else if (giftType == 3) {
				userSession.getUser().playerCode -= amountOrIndex;
			}
			
			// you always lose 19000 code per gift
			userSession.getUser().playerCode -= 19000;
			
			userSession.getUser().saveUser();
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
					toUserSession.sendMessage(new ElementsOrCodeGiftReceivedHandler(lobbyServer, userSession).getResponse(userSession.getUser().username, toUserSession, giftType, amountOrIndex));
				}
			}
			else {
				Session session = Database.getSession();
				session.beginTransaction();
				Gift gift = new Gift();
				gift.setFromUsername(userSession.getUser().username);
				gift.setToUsername(toUsername);
				gift.setGiftType(giftType);
				gift.setAmount(amountOrIndex);
				
				if (giftType == 1) {
					gift.setCardID(card.getId());
					gift.setCardPremiumDays(card.getPremiumDays());
					gift.setCardLevel(card.getLevel());
					gift.setCardSkill(card.getSkill());
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
