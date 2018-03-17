
public class HeuristicMostMoves implements HeuristicInterface {

	@Override
	public int h(SearchNode n) {
		BirdsOfAFeatherNode node = (BirdsOfAFeatherNode) n;
		int possibleMoves = 0;
		for (int row1 = 0; row1 < node.grid.length - 1; row1++)
			for (int col1 = 0; col1 < node.grid[0].length - 1; col1++) {
				Card card1 = node.grid[row1][col1];
				if (card1 != null){
					for (int row2 = row1 + 1; row2 < node.grid.length; row2++){
						Card card2 = node.grid[row2][col1];
						if (card2 != null && card2 != card1 // not same cells
								&& (card1.suit == card2.suit // same suit ... 
								|| Math.abs(card1.rank - card2.rank) <= 1)) { // or same/adjacent rank.
							possibleMoves++;
							}
						}
					for (int col2 = col1 + 1; col2 < node.grid[0].length; col2++) {
						Card card2 = node.grid[row1][col2];
						if (card2 != null && card2 != card1 // not same cells
								&& (card1.suit == card2.suit // same suit ... 
								|| Math.abs(card1.rank - card2.rank) <= 1)) { // or same/adjacent rank.
							possibleMoves++;
							}
						}
				}
			}
		return -possibleMoves;
	}

}
