package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import net.objects.User;
import tools.ExtendedByteBuffer;

public class ElementsOrCodeGiftReceivedHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x40;
	
	public ElementsOrCodeGiftReceivedHandler(LobbyServer lobbyServer, UserSession userSession) {
		super(lobbyServer, userSession);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void interpretBytes() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processMessage() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getResponse() throws SQLException {
		return null;
	}
	
	public byte[] getResponse(String fromUsername, UserSession toUserSession, int giftType, long amount) throws SQLException {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.ELEMENTS_OR_CODE_GIFT_RECEIVED_RESPONSE);
		
		output.putString(0x14, fromUsername);
		output.putInt(0x24, giftType); // 2,8,11 (same) = elements. 3,9,12 (same) = code
		output.putLong(0x28, amount);
		output.putString(0x30, "hello"); // ??
		
		if (giftType == 2) {
			userSession.getUser().setWhiteCard((int) amount / 10000 - 1, userSession.getUser().getWhiteCard((int) amount / 10000 - 1) + (int) amount % 10000);
			User.saveUser(toUserSession.getUser());
		}
		else if (giftType == 3) {
			toUserSession.getUser().setPlayerCode(toUserSession.getUser().getPlayerCode() + amount);
			User.saveUser(toUserSession.getUser());
		}
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
