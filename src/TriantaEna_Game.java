import java.util.*;

/**
 * @author Zhaoyu Yin
 * TriantaEna_Game class contains the logic of
 * 		card game TriantaEna. When user inputs 2,
 * 		TriantaEna game will be initiated.
 * variables:
 * 		gamblers -- list of gamblers in the game
 * 		deck -- deck of 2 * 52 standard cards,
 * 		auto-refill if needed.
 * 		minimalMoney -- minimum amount of money
 * 		to join a game.
 */
public class TriantaEna_Game {
	
	private ArrayList<TriGambler> gamblers;
	private Deck deck = new Deck(2);
	final int minimalMoney = 50;

	/**
	 * initializeGame method mainly makes preparation of TE game.
	 * It handles the number of players includes banker, names each
	 * 		gambler, and set triple money for banker.
	 * @param gameUI UI class to display necessary information.
	 * @param userInput InputChecker class to check inputs validation.
	 */
	public void initializeGame(UI gameUI, Input_Check userInput) {
		gameUI.triIntroduction();
		int playerNum = userInput.triPlayerNumber();
		int money = userInput.setMoney(minimalMoney);
		gamblers = new ArrayList<>();
		int dealerMoney = 3 * money;
		
		// set up basic information of each gambler
		for(int i = 0; i < playerNum; i++) {
			// nickname
			String gamblerName = userInput.gamblerName(i);
			// construct the player and add it to the gambler list
			TriGambler gambler = new TriGambler(gamblerName, money);
			gamblers.add(gambler);
		}
		System.out.println("The first Player named " + gamblers.get(0).getPlayerName() +
				" has been automatically set as dealer. ");
		gamblers.get(0).setMoney(dealerMoney);
		System.out.println("The dealer has $" + gamblers.get(0).getMoney());
	}

	/**
	 * dealing cards to all players
	 * @param cardNum The number of cards should be dealt
	 */
	public void dealCard(int cardNum) {
		System.out.println("\nDealing cards!\n");
		for(int i = 0; i < cardNum ; i++) {
			for(TriGambler gambler : gamblers) {
				gambler.getHand().addCard(deck.deal());
			}
		}
	}

	/**
	 * set the bet for player's hand and deduct
	 * 		that amount from his pocket
	 * @param userInput Scanning users' input
	 */
	public void setBet(Input_Check userInput) {
		int bet;
		System.out.println("Dealer " + gamblers.get(0).getPlayerName() + " has: ");
		gamblers.get(0).getHand().cardsPrint(false);
		for(int i = 1; i < gamblers.size(); i++) {
			bet = userInput.setTriBet(gamblers, i, 0, gamblers.get(i).getMoney());
			gamblers.get(i).getHand().setBet(bet);
			gamblers.get(i).setMoney(gamblers.get(i).getMoney() - bet);
			if(bet == 0)
				System.out.println("Player " + gamblers.get(i).getPlayerName() + " chooses to fold.");
		}
	}

	/**
	 * Two actions that available to players
	 * @param userInput Input check for validation
	 * @return whether all players bust
	 */
	public boolean HitOrStand(Input_Check userInput) {
		boolean hasActiveGambler = false;
		int numBustPlayer = 0;

		// whether there's a hand can make action
		for (TriGambler gambler : gamblers) {
			if (!gambler.getHand().isLocked()) {
				hasActiveGambler = true;
				break;
			}
		}
		
		while(hasActiveGambler) {
			// each player make actions on his hand until busts or stands
			for(int i = 1; i < gamblers.size(); i++) {
				while(!gamblers.get(i).getHand().isLocked()) {
					System.out.println("Player " + gamblers.get(i).getPlayerName() +
							", You have the following cards: ");
					gamblers.get(i).getHand().cardsPrint(false);
					int actionChoice = userInput.HitOrStand();
					
					//Hit
					if(actionChoice == 1) {
						// We auto-check if hand busts after hit
						// and set bet to 0 if busts
						gamblers.get(i).getHand().hit(deck);
						if(gamblers.get(i).getHand().getBust()) {
							System.out.println("Player " + gamblers.get(i).getPlayerName() +
									", You have the following cards:");
							gamblers.get(i).getHand().cardsPrint(false);
							System.out.println("Sorry your cards in Hand has bust.");
							gamblers.get(0).dealerWin(gamblers.get(i).getHand().getBet());
							gamblers.get(i).bust();
							numBustPlayer++;
						}
					}
					//Stand
					else if(actionChoice == 2) {
						System.out.println("Player " + gamblers.get(i).getPlayerName() + " chose to stand.");
						gamblers.get(i).getHand().setStand();
					}
				}
			}
			
			hasActiveGambler = false;
			for (TriGambler gambler : gamblers) {
				if (!gambler.getHand().isLocked()) {
					hasActiveGambler = true;
					break;
				}
			}
		}
		// whether all players bust
		return numBustPlayer == gamblers.size() - 1;
	}

	/**
	 * banker will compare his points with remaining hands of players
	 */
	public void dealerAction() {
		gamblers.get(0).dealerPlay(deck);
	}

	/**
	 * Handle different cases after banker finishes his actions
	 */
	public void handleBet() {
		int dealerScore = gamblers.get(0).getHand().calTotal();

		// if dealer busts, all remaining win
		if(dealerScore > 31) {
			System.out.println("The dealer's score is " + (dealerScore) +
					"\nSince dealer busts. All remaining hand win!");
			for(int i = 1; i < gamblers.size(); i++) {
				if(!gamblers.get(i).getHand().getBust()) {
					gamblers.get(0).dealerLose(gamblers.get(i).getHand().getBet());
					gamblers.get(i).win();
				}
			}
		}
		else {
			int cardsInDealerHand = gamblers.get(0).getHand().getCardsNum();
			//if dealer is natural TriantaEna
			if(cardsInDealerHand == 3 && dealerScore == 31) {
				for(int i = 1; i < gamblers.size(); i++) {
					System.out.println("Dealer achieves natural 31.");
					if(!gamblers.get(i).getHand().getBust()) {
						gamblers.get(0).dealerWin(gamblers.get(i).getHand().getBet());
						gamblers.get(i).bust();
					}
				}
			}
			else {
				for(int i = 1; i < gamblers.size(); i++) {
					if(!gamblers.get(i).getHand().getBust()) {
						System.out.println("The dealer's score is " + (dealerScore) +
								"\n" + gamblers.get(i).getPlayerName() + "'s hand " +
								" has points: " + gamblers.get(i).getHand().calTotal());
						if(gamblers.get(i).getHand().calTotal() > dealerScore) {
							gamblers.get(0).dealerLose(gamblers.get(i).getHand().getBet());
							System.out.println("Win");
							gamblers.get(i).win();
						}
						else if(gamblers.get(i).getHand().calTotal() == dealerScore) {
							System.out.println("push");
							gamblers.get(i).setMoney(gamblers.get(i).getMoney() + gamblers.get(i).getHand().getBet());
						}
						else {
							gamblers.get(0).dealerWin(gamblers.get(i).getHand().getBet());
							System.out.println("Lose");
							gamblers.get(i).bust();
						}
						
					}
				}
			}
		}
	}

	/**
	 * Show the players and their balances
	 * that still have money to play next game
	 */
	public void showMoney() {
		System.out.println("\nDealer " + gamblers.get(0).getPlayerName() + " has " + gamblers.get(0).getMoney());
		for(int i = 1; i < gamblers.size(); i++) {
			if(gamblers.get(i).getMoney() > 0) {
				System.out.println("Player " + gamblers.get(i).getPlayerName() + " has " + gamblers.get(i).getMoney());
			}
			else if(gamblers.get(i).getMoney() == 0) {
				System.out.println("Player " + gamblers.get(i).getPlayerName() +
						" has no money, will leave the table. ");
				gamblers.remove(i);
				i--;
			}
		}
		for (TriGambler gambler : gamblers) {
			gambler.handReset();
		}
	}

	/**
	 * Banker rotation algorithm
	 */
	public void dealerReplace() {
		Scanner sc = new Scanner(System.in);
		//first get current dealer's name and balance
		String dealerName = gamblers.get(0).getPlayerName();
		int dealerMoney = gamblers.get(0).getMoney();

		String feedback;
		//bubble sort balance from largest to lowest
		for(int i = 0; i < gamblers.size() - 1; i++){
			for(int j = 0; j < gamblers.size() - 1 - i; j++){
				if(gamblers.get(j).getMoney() < gamblers.get(j + 1).getMoney()) {
					gamblers.add(j, gamblers.get(j + 1));
					gamblers.remove(j+2);
				}
			}
		}
		
		//find the index of the original dealer
		int originalDealerIndex = 0;
		for(int i = 0; i < gamblers.size(); i++) {
			if(gamblers.get(i).getMoney() == dealerMoney &&
					gamblers.get(i).getPlayerName().equals(dealerName)) {
				originalDealerIndex = i;
				break;
			}
		}
		
		//swap player to index 0 if the player enters y
		for(int j = 0; j < originalDealerIndex; j++) {
			System.out.print(gamblers.get(0).getPlayerName() + ", enter y/Y if you want to be the new dealer: ");
			feedback = sc.next();
			if(feedback.equalsIgnoreCase("Y")) {
				Collections.swap(gamblers, 0, j);
				break;
			}
		}
	}

	/**
	 * prompt user whether he/she wants next round
	 * @return whether there would be next round
	 */
	public boolean nextRound() {
		boolean flagNextRound = Input_Check.continueGame(gamblers);
		if(gamblers.size() < 2) {
			System.out.println("Number of players not enough.\n");
			flagNextRound = false;
		}
		return flagNextRound;
	}
}
