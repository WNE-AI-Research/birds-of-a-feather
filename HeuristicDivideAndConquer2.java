import java.util.ArrayList;

/*
 * Splits the 4x4 board into 9 2x2, states are valued higher
 * based on how many of these 2x2 are solvable
 */
public class HeuristicDivideAndConquer2 implements HeuristicInterface{

	@Override
	public double h(SearchNode n) {
		BirdsOfAFeatherNode node = (BirdsOfAFeatherNode) n;
		Card[][] grid1 = new Card[2][2];
		Card[][] grid2 = new Card[2][2];
		Card[][] grid3 = new Card[2][2];
		Card[][] grid4 = new Card[2][2];
		Card[][] grid5 = new Card[2][2];
		Card[][] grid6 = new Card[2][2];
		Card[][] grid7 = new Card[2][2];
		Card[][] grid8 = new Card[2][2];
		Card[][] grid9 = new Card[2][2];
		for(int row = 0; row < 2; row++){
			for(int col = 0; col < 2; col++){
				grid1[row][col] = node.grid[row][col];
				grid2[row][col] = node.grid[row][col+1];
				grid3[row][col] = node.grid[row][col+2];
				grid4[row][col] = node.grid[row+1][col];
				grid5[row][col] = node.grid[row+1][col+1];
				grid6[row][col] = node.grid[row+1][col+2];
				grid7[row][col] = node.grid[row+2][col];
				grid8[row][col] = node.grid[row+2][col+1];
				grid9[row][col] = node.grid[row+2][col+2];

			}
		}
		
		BirdsOfAFeatherNodeCustom part1 = new BirdsOfAFeatherNodeCustom(grid1);
		BirdsOfAFeatherNodeCustom part2 = new BirdsOfAFeatherNodeCustom(grid2);
		BirdsOfAFeatherNodeCustom part3 = new BirdsOfAFeatherNodeCustom(grid3);
		BirdsOfAFeatherNodeCustom part4 = new BirdsOfAFeatherNodeCustom(grid4);
		BirdsOfAFeatherNodeCustom part5 = new BirdsOfAFeatherNodeCustom(grid5);
		BirdsOfAFeatherNodeCustom part6 = new BirdsOfAFeatherNodeCustom(grid6);
		BirdsOfAFeatherNodeCustom part7 = new BirdsOfAFeatherNodeCustom(grid7);
		BirdsOfAFeatherNodeCustom part8 = new BirdsOfAFeatherNodeCustom(grid8);
		BirdsOfAFeatherNodeCustom part9 = new BirdsOfAFeatherNodeCustom(grid9);
		ArrayList<BirdsOfAFeatherNodeCustom> parts = new ArrayList<BirdsOfAFeatherNodeCustom>();
		parts.add(part1);
		parts.add(part2);
		parts.add(part3);
		parts.add(part4);
		parts.add(part5);
		parts.add(part6);
		parts.add(part7);
		parts.add(part8);
		parts.add(part9);
		
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
