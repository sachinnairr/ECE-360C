public class Program3 {

    public int maxFoodCount (int[] plots) {
        // Implement your dynamic programming algorithm here
        // You may use helper functions as needed
        int length = plots.length;

		int[][] OPT = new int[length][length]; //Creating nxn OPT Table o(1)
		
		int[][] CUT = new int[length][length];	//2D array to store Food for any cut per day o(1)
		
		// Fill in Cuts 2D Array to have constant time checking of food yielded for a section for each day o
		for (int i = 0; i < length; i++) {
			for (int j = i; j < length; j++) {
				if (i != j) {
					CUT[i][j] = plots[j] + CUT[i][j - 1];
				} else{
					CUT[i][j] = plots[j];
				}
				
			}
		}
		
		//Step 2 Fill in Opt Table o(n^2 * log(n))
		for (int i = length - 1; i >= 0; i--) {
			//Base Case if there is 1 plot left, set opt[i][j] to 0
			//Base Case if there are 2 plots left, set opt[i][j] to the lowest plot
			for (int j = i; j < length; j++) {
				if (i == j) {	//base case 1 plot remains
					OPT[i][j] = 0;
				} else if (j - i == 1) { //base case 2 plots remain
					OPT[i][j] = Math.min(plots[i], plots[j]);
				}
				//recurrence case
				//Divide problem into smaller problem
				else {	
					int low = i;
					int high = j;
					int mid = 0;
					int midValue = 0;
					int midR = 0;
					int midL = 0;
					while(true) {
						mid = (high + low) / 2;
						if (high == low + 1) {
							OPT[i][j] = Math.min(CUT[mid + 1][j] + OPT[mid + 1][j], CUT[i][mid] + OPT[i][mid]);
							break;
						}
						if (high == low + 2) {
							OPT[i][j] = Math.max((Math.min(CUT[i][i] + OPT[i][i], CUT[mid][j] + OPT[mid][j] )), (Math.min(OPT[i][mid] + CUT[i][mid], OPT[mid + 1][j] + CUT[mid + 1][j])));
							break;
						}
						
						midValue = Math.min(CUT[i][mid]+ OPT[i][mid], CUT[mid + 1][j] + OPT[mid + 1][j]);
						midR = Math.min(CUT[i][mid + 1] + OPT[i][mid + 1], CUT[mid + 2][j] + OPT[mid + 2][j]);
						midL = Math.min(CUT[i][mid - 1] + OPT[i][mid - 1], CUT[mid][j]+ OPT[mid][j]);
						
						//use BST Style to find max o(log(n))
						
						if (midL < midValue ) {	
							if(midR < midValue){
								OPT[i][j] = midValue;
								break;
							}
						}

						if (midValue > midL) {
							low = mid;
							continue;
						}
						
						high = mid;
					}
				}
			}
		}
		//we know OPT[0][length -1] is our solution based on how we create the opt table
		return OPT[0][length - 1];
	}
    }

