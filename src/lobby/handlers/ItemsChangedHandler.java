package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.UserSession;
import net.objects.User;

public class ItemsChangedHandler extends LobbyHandler {
	public ItemsChangedHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		// these are indexes and not ID's
		userSession.getUser().setMagicIndex(input.getInt(0x14));
		userSession.getUser().setWeaponIndex(input.getInt(0x18));
		userSession.getUser().setAccessoryIndex(input.getInt(0x1C));
		userSession.getUser().setPetIndex(input.getInt(0x20));
		userSession.getUser().setFootIndex(input.getInt(0x24));
		userSession.getUser().setBodyIndex(input.getInt(0x28));
		userSession.getUser().setHand1Index(input.getInt(0x2C));
		userSession.getUser().setHand2Index(input.getInt(0x30));
		userSession.getUser().setFaceIndex(input.getInt(0x34));
		userSession.getUser().setHairIndex(input.getInt(0x38));
		userSession.getUser().setHeadIndex(input.getInt(0x3C));
	}

	@Override
	public void afterSend() throws IOException {
		if (userSession.getUser().isInRoom()) {
			lobbyServer.sendRoomMessage(userSession, new RoomPlayersUpdateHandler(lobbyServer, userSession).getResponse(userSession), true);
		}
	}

	@Override
	public byte[] getResponse() {
		return null;
	}

	@Override
	public void processMessage() throws SQLException {
		User.saveUser(userSession.getUser());
	}
}
