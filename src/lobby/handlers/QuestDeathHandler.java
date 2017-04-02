package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.ExperienceHelper;
import net.Messages;
import net.UserTCPSession;
import net.objects.Room;
import tools.ExtendedByteBuffer;

public class QuestDeathHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0xB0;
	
	public QuestDeathHandler(LobbyServer lobbyServer, UserTCPSession tcpServer) {
		super(lobbyServer, tcpServer);
	}

	@Override
	public void interpretBytes() {
	}

	public byte[] getResponse(int monsterIndex, int[] damageDone) throws SQLException {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);

		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.QUEST_DEATH_RESPONSE);
		
		output.putInt(0x14, monsterIndex);
		output.putInt(0x18, userSession.getUser().roomSlot); // slot
		output.putInt(0x1C, 0); // byte
		output.putInt(0x20, 0);
		
		int randomLucky = ExperienceHelper.getLuckyMultiplier();
		int[] experienceGained = ExperienceHelper.getExperience(damageDone);
		
		int[] luckyMultiplier = new int[8];
		for (int i = 0; i < 8; i++) {
			luckyMultiplier[i] = randomLucky;
		}
		
		output.putInts(0x24, luckyMultiplier);
		output.putInts(0x44, experienceGained);
		
		long[] experiences = new long[8];
		Room room = lobbyServer.getRoom(userSession.getUser().roomIndex);
		
		for (int i = 0; i < 8; i++) {
			if (room.getUser(i) != null) {
				room.getUser(i).getUser().playerExperience += experienceGained[i] * luckyMultiplier[i];
				room.getUser(i).getUser().playerCode += experienceGained[i] * luckyMultiplier[i];
				room.getUser(i).getUser().playerLevel = ExperienceHelper.getLevel(room.getUser(i).getUser().playerExperience);
				room.getUser(i).getUser().saveUser();
				experiences[i] = room.getUser(i).getUser().playerExperience;
			}
		}
		
		output.putInts(0x64, ExperienceHelper.getLevels(experiences));
		output.putInt(0x84, 100); // another array. unknown yet. maybe guild points?
		
		int elementType = (int)(Math.random() * 4) + 1;
		int elementAmount = ExperienceHelper.getElementCount();
		int elementMultiplier = ExperienceHelper.getLuckyMultiplier();
		
		if (elementAmount != 0) {
			userSession.getUser().whiteCards[elementType - 1] += elementAmount * elementMultiplier;
			output.putInt(0xA4, elementType);
			output.putInt(0xA8, elementAmount);
			output.putInt(0xAC, elementMultiplier);
		}
		
		userSession.getUser().saveUser();
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
	}

	@Override
	public void processMessage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getResponse() {
		// TODO Auto-generated method stub
		return null;
	}
}
