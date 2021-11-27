/**
 * @author Hanlin Zou
 * Representative of a gambler in TriantaEna.
 * variables:
 * 		Money -- Amount of money the gambler
 * 			has at beginning of the game.
 * 		hand -- hand of the gambler
 * Constructor:
 * 		No-arg constructor will generate a player
 * 			named triGambler with no other meaningful
 * 			data.
 * 		Standard constructor will generate a single
 * 			player with money declared, who fight
 * 			for himself against banker.
 */
public class TriGambler extends Player{
	
	private int Money;
	private TriHand hand;

	// Constructor chaining
	public TriGambler(String playerName, String teamName, int teamId, int money) {
		super(playerName, teamName, teamId);
		setMoney(money);
		hand = new TriHand();
	}
	
	public TriGambler(String playerName, int money) {
		this(playerName, "No team", 0, money);
	}
	
	public TriGambler() {
		super("triGambler");
	}
	
	/**
	 * set the new amount of money of a gambler
	 * @param money new money
	 */
	public void setMoney(int money) {
		this.Money = money;
	}
	
	/**
	 * get how much the gambler has left
	 * @return amount of money the gambler has
	 */
	public int getMoney() {
		return this.Money;
	}
	
	/**
	 * return the hand current gambler
	 * @return the gambler's hand
	 */
	public TriHand getHand() {
		return this.hand;
	}
	
	/**
	 * settle a hand when win the dealer
	 */
	public void win() {
		setMoney(Money + 2 * hand.getBet());
		hand.setBet(0);
	}
	
	/**
	 * settle a hand when busted
	 */
	public void bust() {
		hand.setBet(0);
	}

	/**
	 * money transaction when banker loses
	 * @param bet bet of the hand
	 */
	public void dealerLose(int bet) {
		setMoney(Money - bet);
	}

	/**
	 * money transaction when banker wins
	 * @param bet bet of the hand
	 */
	public void dealerWin(int bet) {
		setMoney(Money + bet);
	}


	public void dealerPlay(Deck deck) {
		System.out.println("\nDealer's turn now\n");
		System.out.println("Dealer now has the following card: ");
		hand.cardsPrint(false);
		int one = 1;
		while(hand.calTotal() < 27) {
			if(one == 1) {
				System.out.println("Dealer hit");
				one--;
			}
			hand.addCard(deck.deal());
		}

		System.out.println("Dealer has the following cards:");
		hand.cardsPrint(false);

		if(hand.calTotal() > 31) {
			System.out.println("Dealer busts.");
		}
		else {
			System.out.println("Now compare with players' results:");
		}
	}

	@Override
	public boolean isNatural() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void handReset() {
		// TODO Auto-generated method stub
		hand.clearHand();
		hand.isStand = false;
		hand.resetBust();
	}

}
