import java.util.ArrayList;

/**
 * @author Zhaoyu Yin
 * Represents a hand of cards of a gambler
 * variables:
 * 		cards -- the list of current cards
 * 		bet -- amount of money the gambler make
 * 		isStand -- whether gambler declared stand
 * 			on this hand
 * Constructors:
 * 		No-arg constructor generate a hand with no
 * 			money bet on, which is meaningless.
 * 		Standard constructor takes an integer as its
 * 			bet. generates an empty hand.
 */

public abstract class Hand {
	protected ArrayList<Card> cards;
	protected int bet;
	protected boolean isStand;

	/**
	 * No-arg constructor
	 */
	public Hand() {
		this(0);
	}
	
	/**
	 * Standard constructor
	 * @param bet The amount of money a gambler
	 *            make on
	 */
	public Hand(int bet) {
		setBet(bet);
		cards = new ArrayList<>();
		this.isStand = false;
	}
	
	/**
	 * set bet of a hand
	 * @param bet amount of money
	 */
	public void setBet(int bet) {
		this.bet = bet;
	}

	/**
	 * set the hand is finished
	 */
	public void setStand() {
		this.isStand = true;
	}

	/**
	 * get status of isStand
	 * @return whether the hand is finished
	 */
	public boolean getStand() {
		return isStand;
	}
	
	/**
	 * get status of hand
	 * @return whether the hand is settled due
	 * 		to bust, natural blackjack, or finished
	 */
	public boolean isLocked() {
		return bet == 0 || getStand();
	}

	/**
	 * get the bet of the hand
	 * @return the bet of the hand
	 */
	public int getBet() {
		return bet;
	}
	
	/**
	 * empty a hand of a player
	 */
	public void clearHand() {
		cards.clear();
	}
	
	/**
	 * get a card from a hand
	 * @param index index of a card in the hand
	 * @return a card
	 */
	public Card getCard(int index) {
		return cards.get(index);
	}

	/**
	 * add a card to hand
	 * @param card a card
	 */
	public void addCard(Card card) {
		cards.add(card);
	}

	/**
	 * usually called when splitting a hand
	 */
	public void removeCard() {
		cards.remove(cards.size()-1);
	}
	
	/**
	 * display the cards that is visible to everyone
	 * @param isDealer whether the people is dealer
	 */
	public void cardsPrint(boolean isDealer) {
		// TODO Auto-generated method stub
		if(!isDealer) {
			for (Card card : cards) {
				System.out.println(card.getSuitName() + " " + card.getValueName());
			}
		}
		else {
			for(int i = 1;i < cards.size(); i++){
			    System.out.println(cards.get(i).getSuitName() + " " + cards.get(i).getValueName());
			}
		}
	}
	
	public abstract void hit(Deck deck);
	public abstract int calTotal();
}
