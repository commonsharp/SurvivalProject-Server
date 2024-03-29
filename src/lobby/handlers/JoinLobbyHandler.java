package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;

import database.Database;
import game.handlers.GameNotificationHandler;
import game.handlers.GameNotificationHandler.GameAnnouncementResult;
import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import net.objects.Card;
import net.objects.Gift;
import net.objects.Memo;
import net.objects.User;
import tools.ExtendedByteBuffer;

public class JoinLobbyHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x979;
	
	final int minPointForeveDword[] = {-5, 1, 50, 100,
			200, 400, 800, 1600,
			2400, 3200, 6400, 12800,
			25600, 51200, 102400, 204800,
			409600, 819200, 1638400, 3276800,
			6553600, 13107200, 26214400, 52428800,
			104857600, 209715200, 419430400, 838860800,
			1677721600};
    final long minPointForLevelQword[] = {3355443200L, 6710886400L, 13421772800L, 26843545600L};
	
	
	// when you log in you get a visit bonus
	int visitBonusMoney = 123456;
	int visitBonusElementsType = 2;
	int visitBonusElements = 5;
	int visitBonusElementsMultiplier = 4;
	int visitBonusAvatarMoney = 55;
	
	// 1 - highest level player in the game/server
	int playerRank = 10;
	
	int response = 1;
	
	// Registry/IOSPK/Version
	int spVersion = 11;
	int ioProtectVersion = 12;
	int survivalprojectVersion = 13;
	
	String username;
	
	public JoinLobbyHandler(LobbyServer lobbyServer, UserSession tcpServer, byte[] messageBytes) {
		super(lobbyServer, tcpServer, messageBytes);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void interpretBytes() {
		username = input.getString(0x14);
		System.out.println(input.getString(0x14));
		System.out.println(input.getString(0x21));
		System.out.println(input.getString(0x3A));
		System.out.println("Process ID: " + input.getInt(0x44));
		System.out.println("SP version: " + input.getInt(0x48));
		System.out.println("ioProtect version : " + input.getInt(0x4C));
		System.out.println("Survival project version: " + input.getInt(0x50));
		System.out.println(input.getInt(0x54));
		System.out.println(input.getInt(0x58));
	}

	@SuppressWarnings("unchecked")
	@Override
	public void afterSend() throws IOException, SQLException {
		// Change the population
		Session session = Database.getSession();
		session.beginTransaction();
		lobbyServer.server.setPopulation(lobbyServer.getUserSessions().size());
		session.update(lobbyServer.server);
		session.getTransaction().commit();
		session.close();
		
		// Get the memos
		session = Database.getSession();
		session.beginTransaction();
		List<Memo> memos = session.createQuery("FROM Memo WHERE toUsername = :toUsername").setParameter("toUsername", userSession.getUser().getUsername()).list();
		
		for (Memo memo : memos) {
			userSession.sendMessage(new MemoArrivalHandler(lobbyServer, userSession).getResponse(memo));
			session.delete(memo);
		}
		
		session.getTransaction().commit();
		session.close();
		
		// Get the gifts
		session = Database.getSession();
		session.beginTransaction();
		List<Gift> gifts = session.createQuery("FROM Gift WHERE toUsername = :toUsername").setParameter("toUsername", userSession.getUser().getUsername()).list();
		
		for (Gift gift : gifts) {
			if (gift.getGiftType() == 1) {
				Card card = new Card(gift.getCardID(), gift.getCardPremiumDays(), gift.getCardLevel(), gift.getCardSkill());
				sendTCPMessage(new CardGiftReceivedHandler(lobbyServer, userSession).getResponse(userSession, card));
			}
			else if (gift.getGiftType() == 2 || gift.getGiftType() == 3) {
				sendTCPMessage(new ElementsOrCodeGiftReceivedHandler(lobbyServer, userSession).getResponse(userSession.getUser().getUsername(), userSession, gift.getGiftType(), gift.getAmount()));
			}
			
			session.delete(gift);
		}
		
		session.getTransaction().commit();
		session.close();
		
		if (userSession.getUser().getPlayerLevel() >= 29) {
			lobbyServer.sendBroadcastGameMessage(userSession, new GameNotificationHandler(lobbyServer.gameServer).getResponse(
					GameAnnouncementResult.JOIN_SERVER, userSession.getUser().getUsername(), userSession.getUser().getPlayerLevel(), 0));
		}
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.JOIN_LOBBY_RESPONSE);
		output.putInt(0x14, response); // 0x14
		output.putString(0x18, userSession.getUser().getGuildName()); // 0x18
		output.putString(0x25, userSession.getUser().getGuildDuty()); // 0x25
		output.putBoolean(0x32, userSession.getUser().isMale());
		output.putInt(0x34, userSession.getUser().getPlayerWins()); // 0x34
		output.putInt(0x38, userSession.getUser().getPlayerLoses()); // 0x38
		output.putInt(0x3c, 1); // not used. getting read but nothing is done with its value
		output.putInt(0x40, 1);
		output.putInt(0x44, userSession.getUser().getPlayerKOs()); // 0x44
		output.putInt(0x48, userSession.getUser().getPlayerDowns()); // 0x48
		output.putInt(0x50, 1); // 0x50
		output.putInt(0x54, 1); // 0x54
		output.putInt(0x58, 1); // not sure if this should even be here
		output.putInt(0x5c, 1); // 0x5c
		output.putLong(0x60, userSession.getUser().getPlayerExperience()); // 0x60
		output.putLong(0x68, userSession.getUser().getPlayerCode()); // 0x68
		output.putLong(0x70, userSession.getUser().getAvatarMoney()); // 0x70
		output.putInt(0x78, userSession.getUser().getPlayerLevel()); // 0x78
		output.putInt(0x7C, userSession.getUser().getUsuableCharacterCount()); // 0x7c
		output.putInts(0x80, userSession.getUser().getScrolls());
		output.putInts(0x8C, userSession.getUser().getWhiteCards()); // 0x8c
		output.putInt(0x9C, userSession.getUser().getUserType()); // which channels you can connect
		
		for (int i = 0; i < 96; i++) {
			if (userSession.getUser().getItemID(i) != -1) {
				// If the item exists, put 1 to mark it as "exists"
				output.putByte(0xA0 + i, (byte) 1);
			}
		}
		
		for (int i = 0; i < 96; i++) {
			output.putInt(0x280 + i * 4, userSession.getUser().getItemID(i));
		}
		
		for (int i = 0; i < 96; i++) {
			output.putInt(0x400 + i * 4, userSession.getUser().getItemPremiumDays(i));
		}
		
		for (int i = 0; i < 96; i++) {
			output.putInt(0x580 + i * 4, userSession.getUser().getItemLevel(i));
		}
		
		for (int i = 0; i < 96; i++) {
			output.putInt(0x700 + i * 4, userSession.getUser().getItemSkill(i));
		}
		
		output.putInt(0x880, userSession.getUser().getPlayerInventorySlots()); // 0x880
		output.putInts(0x884, minPointForeveDword); // 0x884
		output.putLongs(0x8F8, minPointForLevelQword); //0x8F8
		output.putInt(0x918, userSession.getUser().getChannelType()); // 0x918
		output.putInt(0x91C, spVersion); // 0x91c
		output.putInt(0x920, ioProtectVersion); // 0x920
		output.putInt(0x924, survivalprojectVersion); // 0x924
		output.putInt(0x928, visitBonusMoney);
		output.putInt(0x92C, visitBonusElementsType);
		output.putInt(0x930, visitBonusElements);
		output.putInt(0x934, visitBonusElementsMultiplier);
		output.putInt(0x938, visitBonusAvatarMoney);
		output.putBytes(0x93C, userSession.getUser().getPlayerEventFlags()); // 0x93C
		output.putInt(0x944, playerRank); // 0x944
		output.putByte(0x948, (byte) 0); // if it's 1 then the next strings are used somewhere: "Server recapturing battle has ended", "Server recapturing in progress", "Server capturing battle continue next Tuesday". something with packet 0x1132
		output.putInt(0x94C, LobbyServer.MAX_ROOMS); // 0x94c
		output.putInt(0x950, userSession.getUser().getFootIndex());
		output.putInt(0x954, userSession.getUser().getBodyIndex());
		output.putInt(0x958, userSession.getUser().getHand1Index());
		output.putInt(0x95C, userSession.getUser().getHand2Index());
		output.putInt(0x960, userSession.getUser().getFaceIndex());
		output.putInt(0x964, userSession.getUser().getHairIndex());
		output.putInt(0x968, userSession.getUser().getHeadIndex());
		output.putInt(0x96C, 1); // something with guild mark
		output.putInt(0x970, 1); // 0x970 = field_A0 in Login.something with event flag 5 and 7. special values: 244h, 230h
		output.putInt(0x974, userSession.getUser().getPlayerType()); // 0x974
		output.putByte(0x978, (byte) 0); // boolean. 1 = the account will be deleted after 15 inactive days.
		
		return output.toArray();
	}

	@Override
	public void processMessage() throws SQLException, IOException {
		userSession.setUser(User.loadUser(username, lobbyServer));
		userSession.getUser().setChannelType(lobbyServer.server.getChannelType() + 1);
		lobbyServer.moveToCorrectPlace();
		
		// This is here because the mission level needs to get sent before the join lobby response.
		lobbyServer.onJoinLobby(userSession);
	}
}
