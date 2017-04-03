package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.CurrencyHelper;
import net.Messages;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class BuyScrollHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x30;
	
	protected boolean isSell;
	protected int scrollID;
	
	protected int response;
	protected int scrollIndex;
	
	public BuyScrollHandler(LobbyServer lobbyServer, UserTCPSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		printMessage();
		isSell = input.getBoolean(0x14);
		scrollID = input.getInt(0x18);
	}

	@Override
	public void processMessage() throws SQLException {
		if (isSell) {
			userSession.getUser().scrolls[scrollID] = 0;
			userSession.getUser().playerCode += CurrencyHelper.getScrollCode(scrollID);
			scrollIndex = scrollID;
			response = 0;
		}
		else {
			int emptyScrollIndex = userSession.getUser().getEmptyScrollSlot();
			
			if (emptyScrollIndex != -1) {
				response = 0;
				userSession.getUser().scrolls[emptyScrollIndex] = scrollID + 1;
				userSession.getUser().playerCode -= CurrencyHelper.getScrollCode(scrollID);
				scrollIndex = emptyScrollIndex;
			}
			else {
				response = 2;
			}
		}
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.BUY_SCROLL_RESPONSE);
		output.putBoolean(0x14, isSell); // 0 - buy scroll. 1 - sell scroll
		output.putInt(0x18, response); // response. 0 - good. 1 - not enough code. 2 - not enough space
		output.putInt(0x1C, scrollIndex); // the scroll index - 0, 1 or 2.
		output.putInt(0x20, scrollID + 1);
		output.putLong(0x28, userSession.getUser().playerCode);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
