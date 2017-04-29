package net.objects;

public class Trade {
	public String firstUsername;
	public String secondUsername;
	
	public long firstCode, secondCode;
	public Card[] firstCards, secondCards;
	
	public TradeState firstState, secondState;
	
	public enum TradeState {
		FIRST,
		AFTER_CONFIRM,
		FINAL_CONFIRM
	}
	
	public Trade(String firstUsername, String secondUsername) {
		this.firstUsername = firstUsername;
		this.secondUsername = secondUsername;
		firstCards = new Card[4];
		secondCards = new Card[4];
		firstState = TradeState.FIRST;
		secondState = TradeState.FIRST;
	}
}
