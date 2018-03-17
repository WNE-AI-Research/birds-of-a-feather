import java.util.ArrayList;
import java.util.Scanner;

public class Experiment2 {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.print("Start seed? ");
    	int startSeed = in.nextInt();
		System.out.print("How many seeds? ");
    	int numSeeds = in.nextInt();
    	int numSolved = 0;
    	Searcher searcher = new DepthFirstSearcherNoRepeat();
    	ArrayList<Integer> unsolvableOddBirds = new ArrayList<Integer>();
    	ArrayList<Integer> unsolvable = new ArrayList<Integer>();
    	for (int seed = startSeed; seed < startSeed + numSeeds; seed++) {
    		System.out.print("Seed " + seed + ": ");    	
    		BirdsOfAFeatherNode node = new BirdsOfAFeatherNode(seed);
    		boolean solvable = false;
    		if (node.hasOddBird()) {
    			unsolvableOddBirds.add(seed);
    			System.out.print("odd bird ");
    		}
    		else {
    			searcher = new DepthFirstSearcherNoRepeat();
    			solvable = searcher.search(node);
    			if (solvable) {
    				System.out.println("solved.");
    				numSolved++;
    			}
    		}
    		if (!solvable) {
    			unsolvable.add(seed);
    			System.out.println("unsolvable " + seed);
    			System.out.println(node);
    		}
    	}
    	System.out.printf("Seeds %d-%d: %d solved, %d not solvable\n", startSeed, startSeed + numSeeds - 1, numSolved, numSeeds - numSolved);
    	System.out.println("Unsolvable odd birds: " + unsolvableOddBirds);
    	System.out.println("          Unsolvable: " + unsolvable);
    	in.close();
    }


}
