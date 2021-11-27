/**
 * @author Zhaoyu Yin
 * Representative of a dealer in blackjack.
 * variables:
 * 		hand -- a hand of cards
 * Constructor:
 * 		No-arg constructor will generate a player
 * 			named Gambler with no other meaningful
 * 			data.
 * 		Standard constructor will generate a single
 * 			player with money declared, who fight
 * 			for himself against dealer.
 */
public class Dealer extends Player{
	
	private BjHand hand;

	// Constructor chaining
	public Dealer(String pName, String tName, int tid) {
		super(pName, tName, tid);
		hand = new BjHand();
	}
	
	public Dealer(String pName) {
		this(pName, "No team", 0);
	}
	
	public Dealer() {
		this("Dealer");
	}

	@Override
	public void handReset() {
		// TODO Auto-generated method stub
		hand.clearHand();
	}

	// Add a card to dealer's hand
	public void addCard(Card card) {
		hand.addCard(card);
	}

	// print out dealer's hand
	public void handPrint(boolean dealerAction) {
		hand.cardsPrint(dealerAction);
	}

	// get score of dealer's hand
	public int getScore() {
		return hand.calTotal();
	}

	// whether dealer has natural
	public boolean isNatural() {
		return hand.calTotal() == 21;
	}

	// final phase of game
	public void dealerPlay(Deck deck) {
		System.out.println("\nDealer's turn now\nDealer has the following cards:");
		this.handPrint(false);

		int one = 1;
		while(hand.calTotal() < 17) {
			// only print once while hitting cards
			if(one == 1) {
				System.out.println("Dealer hit");
				one--;
			}
			hand.addCard(deck.deal());
		}

		System.out.println("Dealer has the following cards:");
		this.handPrint(false);

		if(hand.calTotal() > 21) {
			System.out.println("Dealer busts. ");
		}
		else {
			System.out.println("Now compare with players' results:");
		}
	}
}
