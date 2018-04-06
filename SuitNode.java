
public class SuitNode extends BirdsOfAFeatherNode {

	int[] prevSuits;
	int[] currentSuits;
	int size;
	
	public SuitNode( int seed ) {
		super(seed);
		int nrow = grid.length;
		int ncol = grid[0].length;
		currentSuits = new int[4];
		size = nrow*ncol;
		Card c;
		
		for (int i = 0; i < nrow; i++) {
			for (int j = 0; j < ncol; j++) {
				c = grid[i][j];
				int suit = c.getSuit();
				currentSuits[suit]++;
			}
		}
	}
	public SuitNode makeMove(int row1, int col1, int row2, int col2) {
		SuitNode child = (SuitNode) childClone(); 
		child.prevMove = child.grid[row1][col1] + "-" + child.grid[row2][col2];
		int suit = child.grid[row2][col2].getSuit();
		child.prevSuits = currentSuits;
		child.size--;
		setSuits(suit, child);
		child.grid[row2][col2] = child.grid[row1][col1];
		child.grid[row1][col1] = null;
		child.stackSize[row2][col2] += child.stackSize[row1][col1];
		child.stackSize[row1][col1] = 0;
		return child;
	}
	public void setSuits( int suit, SuitNode n ) {
		n.currentSuits = new int[4];
		for (int i = 0; i < n.currentSuits.length; i++ )
			n.currentSuits[i] = n.prevSuits[i];
		
		n.currentSuits[suit]--;
	}
	public int[] getSuits() {
		return currentSuits;
	}
}
