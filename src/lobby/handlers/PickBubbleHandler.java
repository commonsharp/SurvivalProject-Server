package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.ExperienceHelper;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class PickBubbleHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x28;
	
	public PickBubbleHandler(LobbyServer lobbyServer, UserSession userSession) {
		super(lobbyServer, userSession);
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
	
	public byte[] getResponse(boolean isElement, int elementType) throws SQLException {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.PICK_BUBBLE_RESPONSE);
		
		/*
		 * 0 - gain code
		 * 1 - gain nothing?
		 * 4 - gain element
		 */
		int response = isElement ? 4 : 0;
		
		output.putInt(0x14, userSession.getUser().roomSlot);
		output.putInt(0x18, response);
		output.putInt(0x1C, elementType);
		
		if (isElement) {
			output.putInt(0x20, ExperienceHelper.getElementCount(false));
			output.putInt(0x24, ExperienceHelper.getLuckyMultiplier());
		}
		else {
			if (elementType == 0) {
				output.putInt(0x20, 1000);
			}
			else {
				output.putInt(0x20, 3000);
			}
		}
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}
}
