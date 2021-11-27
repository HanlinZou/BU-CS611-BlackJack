import java.util.ArrayList;

/**
 * @author Hanlin Zou
 * Blackjack_Game class contains the logic of
 * 		card game Blackjack. When user inputs 1,
 * 		blackjack game will be initiated.
 * variables:
 * 		gamblers -- list of gamblers in the game
 * 		deck -- deck of 52 standard cards, auto-refill
 * 			if needed.
 * 		dealer -- The dealer of the game
 * 		minimalBet,minimalMoney -- minimum amount of
 * 			money to make a bet and join a game.
 */

public class Blackjack_Game {
	
	private ArrayList<BjGambler> gamblers;
	private Deck deck;
	private final Dealer dealer = new Dealer();
	final int minimalBet = 20;
	final int minimalMoney = 20;

	/**
	 * initializeGame method mainly makes preparation of bj game.
	 * It handles the number of players except dealer, names each
	 * 		gambler and prepare the deck of cards.
	 * @param gameUI UI class to display necessary information.
	 * @param userInput InputChecker class to check inputs validation.
	 */
	public void initializeGame(UI gameUI, Input_Check userInput) {
		// display rules of bj
		gameUI.bjIntroduction();

		// prompts number of players and construct list of gamblers and cards
		int playerNum = userInput.playerNumber();
		gamblers = new ArrayList<>();
		deck = new Deck();

		// set up basic information of each gambler
		for(int i = 0; i < playerNum; i++) {
			// nickname
			String gamblerName = userInput.gamblerName(i);
			int money = userInput.setMoney(minimalMoney);
			// construct the player and add it to the gambler list
			BjGambler gambler = new BjGambler(gamblerName, money);
			gamblers.add(gambler);
		}
	}

	/**
	 * set the bet for player's first hand and deduct
	 * 		that amount from his pocket
	 * @param userInput Scanning users' input
	 */
	public void setBet(Input_Check userInput) {
		int bet;
		// Since we already verify the player
		// has enough money to play at least one round
		for(int i = 0; i < gamblers.size(); i++) {
			bet = userInput.setBet(gamblers, i, minimalBet, gamblers.get(i).getMoney());
			gamblers.get(i).getHand(0).setBet(bet);
			gamblers.get(i).setMoney(gamblers.get(i).getMoney() - bet);
		}
	}

	/**
	 * Dealing cards at first round.
	 */
	public void dealCard() {
		System.out.println("Dealing cards!");
		for(int i = 0; i < 2; i++) {
			for (BjGambler gambler : gamblers) {
				gambler.getHand(0).addCard(deck.deal());
			}
			dealer.addCard(deck.deal());
		}
	}

	/**
	 * Show the status of the overall situation after dealing
	 * 		two cards to everyone
	 */
	public void showStatus() {
		for (BjGambler gambler : gamblers) {
			System.out.println("Player " + gambler.getPlayerName() + " has: ");
			gambler.handsPrint();
		}
		System.out.println("\nDealer's faced up cards are: ");
		dealer.handPrint(true);
	}

	/**
	 * Check if there's anyone has natural after dealt two cards
	 * @return whether there's anyone has natural
	 */
	public boolean naturalCheck() {
		// Used to record how many players have natural
		int playerNatural = 0;

		//if dealer has natural, all players either push or lose
		if(dealer.isNatural()) {
			System.out.println("Dealer has reached natural BlackJack condition!");
			dealer.handPrint(false);
			for (BjGambler gambler : gamblers) {
				if (gambler.isNatural()) {
					System.out.println(gambler.getPlayerName() + " pushes");
					gambler.push(0);
				} else {
					System.out.println(gambler.getPlayerName() + " loses");
					gambler.bust(0);
				}
			}
			return true;
		}
		else {
			for (BjGambler gambler : gamblers) {
				if (gambler.isNatural()) {
					playerNatural++;
					System.out.println("Player " + gambler.getPlayerName() + " has natural blackjack!");
					// player has natural, give money right away
					gambler.win(0);
					gambler.getHand(0).clearHand();
					gambler.emptyHand();
				}
			}
			// if not all gamblers have natural, game surely continues
			return playerNatural == gamblers.size();
		}
	}

	/**
	 * Four actions that available to players
	 * @param userInput Input check for validation
	 * @return whether there's any hand doesn't bust
	 */
	public boolean fourActions(Input_Check userInput) {
		boolean allGamblerBust = true;
		boolean hasActiveGambler = false;

		// whether there's a hand can make action
		for (BjGambler gambler : gamblers) {
			if (gambler.checkHasActiveHand()) {
				hasActiveGambler = true;
				break;
			}
		}

		while(hasActiveGambler) {
			// each player make an action on his first available hand
			for(int i = 0; i < gamblers.size(); i++) {
				for(int j = 0; j < gamblers.get(i).handsNum(); j++) {
					if(!gamblers.get(i).getHand(j).isLocked()) {
						// filter cant-do actions
						boolean canDoubleUp = (gamblers.get(i).getMoney() >= gamblers.get(i).getHand(j).getBet())
								&& gamblers.get(i).getHand(j).cards.size() == 2;
						boolean canSplit = gamblers.get(i).getHand(j).canSplit();
						
						int actionChoice = userInput.chooseAction(gamblers, i, j, canSplit, canDoubleUp);
						
						//Hit
						if(actionChoice == 1) {
							System.out.println(gamblers.get(i).getPlayerName() +
									"'s Hand " + (j + 1) + " has: ");

							// We auto-check if hand busts after hit
							// and set bet to 0 if busts
							gamblers.get(i).getHand(j).hit(deck);
							gamblers.get(i).getHand(j).cardsPrint(false);

							if(gamblers.get(i).getHand(j).getBet() == 0) {
								System.out.println("Sorry your Hand " + (j + 1) + " busts. ");
								gamblers.get(i).bust(j);
							}
							break;
						}
						
						//Stand
						else if(actionChoice == 2) {
							System.out.println(gamblers.get(i).getPlayerName() + "'s Hand " + (j + 1) + " stands.");
							gamblers.get(i).getHand(j).setStand();
							break;
						}
						
						//Double up
						else if(actionChoice == 3) {
							System.out.println(gamblers.get(i).getPlayerName() + "'s Hand " + (j + 1) + " has: ");
							gamblers.get(i).setMoney(gamblers.get(i).getMoney() - gamblers.get(i).getHand(j).getBet());
							gamblers.get(i).getHand(j).doubleUp(deck);
							gamblers.get(i).getHand(j).cardsPrint(false);
							if(gamblers.get(i).getHand(j).getBet() == 0) {
								System.out.println("Sorry your Hand " + (j + 1) + " busts. ");
								gamblers.get(i).bust(j);
							}
							break;
						}
						
						//Split
						else {
							gamblers.get(i).setMoney(gamblers.get(i).getMoney() - gamblers.get(i).getHand(j).getBet());
							BjHand hand = new BjHand(gamblers.get(i).getHand(j).getBet());
							Card card = gamblers.get(i).getHand(j).getCard(1);
							gamblers.get(i).getHand(j).removeCard();
							hand.addCard(card);
							gamblers.get(i).addHand(hand);
							
							//hit the old hand
							System.out.println("Player " + gamblers.get(i).getPlayerName() +
									"'s Hand " + (j + 1) + " has: ");
							gamblers.get(i).getHand(j).hit(deck);
							gamblers.get(i).getHand(j).cardsPrint(false);
							
							//hit the new hand
							int newHandIndex = gamblers.get(i).handsNum() - 1;
							System.out.println("Player " + gamblers.get(i).getPlayerName() + "'s Hand " +
									(newHandIndex + 1) + " has: ");
							gamblers.get(i).getHand(newHandIndex).hit(deck);
							gamblers.get(i).getHand(newHandIndex).cardsPrint(false);
						}
						break;
					}
				}
			}
			// check again if there's still any hand can make action
			hasActiveGambler = false;
			for (BjGambler gambler : gamblers) {
				if (gambler.checkHasActiveHand()) {
					hasActiveGambler = true;
					break;
				}
			}
		}
		// check if all players bust in this round so dealer
		// doesn't need to do actions.
		for (BjGambler gambler : gamblers) {
			for (int j = 0; j < gambler.handsNum(); j++) {
				if (gambler.getHand(j).bet != 0)
					allGamblerBust = false;
			}
		}
		return allGamblerBust;
	}

	/**
	 * dealer will reveal his faced down card and compare his
	 * 		points with remaining hands of players
	 */
	public void dealerAction() {
			dealer.dealerPlay(deck);
	}

	/**
	 * Handle different cases after dealer finishes his actions
	 */
	public void handleBet() {
		int dealerScore = dealer.getScore();

		// if dealer busts, all remaining win
		if(dealerScore > 21) {
			System.out.println("The dealer's score is " + (dealerScore) +
					"\nSince dealer busts. All remaining hand win!");
			for (BjGambler gambler : gamblers) {
				for (int j = 0; j < gambler.handsNum(); j++) {
					if (gambler.getHand(j).getBet() > 0) {
						gambler.win(j);
					}
				}
			}
		}
		else {
			for (BjGambler gambler : gamblers) {
				for (int j = 0; j < gambler.handsNum(); j++) {
					System.out.println("The dealer's score is " + (dealerScore) +
							"\n" + gambler.getPlayerName() + "'s hand " + (j + 1) +
							" has points: " + gambler.getHand(j).calTotal());
					// win
					if (gambler.getHand(j).getBet() > 0 && gambler.getHand(j).calTotal() > dealerScore) {
						System.out.println("Win");
						gambler.win(j);
					}
					// push
					else if (gambler.getHand(j).getBet() > 0 && gambler.getHand(j).calTotal() == dealerScore) {
						System.out.println("Push");
						gambler.push(j);
					}
					// lose
					else {
						System.out.println("Lose");
						gambler.bust(j);
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
		for(int i = 0; i < gamblers.size(); i++) {
			if(gamblers.get(i).getMoney() > 0) {
				System.out.println(gamblers.get(i).getPlayerName() + " now has " + gamblers.get(i).getMoney());
			}
			if(gamblers.get(i).getMoney() < minimalBet) {
				System.out.println(gamblers.get(i).getPlayerName() + " is kicked out of game due to low balance.");
				gamblers.remove(i);
				i--;
			}
		}
	}

	/**
	 * Free all hands of all remaining players
	 */
	public void handClear() {
		for (BjGambler gambler : gamblers) {
			gambler.handReset();
		}
		dealer.handReset();
	}

	/**
	 * prompt user whether he/she wants next round
	 * @return whether there would be next round
	 */
	public boolean nextRound() {
		return Input_Check.continueGame(gamblers);
	}
}
