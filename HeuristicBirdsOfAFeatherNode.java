
public abstract class HeuristicBirdsOfAFeatherNode extends BirdsOfAFeatherNode{

	public HeuristicBirdsOfAFeatherNode() {
		super();
	}

	public HeuristicBirdsOfAFeatherNode(int seed) {
		super(seed);
	}

	public HeuristicBirdsOfAFeatherNode(int seed, int size) {
		super(seed, size);
	}

	public HeuristicBirdsOfAFeatherNode(int seed, int numRows, int numColumns) {
		super(seed, numRows, numColumns);
	}
	
	public HeuristicBirdsOfAFeatherNode(BirdsOfAFeatherNode node) {
		 this.grid = node.grid;
		 this.stackSize = node.stackSize;
		 this.prevMove = node.prevMove;
		 this.depth = node.depth;
		 this.parent = node.parent;
	}
	
	public abstract float getHeuristic();

}
