package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import game.handlers.GameNotificationHandler;
import game.handlers.GameNotificationHandler.GameAnnouncementResult;
import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.CurrencyHelper;
import net.Messages;
import net.UserSession;
import net.objects.Card;
import tools.ExtendedByteBuffer;

//change to card fusion
public class FusionHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x979;
	
	protected int itemIndex;
	protected int fusionType; // 1 - level. 2 - skill
	protected int unknown;
	
	protected int result;
	RemovedType removedType;
	
	private enum RemovedType {
		/*
		 * 34 - this one make you lose 1 use if you use a certain. values:
		 * 3 - 2013
		 * 4 - 2014
		 * 5 - 2015 - skill 1
		 * 6 - 2016 - skill 2
		 * 7 - 2018
		 * 8 - 2019
		 * 9 - 2020
		 */
		
		NON(0),
		SKILL1(5),
		SKILL2(6);
		
		private int value;
		
		private RemovedType(int value) {
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
	}
	
	public FusionHandler(LobbyServer lobbyServer, UserSession tcpServer, byte[] messageBytes) {
		super(lobbyServer, tcpServer, messageBytes);
	}

	@Override
	public void interpretBytes() {
		itemIndex = input.getInt(0x14);
		fusionType = input.getInt(0x18);
		unknown = input.getInt(0x1C);

		System.out.println("fusion type: " + fusionType);
		System.out.println("unknown in fusion : " + unknown);
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.FUSION_RESPONSE);
		// 0x1C = result.
		// 1 - level up fusion successful
		// 4 - korean message. not enough elements (or code)
		// 5 - skill fusion successful
		// 7 - "can't combine items in the chatting room".
		// 8 - "can't fuse PC room cards"
		// 9 - "Current event has been exited so there is not enough Codes"
		// others - something else
		
		Card card = userSession.getUser().getCard(itemIndex);
		
		output.putInt(0x14, itemIndex); // item index
		output.putInt(0x1C, result);
		output.putInt(0x20, card.getLevel()); // new level
		output.putInt(0x24, card.getSkill()); // new skill
		output.putInt(0x28, card.getId()); // item ID
		output.putInt(0x2C, card.getPremiumDays()); // premium days
		output.putInt(0x30, userSession.getUser().roomSlot); // player slot
		/*
		 * 34 - this one make you lose 1 use if you use a certain. values:
		 * 3 - 2013
		 * 4 - 2014
		 * 5 - 2015 - skill 1
		 * 6 - 2016 - skill 2
		 * 7 - 2018
		 * 8 - 2019
		 * 9 - 2020
		 */
		output.putInt(0x34, removedType.getValue());
		output.putInt(0x38, 2); // I think this is only used in game (game state 4)
		output.putInt(0x3C, 3);
		output.putLong(0x40, userSession.getUser().playerCode); // new money amount
		output.putInts(0x48, userSession.getUser().whiteCards); // new elements amount
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		if (userSession.getUser().isInRoom || userSession.getUser().isInGame) {
			lobbyServer.sendRoomMessage(userSession, getResponse(), false);
		}
		
		Card card = userSession.getUser().getCard(itemIndex);
		
		if (result == 1 && card.getLevel() >= 6) {
			lobbyServer.sendBroadcastGameMessage(userSession, new GameNotificationHandler(lobbyServer.gameServer).getResponse(
					GameAnnouncementResult.CARD_UPGRADE, userSession.getUser().username, card.getId(), card.getLevel()));
		}
	}

	@Override
	public void processMessage() throws SQLException {
		Card card = userSession.getUser().getCard(itemIndex);
		removedType = RemovedType.NON;
		
		// level fusion
		if (fusionType == 1) {
			userSession.getUser().whiteCards[card.getElement() - 1] -= CurrencyHelper.getLevelFusionSpirits(card);
			userSession.getUser().playerCode -= CurrencyHelper.getLevelFusionCode(card);
			
			int successPercentage = CurrencyHelper.getLevelFusionLevelSuccessRate(card);
			
			if ((int)(Math.random() * 100 + 1) <= successPercentage) {
				result = 1;
				card.setLevel(card.getLevel() + 1);
			}
			else {
				result = 2;
				card.setSkill(card.getRandomSkill(0));
			}
		}
		// skill fusion
		else if (fusionType == 2) {
			userSession.getUser().whiteCards[card.getElement() - 1] -= CurrencyHelper.getSkillFusionSpirits(card);
			userSession.getUser().playerCode -= CurrencyHelper.getSkillFusionCode(card);
			
			result = 5;
			card.setSkill(card.getRandomSkill(0));
		}
		// only skill 1 card
		else if (fusionType == 5) {
			result = 5;
			removedType = RemovedType.SKILL1;
			card.setSkill(card.getRandomSkill(1));
			
			for (int i = 0; i < 96; i++) {
				if (userSession.getUser().cards[i] != null && userSession.getUser().cards[i].getId() == Card.SKILL1_FUSION) {
					userSession.getUser().cards[i].setLevel(userSession.getUser().cards[i].getLevel() - 1);
					
					if (userSession.getUser().cards[i].getLevel() == 0) {
						userSession.getUser().cards[i] = null;
					}
					
					break;
				}
			}
		}
		// only skill 2 card
		else if (fusionType == 6) {
			result = 5;
			removedType = RemovedType.SKILL2;
			card.setSkill(card.getRandomSkill(2));
			
			for (int i = 0; i < 96; i++) {
				if (userSession.getUser().cards[i] != null && userSession.getUser().cards[i].getId() == Card.SKILL2_FUSION) {
					userSession.getUser().cards[i].setLevel(userSession.getUser().cards[i].getLevel() - 1);
					
					if (userSession.getUser().cards[i].getLevel() == 0) {
						userSession.getUser().cards[i] = null;
					}
					
					break;
				}
			}
		}
		
		userSession.getUser().saveUser();
	}
}
