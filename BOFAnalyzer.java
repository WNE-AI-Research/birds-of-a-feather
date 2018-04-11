
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;


public class BOFAnalyzer {
	public static void main(String[] args){
		int startSeed = 0;
		int numSeeds = 10000;
		try{
			FileWriter fw = new FileWriter("data.csv");
			
			//Header row
			fw.write("Seed,Clubs,Hearts,Spades,Diamonds,Ace,Two,Three,Four,Five,Six,Seven,Eight,Nine,Ten,Jack,Queen,King,");
			fw.write("SuitEuclid,SuitEucIgnZero,SuitMedian,SuitMedIgnZero,SuitRange,SuitRngIgnZero,SuitStdDev,SuitSDIgnZero,SuitsPres\n");
			
			for (int seed = startSeed; seed < startSeed + numSeeds; seed++) {
	    		BirdsOfAFeatherNode node = new BirdsOfAFeatherNode(seed);
	    		//System.out.println("Seed: "+seed);
	    		int ranks[] = new int[13];
	    		int suits[] = new int[4];
	    		for(int r=0; r<ranks.length; r++) ranks[r] = 0;
	    		for(int s=0; s<suits.length; s++) suits[s] = 0;
	    		
	    		Card[][] grid = node.getGrid();
	    		for(Card[] row : grid){
	    			for(Card c:row){
	    				int rank = c.getRank();
	    				int suit = c.getSuit();
	    				ranks[rank]++;
	    				suits[suit]++;
	    			}
	    		}
	    		// Write seed
	    		fw.write(seed+",");
	    		// Write suit distrib
	    		for(int i:suits){
	    			fw.write(i+",");
	    		}
	    		// Write rank distrib
	    		for(int i:ranks){
	    			fw.write(i+",");
	    		}
	    		
	    		//Analysis
	    		fw.write(euclideanSuits(suits)+",");
	    		fw.write(euclideanIgnoreZero(suits)+",");
	    		fw.write(median(suits)+",");
	    		fw.write(medianIgnoreZero(suits)+",");
	    		fw.write(range(suits)+",");
	    		fw.write(rangeIgnoreZero(suits)+",");
	    		fw.write(standardDeviation(suits)+",");
	    		fw.write(standardDeviationIgnoreZero(suits)+",");
	    		fw.write(present(suits)+"\n");
			}
			fw.close();
		}
		catch(IOException ioe){
			System.err.println(ioe);
		}
	}
	
	private static int[] removeZeros(int[] array){
		int zeroCount=0;
		for(int i:array){
			if(i==0) zeroCount++;
		}
		int[] copy = new int[array.length-zeroCount];
		int j = 0;
		for(int i=0; i<array.length; i++){
			if(array[i] != 0){
				copy[j] = array[i];
				j++;
			}
		}
		
		return copy;
	}
	
	private static double sum(int[] array){
		double total = 0;
		for(int i:array) total += i;
		return total;
	}
	
	private static double mean(int[] array){
		return sum(array) / (double) array.length;
	}
	
	private static double euclidean(int[] a, double[] b){
		double sum = 0;
		for(int i=0; i<a.length; i++)
			sum += Math.pow(a[i] - b[i], 2.0);
		return Math.sqrt(sum);
	}
	
	public static double euclideanSuits(int[] suits){
		double mean = mean(suits);
		double[] base = {mean, mean, mean, mean};
		return euclidean(suits, base);
	}
	
	public static double euclideanRanks(int[] ranks){
		double mean = mean(ranks);
		double[] base = new double[13];
		for(int i=0; i<base.length; i++) base[i] = mean;
		return euclidean(ranks, base);
	}
	
	public static double euclideanIgnoreZero(int[] array){
		array = removeZeros(array);
		
		double[] base = new double[array.length];
		double mean = mean(array);
		for(int i = 0; i < base.length; i++){
			base[i] = mean;
		}
		return euclidean(array, base);
	}
	
	public static double median(int[] array){
		int[] copy = Arrays.copyOf(array, array.length);
		Arrays.sort(copy);
		
		if(copy.length % 2 == 0)
			return (copy[copy.length/2] + copy[(copy.length/2)-1])/2.0;
		else
			return copy[copy.length/2];
	}
	
	public static double medianIgnoreZero(int[] array){
		array = removeZeros(array);
		return median(array);
	}
	
	public static int range(int[] array){
		int[] copy = Arrays.copyOf(array, array.length);
		Arrays.sort(copy);
		
		return copy[copy.length-1] - copy[0];
	}
	
	public static int rangeIgnoreZero(int[] array){
		array = removeZeros(array);
		return range(array);
	}
	
	public static int present(int[] array){
		int total = 0;
		for(int i:array){
			if(i != 0) total++;
		}
		return total;
	}
	
	public static double standardDeviation(int[] array){
		double mean = mean(array);
		double total = 0;
		for(int i:array){
			double diff = i - mean;
			total += Math.pow(diff, 2);
		}
		return Math.sqrt(total/array.length);
	}
	
	public static double standardDeviationIgnoreZero(int[] array){
		array = removeZeros(array);
		return standardDeviation(array);
	}
}
