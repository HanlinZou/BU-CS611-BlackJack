/**
 * @author Hanlin Zou
 * Represents a hand of cards of a gambler in bj
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

public class BjHand extends Hand{

	/**
	 * Standard constructor
	 * @param bet The amount of money a gambler
	 *            make on
	 */
	public BjHand(int bet) {
		super(bet);
	}
	
	/**
	 * No-arg constructor
	 */
	public BjHand() {
		super(0);
	}

	/**
	 * When user wants a hit
	 * @param deck deck of cards
	 */
	public void hit(Deck deck) {
		cards.add(deck.deal());
		// check if current hand busts or has a blackjack
		if(calTotal()>21)
			setBet(0);
		else if(calTotal() ==  21)
			setStand();
	}

	/**
	 * check whether a hand can split
	 * @return whether a hand can split
	 */
	public boolean canSplit() {
		return cards.size() == 2 && cards.get(0).getIndex() == cards.get(1).getIndex();
	}

	/**
	 * when player wants a double on the hand
	 * @param deck the deck of card
	 */
	public void doubleUp(Deck deck) {
		bet *= 2;
		hit(deck);
		setStand();
	}

	/**
	 * calculate the total points of a hand
	 * @return total points of a hand
	 */
	public int calTotal() {
		int total = 0;
		int numAce = 0;
		for (Card card : cards) {
			int value = card.getIndex();
			if(value == 1) {
				total += 11;
				numAce++;
			}
			else total += Math.min(value, 10);
		}
		if(numAce != 0 && total > 21) {
			total -= 10 * numAce;
		}
		return total;
	}
}
