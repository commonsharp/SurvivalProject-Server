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
import net.objects.User;
import tools.ExtendedByteBuffer;

//change to card fusion
public class FusionHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x58;
	
	protected int cardIndex;
	protected int fusionType; // 1 - level. 2 - skill
	protected int unknown;
	
	protected int result;
	RemovedType removedType;
	int gainedElementsAmount;
	
	private enum RemovedType {
		/*
		 * 34 - this one make you lose 1 use if you use a certain. values:
		 * 3 - 2013 - level
		 * 4 - 2014 - skill
		 * 5 - 2015 - skill 1
		 * 6 - 2016 - skill 2
		 * 7 - 2018 - skill 1-1
		 * 8 - 2019 - skill 2-1
		 * 9 - 2020 - skil 2-2
		 */
		
		NON(0),
		GAIN_ELEMENTS(1),
		LEVEL(3),
		SKILL(4),
		SKILL1(5),
		SKILL2(6),
		SKILL1_1(7),
		SKILL2_1(8),
		SKILL2_2(9);
		
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
		cardIndex = input.getInt(0x14);
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
		
		Card card = userSession.getUser().getCard(cardIndex);
		
		output.putInt(0x14, cardIndex); // item index
		output.putInt(0x1C, result);
		output.putInt(0x20, card.getCardLevel()); // new level
		output.putInt(0x24, card.getCardSkill()); // new skill
		output.putInt(0x28, card.getCardID()); // item ID
		output.putInt(0x2C, card.getCardPremiumDays()); // premium days
		output.putInt(0x30, userSession.getUser().getRoomSlot()); // player slot
		output.putInt(0x34, removedType.getValue());
		output.putInt(0x38, gainedElementsAmount);
		output.putLong(0x40, userSession.getUser().getPlayerCode()); // new money amount
		output.putInts(0x48, userSession.getUser().getWhiteCards()); // new elements amount
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		if (userSession.getUser().isInRoom() || userSession.getUser().isInGame()) {
			lobbyServer.sendRoomMessage(userSession, getResponse(), false);
		}
		
		Card card = userSession.getUser().getCard(cardIndex);
		
		if (result == 1 && card.getCardLevel() >= 6) {
			lobbyServer.sendBroadcastGameMessage(userSession, new GameNotificationHandler(lobbyServer.gameServer).getResponse(
					GameAnnouncementResult.CARD_UPGRADE, userSession.getUser().getUsername(), card.getCardID(), card.getCardLevel()));
		}
	}

	@Override
	public void processMessage() throws SQLException {
		Card card = userSession.getUser().getCard(cardIndex);
		removedType = RemovedType.NON;
		int removedIndex = -1;
		
		// level fusion (normal)
		if (fusionType == 1) {
			userSession.getUser().setWhiteCard(card.getElement() - 1, userSession.getUser().getWhiteCard(card.getElement() - 1) - CurrencyHelper.getLevelFusionSpirits(card));
			userSession.getUser().setPlayerCode(userSession.getUser().getPlayerCode() - CurrencyHelper.getLevelFusionCode(card));
			
			int successPercentage = CurrencyHelper.getLevelFusionLevelSuccessRate(card);
			
			if ((int)(Math.random() * 100 + 1) <= successPercentage) {
				result = 1;
				card.setCardLevel(card.getCardLevel() + 1);
			}
			else {
				gainedElementsAmount = CurrencyHelper.getLevelFusionSpirits(card) / 5;
				userSession.getUser().setWhiteCard(card.getElement() - 1, userSession.getUser().getWhiteCard(card.getElement() - 1) + gainedElementsAmount);
				removedType = RemovedType.GAIN_ELEMENTS;
				result = 5;
				card.setCardSkill(card.getRandomSkill(0));
			}
		}
		// skill fusion (normal)
		else if (fusionType == 2) {
			userSession.getUser().setWhiteCard(card.getElement() - 1, userSession.getUser().getWhiteCard(card.getElement() - 1) - CurrencyHelper.getSkillFusionSpirits(card));
			userSession.getUser().setPlayerCode(userSession.getUser().getPlayerCode() - CurrencyHelper.getSkillFusionCode(card));
			
			result = 5;
			card.setCardSkill(card.getRandomSkill(0));
		}
		// level fusion card
		else if (fusionType == 3) {
			removedType = RemovedType.LEVEL;
			int successPercentage = CurrencyHelper.getLevelFusionLevelSuccessRate(card);
			
			if ((int)(Math.random() * 100 + 1) <= successPercentage) {
				result = 1;
				card.setCardLevel(card.getCardLevel() + 1);
			}
			else {
				result = 3;
				card.setCardSkill(card.getRandomSkill(0));
			}
			
			removedIndex = removeCard(Card.LEVEL_FUSION);
		}
		// skill fusion card
		else if (fusionType == 4) {
			result = 5;
			removedType = RemovedType.SKILL;
			card.setCardSkill(card.getRandomSkill(0));
			removedIndex = removeCard(Card.SKILL_FUSION);
		}
		// only skill 1 card
		else if (fusionType == 5) {
			result = 5;
			removedType = RemovedType.SKILL1;
			card.setCardSkill(card.getRandomSkill(1));
			removedIndex = removeCard(Card.SKILL1_FUSION);
		}
		// only skill 2 card
		else if (fusionType == 6) {
			result = 5;
			removedType = RemovedType.SKILL2;
			card.setCardSkill(card.getRandomSkill(2));
			removedIndex = removeCard(Card.SKILL2_FUSION);
		}
		// skill 1-1
		else if (fusionType == 7) {
			result = 5;
			removedType = RemovedType.SKILL1_1;
			card.setCardSkill(card.getRandomSkill(1));
			removedIndex = removeCard(Card.SKILL_1_1_FUSION);
		}
		// skill 2-1
		else if (fusionType == 8) {
			result = 5;
			removedType = RemovedType.SKILL2_1;
			card.setCardSkill(card.getRandomSkill(2));
			removedIndex = removeCard(Card.SKILL_2_1_FUSION);
		}
		
		// skill 2-2
		else if (fusionType == 9) {
			result = 5;
			removedType = RemovedType.SKILL2_2;
			card.setCardSkill(card.getRandomSkill(3));
			removedIndex = removeCard(Card.SKILL_2_2_FUSION);
		}
		
		User.saveUser(userSession.getUser());
		
		if (removedIndex != -1) {
			User.saveCards(userSession.getUser(), cardIndex, removedIndex);
		}
		else {
			User.saveCards(userSession.getUser(), cardIndex);
		}
	}
	
	private int removeCard(int cardID) {
		int i;
		
		for (i = 0; i < 96; i++) {
			if (userSession.getUser().getCard(i) != null && userSession.getUser().getCard(i).getCardID() == cardID) {
				userSession.getUser().getCard(i).setCardLevel(userSession.getUser().getCard(i).getCardLevel() - 1);
				
				if (userSession.getUser().getCard(i).getCardLevel() == 0) {
					userSession.getUser().removeCard(i);
				}
				
				break;
			}
		}
		
		return i;
	}
}
