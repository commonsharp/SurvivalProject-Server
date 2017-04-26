package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import net.objects.Room;
import tools.ExtendedByteBuffer;

public class InfinityKingPointsHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x3C;
	
	public InfinityKingPointsHandler(LobbyServer lobbyServer, UserSession userSession) {
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
		return null;
	}
	
	public byte[] getResponse(int[] pointsDifference, int type, int monsterIndex) {
		Room room = lobbyServer.getRoom(userSession.getUser().roomIndex);
		
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.INFINITY_KING_POINTS_RESPONSE);
		
		/*
		 * 0 - First kings with 0 starting points
		 * 1 - First kings with a set starting points
		 * 2 - something with monsters array
		 * 3 - something with monsters array
		 * 4 - player death
		 */
		output.putInt(0x14, type);
		output.putInts(0x18, pointsDifference);
		output.putInt(0x38, monsterIndex); // monster index (for symbols probably)
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
