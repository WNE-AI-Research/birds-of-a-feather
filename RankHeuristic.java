public class RankHeuristic implements HeuristicInterface{
	
	private int[] rankcount; //corresponding to A-10,J-K
	private double value; //heuristic value for node
	
	public double h(SearchNode n) {
		BirdsOfAFeatherNode node = (BirdsOfAFeatherNode) n;
		Card[][] grid = node.grid;
		int nrow = grid.length;
		int ncol = grid[0].length;
		Card current;
		Card current2;
		int size = 0; //number of cards in grid
		rankcount = new int[14];
		int rank, rank2;
		for (int i: rankcount)
			i = 0;
		
		
		for (int i = 0; i < nrow; i++) {
			for (int j = 0; j < ncol; j++) {
				current = grid[i][j];
				if ( current != null ) {
					size ++; //increment number of cards
					rank = current.getRank();
					for ( int row=0 ; row < nrow; row++ ) {
						for ( int col = 0; col < nrow; col++ ) {
							current2 = grid[row][col];
							if ( current2 != null && ( i != row && j != col ) ) {
								rank2 = current2.getRank();
								if ( (rank2 <= rank + 1) && (rank2 >= rank - 1) )
									rankcount[rank]++; //increment number of that suit
							}
							
						}
					}
					//System.out.println("Suit is: " + suit);
						
					//System.out.println("Number of suits: " + rankcount[suit]);
				}
			}
		}
		//System.out.println( "Number of CLUBS: " + rankcount[0] + "\nNumber of HEARTS: " + rankcount[1] + "\nNumber of SPADES: " + rankcount[2]
		//		+ "\nNumber of DIAMONDS: " + rankcount[3]);
		//sum of all (suitcounts/number of cards)^2
		value = 0;
		for (int i = 0; i < rankcount.length; i++) {
			value += rankcount[i] * 1.0;
		}
		return 1/(1+(value/size)); //invert for lower heursitic value with better state and prevent division by 0
	}
}
