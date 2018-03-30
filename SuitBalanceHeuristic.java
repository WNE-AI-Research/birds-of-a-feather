public class SuitBalanceHeuristic implements HeuristicInterface{
	
	//private int[] suitcount = {0,0,0,0}; //corresponding to C, H, S , D
	//private double value; //heuristic value for node
	
	/* This heuristic compares the computed Manhattan distance of the node to the assumed
	 * ideal distance of [4,4,4,4], further implementations will see if this can be
	 * made better when suits are missing
	 */
	public double h(SearchNode n) {
		BirdsOfAFeatherNode node = (BirdsOfAFeatherNode) n;
		Card[][] grid = node.grid;
		int nrow = grid.length;
		int ncol = grid[0].length;
		//int[] defaultsuit = {4,4,4,4};
		int[] suitcount = {0,0,0,0};
		
		double value = 0;
		Card current;
		int size = 0; //number of cards in grid
			
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
			double diff = 1.0*suitcount[i];//-(size/4);
			value += diff*diff;
		}
		value = Math.sqrt( value );
		return 1/(1+value);
	}
}
