import java.util.*;

/**
 * @author Hanlin Zou
 * Deck Class: Representative of a deck of cards
 * Variable: An arraylist of data type Card
 * Two Constructors:
 * 		No-arg constructor will generate ONE
 * 			deck of shuffled cards.
 *      Constructor with an integer parameter will
 *      	generate n decks of shuffled cards.
 */

public class Deck {
	ArrayList<Card> deck = new ArrayList<>();

	/**
	 * No-arg constructor, will generate one deck
	 * of shuffled cards.
	 */
	Deck(){
		this(1);
	}

	/**
	 * Standard constructor, which will generate n
	 * decks of shuffled cards.
	 * @param n Represents the number of sets of 52
	 *          cards a game needs.
	 */
	Deck(int n){
		char suit;
		Random random = new Random();
		for(int i = 0; i < n; i++) {
			for (int j = 1; j < 14; j++) {
				for(int k = 0; k < 4; k++){
					if(k == 0)
						suit = 'C';
					else if(k == 1)
						suit = 'D';
					else if(k == 2)
						suit = 'H';
					else
						suit = 'S';
					Card card = new Card(suit, j);
					deck.add(random.nextInt(deck.size() + 1), card);
				}
			}
		}
	}

	/**
	 * Usually called at the beginning of a game or each round.
	 * Used to deal cards to players.
	 * @return Since all cards face down, we deal the top one.
	 */
	public Card deal(){
		if(deck.size() == 0)
			this.refill();
		return deck.remove(deck.size() - 1);
	}

	/**
	 * refill the current deck with 52 cards
	 */
	public void refill(){
		char suit;
		Random random = new Random();
		for (int j = 1; j < 14; j++) {
			for(int k = 0; k < 4; k++){
				if(k == 0)
					suit = 'C';
				else if(k == 1)
					suit = 'D';
				else if(k == 2)
					suit = 'H';
				else
					suit = 'S';
				Card card = new Card(suit, j);
				deck.add(random.nextInt(deck.size() + 1), card);
				}
			}
	}
	
	public String toString() {
		StringBuilder string = new StringBuilder();
		for (Card card : deck) {
			string.append(card.toString()).append("\n");
		}
		return string.toString();
	}
}
