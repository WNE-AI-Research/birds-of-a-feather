import java.util.Arrays;

// From http://rosettacode.org/wiki/Deal_cards_for_FreeCell#Java ; downloaded January 23, 2016
// January 23, 2016, TWN added public String[] getDeckStr(int seed) for use by Card method: static public Stack<Card> getShuffle(int seed)

// NOTE: You will not need to use this code directly. Instead use this Card method: static public Stack<Card> getShuffle(int seed)

public class FreeCellShuffler {

	private int seed;

	private String[] deck = {
			"AC", "AD", "AH", "AS",
			"2C", "2D", "2H", "2S",
			"3C", "3D", "3H", "3S",
			"4C", "4D", "4H", "4S",
			"5C", "5D", "5H", "5S",
			"6C", "6D", "6H", "6S",
			"7C", "7D", "7H", "7S",
			"8C", "8D", "8H", "8S",
			"9C", "9D", "9H", "9S",
			"TC", "TD", "TH", "TS",
			"JC", "JD", "JH", "JS",
			"QC", "QD", "QH", "QS",
			"KC", "KD", "KH", "KS",
	};

	private int random() {
		seed = (214013 * seed + 2531011) & Integer.MAX_VALUE;
		return seed >> 16;
	}

	//shuffled cards go to the end
	private String[] getShuffledDeck() {
		String[] deck = Arrays.copyOf(this.deck, this.deck.length);
		for(int i = deck.length - 1; i > 0; i--) {
			int r = random() % (i + 1);
			String card = deck[r];
			deck[r] = deck[i];
			deck[i] = card;
		}
		return deck;
	}

	//deal from end first
	public void dealGame(int seed) {
		this.seed = seed;
		String[] shuffledDeck = getShuffledDeck();
		for(int count = 1, i = shuffledDeck.length - 1; i >= 0; count++, i--) {
			System.out.print(shuffledDeck[i]);
			if(count % 8 == 0) {
				System.out.println();
			} else {
				System.out.print(" ");
			}
		}
		System.out.println();
	}

	// TWN addition: getDeckStr(int seed) - Get an array of Card Strings for the given seed deal
	public String[] getDeckStr(int seed) {
		this.seed = seed;
		return getShuffledDeck();
	}
	
	public static void main(String[] args) {
		FreeCellShuffler s = new FreeCellShuffler();
		s.dealGame(1);
		System.out.println();
		s.dealGame(617);
		// TWN: Output verified against deals at http://freecellgamesolutions.com/
	}

}