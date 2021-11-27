/**
 * @author Zhaoyu Yin & Hanlin Zou
 * Menu class is a representative of game center.
 *		After the game is run, the user would be
 *		prompt to give a feedback of which game
 *		he/she would like to play.
 * Different game will be initiated based on user's
 *		input.
 */

public class Menu {
	public void Run() {
		// Used for game mode switch after one game finishes
		boolean Flag_Game = true;

		// prepare the welcoming UI and inputChecker ready
		UI gameUI = new UI();
		Input_Check userInput = new Input_Check();
		
		while(Flag_Game) {
			gameUI.menuIntro();
			
			// receive user's feedback
			int gameType = userInput.gameType();
			
			// choice 1 Blackjack
			if(gameType == 1) {
				gameUI.bjHalvingLine();
				Blackjack_Game bjGame = new Blackjack_Game();
				bjGame.initializeGame(gameUI, userInput);
				do {
					bjGame.setBet(userInput);
					bjGame.dealCard();
					bjGame.showStatus();
					boolean gameDone = bjGame.naturalCheck();
					//game ends if dealer has natural or all players have natural
					if(!gameDone) {
						gameDone = bjGame.fourActions(userInput);
						//game ends if all players bust
						if (!gameDone) {
							bjGame.dealerAction();
							bjGame.handleBet();
						}
						else
							System.out.println("All players bust, dealer wins");
					}
					bjGame.showMoney();
					bjGame.handClear();
				}while(bjGame.nextRound());
				gameUI.HalvingLine();
			}
			//choice 2 TriantaEna
			else if(gameType == 2) {
				gameUI.triHalvingLine();
				TriantaEna_Game triGame = new TriantaEna_Game();
				triGame.initializeGame(gameUI, userInput);
				do {
					triGame.dealCard(1);
					triGame.setBet(userInput);
					triGame.dealCard(2);
					boolean gameDone = triGame.HitOrStand(userInput);
					//game ends if all players bust
					if(!gameDone){
						triGame.dealerAction();
						triGame.handleBet();
					}
					else
						System.out.println("All players bust, dealer wins");
					triGame.showMoney();
					triGame.dealerReplace();
				}while(triGame.nextRound());
				gameUI.HalvingLine();
			}
			//choice 3 exit
			else {
				Flag_Game = false;
			}
		}
		Input_Check.input.close();
		gameUI.End();
	}
}
