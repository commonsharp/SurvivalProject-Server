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

public class CardGiftReceivedHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x28;
	
	public CardGiftReceivedHandler(LobbyServer lobbyServer, UserSession userSession) {
		super(lobbyServer, userSession);
	}

	@Override
	public void interpretBytes() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processMessage() throws SQLException {
	}

	@Override
	public byte[] getResponse() throws SQLException {
		return null;
	}
	
	public byte[] getResponse(UserSession userSession, Card card) throws SQLException {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.CARD_GIFT_RECEIVED_RESPONSE);
		
		
		int emptySlot = userSession.getUser().addCard(card);
		User.saveUser(userSession.getUser());
		User.saveCards(userSession.getUser(), emptySlot);
		
		output.putInt(0x14, emptySlot);
		output.putInt(0x18, card.getCardID());
		output.putInt(0x1C, card.getCardPremiumDays());
		output.putInt(0x20, card.getCardLevel());
		output.putInt(0x24, card.getCardSkill());
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		
	}
}
