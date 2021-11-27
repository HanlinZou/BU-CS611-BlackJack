/**
 * @author Zhaoyu Yin
 * UI class is the class that displays all necessary
 * 		information on console during the whole game.
 * 		Such as having lines, rules introduction,
 * 		game results, etc.
 */

public class UI {
	
	public void menuIntro() {
		System.out.println("Welcome to Game Menu!\nEnter the number of your decision." +
				"\n\n1. Black Jack\n2. TriantaEna\n3. Exit\n");
	}
	
	public void End() {
		System.out.println("\nHope you enjoying the game!\nGoodbye!");
	}
	
	public void HalvingLine() {
		System.out.println("-----------------Back to Menu-----------------");
	}
	
	public void bjHalvingLine() {
		System.out.println("\n-----------------Black Jack-----------------");
	}

	public void bjIntroduction() {
		System.out.println("BLACKJACK RULES:\nEach one will be dealt 2 cards." +
				"\nThe dealer will have one faces up and the other faces down." +
				"\nThen Players make their bet(minimum: $20)." +
				"\nThere are four actions players can take in each round" +
				"\n1. Hit 2. Stand 3. Spilt 4. Double" +
				"\nIf the points in player's hand exceeds 21, the player busts and loses the bet." +
				"\nAfter all players stand, the dealer will reveal faced down card. " +
				"\nAll players with higher points than dealer's win\n");
	}

	public void triHalvingLine() {
		System.out.println("\n-----------------TriantaEna-----------------");
	}

	public void triIntroduction() {
		System.out.println("TriantaEna RULES:\nThe first player will be automatically set to be dealer." +
				"\nThe dealer will have one faces up and other faces down." +
				"\nThen Players make their bet(minimum: $20)." +
				"\nThere are two actions players can take in each round" +
				"\n1. Hit 2. Stand" +
				"\nIf the points in player's hand exceeds 31, the player busts and loses the bet." +
				"\nAfter all players stand, the dealer will reveal faced down card. " +
				"\nAll players with higher points than dealer's win\n");
	}
}
