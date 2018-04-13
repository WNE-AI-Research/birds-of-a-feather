/*
 * This class is just a way to build a Node with a
 * grid of cards that you give to the constructor.
 */
public class BirdsOfAFeatherNodeCustom extends BirdsOfAFeatherNode {
	public BirdsOfAFeatherNodeCustom(Card[][] customGrid){
		grid = new Card[customGrid.length][customGrid[0].length];
		stackSize = new int[customGrid.length][customGrid[0].length];
		for (int r = 0; r < customGrid.length; r++)
			for (int c = 0; c < customGrid[0].length; c++) {
				grid[r][c] = customGrid[r][c];
				stackSize[r][c] = 1;
			}
	}
}
