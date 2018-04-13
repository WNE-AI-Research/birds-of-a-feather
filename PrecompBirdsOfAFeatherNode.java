
abstract class PrecompBirdsOfAFeatherNode extends BirdsOfAFeatherNode {
	
	@Override
	public PrecompBirdsOfAFeatherNode makeMove(int row1, int col1, int row2, int col2) {
		PrecompBirdsOfAFeatherNode child = (PrecompBirdsOfAFeatherNode) childClone(); 
		
		saveInformation(child, row1, col1, row2, col2);
		
		child.prevMove = child.grid[row1][col1] + "-" + child.grid[row2][col2];
		child.grid[row2][col2] = child.grid[row1][col1];
		child.grid[row1][col1] = null;
		child.stackSize[row2][col2] += child.stackSize[row1][col1];
		child.stackSize[row1][col1] = 0;
		return child;
	}
	
	abstract void saveInformation(PrecompBirdsOfAFeatherNode child, int row1, int col1, int row2, int col2);
	
	
	public PrecompBirdsOfAFeatherNode(){
		super();
	}
	
	public PrecompBirdsOfAFeatherNode(int seed){
		super(seed);
	}
}
