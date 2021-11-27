/**
 * @author Hanlin Zou
 * Represents a hand of cards of a gambler in Ena
 * variables:
 * 		cards -- the list of current cards
 * 		bet -- amount of money the gambler make
 * 		isStand -- whether gambler declared stand
 * 			on this hand
 * 		bust -- whether the hand is bust
 * Constructors:
 * 		No-arg constructor generate a hand with no
 * 			money bet on, which is meaningless.
 * 		Standard constructor takes an integer as its
 * 			bet. generates an empty hand.
 */
public class TriHand extends Hand{
	
	private boolean bust;
	
	/**
	 * Standard constructor
	 * @param bet The amount of money a gambler
	 *            make on
	 */
	public TriHand(int bet) {
		super(bet);
		this.bust = false;
	}
	
	/**
	 * No-arg constructor
	 */
	public TriHand() {
		super(0);
	}

	/**
	 * get total number of cards in the hand
	 * @return number of cards
	 */
	public int getCardsNum() {
		return cards.size();
	}

	/**
	 * set the hand is bust
	 */
	public void setBust() {
		this.bust  = true;
	}

	public void resetBust(){
		this.bust = false;
	}
	/**
	 * get if hand is bust
	 * @return whether the hand busts
	 */
	public boolean getBust() {
		return bust;
	}

	/**
	 * When user wants a hit
	 * @param deck deck of cards
	 */
	@Override
	public void hit(Deck deck) {
		// TODO Auto-generated method stub
		cards.add(deck.deal());
		if(calTotal()>31) { setBust(); }
		else if(calTotal() ==  31) { setStand();}
	}

	/**
	 * calculate the total points of a hand
	 * @return total points of a hand
	 */
	@Override
	public int calTotal() {
		// TODO Auto-generated method stub
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
		if(numAce != 0 && total > 31)
			total -= 10;
		return total;
	}
}
