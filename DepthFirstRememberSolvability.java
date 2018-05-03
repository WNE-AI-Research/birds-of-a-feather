import java.util.HashSet;
import java.util.Stack;
import java.util.ArrayList;
import java.util.HashMap;


public class DepthFirstRememberSolvability extends Searcher {

	HashMap<String, Boolean> solvable = new HashMap<String, Boolean>();
	
	
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

	    Boolean solved = solvable.get(str);
	    //If this node is known to be unsolvable
	    if(solved != null && solved.equals((Boolean) false))
	    	continue;
	    
	    // If the search node is a goal node, store it and return
	    // with success (true).
	    if ((solved != null && solved.equals((Boolean) true)) || node.isGoal()) {
	    	
	    	//Add solvable nodes
	    	SearchNode addNode = node;
	    	while(addNode != null){
	    		solvable.put(addNode.toString(), true);
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
	    	solvable.put(str, false); //Add this node
	    	SearchNode current = node;
	    	while(current.parent.parent.equals(stack.peek().parent)){ //Adds more nodes to solvable if you are backtracking through multiple nodes
	    		solvable.put(current.parent.toString(), false);
	    		current = current.parent;
	    	}
	    		
	    	
	    }
	    
	    else{
	    	for (SearchNode child : children)
	    		stack.push(child);
	    }

	}

    }
    
}// DepthFirstSearcher


