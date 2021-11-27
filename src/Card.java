/**
 * @author Zhaoyu Yin
 * Card Class: Representative of a card in Card game.
 * Variables:
 *		index -- 1 to 13 stand for Ace to King.
 *			Same as rank.
 *		suit -- one of the four suits.
 * Two constructors:
 *  	No-arg constructor will generate a card
 *  		without any meaningful value.
 *  	Standard constructor will generate a card
 *  		with valid rank and suit.
 */
public class Card {
	
	private int index;
	private char suit;

	// No-arg constructor
	public Card() {
		//undefined suit
		suit = 'U';
		index = 0;
	}

	// Standard constructor
	public Card(char Suit, int Index) {
		setSuit(Suit);
		setIndex(Index);
	}

	// set suit of a card
	public void setSuit(char Suit) {
		this.suit = Suit;
	}

	// set index(rank) of a card
	public void setIndex(int Index) {
		this.index = Index;
	}

	// get suit of a card
	public String getSuitName() {
		if(this.suit == 'H') {
			return "Heart";
		}
		else if(this.suit == 'S') {
			return "Spade";
		}
		else if(this.suit == 'C') {
			return "Club";
		}
		else if(this.suit == 'D') {
			return "Diamond";
		}
		else {
			return "Undefined";
		}
	}

	// get the value that the card actual represents
	public String getValueName() {
		if (this.index == 1)
			return "Ace";
		else if (this.index == 11)
			return "Jack";
		else if (this.index == 12)
			return "Queen";
		else if (this.index == 13)
			return "King";
		else if(this.index > 1 && this.index < 11)
			return String.valueOf(this.index);
		return "Undefined";
	}

	// get index of a card
	public int getIndex() {
		return this.index;
	}
	
	public String toString() {
		return getSuitName() + " " + getValueName();
	}
}
