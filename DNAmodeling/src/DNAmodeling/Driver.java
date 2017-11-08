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

	// factorial function, needed for math stuff
	static int factorial(int num) {
		int fact = 1;
		for (int i = 1; i <= num; i++) {
			fact = fact * i;
		}
		return fact;
	}

	public static void main(String[] args) {
		int numVertex = 0;// number of vertexes in the graph
		int[][] graphDefinition = new int[numVertex][numVertex];

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
		 * of the size of the first dimension. The size of this dimension will vary
		 * according to the combination of the tile size choosing the connectionTypes.
		 * The third dimension is the tiles themselves
		 */
		int[][][] complexTiles = new int[maxTileSize][][];

		baseTile tile;
		for (int i = 0; i < complexTiles.length; i++) {
			tile = new baseTile(i + 1, connectionTypes);
			complexTiles[i] = new int[factorial(connectionTypes) / (factorial(connectionTypes - (i + 1)) * factorial(i + 1))][];
			for (int j = 0; j < complexTiles[i].length; j++) {
				complexTiles[i][j] = tile.returnTile();
				tile.count();
			}
		}

	}
}