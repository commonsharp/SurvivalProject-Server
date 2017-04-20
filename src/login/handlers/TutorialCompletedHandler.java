package login.handlers;

import java.sql.SQLException;

import login.LoginHandler;
import net.Messages;
import net.UserSession;
import net.objects.Card;
import tools.ExtendedByteBuffer;

public class TutorialCompletedHandler extends LoginHandler {
	public static final int RESPONSE_LENGTH = 0x98;

	protected int item1ID;
	protected int item2ID;
	protected int petID;
	
	public TutorialCompletedHandler(UserSession tcpServer) {
		super(tcpServer);
	}
	
	public TutorialCompletedHandler(UserSession tcpServer, byte[] messageBytes) {
		super(tcpServer, messageBytes);
	}

	@Override
	public void interpretBytes() {
		// no fields
	}

	@Override
	public void afterSend() {
		
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.TUTORIAL_COMPLETED_RESPONSE);
		output.putInt(0x14, item1ID); // Item 1 ID
		output.putInt(0x18, 0); // Item 1 level
		output.putInt(0x1C, item2ID); // Item 2 ID
		output.putInt(0x20, 0); // Item 2 level
		output.putInt(0x24, 0); // ?
		output.putInt(0x28, petID); // Item 3 ID
		
		return output.toArray();
	}

	@Override
	public void processMessage() throws SQLException {
		int element = (int)(Math.random() * 4) + 1;
		item1ID = 1100 + 10 * element + (int)(Math.random() * 4);
		item2ID = 1200 + 10 * element + (int)(Math.random() * 4);
		petID = (int) (Math.random() * 3) + 5000;

		userSession.getUser().playerLevel = 1;
		userSession.getUser().playerCode = 1010;
		userSession.getUser().cards[0] = new Card(item1ID, 0, 0, 0);
		userSession.getUser().cards[1] = new Card(item2ID, 0, 0, 0);
		userSession.getUser().cards[2] = new Card(petID, 0, 0, 0); // This must be an unlimited time card because it's premium days are 10000 
		userSession.getUser().saveUser();
	}
}
