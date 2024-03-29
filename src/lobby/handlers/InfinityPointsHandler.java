package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.UserSession;
import net.objects.Room;
import net.objects.User;
import tools.ExtendedByteBuffer;

public class InfinityPointsHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x1BC;
	
	public InfinityPointsHandler(LobbyServer lobbyServer, UserSession userSession) {
		super(lobbyServer, userSession);
	}

	@Override
	public void interpretBytes() {
		
	}

	@Override
	public void processMessage() throws SQLException {
		
	}

	public byte[] getResponse() {
		return null;
	}
	
	public byte[] getResponse(User user, int[] luckyMultiplier, int[] levels, int[] elements, int[] coins) throws SQLException {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, 0x4473);
		
		int[] results = new int[8];
		
		results[0] = 1;
		
		output.putInts(0x14, results);
		
		output.putInt(0x34, user.getGameKO()); // ko
		output.putInt(0x38, 0); // ?
		output.putLong(0x40, user.getPlayerExperience()); // old points amount
		output.putLong(0x48, user.getPlayerCode()); // old code amount
		output.putLong(0x50, user.getAvatarMoney()); // old coin amount
		
		for (int i = 0; i < 8; i++) {
			output.putInt(0x58 + 4 * i, 8000); // experience gained
			output.putInt(0x78 + 4 * i, 8000); // code gained
			output.putInt(0x98 + 4 * i, elements[i]); // element type
			output.putInt(0xB8 + 4 * i, 5); // element count
			output.putInt(0xD8 + 4 * i, luckyMultiplier[i] + 1); // element multiplier
			output.putInt(0xF8 + 4 * i, luckyMultiplier[i]); // lucky multiplier
			output.putInt(0x118 + 4 * i, levels[i]); // new level
			output.putInt(0x138 + 4 * i, 0); // ?
			output.putInt(0x158 + 4 * i, coins[i]); // coins gained
			output.putInt(0x178 + 4 * i, 2); // coins multiplier
			output.putInt(0x198 + 4 * i, 0); // ?
		}
		
		int mySlot = userSession.getUser().getRoomSlot();
		userSession.getUser().setWhiteCard(elements[mySlot] - 1, userSession.getUser().getWhiteCard(elements[mySlot] - 1) + 5 * (luckyMultiplier[mySlot] + 1));
		userSession.getUser().setPlayerLevel(levels[mySlot]);
		userSession.getUser().setPlayerCode(userSession.getUser().getPlayerCode() + 8000 * luckyMultiplier[mySlot]);
		userSession.getUser().setPlayerExperience(userSession.getUser().getPlayerExperience() + 8000 * luckyMultiplier[mySlot]);
		userSession.getUser().setAvatarMoney(userSession.getUser().getAvatarMoney() + coins[mySlot] * 2);
		User.saveUser(userSession.getUser());
		
		output.putInt(0x1B8, 0); // no use it seems

		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		Room room = lobbyServer.getRoom(userSession.getUser().getRoomIndex());
		
		if (room != null) {
			room.isStarted = false;
			room.roomStartTime = 0;
		}
	}
}
