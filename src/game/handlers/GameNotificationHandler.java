package game.handlers;

import java.io.IOException;
import java.sql.SQLException;

import game.GameHandler;
import game.GameServer;
import tools.ExtendedByteBuffer;

public class GameNotificationHandler extends GameHandler {
	public static final int RESPONSE_LENGTH = 0x40;
	
	public enum GameAnnouncementResult {
		CARD_UPGRADE (1), // player upgraded card to level x
		LEVEL_UP(2), // player leveled up to level x
		TRAINING_RECORD(3), // * Player %s has set a new record in training %d mode mission %d
		SPIRIT_10(4), // * Player %s is eligible for spirit 10 times event.
		TREASURE_MAP(5), // * Player %s has obtained the treasure map.
		JOIN_SERVER(7), // A %s-class player has joined the server: %s
		RESULT_8(8), // something with a surfing event?
		LUCKY_10(9), // &lt;%s &gt; won a prize &lt;Lucky Point 10 times&gt;.
		SERVER_CAPTURE(10), // Server capture will end in [%d] minutes.
		TWISTER_EVENT(11), // twister event or something
		CULTURAL_GIFT(12), // username has won a culutral gift?
		VISITS_1800(13), // more than 1800 visits?
		VISITS_100(14); // more than 100 visits?
		
		private int value;
		
		private GameAnnouncementResult(int id) {
			this.value = id;
		}
		
		public int getValue() {
			return value;
		}
	}
	
	public GameNotificationHandler(GameServer gameServer) {
		super(gameServer);
	}

	@Override
	public void interpretBytes() {
		
	}

	@Override
	public void processMessage() throws SQLException {
		
	}

	@Override
	public byte[] getResponse() throws SQLException {
		return null;
	}
	
	public byte[] getResponse(GameAnnouncementResult response, String username, int field38, int field3C) throws SQLException {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, 0x1128);
		
		output.putInt(0x24, 8);
//		output.putInt(0x24, response.getValue());
		output.putString(0x28, username);
		output.putInt(0x38, field38);
		output.putInt(0x3C, field3C);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
