package DNAmodeling;

public class basePot {
	int base;
 	int[]basePot;
 	int potSize;
 	int[]currentMin;
 	short[][]tiles;
 	public int completeGraphs;
 	
 	basePot(int inputPotSize,int inputBase,short[][]inputTiles){
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
 	short [][] returnPot(){
 		short[][]pot = new short[potSize][];
 		for (int i=0;i<potSize;i++) {
 			pot[i]=tiles[basePot[i]];
 		}
 	return pot;
 	}
 	
 	//checks if the current basePot graph is a complete graph
 	boolean completeGraph() {
 		short [][]pot = new short[potSize][];
 		for (int i=0;i<potSize;i++) {
 			pot[i]=tiles[basePot[i]];
 		}
 		int arms=0;
 		for (int i=0; i<potSize; i++) {
 			for (int j=0; j<pot[i].length; j++) {
 				arms++;
 			}
 		}
 		int [] freeArms = new int [arms];
 		int index=0;
 		for (int i=0; i<potSize; i++) {
 			for (int j=0; j<pot[i].length; j++) {
 				freeArms[index]=pot[i][j];
 				index++;
 			}	
 		}
 			
 		int[] connections = new int[arms];
 		for (int i = 0; i<arms; i++) {
 			connections[i]=0;
 		}
 		
 		index = 0;
 		for (int i = 0; i<arms;i++) {
 			if (freeArms[index]==(-1*(freeArms[i]))) {
 				if (connections[index]==0 && connections[i]==0) {
 					connections[index]=1;
 					connections[i]=1;
 					index++;
 					i=index-1;
 				}
 			}
 		}
 		boolean complete = true;
 		for (int v : connections) {
 			if (v==0) {
 				complete = false;
 				break;
 			}
 		}
 		if (complete==true)completeGraphs++;
 		return complete;
 	}
 	
 	boolean finalPot() {
 		boolean isFinal=true;
 		for (int v : basePot) {
 			if (v<(base-1)) {
 				isFinal=false;
 				break;
 			}	
 		}
 		return isFinal;
 	}
}