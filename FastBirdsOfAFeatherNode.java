import java.util.ArrayList;

public class FastBirdsOfAFeatherNode extends BirdsOfAFeatherNode {
	
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
	
	public FastBirdsOfAFeatherNode makeMove(int row1, int col1, int row2, int col2) {
		FastBirdsOfAFeatherNode child = (FastBirdsOfAFeatherNode) childClone(); 
		child.prevMove = child.grid[row1][col1] + "-" + child.grid[row2][col2];

		//Save information about previous state in child
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
		
		child.grid[row2][col2] = child.grid[row1][col1];
		child.grid[row1][col1] = null;
		child.stackSize[row2][col2] += child.stackSize[row1][col1];
		child.stackSize[row1][col1] = 0;
		return child;
	}
	
	public ArrayList<SearchNode> expand() {
		ArrayList<SearchNode> children = new ArrayList<SearchNode>();
		// for each row pair of cards, collect legal children moves
		for (int r = 0; r < grid.length; r++) 
			for (int c1 = 0; c1 < grid[r].length - 1; c1++)
				for (int c2 = c1 + 1; c2 < grid[r].length; c2++)
					if (isLegalMove(r, c1, r, c2)) {
						children.add(makeMove(r, c1, r, c2));
						children.add(makeMove(r, c2, r, c1));
					}

		// for each column pair of cards, collect legal children moves
		for (int c = 0; c < grid[0].length; c++) 
			for (int r1 = 0; r1 < grid.length - 1; r1++)
				for (int r2 = r1 + 1; r2 < grid.length; r2++)
					if (isLegalMove(r1, c, r2, c)) {
						children.add(makeMove(r1, c, r2, c));
						children.add(makeMove(r2, c, r1, c));
					}
					
		return children;
	}
	
	public FastBirdsOfAFeatherNode(){
		super();
	}
	
	public FastBirdsOfAFeatherNode(int seed){
		super(seed);
	}
}
