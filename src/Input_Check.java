import java.util.*;

/**
 * @author Hanlin Zou
 * Input_Check class is the class that checks the
 * 		validation of all inputs from user in different
 * 		prompts.
 */
public class Input_Check {
	
	public static Scanner input = new Scanner(System.in);

	/**
	 * Prompt user to choose a kind of game or exit
	 * @return index of a game
	 */
	public int gameType(){
		System.out.print("Please enter your choice here: ");
		String num1 = input.next();
		while(!num1.equals("1") && !num1.equals("2") && !num1.equals("3")) {
			System.out.print("Please enter a number between 1 and 3: ");
			num1 = input.next();
		}
		return Integer.parseInt(num1);
	}

	/**
	 * set user's choice of number of players
	 * @return number of players in a game except dealer
	 */
	public int playerNumber() {
		String number;
		do {
			System.out.print("Please enter the number of players(maximum: 7):");
			number = input.next();
		}while(!number.matches("^[1-7]$"));
		
		return Integer.parseInt(number);
	}

	/**
	 * set user's choice of number of players
	 * @return number of players in a game except dealer
	 */
	public int triPlayerNumber() {
		String number;
		do {
			System.out.print("Please enter the number of players(min: 2, max: 7):");
			number = input.next();
		}while(!number.matches("^[2-7]$"));

		return Integer.parseInt(number);
	}

	/**
	 * set user's choice of player's nicknames
	 * @param index index of gambler in the list
	 * @return nickname of the gambler
	 */
	public String gamblerName(int index) {
		String name;
		System.out.print("Please enter player " + (index + 1) + "'s name here: ");
		name = input.next();
		return name;
	}

	/**
	 * set user's choice of starting money
	 * @param minimalMoney minimum money that a player should have
	 * @return customized money that each player has at
	 * 		   beginning of game
	 */
	public int setMoney(int minimalMoney) {
		String money = "0";
		while(Integer.parseInt(money) < minimalMoney){
			do {
				System.out.print("Please enter the money you want to play with " +
						"(minimum " + minimalMoney + " and less than 1 billion): ");
				money = input.next();
			}while(!money.matches("^[-]?[\\d]*$") || money.length() > 9);
		}
		return Integer.parseInt(money);
	}

	/**
	 * set user's choice of bet
	 * @param gamblers the list of gamblers
	 * @param gamblerIndex index of a gambler in the list
	 * @param minimum minimum to bet
	 * @param maximum the money that the gambler has
	 * @return value of bet
	 */
	public int setBet(ArrayList<? extends Player> gamblers, int gamblerIndex, int minimum, int maximum) {
		String bet = "0";
		while(Integer.parseInt(bet) > maximum || Integer.parseInt(bet) < minimum){
			do {
				System.out.print("Enter the bet of " + gamblers.get(gamblerIndex).getPlayerName() +
						" (minimum: $" + minimum + ", maximum: $" +
						maximum + "): ");
				bet = input.next();
			}while(!bet.matches("^[-]?[\\d]*$") || bet.length() > 9);
		}
		return Integer.parseInt(bet);
	}

	/**
	 * let player make a valid action option
	 * @param gamblers list of players
	 * @param gamblerIndex index of the player current making decision
	 * @param handIndex index of the hand of the player
	 * @param canSplit whether the hand can split
	 * @param canDoubleUp whether the hand can double
	 * @return player's choice of action
	 */
	public int chooseAction(ArrayList<? extends Player> gamblers, int gamblerIndex,
							int handIndex, boolean canSplit, boolean canDoubleUp) {
		String number;
		
		if(canDoubleUp) {
			if(canSplit) {
				do {
					System.out.println(gamblers.get(gamblerIndex).getPlayerName() +
							" You have 4 choices for Hand " + (handIndex+1));
					System.out.println("1. Hit 2. Stand 3. Double Up 4. Split");
					System.out.print("Please choose the operation: ");
					number = input.next();
				}while(!number.matches("^[1-4]$"));
			}
			else {
				do {
					System.out.println(gamblers.get(gamblerIndex).getPlayerName() +
							" You have 3 choices for Hand " + (handIndex+1));
					System.out.println("1. Hit 2. Stand 3. Double Up");
					System.out.print("Please choose the operation: ");
					number = input.next();
				}while(!number.matches("^[1-3]$"));
			}
		}
		else {
			do {
				System.out.println(gamblers.get(gamblerIndex).getPlayerName() +
						" You have 2 choices for Hand " + (handIndex+1));
				System.out.println("1. Hit 2. Stand");
				System.out.print("Please choose the operation: ");
				number = input.next();
			}while(!number.matches("^[1-2]$"));
		}
		return Integer.parseInt(number);
	}

	/**
	 * let user decide whether play another round
	 * @param Gamblers list of gamblers
	 * @return whether the game continues
	 */
	public static boolean continueGame(ArrayList<? extends Player> Gamblers) {
		System.out.print("Enter y/Y if any of you want to continue playing: ");
		String flagContinue = input.next();
		String keepPlayer;
		if(flagContinue.equalsIgnoreCase("Y")) {
			// ask each player whether he/she wants to cash out
			for (int i = 0; i < Gamblers.size(); i++) {
				System.out.print(Gamblers.get(i).getPlayerName() + ", do you play next round? Enter y/Y to continue playing: ");
				keepPlayer = input.next();
				if (!keepPlayer.equalsIgnoreCase("Y")) {
					Gamblers.remove(i);
					i--;
				}
			}
		}
		// if the game should continue and still has players available, proceed
		return flagContinue.equalsIgnoreCase("Y") && Gamblers.size() != 0;
	}

	/**
	 * Set bet in TriantaEna game
	 * @param gamblers list of gamblers
	 * @param gamblerIndex index of current gambler
	 * @param minimum minimum to bet which 0(fold)
	 * @param maximum maximum to bet
	 * @return a valid number of bet
	 */
	public int setTriBet(ArrayList<TriGambler> gamblers, int gamblerIndex, int minimum, int maximum) {
		String bet = "-1";
		System.out.println("Player " + gamblers.get(gamblerIndex).getPlayerName() + ", Your faced down card is: " );
		gamblers.get(gamblerIndex).getHand().cardsPrint(false);
		System.out.println("Enter " + minimum + " to fold." );
		while(Integer.parseInt(bet) < 0 || Integer.parseInt(bet) > maximum){
			do {
				System.out.print("Enter the bet of " + gamblers.get(gamblerIndex).getPlayerName() +
						"(maximum: $" + maximum + "): ");
				bet = input.next();
			}while(!bet.matches("^[-]?[\\d]*$") || bet.length() > 9);
		}
		return Integer.parseInt(bet);
	}

	/**
	 * verify the validation of gambler action in TriantaEna
	 * @return number that represents an action
	 */
	public int HitOrStand() {
		String number;
		do {
			System.out.print("You have 2 choices:\n1. Hit 2. Stand\nPlease choose one: ");
			number = input.next();
		}while(!number.matches("^[1-2]$"));

		return Integer.parseInt(number);
	}
}
