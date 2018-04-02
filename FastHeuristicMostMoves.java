public class FastHeuristicMostMoves implements HeuristicInterface {

	@Override
	public double h(SearchNode n) {
		FastBirdsOfAFeatherNode node = (FastBirdsOfAFeatherNode) n;
		Card[][] grid = node.grid;
		int possibleMoves = 0;
		Card card1;
		Card card2;
		
		if(node.possibleMovesUpdated == true) //If the heuristic was calculated already
			return 1/(1+node.possibleMoves);
		
		if(node.possibleMoves == -1){ //If this is a first move node (parent has not calculated possible moves) check every move
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[0].length; j++) {
					 card1 = grid[i][j];
					 if (card1 != null) {
					
						for (int col = j + 1; col < grid[0].length; col++) {
							card2 = grid[i][col];
							if (card2 != null && card2 != card1 // not same cells
							&& (card1.suit == card2.suit // same suit ... 
							|| Math.abs(card1.rank - card2.rank) <= 1)) // or same/adjacent rank.
								possibleMoves++;
						}
		
						for (int row = i + 1; row < grid.length; row++) {
							card2 = grid[row][j];
							if (card2 != null && card2 != card1 // not same cells
							&& (card1.suit == card2.suit // same suit ... 
							|| Math.abs(card1.rank - card2.rank) <= 1))  // or same/adjacent rank.
								possibleMoves++;
						}
					}
				}
			}
		}
	
		else{
			int movesLost = 0;
			int movesGained = 0;
			
			//Calculate number of moves lost
			for (int col = 0; col < grid[0].length; col++) {
				card2 = grid[node.prevMoveRow1][col];
				if (card2 != null && col != node.prevMoveCol1 // not same cells
				&& (node.prevCard1Suit == card2.suit // same suit ... 
				|| Math.abs(node.prevCard1Rank - card2.rank) <= 1)) // or same/adjacent rank.
					movesLost++;
			}
			for (int row = 0; row < grid.length; row++) {
				card2 = grid[row][node.prevMoveCol1];
				if (card2 != null && row != node.prevMoveRow1 // not same cells
				&& (node.prevCard1Suit == card2.suit // same suit ... 
				|| Math.abs(node.prevCard1Rank - card2.rank) <= 1))  // or same/adjacent rank.
					movesLost++;
			}
			
			for (int col = 0; col < grid[0].length; col++) {
				card2 = grid[node.prevMoveRow2][col];
				if (card2 != null && col != node.prevMoveCol2 // not same cells
				&& (node.prevCard2Suit == card2.suit // same suit ... 
				|| Math.abs(node.prevCard2Rank - card2.rank) <= 1)) // or same/adjacent rank.
					movesLost++;
			}
			for (int row = 0; row < grid.length; row++) {
				card2 = grid[row][node.prevMoveCol2];
				if (card2 != null && row != node.prevMoveRow2 // not same cells
				&& (node.prevCard2Suit == card2.suit // same suit ... 
				|| Math.abs(node.prevCard2Rank - card2.rank) <= 1))  // or same/adjacent rank.
					movesLost++;
			}
			movesLost++; //To account for the move that was just made
			
			//Calculate moves gained
			card1 = grid[node.prevMoveRow2][node.prevMoveCol2];
			for (int col = 0; col < grid[0].length; col++) {
				card2 = grid[node.prevMoveRow2][col];
				if (card2 != null && col != node.prevMoveCol1 // not same cells
				&& (card1.suit == card2.suit // same suit ... 
				|| Math.abs(card1.rank - card2.rank) <= 1)) // or same/adjacent rank.
					movesGained++;
			}
			for (int row = 0; row < grid.length; row++) {
				card2 = grid[row][node.prevMoveCol2];
				if (card2 != null && row != node.prevMoveRow1 // not same cells
				&& (card1.suit == card2.suit // same suit ... 
				|| Math.abs(card1.rank - card2.rank) <= 1))  // or same/adjacent rank.
					movesGained++;
			}
			
			int netMoves = movesGained - movesLost;
			
			node.possibleMoves += netMoves;
			node.possibleMovesUpdated = true;
			
		}
		
		return 1/(1+possibleMoves);
	}

}