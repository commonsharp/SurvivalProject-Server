package game.handlers;

import java.io.IOException;
import java.sql.SQLException;

import game.GameHandler;
import net.GenericUDPServer;
import tools.ExtendedByteBuffer;

public class GameAnnouncementHandler extends GameHandler {
	public static final int RESPONSE_LENGTH = 0x40;
	
	public GameAnnouncementHandler(GenericUDPServer udpServer) {
		super(udpServer);
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
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, 0x1128);
		
		int response = 12;
		
		output.putInt(0x14, 99);
		
		output.putInt(0x24, response);
		output.putString(0x28, "baraklevy");
		
		switch (response) {
		case 1: // player upgraded card to level x
			output.putInt(0x38, 1111);
			output.putInt(0x3C, 8);
			break;
		case 2:
			break;
		case 3: // * Player %s has set a new record in training %d mode mission %d
			output.putInt(0x38, 1);
			output.putInt(0x3C, 2);
			break;
		case 4: // * Player %s is eligible for spirit 10 times event.
		case 5: // * Player %s has obtained the treasure map.
		case 7: // A %s-class player has joined the server: %s
			output.putInt(0x38, 32); // from 29 to 32. it's the level
		case 8:
		case 9: // &lt;%s &gt; won a prize &lt;Lucky Point 10 times&gt;.
		case 10: // Server capture will end in [%d] minutes.
		case 11: // twister event or something. korean text
		case 12:
		case 13:
		}
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
