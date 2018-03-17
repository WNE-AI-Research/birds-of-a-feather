public class SuitHeuristic implements HeuristicInterface{
	
	private int[] suitcount; //corresponding to C, H, S , D
	private double value; //heuristic value for node
	
	public double h(SearchNode n) {
		BirdsOfAFeatherNode node = (BirdsOfAFeatherNode) n;
		Card[][] grid = node.grid;
		int nrow = grid.length;
		int ncol = grid[0].length;
		Card current;
		int size = 0; //number of cards in grid
		suitcount = new int[4];
		for (int i: suitcount)
			i = 0;
		
		
		for (int i = 0; i < nrow; i++) {
			for (int j = 0; j < ncol; j++) {
				current = grid[i][j];
				if ( current != null ) {
					size ++; //increment number of cards
					int suit = current.getSuit();
					//System.out.println("Suit is: " + suit);
					suitcount[suit]++; //increment number of that suit
					//System.out.println("Number of suits: " + suitcount[suit]);
				}
			}
		}
		//System.out.println( "Number of CLUBS: " + suitcount[0] + "\nNumber of HEARTS: " + suitcount[1] + "\nNumber of SPADES: " + suitcount[2]
		//		+ "\nNumber of DIAMONDS: " + suitcount[3]);
		//sum of all (suitcounts/number of cards)^2
		for (int i = 0; i < suitcount.length; i++) {
			double count = suitcount[i] * 1.0;
			double suit_rank = (count/size); 
			value += (suit_rank*suit_rank);
		}
		return 1/(1+value); //invert for lower heursitic value with better state and prevent division by 0
	}
}
