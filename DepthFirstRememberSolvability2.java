import java.util.HashSet;
import java.util.List;
import java.util.Stack;
import java.util.ArrayList;


public class DepthFirstRememberSolvability2 extends Searcher {

	HashSet<String> solvable = new HashSet<String>();
	HashSet<String> unsolvable = new HashSet<String>();

	
    public boolean search(SearchNode rootNode) {

    	
    HashSet<String> closed = new HashSet<String>();
    	
	Stack<SearchNode> stack = new Stack<SearchNode>();
	stack.push(rootNode);
	
	// Main search loop.
	while (true) {
		if(nodeCount > 1000000){ //Skip seeds that eventually run out of memory
			nodeCount = 0;
			return false;
		}

	    // If the search stack is empty, return with failure
	    // (false).
	    if (stack.isEmpty()) {
	    	System.out.println("Number of nodes visited: " + nodeCount );
	    	nodeCount = 0;
	    	return false;
	    }
	    // Otherwise pop the next search node from the top of
	    // the stack.
	    SearchNode node = stack.pop();
	    String str = node.toString();
	    if (closed.contains(str))
	    	continue;
	    closed.add(str);
//	    System.out.println(str);
	    nodeCount++;

	    //If this node is known to be unsolvable
	    if(unsolvable.contains(node))
	    	continue;
	    
	    // If the search node is a goal node, store it and return
	    // with success (true).
	    if ( node.isGoal() || solvable.contains(node)) {
	    	
	    	//Add solvable nodes
	    	SearchNode addNode = node;
	    	while(addNode != null){
	    		addSolvablePermutations(addNode);
	    		addNode = addNode.parent;
	    	}
	    	
	    	goalNode = node;
	    	System.out.println("Number of nodes visited: " + nodeCount );
	    	nodeCount = 0;
	    	return true;
	    }

	    // Otherwise, expand the node and push each of its
	    // children into the stack.
	    
	    ArrayList<SearchNode> children = node.expand();
	    
	    //First check if it has no children (is unsolvable)
	    if(children.isEmpty()){
    		addUnsolvablePermutations(node);
	    	SearchNode current = node;
	    	while(!(current.parent.equals(stack.peek().parent))){ //Adds more nodes to solvable if you are backtracking through multiple nodes
	    		addUnsolvablePermutations(current.parent);
	    		current = current.parent;
	    	}
	    		
	    	
	    }
	    
	    else{
	    	for (SearchNode child : children)
	    		stack.push(child);
	    }

	}

    }
    
    private void addSolvablePermutations(SearchNode node){
    	BirdsOfAFeatherNode birdNode = (BirdsOfAFeatherNode) node;
    	Card[][] grid = birdNode.grid;
    	ArrayList<Card[][]> permutations = permute(grid);
    	for(Card[][] g : permutations)
    		solvable.add(gridToString(g));
    }
    
    private void addUnsolvablePermutations(SearchNode node){
    	BirdsOfAFeatherNode birdNode = (BirdsOfAFeatherNode) node;
    	Card[][] grid = birdNode.grid;
    	ArrayList<Card[][]> permutations = permute(grid);
    	for(Card[][] g : permutations)
    		unsolvable.add(gridToString(g));
    }
    
    
    int[][] fourPerm = {{0,1,2,3}, {0,1,3,2}, {0,2,1,3}, {0,2,3,1}, {0,3,1,2}, {0,3,2,1}, 
    					{1,0,2,3}, {1,0,3,2}, {1,2,0,3}, {1,2,3,0}, {1,3,0,2}, {1,3,2,0},
    					{2,1,0,3}, {2,1,3,0}, {2,0,1,3}, {2,0,3,1}, {2,3,1,0}, {2,3,0,1},
    					{3,1,2,0}, {3,1,0,2}, {3,2,1,0}, {3,2,0,1}, {3,0,1,2}, {3,0,2,1}};
    
    private ArrayList<Card[][]> permute(Card[][] grid){
    	ArrayList<Card[][]> permutations = new ArrayList<Card[][]>();
    	Card[][]turnedGrid1 = turn(grid);
    	Card[][]turnedGrid2 = turn(turnedGrid1);
    	Card[][]turnedGrid3 = turn(turnedGrid2);
    	
    	for(int[] num : fourPerm){
    		Card[][] newGrid = {grid[num[0]], grid[num[1]], grid[num[2]], grid[num[3]]};
    		Card[][] turnedNewGrid1 = {turnedGrid1[num[0]], turnedGrid1[num[1]], turnedGrid1[num[2]], turnedGrid1[num[3]]};
    		Card[][] turnedNewGrid2 = {turnedGrid2[num[0]], turnedGrid2[num[1]], turnedGrid2[num[2]], turnedGrid2[num[3]]};
    		Card[][] turnedNewGrid3 = {turnedGrid3[num[0]], turnedGrid3[num[1]], turnedGrid3[num[2]], turnedGrid3[num[3]]};
    		permutations.add(newGrid);
    		permutations.add(turnedNewGrid1);
    		permutations.add(turnedNewGrid2);
    		permutations.add(turnedNewGrid3);
    	}
    	
    	return permutations;
    }
    
    
    //Turns the grid 90°
    private Card[][] turn(Card[][] grid){
        Card[][] rotated = new Card[4][4];
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                rotated[c][3-r] = grid[r][c];
            }
        }
        return rotated;
    }
    
    
    //Copied from BirdsOfAFeatherNode
    private String gridToString(Card[][] grid) {
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
    
}// DepthFirstSearcher


