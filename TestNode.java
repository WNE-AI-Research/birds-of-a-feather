import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class TestNode extends SearchNode {

	static public final int DEFAULT_SIZE = 4;
	Card[][] grid;
	int[][] stackSize;
	String prevMove = "";
	
	public TestNode() {
		this(new Random().nextInt());
	}
	
	public TestNode(int seed) {
		this(seed, DEFAULT_SIZE);
	}
	
	public TestNode(int seed, int size) {
		this(seed, size, size);
	}
	
	public TestNode(int seed, int numRows, int numColumns) {
//		System.out.printf("Seed: %d; Rows: %d; Columns: %d\n", seed, numRows, numColumns);
		Stack<Card> deck = Card.getShuffle(seed);
		grid = new Card[numRows][numColumns];
		stackSize = new int[numRows][numColumns];
		for (int r = 0; r < numRows; r++)
			for (int c = 0; c < numColumns; c++) {
				grid[r][c] = deck.pop();
				stackSize[r][c] = 1;
			}
//		System.out.println(this);
	}
	
	@Override
	public boolean isGoal() {
		int numCards = grid.length * grid[0].length;
		for (int r = 0; r < grid.length; r++)
			for (int c = 0; c < grid[r].length; c++)
				if (stackSize[r][c] > 0 && stackSize[r][c] < numCards)
					return false;
		return true;
	}

	@Override
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

	/**
	 * Return whether or not the given move is legal.  A move is legal if it is to a different cell in the
	 * same row or same column where the top cards share the same suit or same/adjacent ranks.
	 * @param row1 source row
	 * @param col1 source column
	 * @param row2 destination row
	 * @param col2 destination column
	 * @return whether or not the given move is legal
	 */
	public boolean isLegalMove(int row1, int col1, int row2, int col2) {
		return (((row1 != row2) || (col1 != col2)) // not same cells
				&& ((row1 == row2) || (col1 == col2)) // row or column move
				&& (grid[row1][col1] != null && grid[row2][col2] != null) // neither stack empty
				&& (grid[row1][col1].suit == grid[row2][col2].suit // same suit ... 
				    || Math.abs(grid[row1][col1].rank - grid[row2][col2].rank) <= 1)); // or same/adjacent rank.
	}
	
	/**
	 * Make a given move, returning the new resulting node. The caller is responsible for checking legality. 
	 * @param row1 source row
	 * @param col1 source column
	 * @param row2 destination row
	 * @param col2 destination column
	 * @return new resulting node
	 */
	public TestNode makeMove(int row1, int col1, int row2, int col2) {
		TestNode child = (TestNode) childClone(); 
		child.prevMove = child.grid[row1][col1] + "-" + child.grid[row2][col2];
		child.grid[row2][col2] = child.grid[row1][col1];
		child.grid[row1][col1] = null;
		child.stackSize[row2][col2] += child.stackSize[row1][col1];
		child.stackSize[row1][col1] = 0;
		return child;
	}
	
    /**
     * <code>clone</code> - return a deep copy of this node.
     *
     * @return an <code>Object</code> value
     */
	public Object clone() {
		TestNode newNode = (TestNode) super.clone();
		newNode.grid = (Card[][]) grid.clone();
		newNode.stackSize = (int[][]) stackSize.clone();
		for (int r = 0; r < grid.length; r++) {
			newNode.grid[r] = grid[r].clone();
			newNode.stackSize[r] = stackSize[r].clone();
		}
		return newNode;
	} 
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int r = 0; r < grid.length; r++) {
			for (int c = 0; c < grid[r].length; c++) {
				// uncomment next line to show stack (flock) sizes
//				sb.append(grid[r][c] == null ? "       " : String.format("%s(%2d) ", grid[r][c].toString(), stackSize[r][c]));
				sb.append(grid[r][c] == null ? "   " : String.format("%s ", grid[r][c].toString()));
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public String solutionString() {
		StringBuilder sb = new StringBuilder();
		buildSolutionString(sb);
		return sb.toString();
	}
	
	public void buildSolutionString(StringBuilder sb) {
		if (parent != null) {
			((TestNode) parent).buildSolutionString(sb);
			sb.append(" ");
			sb.append(prevMove);
		}	
	}
	
	public boolean hasOddBird() {
		int[] suitCount = new int[Card.NUM_SUITS];
		int[] rankCount = new int[Card.NUM_RANKS];
		Card[] lastOfSuit = new Card[Card.NUM_SUITS];
		for (int r = 0; r < grid.length; r++)
			for (int c = 0; c < grid[r].length; c++) 
				if (grid[r][c] != null) {
					suitCount[grid[r][c].suit]++;
					rankCount[grid[r][c].rank]++;
					lastOfSuit[grid[r][c].suit] = grid[r][c];
				}
		for (int suit = 0; suit < Card.NUM_SUITS; suit++)
			if (suitCount[suit] == 1) {
				Card card = lastOfSuit[suit];
				int rank = card.rank;
				if (rankCount[rank] == 1 && (rank == 0 || rankCount[rank - 1] == 0) && (rank == Card.NUM_RANKS - 1 || rankCount[rank + 1] == 0))
					return true;
			}
		return false;
	}
	
    public static void main(String[] args){
    	Searcher searcher = new DepthFirstSearcherNoRepeat();
    	SearchNode root = new TestNode(367297990);

    	if (searcher.search(root)) {
    		// successful search
    		System.out.println("Goal node found in " + searcher.getNodeCount() 
    		+ " nodes.");
    		System.out.println("Goal path:");
    		searcher.printGoalPath();
    		System.out.println("Solution string: " + ((TestNode) searcher.goalNode).solutionString());
    	} else {
    		// unsuccessful search
    		System.out.println("Goal node not found in " 
    				+ searcher.getNodeCount() + " nodes.");
    	}
    }
	
}
