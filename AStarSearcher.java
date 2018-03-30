import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

/*
 * Abstract Searcher class to implement A* search.
 * Contains a Heuristic interface, to be implemented separately.
 */
public class AStarSearcher extends Searcher {

	HashSet<String> closed = new HashSet<String>();
	HeuristicInterface heuristic;
	
	
	public AStarSearcher(HeuristicInterface h){
		heuristic = h;
	}
	
    /**
     * Given an initial node, perform A* search.
     *
     * @param rootNode - the initial node
     * @return whether or not goal node was found
     */
    public boolean search(SearchNode rootNode) {
	
		// Initialize search variables.
		LinkedList<SearchNode> list = new LinkedList<SearchNode>();
		list.push(rootNode);
		
		// Main search loop.
		while (true) {
	
		    // If the search list is empty, return with failure
		    // (false).
		    if (list.isEmpty()) {
		    	System.out.println("Number of nodes visited: " + nodeCount );
		    	nodeCount = 0;
		    	return false;
		    }
	
		    // Otherwise use the heuristic to find the next
		    // node to pop off the list.
		    SearchNode node = heuristicNextNode(list);
		    list.remove(node);
		    String str = node.toString();
		    if (closed.contains(str))
		    	continue;
		    closed.add(str);
//		    System.out.println(str);
		    nodeCount++;
	
		    // If the search node is a goal node, store it and return
		    // with success (true).
		    if (node.isGoal()) {
		    	goalNode = node;
		    	System.out.println("Number of nodes visited: " + nodeCount );
		    	nodeCount = 0;
		    	return true;
		    }
	
		    // Otherwise, expand the node and push each of its
		    // children into the stack based on heuristic value.
		    ArrayList<SearchNode> frontier = node.expand();
		    for (int i = 0; i < frontier.size(); i++){
		    	list.add(frontier.get(i));
		    }
	    
		}
	}
    
    /**
     * Takes a list of nodes and returns the node with the 
     * lowest depth + heuristic value.
     * @param list The list of nodes that can be opened
     * @return The node to open next
     */
	private SearchNode heuristicNextNode(LinkedList<SearchNode> list){


		SearchNode bestNode = null;
		double currentValue;
		double minValue = Double.MAX_VALUE;
		
		for(SearchNode n : list){
			currentValue =  heuristic.h(n) - n.depth;
			if( currentValue < minValue ) {
//				System.out.println(currentValue);
				minValue = currentValue;
				bestNode = n;
			}
		}
		return bestNode;
    }
    
}
