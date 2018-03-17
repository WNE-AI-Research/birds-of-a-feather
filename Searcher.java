/**
 * Searcher.java - a superclass for AI searcher classes.
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

public abstract class Searcher {

	/**
	 * <code>nodeCount</code> - number of nodes that have been goal-checked
	 */
	int nodeCount = 0;

	/**
	 * <code>goalNode</code> - a goal node if the previous search was
	 * successful; null otherwise.
	 */
	SearchNode goalNode = null;

	/**
	 * <code>search</code> - Search for a goal node starting at the
	 * given "root" SearchNode, and return whether or not a goal node
	 * was found.
	 *
	 * @param node a <code>SearchNode</code> value - the initial
	 * "root" search node
	 * @return a <code>boolean</code> value - whether or not a goal
	 * node was found */
	public abstract boolean search(SearchNode node);


	/**
	 * <code>getGoalNode</code> - Returns a goal node if the previous
	 * search was successful, and null otherwise.
	 *
	 * @return a <code>SearchNode</code> value - the goal node from
	 * previous search or null if no goal node was found */
	public SearchNode getGoalNode() 
	{
		return goalNode;
	}


	/**
	 * <code>getNodeCount</code> - Returns the number of nodes
	 * examined (goal-checked) in the previous search.
	 *
	 * @return an <code>int</code> value - the number of nodes checked
	 * in the previous search.  This may be considerably less than the
	 * number of children generated. */
	public int getNodeCount() 
	{
		return nodeCount;
	}


	/**
	 * <code>printGoalPath</code> - If the previous search was
	 * successful, print each node along the goal path in sequence
	 * starting with the root node.  Otherwise, print "Goal node not
	 * found".  */
	public void printGoalPath() 
	{
		if (goalNode == null)
			System.out.println("Goal node not found");
		else
			printPath(goalNode);
	}


	/**
	 * <code>printPath</code> - print each node along the path to the
	 * given node in sequence starting with the root node.
	 *
	 * @param node a <code>SearchNode</code> value */
	private void printPath(SearchNode node) 
	{
		if (node.parent != null)
			printPath(node.parent);
		System.out.println(node);
	}


}// Searcher
