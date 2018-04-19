import java.io.*;
import java.util.Scanner;

/** BOFUnsolvable uses data provided by BOFAnalyzer data.csv to export data exclusive to unsolvable seeds */

public class BOFUnsolvable {
	
	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		File file = new File( "solves.csv" );
		Scanner solvabilityFile = new Scanner( file );
		String line;
		String unsolves;
		int sol = 0;
		int seedNum;
		while (solvabilityFile.hasNext() ) 
		{
			line = solvabilityFile.nextLine();
			unsolves = line.split( "," )[1];
			if ( unsolves.equals( "FALSE" ) )
				sol++;
		}
		solvabilityFile.close();
		
		solvabilityFile = new Scanner( file );
		int[] unsolvable = new int[sol];
		int i = 0;

		while (solvabilityFile.hasNext() ) 
		{
			line = solvabilityFile.nextLine();
			seedNum = Integer.parseInt( line.split( "," )[0] );
			unsolves = line.split( "," )[1];
			if ( unsolves.equals( "FALSE" ) ) 
			{
				unsolvable[i] = seedNum;
				i++;
			}
		}
		solvabilityFile.close();
		
		solvabilityFile = new Scanner( new File( "data.csv" ) );
		String header = solvabilityFile.nextLine();
		int size = header.split( "," ).length;
		String[] data = new String[size];
//		System.out.println( header + "\n" );
		double euclidMean,euclidIgnZeroMean,medianMean,medIgnZeroMean,rangeMean,rngIgnZeroMean,stdDevMean,stdDevIgnZeroMean,presentMean;
		euclidMean = euclidIgnZeroMean = medianMean = medIgnZeroMean = rangeMean = rngIgnZeroMean = stdDevMean = stdDevIgnZeroMean = presentMean = 0;
		double solveEuclidMean,solveEuclidIgnZeroMean,solveMedianMean,solveMedIgnZeroMean,solveRangeMean,solveRngIgnZeroMean,solveStdDevMean,solveStdDevIgnZeroMean,solvePresentMean;
		solveEuclidMean = solveEuclidIgnZeroMean = solveMedianMean = solveMedIgnZeroMean = solveRangeMean = solveRngIgnZeroMean = solveStdDevMean = solveStdDevIgnZeroMean = solvePresentMean = 0;
		int numSeeds = 0;
		int numSolvable = 0;
		int numUnsolvable = 0;
		double[] solvableEuclid = new double[30000];
		double[] totalEuclid = new double[30000];
		double[] solvableRange = new double[30000];
		double[] totalRange = new double[30000];
//		int solveIndex = 0;
//		int totalIndex = 0;
		
		FileWriter fw = new FileWriter( "unsolvable.csv" );
		fw.write( "Seed,Count,SuitEuclid,SuitEucIgnZero,SuitMedian,SuitMedIgnZero,SuitRange,SuitRngIgnZero,SuitStdDev,SuitSDIgnZero,SuitsPres\n");
		boolean found;
		
		while (solvabilityFile.hasNext() ) 
		{
			data = solvabilityFile.nextLine().split( "," );
			seedNum = Integer.parseInt( data[0] );
			found = false;
			for ( int x: unsolvable ) 
			{
				if ( x == seedNum )
				{
//					System.out.println( "Seed: " + data[0] );
//					System.out.println( "SuitEuclid: " + data[18]);
//					System.out.println( "SuitEuclidIgnZero: " + data[19]);
//					System.out.println( "SuitMedian: " + data[20]);
//					System.out.println( "SuitMedIgnZero: " + data[21]);
//					System.out.println( "SuitRange: " + data[22]);
//					System.out.println( "SuitRngIgnZero: " + data[23]);
//					System.out.println( "SuitStdDev: " + data[24]);
//					System.out.println( "SuitSDIgnZero: " + data[25]);
////					System.out.println( "SuitsPresent: " + data[26] + "\n");
					fw.write( data[0] + "," + (numUnsolvable+1) + "," + data[18] + "," + data[19] + "," + data[20] + "," + data[21] + "," + data[22] + "," + data[23] + "," + data[24] + ","
							+ data[25] + "," + data[26] + "\n" );
					numUnsolvable++;
					found = true;
				}
			}
			if ( !found ) 
			{
				solveEuclidMean += Double.parseDouble( data[18] );
				solveEuclidIgnZeroMean += Double.parseDouble( data[19] );
				solveMedianMean += Double.parseDouble( data[20] );
				solveMedIgnZeroMean += Double.parseDouble( data[21] );
				solveRangeMean += Double.parseDouble( data[22] );
				solveRngIgnZeroMean += Double.parseDouble( data[23] );
				solveStdDevMean += Double.parseDouble( data[24] );
				solveStdDevIgnZeroMean += Double.parseDouble( data[25] );
				solvePresentMean += Double.parseDouble( data[26] );
				solvableEuclid[numSolvable] = Double.parseDouble( data[18] );
				solvableRange[numSolvable] = Double.parseDouble( data[22] );
				numSolvable++;
			}
			euclidMean += Double.parseDouble( data[18] );
			euclidIgnZeroMean += Double.parseDouble( data[19] );
			medianMean += Double.parseDouble( data[20] );
			medIgnZeroMean += Double.parseDouble( data[21] );
			rangeMean += Double.parseDouble( data[22] );
			rngIgnZeroMean += Double.parseDouble( data[23] );
			stdDevMean += Double.parseDouble( data[24] );
			stdDevIgnZeroMean += Double.parseDouble( data[25] );
			presentMean += Double.parseDouble( data[26] );
			totalEuclid[numSeeds] = Double.parseDouble( data[18] );
			totalRange[numSeeds] = Double.parseDouble( data[22] );
			numSeeds++;
		}
		solvabilityFile.close();
		euclidMean /= numSeeds;
		euclidIgnZeroMean /= numSeeds;
		medianMean /= numSeeds;
		medIgnZeroMean /= numSeeds;
		rangeMean /= numSeeds;
		rngIgnZeroMean /= numSeeds;
		stdDevMean /= numSeeds;
		stdDevIgnZeroMean /= numSeeds;
		presentMean /= numSeeds;
		
		solveEuclidMean /= numSolvable;
		solveEuclidIgnZeroMean /= numSolvable;
		solveMedianMean /= numSolvable;
		solveMedIgnZeroMean /= numSolvable;
		solveRangeMean /= numSolvable;
		solveRngIgnZeroMean /= numSolvable;
		solveStdDevMean /= numSolvable;
		solveStdDevIgnZeroMean /= numSolvable;
		solvePresentMean /= numSolvable;
		
		double solvableEuclidStDev = standardDeviation( solvableEuclid );
		double totalEuclidStDev = standardDeviation( totalEuclid );
		
		double solvableRangeStDev = standardDeviation( solvableRange );
		double totalRangeStDev = standardDeviation( totalRange );
		
//		System.out.println( "MEAN" + "\n-----" );
//		System.out.println( "SuitEuclid: " + euclidMean );
//		System.out.println( "SuitEuclidIgnZero: " + euclidIgnZeroMean );
//		System.out.println( "SuitMedian: " + medianMean );
//		System.out.println( "SuitMedIgnZero: " + medIgnZeroMean );
//		System.out.println( "SuitRange: " + rangeMean );
//		System.out.println( "SuitRngIgnZero: " + rngIgnZeroMean );
//		System.out.println( "SuitStdDev: " + stdDevMean );
//		System.out.println( "SuitSDIgnZero: " + stdDevIgnZeroMean );
//		System.out.println( "SuitsPresent: " + presentMean );
		fw.write( "SOLVABLE MEAN" + "," + numSolvable + "," + solveEuclidMean + "," + solveEuclidIgnZeroMean + "," + solveMedianMean + "," + solveMedIgnZeroMean + "," + solveRangeMean + "," 
				+ solveRngIgnZeroMean + "," + solveStdDevMean + "," + solveStdDevIgnZeroMean + "," + + solvePresentMean + "\n" );
		fw.write( "TOTAL MEAN" + "," + numSeeds + "," + euclidMean + "," + euclidIgnZeroMean + "," + medianMean + "," + medIgnZeroMean + "," + rangeMean + "," + rngIgnZeroMean + "," + stdDevMean + ","
				+ stdDevIgnZeroMean + "," + + presentMean + "\n" );
		fw.write( "\nStandard Deviation\n,Unsolvable,Solvable,Total\n" );
		fw.write( "Euclidean,," + solvableEuclidStDev + "," + totalEuclidStDev + "\n" );
		fw.write( "Range,," + solvableRangeStDev + "," + totalRangeStDev );
		fw.close();
	}
	private static double sum( double[] array)
	{
		double total = 0;
		for(double i:array) total += i;
		return total;
	}
	private static double mean( double[] array)
	{
		return sum(array) / (double) array.length;
	}
	public static double standardDeviation( double[] array)
	{
		double mean = mean(array);
		double total = 0;
		for( double i:array){
			double diff = i - mean;
			total += Math.pow(diff, 2);
		}
		return Math.sqrt(total/array.length);
	}
	
}
