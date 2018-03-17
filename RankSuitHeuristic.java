public class RankSuitHeuristic implements HeuristicInterface{
	
//	private int[] rankcount; //corresponding to A-10,J-K
//	private int[] suitcount;
//	private double value; //heuristic value for node
	private HeuristicInterface heuristic;
//	
//	public double h(SearchNode n) {
//		
//		heuristic = new SuitHeuristic();
//		double suitvalue = heuristic.h( n );
//		heuristic = new RankHeuristic();
//		double rankvalue = heuristic.h( n );
//		return suitvalue + rankvalue;
//	}
	public double h(SearchNode n) {
		
		heuristic = new SuitHeuristic();
		double suitvalue = heuristic.h( n );
		heuristic = new RankHeuristic();
		double rankvalue = heuristic.h( n );
		heuristic = new HeuristicMostMoves();
		double movesvalue = heuristic.h( n );
		return (suitvalue + rankvalue)*movesvalue;
		
//		BirdsOfAFeatherNode node = (BirdsOfAFeatherNode) n;
//		Card[][] grid = node.grid;
//		int nrow = grid.length;
//		int ncol = grid[0].length;
//		Card current;
//		Card current2;
//		int size = 0; //number of cards in grid
//		rankcount = new int[14];
//		suitcount = new int[4];
//		int suit, rank, rank2;
//		for (int i: rankcount)
//			i = 0;
//		for (int i: suitcount)
//			i = 0;
//		
//		for (int i = 0; i < nrow; i++) {
//			for (int j = 0; j < ncol; j++) {
//				current = grid[i][j];
//				if ( current != null ) {
//					size ++; //increment number of cards
//					suit = current.getSuit();
//					suitcount[suit]++;
//					rank = current.getRank();
//					for ( int row=0 ; row < nrow; row++ ) {
//						for ( int col = 0; col < nrow; col++ ) {
//							current2 = grid[row][col];
//							if ( current2 != null && ( i != row && j != col ) ) {
//								rank2 = current2.getRank();
//								if ( (rank2 <= rank + 1) && (rank2 >= rank - 1) )
//									rankcount[rank]++; //increment number of that suit
//							}
//							
//						}
//					}
//					
//				}
//			}
//		}
//		//System.out.println( "Number of CLUBS: " + suitcount[0] + "\nNumber of HEARTS: " + suitcount[1] + "\nNumber of SPADES: " + suitcount[2]
//		//		+ "\nNumber of DIAMONDS: " + suitcount[3]);
//		//sum of all (suitcounts/number of cards)^2
//		double suitvalue = 0;
//		for (int i = 0; i < suitcount.length; i++) {
//			double count = suitcount[i] * 1.0;
//			double suit_rank = (count/size); 
//			suitvalue += (suit_rank*suit_rank);
//		}
//		double rankvalue = 0;
//		for (int i = 0; i < rankcount.length; i++) {
//			rankvalue += rankcount[i] * 1.0;
//		}
//		value = 1/(1+(rankvalue+suitvalue));
//		return value; //invert for lower he
//		}
	}
}
