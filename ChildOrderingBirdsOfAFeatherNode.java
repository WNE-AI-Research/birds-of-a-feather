import java.util.ArrayList;

public abstract class ChildOrderingBirdsOfAFeatherNode extends HeuristicBirdsOfAFeatherNode implements Comparable<HeuristicBirdsOfAFeatherNode> {

	public ChildOrderingBirdsOfAFeatherNode() {
		super();
	}

	public ChildOrderingBirdsOfAFeatherNode(int seed) {
		super(seed);
	}

	public ChildOrderingBirdsOfAFeatherNode(int seed, int size) {
		super(seed, size);
	}

	public ChildOrderingBirdsOfAFeatherNode(int seed, int numRows, int numColumns) {
		super(seed, numRows, numColumns);
	}

	public ChildOrderingBirdsOfAFeatherNode(BirdsOfAFeatherNode node) {
		super(node);
	}
	
	public abstract ArrayList<SearchNode> expand();

	public int compareTo(HeuristicBirdsOfAFeatherNode other) {
		float heuristicDiff = this.getHeuristic() - other.getHeuristic();
		if(heuristicDiff > 0) {
			return 1;
		}
		else if(heuristicDiff < 0) {
			return -1;
		}
		else {
			return 0;
		}
	}

}
