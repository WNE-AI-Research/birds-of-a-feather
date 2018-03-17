//It turns A* into depth first search..
public class testHeuristic implements HeuristicInterface {

	@Override
	public int h(SearchNode n) {
		return (0 - 2*n.depth);
	}
	
}
