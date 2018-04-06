
public class SuitState extends State {
	
	int[] current = {0,0,0,0};
	String move;
	
	public SuitState( BirdsOfAFeatherNode node ) {
		move = node.prevMove;
		
	}
	int[] getState() {
		 String[] movearray = move.split("-");
		 char suit = movearray[1].charAt(1);
		 switch(suit) {
		 case 'A':
		
		 }
		 return current;
	 }
}
