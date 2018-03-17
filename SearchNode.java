import java.util.ArrayList;

/**
 * SearchNode.java - a simple node for uninformed AI search (assuming
 * cost equals depth).
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

public abstract class SearchNode implements Cloneable {


	/**
	 * variable <code>parent</code> - parent search node; null if and
	 * only if node is the root of the search tree.  */
	public SearchNode parent = null;


	/**
	 * variable <code>depth</code> - search depth; 0 at the root
	 * search node; a child node has its parent node depth + 1. */
	public int depth = 0;


	/**
	 * Creates an <code>SearchNode</code> instance and sets it to an
	 * initial search state. One will generally want to override this
	 * constructor to initialize a root node for search. */
	public SearchNode() {}


	/**
	 * <code>isGoal</code> - test whether or not the current node is a
	 * goal node.
	 *
	 * @return a <code>boolean</code> value - whether or not the
	 * current node is a goal node */
	public abstract boolean isGoal();


	/**
	 * <code>expand</code> - return a (possibly empty) ArrayList of this
	 * node's children.  A new child is created by calling
	 * <code>childClone</code> and appropriately modifying the state
	 * of the returned node.
	 *
	 * @return an <code>ArrayList</code> of SearchNodes that are children
	 * of this node */
	public abstract ArrayList<SearchNode> expand();


	/**
	 * <code>childClone</code> - returns a clone of this node that has
	 * been made a child of this node and has a depth one greater than
	 * this.
	 *
	 * @return a <code>SearchNode</code> value */
	public SearchNode childClone() 
	{
		SearchNode child = (SearchNode) clone();
		child.parent = this;
		child.depth = depth + 1;
		return child;
	}


	/**
	 * <code>clone</code> - performs a DEEP clone (copy) of this
	 * node.  That is, any change to the original/cloned node should
	 * have no side-effect on the other.  For example, when the node's
	 * state is described using an array, you must create a new array
	 * for the cloned node and copy the contents of the original node
	 * array.
	 *
	 * @return an <code>Object</code> value */
	public Object clone() 
	{
		try {
			return super.clone();
		}
		catch (CloneNotSupportedException e) {
			throw new RuntimeException ("This class does not implement Cloneable.");
		}
	}    


	/**
	 * NOTE: It is generally beneficial to also override the default
	 * toString method to provide a text representation of the node
	 * state.  You will want to override the default equals method if
	 * you're using a search algorithm with repeated-state
	 * checking. */

}// SearchNode

