import java.util.ArrayList;

/*
 * Splits the 4x4 board into 8 1x4 or 4x1, states are valued higher
 * based on how many of these are solvable
 */
public class HeuristicDivideAndConquer3 implements HeuristicInterface{

	@Override
	public double h(SearchNode n) {
		BirdsOfAFeatherNode node = (BirdsOfAFeatherNode) n;
		Card[][] grid1 = new Card[1][4];
		Card[][] grid2 = new Card[1][4];
		Card[][] grid3 = new Card[1][4];
		Card[][] grid4 = new Card[1][4];
		Card[][] grid5 = new Card[4][1];
		Card[][] grid6 = new Card[4][1];
		Card[][] grid7 = new Card[4][1];
		Card[][] grid8 = new Card[4][1];

		for(int i = 0; i < 4; i++){
				grid1[0][i] = node.grid[0][i];
				grid2[0][i] = node.grid[0][i];
				grid3[0][i] = node.grid[0][i];
				grid4[0][i] = node.grid[0][i];
				grid5[i][0] = node.grid[i][0];
				grid6[i][0] = node.grid[i][0];
				grid7[i][0] = node.grid[i][0];
				grid8[i][0] = node.grid[i][0];

			}
		
		BirdsOfAFeatherNodeCustom part1 = new BirdsOfAFeatherNodeCustom(grid1);
		BirdsOfAFeatherNodeCustom part2 = new BirdsOfAFeatherNodeCustom(grid2);
		BirdsOfAFeatherNodeCustom part3 = new BirdsOfAFeatherNodeCustom(grid3);
		BirdsOfAFeatherNodeCustom part4 = new BirdsOfAFeatherNodeCustom(grid4);
		BirdsOfAFeatherNodeCustom part5 = new BirdsOfAFeatherNodeCustom(grid5);
		BirdsOfAFeatherNodeCustom part6 = new BirdsOfAFeatherNodeCustom(grid6);
		BirdsOfAFeatherNodeCustom part7 = new BirdsOfAFeatherNodeCustom(grid7);
		BirdsOfAFeatherNodeCustom part8 = new BirdsOfAFeatherNodeCustom(grid8);
		ArrayList<BirdsOfAFeatherNodeCustom> parts = new ArrayList<BirdsOfAFeatherNodeCustom>();
		parts.add(part1);
		parts.add(part2);
		parts.add(part3);
		parts.add(part4);
		parts.add(part5);
		parts.add(part6);
		parts.add(part7);
		parts.add(part8);
		
		int solvable = 0;
		DepthFirstSearcherNoRepeat search = new DepthFirstSearcherNoRepeat();
		for(BirdsOfAFeatherNodeCustom part : parts){
			
			if(search.search(part) == true){
				solvable++;
			}
		}
		
		return 1/(1+solvable);
	}

}
