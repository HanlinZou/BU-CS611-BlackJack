import java.util.*;

/**
 * @author Hanlin Zou
 * Representative of a gambler in blackjack.
 * variables:
 * 		Money -- Amount of money the gambler
 * 			has at beginning of the game.
 * 		stillHasActiveHand -- whether a gambler
 * 			can still make a call on any hand
 * 		hands -- list of hands of the gambler
 * Constructor:
 * 		No-arg constructor will generate a player
 * 			named Gambler with no other meaningful
 * 			data.
 * 		Standard constructor will generate a single
 * 			player with money declared, who fight
 * 			for himself against dealer.
 */

public class BjGambler extends Player{
	
	private int Money;
	private ArrayList<BjHand> hands;

	// Constructor chaining
	public BjGambler(String playerName, String teamName, int teamId, int money) {
		super(playerName, teamName, teamId);
		setMoney(money);
		hands = new ArrayList<>();
		hands.add(new BjHand());
	}
	
	public BjGambler(String playerName, int money) {
		this(playerName, "No team", 0, money);
	}
	
	public BjGambler() {
		super("Gambler");
	}

	/**
	 * settle a hand when win the dealer
	 * @param handIndex index of the winning hand
	 */
	public void win(int handIndex) {
		setMoney(Money + 3 * hands.get(handIndex).getBet());
		hands.get(handIndex).setBet(0);
	}

	/**
	 * settle a hand when busted
	 * @param handIndex index of the busted hand
	 */
	public void bust(int handIndex) {
		hands.get(handIndex).setBet(0);
	}

	/**
	 * settle a hand when push
	 * @param handIndex index of the push hand
	 */
	public void push(int handIndex) {
		setMoney(Money + hands.get(handIndex).getBet());
		hands.get(handIndex).setBet(0);
	}

	/**
	 * settle hands when dealer has natural
	 */
	public void emptyHand(){
		hands.clear();
	}

	/**
	 * set the new amount of money of a gambler
	 * @param money new money
	 */
	public void setMoney(int money) {
		this.Money = money;
	}

	/**
	 * get the number of hands of current gambler
	 * @return the number of hands of current gambler
	 */
	public int handsNum() {
		return hands.size();
	}

	/**
	 * check whether the gambler still has a hand
	 * 		of cards to operate
	 * @return whether a gambler can make a call on
	 * 		any hand
	 */
	public boolean checkHasActiveHand() {
		for (BjHand hand : hands) {
			if (!hand.isLocked()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * return one hand of current gambler
	 * @param index index of a hand in the list
	 * @return the indexed hand in the list
	 */
	public BjHand getHand(int index) {
		return hands.get(index);
	}

	/**
	 * get how much the gambler has left
	 * @return amount of money the gambler has
	 */
	public int getMoney() {
		return this.Money;
	}

	@Override
	public void handReset() {
		// TODO Auto-generated method stub
		hands.clear();
		hands.add(new BjHand());
	}

	/**
	 * check whether the first hand is natural
	 * @return	whether the first hand is natural
	 */
	public boolean isNatural() {
		return hands.get(0).calTotal() == 21;
	}

	/**
	 * add another hand, usually called when split
	 * @param hand the new hand
	 */
	public void addHand(BjHand hand) {
		hands.add(hand);
	}

	/**
	 * print out all hands of a gambler
	 */
	public void handsPrint() {
		for (int i = 0; i < hands.size(); i++) {
			System.out.println("Hand " + (i + 1) + ": ");
			hands.get(i).cardsPrint(false);
		}
	}
}
