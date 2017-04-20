package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class Test4385Handler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x24;
	
	public Test4385Handler(LobbyServer lobbyServer, UserSession userSession) {
		super(lobbyServer, userSession);
	}

	@Override
	public void interpretBytes() {
		
	}

	@Override
	public void processMessage() throws SQLException {
		
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, 0x4385);
		
		int response = 12;
		output.putInt(0x14, response);
		
		switch (response) {
		case 2: // get a card
			output.putInt(0x18, 1111); // card id
			output.putInt(0x1C, 5); // card level
			output.putInt(0x20, 13); // card premium days
			break;
		case 4: // get a card
			output.putInt(0x18, 1111); // card id
			output.putInt(0x1C, 5); // card level
			output.putInt(0x20, 13); // card premium days
			break;
		case 5:
			output.putInt(0x18, 1); // card id
			output.putInt(0x1C, 5); // card level
			output.putInt(0x20, 1); // card premium days
			break;
		case 11: // get a card
			output.putInt(0x18, 80); // card index
			output.putInt(0x1C, 1111); // card id
			output.putInt(0x20, 10); // card premium days
			break;
		case 12: // free premium card with the purchase of super silver
			break;
		case 1: // something in korean
		case 3: // purchase of super silver
		case 8: // you get elements after purchasing a card/charging it
		case 9: // pet purchase
		case 10: // you get elements after selling your pet
		case 13: // get elements
			output.putInt(0x18, 1); // element type
			output.putInt(0x1C, 3); // element amount
			break;
		}
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
