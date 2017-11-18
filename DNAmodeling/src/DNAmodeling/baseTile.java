package DNAmodeling;

class baseTile {
 	short base;
 	short[]baseTile;
 	short tileSize;
 	short[]currentMin;
 	
 	baseTile(short inputTileSize,short inputBase){
 		base = inputBase;
 		tileSize = inputTileSize;
 		baseTile = new short[tileSize];
 		currentMin = new short[tileSize];
 		for (int i = 0;i<tileSize;i++)
 			currentMin[i] = 0;
 	}
 	
 	//moves to the next arm combination
 	void count(){
 		for (int i=0;i<tileSize;i++) {
 			if (baseTile[i]<(base-1)) {
 				baseTile[i]++;
 				break;
 			}
 			else if (i<(tileSize-1)) {
	 				currentMin[i]++;
	 				for (int j=i;j>=0;j--)
	 					currentMin[j]=currentMin[i];
	 				if (baseTile[i+1]<(base-1)) {
	 					for (int j=i;j>=0;j--)
	 						baseTile[j]=currentMin[j];
 					}
 			}
 			else {
 				currentMin[i]++;
 				for (int j=i;j>=0;j--) {
 					currentMin[j]=currentMin[i];
 					baseTile[j]=currentMin[j];
 				}
 			
 			}
 		}
 	}
 		
 	//this method translates the current tile count to a real tile array.
 	short [] returnTile(){
 		/*This constant is never used. I just wanted to display the codification 
 		I'm using. The counter starts at a-hat, moves up through the hatted arms, 
 		then returns to non-hatted a at base/2 and move up through the non-hatted arms from there.
 		*/
 		final short AHAT = 0;
 		short aNonHatted = (short)(base/2);
 		
 		short [] tile = new short[tileSize];
 		for (int i = 0;i<tileSize;i++) {
 			if (baseTile[i]<aNonHatted) {
 			tile[i] = (short)(-1*(baseTile[i]+1));
 			} else {
 				tile[i] = (short)(baseTile[i]-aNonHatted+1);
 			}
 		}
 		return tile;
 	}
}