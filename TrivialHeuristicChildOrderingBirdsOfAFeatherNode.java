import java.util.ArrayList;
import java.util.Collections;

public class TrivialHeuristicChildOrderingBirdsOfAFeatherNode  extends ChildOrderingBirdsOfAFeatherNode {

	public TrivialHeuristicChildOrderingBirdsOfAFeatherNode() {
		super();
	}

	public TrivialHeuristicChildOrderingBirdsOfAFeatherNode(int seed) {
		super(seed);
	}

	public TrivialHeuristicChildOrderingBirdsOfAFeatherNode(int seed, int size) {
		super(seed, size);
	}

	public TrivialHeuristicChildOrderingBirdsOfAFeatherNode(int seed, int numRows, int numColumns) {
		super(seed, numRows, numColumns);
	}

	public TrivialHeuristicChildOrderingBirdsOfAFeatherNode(BirdsOfAFeatherNode node) {
		super(node);
	}

	@Override
	public ArrayList<SearchNode> expand() {
		ArrayList<BirdsOfAFeatherNode> children = new ArrayList<BirdsOfAFeatherNode>();
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
		ArrayList<SearchNode> childrenToReturn = orderNodes(children);
		return childrenToReturn;
	}
	
	public ArrayList<SearchNode> orderNodes(ArrayList<BirdsOfAFeatherNode> children){
		ArrayList<SearchNode> childrenInOrder = new ArrayList<SearchNode>();
		ArrayList<ChildOrderingBirdsOfAFeatherNode> orderableChildren = new ArrayList<ChildOrderingBirdsOfAFeatherNode>();
		for(BirdsOfAFeatherNode child:children) {
			orderableChildren.add(new TrivialHeuristicChildOrderingBirdsOfAFeatherNode(child));
		}
		Collections.sort(orderableChildren);
		for(int i=0; i < orderableChildren.size(); i++) {
			childrenInOrder.add(orderableChildren.get(i));
		}
		return childrenInOrder;
	}

	@Override
	public float getHeuristic() {
		Card firstCard = grid[0][0];
		int heuristic;
		int i=0;
		int j=0;
		while(firstCard == null) {
			j++;
			if(j > 3) {
				j=0;
				i++;
			}
			firstCard = grid[i][j];
		}
		heuristic = firstCard.getRank();
		
		return heuristic;
	}

}
