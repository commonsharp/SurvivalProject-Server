package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class GameMasterBanHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x25;
	
	protected String username;
	public GameMasterBanHandler(LobbyServer lobbyServer, UserTCPSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		username = input.getString(0x14); // username
		input.getString(0x21); // password
		input.getString(0x2E); // reason
		input.getInt(0x94); // possibly ban option (kick/mute/ban)
		input.getInt(0x98); // possibly days
	}

	@Override
	public void processMessage() {
		// ban and stuff
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);

		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.GAME_MASTER_BAN_RESPONSE);

		/*
		 * Results:
		 * -12: Fail to connect server.&#xA;Please contact &#xA;us at our &#xA;Customer Helpdesk.
		 * -11: Player (%s) is connected to&#xA;another server. Please try &#xA;again later.
		 * -10: Player(%s) &#xA;is not logged in &#xA;or disconnected.
		 * -9: Player (%s) is being access &#xA;by another Game Master.
		 * -8: Player &#xA;(%s) &#xA;ban &#xA;duration&#xA;is wrong.
		 * -7: Player (%s) &#xA;is a GM.
		 * -6: Player (%s)&#xA;is not login.
		 * -5: Player (%s) is in the channel,&#xA;recovery is possible.
		 * -4: Player (%s)&#xA;has the same ID as GM.
		 * -3: This is not a GM account,&#xA;GM function is not available.
		 * -2: Player ID &#xA;(%s) &#xA;is wrong.
		 * -1: GM password incorrect.
		 * 0: Changes to player (%s)&#xA;is completed.
		 */
		int result = 0;
		output.putInt(0x14, result);
		output.putString(0x18, username);
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
		
	}

}
