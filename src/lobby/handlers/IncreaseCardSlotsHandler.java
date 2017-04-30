package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import net.objects.Card;
import tools.ExtendedByteBuffer;

public class IncreaseCardSlotsHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x20;
	
	protected int cardIndex;
	
	public IncreaseCardSlotsHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		cardIndex = input.getInt(0x14);
	}

	@Override
	public void processMessage() throws SQLException {
		if (userSession.getUser().getCard(cardIndex).getCardID() == Card.CARD_SLOT6) {
			userSession.getUser().setPlayerInventorySlots(userSession.getUser().getPlayerInventorySlots() + 6);
		}
		else if (userSession.getUser().getCard(cardIndex).getCardID() == Card.CARD_SLOT12){
			userSession.getUser().setPlayerInventorySlots(userSession.getUser().getPlayerInventorySlots() + 12);
		}
		
		userSession.getUser().removeCard(cardIndex);
	}

	@Override
	public byte[] getResponse() throws SQLException {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.INCREASE_CARD_SLOTS_RESPONSE);
		/*
		 * 1 - good
		 * 2 - you can get at most 84 card slots (84+12 = 96 total)
		 * 3 - exception error lol.
		 */
		output.putInt(0x14, 1);
		output.putInt(0x18, cardIndex);
		output.putInt(0x1C, userSession.getUser().getPlayerInventorySlots());
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
