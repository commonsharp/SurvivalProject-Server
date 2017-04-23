package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import net.objects.Card;
import tools.ExtendedByteBuffer;

public class GoldForceChargeHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x24;
	
	int cardIndex;
	int cardID;
	int forceCardIndex;
	int forceCardID;
	
	public GoldForceChargeHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		cardIndex = input.getInt(0x14);
		cardID = input.getInt(0x18);
		forceCardIndex = input.getInt(0x1C);
		forceCardID = input.getInt(0x20);
	}

	@Override
	public void processMessage() throws SQLException {
		Card card = userSession.getUser().cards[cardIndex];
		
		int premiumDays;
		
		if (forceCardID >= 3000 && forceCardID < 3010) {
			premiumDays = 30;
		}
		else if (forceCardID >= 3010 && forceCardID < 3020) {
			premiumDays = 365;
		}
		else if (forceCardID >= 3020 && forceCardID < 3030) {
			premiumDays = 425;
		}
		else if (forceCardID >= 3030 && forceCardID < 3040) {
			premiumDays = 1;
		}
		else if (forceCardID >= 3040 && forceCardID < 3050) {
			premiumDays = 3;
		}
		else if (forceCardID >= 3050 && forceCardID < 3060) {
			premiumDays = 45;
		}
		else if (forceCardID >= 3060 && forceCardID < 3070) {
			premiumDays = 999;
		}
		else if (forceCardID >= 3100 && forceCardID < 3110) {
			premiumDays = 7;
		}
		else {
			premiumDays = 0;
		}
		
		userSession.getUser().cards[forceCardIndex] = null;
		
		card.setPremiumDays(card.getPremiumDays() + premiumDays);
		userSession.getUser().saveUser();
	}

	@Override
	public byte[] getResponse() throws SQLException {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.GOLD_FORCE_CHARGE_RESPONSE);
		
		/*
		 * 1 - success
		 * 2 - The card you want to force  &#xA;fuse has a different index
		 * 3 - The card you want to force &#xA;fusion does not exist
		 * 4 - The card you want to force &#xA;fusion exceeded the maximum days.
		 * 6 - The two cards you want to &#xA;force charge are of different type
		 * 7 - The card you want to force &#xA;charge is a PC room card
		 * 8 - Each of the card you want &#xA;to force charge is of different type.
		 */
		
		int response = 1;
		output.putInt(0x14, response);
		output.putInt(0x18, cardIndex);
		output.putInt(0x1C, cardID);
		output.putInt(0x20, forceCardIndex);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
	}
}
