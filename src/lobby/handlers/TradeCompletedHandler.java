package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class TradeCompletedHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x6A0;
	
	public TradeCompletedHandler(LobbyServer lobbyServer, UserSession userSession) {
		super(lobbyServer, userSession);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void interpretBytes() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processMessage() throws SQLException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getResponse() throws SQLException {
		return null;
	}
	
	public byte[] getResponse(UserSession userSession, String otherUsername) throws SQLException {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.TRADE_COMPLETED_RESPONSE);
		
		output.putString(0x14, otherUsername);
		
		for (int i = 0; i < 96; i++) {
			if (userSession.getUser().getItemID(i) != -1) {
				output.putByte(0x21 + i, (byte) 1); // needs to be 1..
				output.putInt(0x84 + 4 * i, userSession.getUser().getItemID(i)); // id
				output.putInt(0x204 + 4 * i, userSession.getUser().getItemPremiumDays(i)); // premium days
				output.putInt(0x384 + 4 * i, userSession.getUser().getItemLevel(i)); // level
				output.putInt(0x504 + 4 * i, userSession.getUser().getItemSkill(i)); // skill
			}
		}
		
		output.putLong(0x688, userSession.getUser().playerCode);
		output.putInts(0x690, userSession.getUser().whiteCards);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
