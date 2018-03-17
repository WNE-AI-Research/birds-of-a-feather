import java.util.ArrayList;

public class Experiment1 {

	public static void main(String[] args) {
    	int startSeed = 0;
    	int numSeeds = 100;
    	int numSolved = 0;
    	Searcher searcher;
    	
    	//searcher = new DepthFirstSearcherNoRepeat();
    	//searcher = new AStarSearcher(new HeuristicMostMoves());
		//searcher = new AStarSearcher(new RankHeuristic());
	    //searcher = new AStarSearcher(new SuitHeuristic());
	    searcher = new AStarSearcher(new RankSuitHeuristic());
    	ArrayList<Integer> unsolvable = new ArrayList<Integer>();
    	for (int seed = startSeed; seed < startSeed + numSeeds; seed++) {
    		System.out.print("Seed " + seed + ": ");    	
    		BirdsOfAFeatherNode node = new BirdsOfAFeatherNode(seed);
    		
    		boolean solvable = node.hasOddBird() ? false : searcher.search(node);
    		if (solvable) {
    			System.out.println("solved.");
    			numSolved++;
    		}
    		else
    		{
    			unsolvable.add(seed);
    			System.out.println("unsolvable seed " + seed + ".");
    			System.out.println(node);
    		}
    	}
    	System.out.printf("Seeds %d-%d: %d solved, %d not solvable\n", startSeed, startSeed + numSeeds - 1, numSolved, numSeeds - numSolved);
    	System.out.println("          Unsolvable: " + unsolvable);
    }


}
