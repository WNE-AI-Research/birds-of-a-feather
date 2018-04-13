public class FastBirdsOfAFeatherNode extends PrecompBirdsOfAFeatherNode {
	
	int prevMoveCol1 = -1;
	int prevMoveRow1 = -1;
	int prevMoveCol2 = -1;
	int prevMoveRow2 = -1;
	int prevCard1Rank = -1;
	int prevCard1Suit = -1;
	int prevCard2Rank = -1;
	int prevCard2Suit = -1;
	int possibleMoves = -1;
	boolean possibleMovesUpdated = false;
	
	
	public FastBirdsOfAFeatherNode(){
		super();
	}
	
	public FastBirdsOfAFeatherNode(int seed){
		super(seed);
	}

	@Override 
	void saveInformation(PrecompBirdsOfAFeatherNode child1, int row1, int col1, int row2, int col2) {
		FastBirdsOfAFeatherNode child = (FastBirdsOfAFeatherNode) child1;
		
		child.prevMoveCol1 = col1;
		child.prevMoveRow1 = row1;
		child.prevMoveCol2 = col2;
		child.prevMoveRow2 = row2;
		child.prevCard1Rank = child.grid[row1][col1].getRank();
		child.prevCard1Suit = child.grid[row1][col1].getSuit();
		child.prevCard2Rank = child.grid[row2][col2].getRank();
		child.prevCard2Suit = child.grid[row2][col2].getSuit();
		child.possibleMoves = possibleMoves;
		child.possibleMovesUpdated = false;
	}
}
