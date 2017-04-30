package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.UserSession;
import net.objects.User;

public class UseActionHandler extends LobbyHandler {
	protected int actionType;
	protected int subType;
	
	public UseActionHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		/*
		 * 0 - player picks coins
		 * 1 - player picks a flying element
		 * 2 - use a scroll
		 * 4 - pet picks a flying element
		 */
		actionType = input.getInt(0x14);
		subType = input.getInt(0x18);
		input.getInt(0x1C);
		input.getByte(0x20);
		System.out.println("UseActionHandler:");
		System.out.println("0x14 : " + input.getInt(0x14));
		System.out.println("0x18 : " + input.getInt(0x18));
		System.out.println("0x1C : " + input.getInt(0x1C));
		System.out.println("0x20 : " + input.getByte(0x20));
	}

	@Override
	public void processMessage() throws SQLException {
		if (actionType == 2) {
			userSession.getUser().setScroll(subType, 0);
			User.saveUser(userSession.getUser());
		}
	}

	@Override
	public byte[] getResponse() {
		return null;
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		if (actionType == 1 || actionType == 4) {
			lobbyServer.sendRoomMessage(userSession, new PickBubbleHandler(lobbyServer, userSession).getResponse(true, subType), true);
		}
		else if (actionType == 0) {
			lobbyServer.sendRoomMessage(userSession, new PickBubbleHandler(lobbyServer, userSession).getResponse(false, subType), true);
		}
	}

}
