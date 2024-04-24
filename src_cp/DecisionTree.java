import java.util.Random;

public class DecisionTree extends DecisionNode{
	
	private DecisionNode root;

	public DecisionTree() {
		this.root = new DecisionNode();
	}


    /*

    100 bootstrapped datasets first
    make a decision tree on each one
    slect the features with most info gain
    randomly select features (not same) and select one with highest IG

    MAKING A DECISION TREE:
    	- make a bootstrapped dataset (w/ constructor)
    	- recursively split the bootstrapped dataset we made until we are left w/ pure leaf nodes
    	- nodes will contain a condition to split the data (feature will decide condition)
    	- if(!node.isPure()) split(); //this is the stopping criteria, simplest one
    	- now we choose the condition that maximizes info gain (IG)
    	- the model will compare every possible split and will chose the one w/ highest IG
    		- model will traverse through every feature & feature value to search for the best feature and corresponding threshold
    	- greedy algo
    	- need a target value
    	- all following splits will depend on the current one
    	- use gini index, easier than finding entropy
    		- GI = 1 - sum(p_i^(2))
    	- then use to find IG
    		- IG1 = G(parent) - sum(w_iG(child_i))
    		- IG2 = ^^^^^^^
    	- pick the highest one

	*/



	//IP
    public void buildTree(int numOfFeatures) {
        DataContainer bootstrappedData = new DataContainer(1000, 13); //bootstrap first
        root = buildTreeRecursively(bootstrappedData, numOfFeatures);

    }

    //IP
    private DecisionNode buildTreeRecursively(DataContainer data, int numOfFeatures) {
    	//if (data.isPure()) return new DecisionNode(data); //STOPPING CRITERION when pure is our base case

    	int[] randomColumns = featureSelect(numOfFeatures);

    	//NOW PICK WHICH ONE TO SPLIT ON (MOST INFO GAIN)
	    double maxInfoGain = Double.NEGATIVE_INFINITY; 
	    int bestFeatureIndex = -1; 
	    
	    for (int featureIndex : randomColumns) {
	        double infoGain = calculateInfoGain(data, featureIndex); //calculate information gain for each feature
	        if (infoGain > maxInfoGain) {
	            maxInfoGain = infoGain;
	            bestFeatureIndex = featureIndex;
	        }
	    }

	    //THEN REC SPLIT!!!! need to split based on best feature

	    //PLACEHOLDER FOR NOW
	    return new DecisionNode(data); 

    }

    //IP
    private double calculateInfoGain(DataContainer data, int featureIndex) {
    	//System.out.println(data.getValue(0, featureIndex)); //FOR TESTING

    	//double giniIndexLeft = calculateGiniIndex();
    	//double giniIndexRight = calculateGiniIndex();

    	//after i get gini, then calc IG

    	//placeholder for now
    	return 2.0;
    }

    //IP
    // private double calculateGiniIndex() {

    // }


    //randomly select different columns
    private int[] featureSelect(int numOfFeatures) {
    	Random rand = new Random();
    	int[] randomColumns = new int[numOfFeatures];
    	for (int i = 0; i < numOfFeatures; i++) {
    		int randCol;
    		boolean isUnique;
    		do {
                randCol = rand.nextInt(12);
                isUnique = true;
                for (int j = 0; j < numOfFeatures; j++) {
                    if (randomColumns[j] == randCol) {
                        isUnique = false;
                        break;
                    }
                }
            } while (!isUnique);
            randomColumns[i] = randCol;
            //System.out.println(randomColumns[i]); //FOR TESTING
    	}
    	return randomColumns;
    }


    //IP
    public void print() {
    	root.print();
    }


}