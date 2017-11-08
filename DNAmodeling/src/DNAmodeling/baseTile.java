package DNAmodeling;

class baseTile {
 	int base;
 	int[]baseTile;
 	int tileSize;
 	
 	baseTile(int inputTileSize,int inputBase){
 		base = inputBase;
 		tileSize = inputTileSize;
 		baseTile = new int[tileSize];
 	}
 	
 	//moves to the next arm combination
 	void count(){
 		for (int i=0;i<tileSize;i++){
 			if (baseTile[i]<base-1){
 				baseTile[i]++;
 				break;	
 			} else baseTile[i]=0;
 		}
 	}
 	
 	//this method translates the current tile count to a real tile array.
 	int [] returnTile(){
 		/*This constant is never used. I just wanted to display the codification 
 		I'm using. The counter starts at a-hat, moves up through the hatted arms, 
 		then returns to non-hatted a at base/2 and move up through the non-hatted arms from there.
 		*/
 		final int AHAT = 0;
 		int aNonHatted = base/2;
 		
 		int [] tile = new int[tileSize];
 		for (int i = 0;i<tileSize;i++) {
 			if (baseTile[i]<aNonHatted) {
 			tile[i] = -1*(baseTile[i]+1);
 			} else {
 				tile[i] = baseTile[i]-aNonHatted+1;
 			}
 		}
 		return tile;
 	}
}