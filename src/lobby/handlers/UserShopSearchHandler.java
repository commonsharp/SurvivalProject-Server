package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;

import database.Database;
import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import net.objects.User;
import tools.ExtendedByteBuffer;

public class UserShopSearchHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x108;
	
	protected int elementType;
	protected int cardLevel;
	protected int cardType; // last two digits for normal items
	
	public UserShopSearchHandler(LobbyServer lobbyServer, UserSession userSession) {
		super(lobbyServer, userSession);
	}
	
	public UserShopSearchHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		// those fields can also be -1. in which case the server needs to ignore those fields in the search
		elementType = input.getInt(0x14);
		cardLevel = input.getInt(0x18);
		cardType = input.getInt(0x1C); // first digit - item type (weapon/accessory/magic/...). second digit - subtype (axe/sword/...). 0 for elements
		
		System.out.println(elementType);
		System.out.println(cardLevel);
		System.out.println(cardType);
	}

	@Override
	public void processMessage() throws SQLException {
		userSession.getUser().setUserShopResults(lobbyServer.findShops(elementType, cardLevel, cardType));
		userSession.getUser().setUserShopOffset(0);
	}

	@Override
	public byte[] getResponse() throws SQLException {
		return getResponse(userSession.getUser().getUserShopOffset());
	}
		
	@SuppressWarnings("unchecked")
	public byte[] getResponse(int offset) throws SQLException {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.USER_SHOP_SEARCH_RESPONSE);
		
		int i;
		for (i = 0; i + offset < userSession.getUser().getUserShopResults().size() && i < 5; i++) {
			output.putInt(0x18 + i * 4, -1);
			output.putString(0x2C + i * 0xD, userSession.getUser().getUserShopResults().get(i + offset).getUsername());
			
			UserSession currentUserSession = lobbyServer.findUserSession(userSession.getUser().getUserShopResults().get(i + offset).getUsername());
			
			if (currentUserSession != null) {
				output.putInt(0x70 + i * 4, lobbyServer.findUserSession(userSession.getUser().getUserShopResults().get(i + offset).getUsername()).getUser().getPlayerLevel());
				output.putBoolean(0x84 + i, lobbyServer.findUserSession(userSession.getUser().getUserShopResults().get(i + offset).getUsername()).getUser().isMale());
			}
			else {
				Session session = Database.getSession();
				session.beginTransaction();
				List<User> users = session.createQuery("from User where username = :username").
						setParameter("username", userSession.getUser().getUserShopResults().get(i + offset).getUsername()).list();
				
				if (!users.isEmpty()) {
					output.putInt(0x70 + 4 * i, users.get(0).getPlayerLevel());
					output.putBoolean(0x84 + i, users.get(0).isMale());
				}
				
				session.getTransaction().commit();
				session.close();
			}
			
			// how many items were found. <= 0 - no items found.
			if (userSession.getUser().getUserShopResults().size() > 5) {
				output.putInt(0x14, userSession.getUser().getUserShopResults().size() - offset - i); // how many left to read
			}
			
			output.putLong(0x90 + i * 8, userSession.getUser().getUserShopResults().get(i + offset).getCode());
			
			output.putInt(0xB8 + i * 4, userSession.getUser().getUserShopResults().get(i + offset).getCardID());
			output.putInt(0xCC + i * 4, userSession.getUser().getUserShopResults().get(i + offset).getCardPremiumDays());
			output.putInt(0xE0 + i * 4, userSession.getUser().getUserShopResults().get(i + offset).getCardLevel());
			output.putInt(0xF4 + i * 4, userSession.getUser().getUserShopResults().get(i + offset).getCardSkill());
		}
		
//		output.putStrings(0x2C, roomNames, 0xD);
		
		userSession.getUser().setUserShopOffset(userSession.getUser().getUserShopOffset() + i);
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
	}
}
