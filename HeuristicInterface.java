/*
 * Heuristic interface class to be used in AStarSearchers.
 */
public interface HeuristicInterface {
	public double h(SearchNode n); //Returns an integer for the heuristic value of node n.
}
