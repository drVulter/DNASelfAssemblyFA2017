package DNAmodeling;

public class basePot {
	int base;
 	int[]basePot;
 	int potSize;
 	int[]currentMin;
 	int[][]tiles;
 	
 	basePot(int inputPotSize,int inputBase,int[][]inputTiles){
 		base = inputBase;
 		potSize = inputPotSize;
 		basePot = new int[potSize];
 		currentMin = new int[potSize];
 		for (int i = 0;i<potSize;i++)
 			currentMin[i] = 0;
 		tiles = inputTiles;
 	}
 	
 	void count(){
 		for (int i=0;i<potSize;i++) {
 			if (basePot[i]<(base-1)) {
 				basePot[i]++;
 				break;
 			}
 			else if (i<(potSize-1)) {
	 				currentMin[i]++;
	 				for (int j=i;j>=0;j--)
	 					currentMin[j]=currentMin[i];
	 				if (basePot[i+1]<(base-1)) {
	 					for (int j=i;j>=0;j--)
	 						basePot[j]=currentMin[j];
 					}
 			}
 			else {
 				currentMin[i]++;
 				for (int j=i;j>=0;j--) {
 					currentMin[j]=currentMin[i];
 					basePot[j]=currentMin[j];
 				}
 			
 			}
 		}
 	}
 	
 	/*
 	much simpler than the tile generator, the numbers in the baseTile
 	array correspond directly to the index of the desired tile in the
 	tiles array.
 	*/
 	int [][] returnPot(){
 		int[][]pot = new int[potSize][];
 		for (int i=0;i<potSize;i++) {
 			pot[i]=tiles[basePot[i]];
 		}
 	return pot;
 	}
}