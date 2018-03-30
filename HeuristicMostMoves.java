
public class HeuristicMostMoves implements HeuristicInterface {

	@Override
	public double h(SearchNode n) {
		BirdsOfAFeatherNode node = (BirdsOfAFeatherNode) n;
		Card[][] grid = node.grid;
		int possibleMoves = 0;
		Card card1;
		Card card2;
		
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				 card1 = grid[i][j];
				 if (card1 != null) {
				
					for (int col = 0; col < grid[0].length; col++) {
						card2 = grid[i][col];
						if (card2 != null && card2 != card1 // not same cells
						&& (card1.suit == card2.suit // same suit ... 
						|| Math.abs(card1.rank - card2.rank) <= 1)) // or same/adjacent rank.
							possibleMoves++;
					}
	
					for (int row = 0; row < grid.length; row++) {
						card2 = grid[row][j];
						if (card2 != null && card2 != card1 // not same cells
						&& (card1.suit == card2.suit // same suit ... 
						|| Math.abs(card1.rank - card2.rank) <= 1))  // or same/adjacent rank.
							possibleMoves++;
					}
				}
			}
		}
		return 1.0/(1+possibleMoves);
	}

}


