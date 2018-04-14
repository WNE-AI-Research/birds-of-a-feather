import java.util.ArrayList;

public class NodeOrderingDepthFirstBirdsOfAFeatherSearch extends DepthFirstSearcherNoRepeat {

	public boolean search(ChildOrderingBirdsOfAFeatherNode rootNode) {
		Searcher searcher = new DepthFirstSearcherNoRepeat();
		return searcher.search(rootNode);
	}
	
	public static void main(String[] args) {
		NodeOrderingDepthFirstBirdsOfAFeatherSearch searcher = new NodeOrderingDepthFirstBirdsOfAFeatherSearch();
		SolvableBirdsOfAFeatherNode birdsNode = new SolvableBirdsOfAFeatherNode();
		TrivialHeuristicChildOrderingBirdsOfAFeatherNode childOrderingNode = new TrivialHeuristicChildOrderingBirdsOfAFeatherNode(birdsNode);
		System.out.println(childOrderingNode);
		
		System.out.println(searcher.search(childOrderingNode));
		ArrayList<SearchNode> children = childOrderingNode.expand();
		for(SearchNode child : children) {
			System.out.println(child.toString().substring(0, 2));
		}
		
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		
		children = children.get(0).expand();
		for(SearchNode child : children) {
			System.out.println(child.toString());
		}
		
	}

}
