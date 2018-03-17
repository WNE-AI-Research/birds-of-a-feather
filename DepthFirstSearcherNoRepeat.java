import java.util.HashSet;
import java.util.Stack;

/**
 * DepthFirstSearcher.java - a simple iterative implementation of
 * depthRemaining-first search.
 *
 * @author Todd Neller
 * @version 1.1
 *

Copyright (C) 2006 Todd Neller

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

Information about the GNU General Public License is available online at:
  http://www.gnu.org/licenses/
To receive a copy of the GNU General Public License, write to the Free
Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
02111-1307, USA.

 */

public class DepthFirstSearcherNoRepeat extends Searcher {

	HashSet<String> closed = new HashSet<String>();
	
    /**
     * <code>search</code> - given an initial node, perform
     * depthRemaining-first search (DFS).  This particular implementation of
     * DFS is iterative.
     *
     * @param rootNode a <code>SearchNode</code> value - the initial node
     * @return a <code>boolean</code> value - whether or not goal node
     * was found */
    public boolean search(SearchNode rootNode) {
	// IMPLEMENT:
	
	// Initialize search variables.
	Stack<SearchNode> stack = new Stack<SearchNode>();
	stack.push(rootNode);
	
	// Main search loop.
	while (true) {

	    // If the search stack is empty, return with failure
	    // (false).
	    if (stack.isEmpty()) {
	    	System.out.println("Number of nodes visited: " + nodeCount );
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

	    // If the search node is a goal node, store it and return
	    // with success (true).
	    if (node.isGoal()) {
	    	goalNode = node;
	    	System.out.println("Number of nodes visited: " + nodeCount );
	    	return true;
	    }

	    // Otherwise, expand the node and push each of its
	    // children into the stack.
	    for (SearchNode child : node.expand())
		stack.push(child);

	}

    }
    
}// DepthFirstSearcher


