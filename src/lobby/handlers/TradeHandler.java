package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import net.objects.Card;
import net.objects.Trade;
import net.objects.Trade.TradeState;
import tools.ExtendedByteBuffer;
import tools.HexTools;

public class TradeHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x98;
	
	protected String fromUsername;
	protected String toUsername;
	protected int actionType;
	protected long code;
	protected Card[] cards;
	protected int field94;
	byte[] bytes;
	int response;
	
	Trade trade;
	
	public TradeHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
		bytes = messageBytes;
		System.out.println(HexTools.integerToHexString(messageBytes.length));
	}

	@Override
	public void interpretBytes() {
		cards = new Card[4];
		fromUsername = input.getString(0x14);
		actionType = input.getInt(0x24);
		toUsername = input.getString(0x28);
		code = input.getLong(0x48);
		field94 = input.getInt(0x94);
		System.out.println("Action : " + actionType);
		/*
		 * 1 - start trade
		 * 2 - Can not find player %s
		 * 3 - Player %s in denied trade.
		 * 4 - The player is trading with another player
		 * 5 - cancel
		 * 6 - Trade contents has &#xA;been altered. &#xA;Please check again.
		 * 7 - clicked on confirm
		 * 8 - clicked on change rule (after confirm)
		 * 9 - clicked on final confirm
		 * 10 - clicked on cancel (after the final confirm)
		 * 11 - Currently not trading.
		 * 12 - Can not trade while constructing shop OR Player %s is constructing shop.
		 * 13 - The exchange is not possible from&#xA;the chatting window
		 * 14 - Trade cancelled. &#xA;Item in trade has been change by  &#xA;either player, try again after &#xA;decided on the items to be trade.
		 * 15 - Trade cancelled. &#xA;Because you will have more &#xA;Code amount than allowed per player. OR Trade cancelled. Receiver have more&#xA;Code amount than allowed per player.
		 * 16 - Demo player. Demo players can't trade with other users
		 * 17 - The player is a simple loon who can't be traded dafuq google??
		 */
		
		for (int i = 0; i < 4; i++) {
			cards[i] = new Card(
					input.getInt(0x38 + 4 * i), // index
					input.getInt(0x38 + 4 * i + 0x1C), // id
					input.getInt(0x38 + 4 * i + 0x2C), // premium days
					input.getInt(0x38 + 4 * i + 0x3C), // level
					input.getInt(0x38 + 4 * i + 0x4C)); // skill
		}
		
		System.out.println("Code: " + input.getLong(0x48));
		System.out.println("Available slots: " + input.getInt(0x50)); // this one is being used in start trade only. This is the available slots (should be 4)
		
		System.out.println("0x94: " + input.getInt(0x94));
	}

	@Override
	public void processMessage() {
		response = actionType;
		
		// Start a new trade
		if (actionType == 1) {
			if (!lobbyServer.getRoom(userSession.getUser().roomIndex).containsPlayer(toUsername)) {
				response = 2;
				return;
			}
			if (lobbyServer.findTrade(toUsername) != null) {
				response = 4;
				return;
			}
			if (!lobbyServer.findUserSession(toUsername).getUser().isInGame) {
				response = 13;
				return;
			}
			trade = new Trade(fromUsername, toUsername);
			lobbyServer.addTrade(trade);
		}
		// User left during a trade
		else if (actionType == 2) {
			lobbyServer.removeTrade(lobbyServer.findTrade(fromUsername));
		}
		// Trade canceled
		else if (actionType == 5 || actionType == 14 || actionType == 15) {
			lobbyServer.removeTrade(lobbyServer.findTrade(fromUsername));
		}
		// New trade content
		else if (actionType == 6) {
			trade = lobbyServer.findTrade(fromUsername);

			Card[] cards = fromUsername.equals(trade.firstUsername) ? trade.firstCards : trade.secondCards;

			for (int i = 0; i < 4; i++) {
				cards[i] = this.cards[i];
			}

			if (fromUsername.equals(trade.firstUsername)) {
				trade.firstCode = code;
			}
			else {
				trade.secondCode = code;
			}
		}
		// Confirm or cancel after final confirm
		else if (actionType == 7 || actionType == 10) {
			trade = lobbyServer.findTrade(fromUsername);
			
			if (fromUsername.equals(trade.firstUsername)) {
				trade.firstState = TradeState.AFTER_CONFIRM;
			}
			else {
				trade.secondState = TradeState.AFTER_CONFIRM;
			}
		}
		// Cancel after first confirm
		else if (actionType == 8) {
			trade = lobbyServer.findTrade(fromUsername);
			
			if (fromUsername.equals(trade.firstUsername)) {
				trade.firstState = TradeState.FIRST;
			}
			else {
				trade.secondState = TradeState.FIRST;
			}
		}
		// Final confirm
		else if (actionType == 9) {
			trade = lobbyServer.findTrade(fromUsername);
			
			if (fromUsername.equals(trade.firstUsername)) {
				trade.firstState = TradeState.FINAL_CONFIRM;
			}
			else {
				trade.secondState = TradeState.FINAL_CONFIRM;
			}
		}
	}

	@Override
	public byte[] getResponse() {
		return getResponse(response);
	}
	
	public byte[] getResponse(int type) {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.TRADE_REQUEST);
		
		output.putString(0x14, fromUsername);
		output.putInt(0x24, type);
		output.putString(0x28, toUsername);
		
		if (trade != null) {
			Card[] cards = fromUsername.equals(trade.firstUsername) ? trade.firstCards : trade.secondCards;
			
			for (int i = 0; i < 4; i++) {
				if (cards[i] != null) {
					output.putInt(0x38 + 4 * i, cards[i].getIndex());
					output.putInt(0x38 + 4 * i + 0x1C, cards[i].getId());
					output.putInt(0x38 + 4 * i + 0x2C, cards[i].getPremiumDays());
					output.putInt(0x38 + 4 * i + 0x3C, cards[i].getLevel());
					output.putInt(0x38 + 4 * i + 0x4C, cards[i].getSkill());
				}
				else {
					output.putInt(0x38 + 4 * i, -1);
					output.putInt(0x38 + 4 * i + 0x1C, -1);
					output.putInt(0x38 + 4 * i + 0x2C, -1);
					output.putInt(0x38 + 4 * i + 0x3C, -1);
					output.putInt(0x38 + 4 * i + 0x4C, -1);
				}
			}
		}
		else {
			for (int i = 0; i < 4; i++) {
				output.putInt(0x38 + 4 * i, -1);
				output.putInt(0x38 + 4 * i + 0x1C, -1);
				output.putInt(0x38 + 4 * i + 0x2C, -1);
				output.putInt(0x38 + 4 * i + 0x3C, -1);
				output.putInt(0x38 + 4 * i + 0x4C, -1);
			}
		}
		
		output.putLong(0x48, code);
		output.putInt(0x50, 4);
		output.putInt(0x94, field94);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		// This condition is used to send messages only when the trade was actually made
		if (response == 1 || response == 5 || response == 6 || response == 7 || response == 8 || response == 9 || response == 10 || response == 14 || response == 15) {
			lobbyServer.findUserSession(toUsername).sendMessage(getResponse(response));
		}
		
		if (trade != null && trade.firstState == TradeState.FINAL_CONFIRM && trade.secondState == TradeState.FINAL_CONFIRM) {
			lobbyServer.ongoingTrades.remove(trade);
			UserSession firstUserSession = lobbyServer.findUserSession(trade.firstUsername);
			UserSession secondUserSession = lobbyServer.findUserSession(trade.secondUsername);
			
			// Remove the cards
			for (int i = 0; i < 4; i++) {
				if (trade.firstCards[i] != null && trade.firstCards[i].getIndex() != -1) {
					if (trade.firstCards[i].getIndex() >= 10000) {
						firstUserSession.getUser().whiteCards[trade.firstCards[i].getIndex() / 10000 - 1] -= trade.firstCards[i].getIndex() % 10000;
					}
					else {
						firstUserSession.getUser().cards[trade.firstCards[i].getIndex()] = null;
					}
				}
				
				if (trade.secondCards[i] != null && trade.secondCards[i].getIndex() != -1) {
					if (trade.secondCards[i].getIndex() >= 10000) {
						secondUserSession.getUser().whiteCards[trade.secondCards[i].getIndex() / 10000 - 1] -= trade.secondCards[i].getIndex() % 10000;
					}
					else {
						secondUserSession.getUser().cards[trade.secondCards[i].getIndex()] = null;
					}
				}
			}
			
			// Add new cards
			for (int i = 0; i < 4; i++) {
				if (trade.firstCards[i] != null && trade.firstCards[i].getIndex() != -1) {
					if (trade.firstCards[i].getIndex() >= 10000) {
						secondUserSession.getUser().whiteCards[trade.firstCards[i].getIndex() / 10000 - 1] += trade.firstCards[i].getIndex() % 10000;
					}
					else {
						secondUserSession.getUser().cards[secondUserSession.getUser().getEmptyCardSlot()] = trade.firstCards[i];
					}
				}
				
				if (trade.secondCards[i] != null && trade.secondCards[i].getIndex() != -1) {
					if (trade.secondCards[i].getIndex() >= 10000) {
						firstUserSession.getUser().whiteCards[trade.secondCards[i].getIndex() / 10000 - 1] += trade.secondCards[i].getIndex() % 10000;
					}
					else {
						firstUserSession.getUser().cards[firstUserSession.getUser().getEmptyCardSlot()] = trade.secondCards[i];
					}
				}
			}
			
			firstUserSession.getUser().playerCode += trade.secondCode - trade.firstCode;
			secondUserSession.getUser().playerCode += trade.firstCode - trade.secondCode;
			
			firstUserSession.getUser().saveUser();
			secondUserSession.getUser().saveUser();
			
			firstUserSession.sendMessage(new TradeCompletedHandler(lobbyServer, firstUserSession).getResponse(firstUserSession, secondUserSession.getUser().username));
			secondUserSession.sendMessage(new TradeCompletedHandler(lobbyServer, secondUserSession).getResponse(secondUserSession, firstUserSession.getUser().username));
		}
	}
}
