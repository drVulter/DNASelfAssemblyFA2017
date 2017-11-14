package DNAmodeling;

import java.util.ArrayList;
import org.apache.commons.lang3.ArrayUtils;

public class Driver {

	// a method for use in the creation of the rawTileSizeTypes array
	static boolean isUnique(int[] vertexArmCount, int i) {
		boolean isUnique = true;
		for (int j = 0; j < i; j++) {
			if (vertexArmCount[j] == vertexArmCount[i])
				isUnique = false;
		}
		return isUnique;
	}

	// factorial function
	static int factorial(int num) {
		int fact = 1;
		for (int i = 1; i <= num; i++) {
			fact = fact * i;
		}
		return fact;
	}
	
	//an memory-efficient method for calculating nCr stolen from the internet
	//my method was overflowing int so I found a better one
	private static long binomial(int n, int k)
	    {
	        if (k>n-k)
	            k=n-k;
	 
	        long b=1;
	        for (int i=1, m=n; i<=k; i++, m--)
	            b=b*m/i;
	        return b;
	    }

	public static void main(String[] args) {
		int numVertex = 0;// number of vertexes in the graph; to be determined in later versions with graph file input
		int[][] graphDefinition = new int[][]{{0,1,1,1,1},//easy test graph changed as needed, necessary for testing
										  	  {1,0,0,0,0},
										  	  {1,0,0,0,0},
										  	  {1,0,0,0,0},
										  	  {1,0,0,0,0}};

		// creates an array that, for each index in the graph definition array, stores
		// the number of connections on that vertex.
		int[] vertexArmCount = new int[graphDefinition.length];
		for (int i = 0; i < graphDefinition.length; i++) {
			for (int j = 0; j < graphDefinition[i].length; j++) {
				vertexArmCount[i] += graphDefinition[i][j];
			}
		}

		// simplifies the vertexArmCount array into an array with only unique tile sizes
		int[] rawTileSizeTypes = new int[vertexArmCount.length];
		for (int i = 0; i < rawTileSizeTypes.length; i++) {
			if (isUnique(vertexArmCount, i))
				rawTileSizeTypes[i] = vertexArmCount[i];
		}

		// condenses the rawTileSizeTypes array, eliminating all the 0 elements
		ArrayList<Integer> tileSizeTypesList = new ArrayList<Integer>();
		for (int i = 0; i < rawTileSizeTypes.length; i++)
			if (rawTileSizeTypes[i] != 0) {
				tileSizeTypesList.add(rawTileSizeTypes[i]);
			}
		tileSizeTypesList.trimToSize();
		Integer[] integerArray = tileSizeTypesList.toArray(new Integer[0]);
		int[] tileSizeTypes = ArrayUtils.toPrimitive(integerArray);

		// determines the largest tile size, for use later on
		int maxTileSize = 0;
		for (int i : tileSizeTypes) {
			if (i > maxTileSize)
				maxTileSize = i;
		}

		// gives the total number of connection types possibly necessary, including both
		// hatted and unhatted arms
		int connectionTypes = 0;
		for (int i : vertexArmCount) {
			connectionTypes += i;
		}

		// creates a list of the unhatted arms
		int[] unhattedArms = new int[connectionTypes / 2];
		for (int i = 1; i <= unhattedArms.length; i++)
			unhattedArms[i - 1] = i;

		// list of the hatted arms
		int[] hattedArms = new int[connectionTypes / 2];
		for (int i = 1; i <= hattedArms.length; i++)
			hattedArms[i - 1] = -1 * i;// hatted connections are going to be denoted by negative numbers

		/*
		 * This is the initial array that will contain all the possible tiles. Its first
		 * dimension is the size of the tiles in that dimension. This dimension is only
		 * useful for organization. In this implementation, the tiles in each dimension
		 * will be the dimension's index plus 1. The second dimension are all the tiles
		 * of the size indicated by the first dimension. The size of this second
		 * dimension will vary according to the combination of the tile size choosing
		 * the connectionTypes. The third dimension is the tiles themselves. This initial
		 * array, complexTiles unfortunately contains all of the tile combinations less than
		 * or equal to the largest tile in the graph. In many graphs, the small tiles will
		 * not be used at all. These extraneous tile sizes will be removed in the next step.
		 */
		int[][][] complexTiles = new int[maxTileSize][][];

		baseTile tile;
		for (int i = 0; i < complexTiles.length; i++) {
			tile = new baseTile(i + 1, connectionTypes);
			complexTiles[i] = new int[(int)binomial(connectionTypes+i,i+1)][];//updated formula for multiset counting
			for (int j = 0; j < complexTiles[i].length; j++) {
				complexTiles[i][j] = tile.returnTile();
				tile.count();
			}
		}
		
		//now we're going to remove all the extraneous tiles and get our final tile array
		int numTiles=0;
		for (int v: tileSizeTypes) {
			numTiles += (int)binomial(v+connectionTypes-1,connectionTypes);
		}
		int[][] tiles = new int[numTiles][];
	}
}