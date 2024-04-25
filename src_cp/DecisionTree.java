import java.util.Random;
import java.lang.Math;
import java.util.HashMap;

public class DecisionTree extends DecisionNode{
	
	private DecisionNode root;

	public DecisionTree() {
		this.root = new DecisionNode();
	}

    public DecisionTree(DataContainer dataset) {
        this.root = new DecisionNode(dataset);
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
            - will need to find best threshold
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
        //ROOT.LEFT = .....
        //ROOT.RIGHT = .....

    }

    //IP
    private DecisionNode buildTreeRecursively(DataContainer data, int numOfFeatures) {
    	if (data.isPure()) return new DecisionNode(data); //STOPPING CRITERION when pure is our base case

    	int[] randomColumns = featureSelect(numOfFeatures);

    	//NOW PICK WHICH ONE TO SPLIT ON (MOST INFO GAIN)
	    double maxInfoGain = Double.NEGATIVE_INFINITY; 
	    int bestFeatureIndexToSplit = -1; 
        String bestThreshold = null;
	    
        for (int featureIndex : randomColumns) {
            double infoGain;
            String threshold;

            //numeric values (ex: age)
            if (featureIndex == 0 || featureIndex == 2 || featureIndex == 8) { 
                int average = averageThreshold(data, featureIndex);
                infoGain = calculateInfoGainFromNumericData(data, featureIndex, average);
                threshold = String.valueOf(average);
            }  
            //categorical values (ex: gender)              
            else { 
                String mode = modeThreshold(data, featureIndex);
                infoGain = calculateInfoGainFromCategoricalData(data, featureIndex, mode);
                threshold = mode;
            }

            //FOR TESTING
            // System.out.println("\nthreshold = " + threshold);
            // System.out.println("feature index = " + featureIndex);
            // System.out.println("infoGain = " + infoGain);

            if (infoGain > maxInfoGain) {
	            maxInfoGain = infoGain;
	            bestFeatureIndexToSplit = featureIndex;
                bestThreshold = threshold;
	        }
	    }
        if (bestFeatureIndexToSplit == -1) {
            // Handle the case where no valid feature index was found
            // For example, you could return a leaf node here
            return new DecisionNode(data);
        }

        //FOR TESTING
        System.out.println("\nMAX INFO GAIN = " + maxInfoGain);
        System.out.println("bestFeatureIndexToSplit = " + bestFeatureIndexToSplit);
        System.out.println("bestThreshold = " + bestThreshold);

	    //THEN REC SPLIT!!!! need to split based on best feature
        

        //need to split data first, then make nodes
        DataContainer leftData = data.split(true, bestFeatureIndexToSplit, bestThreshold);
        DataContainer rightData = data.split(false, bestFeatureIndexToSplit, bestThreshold);

        System.out.println("\nI AM THE LEFT CHILD, I SPLIT ON " + bestThreshold);
        leftData.print();
        System.out.println("\n\n\n\n\n------------------");


        System.out.println("I AM THE RIGHT CHILD, I SPLIT ON THE REVERSE OF " + bestThreshold);
        rightData.print();

        //now make a decision node with the split data
        DecisionNode currentNode = new DecisionNode(data);

        //recursively build left and right subtrees
        DecisionNode leftChild = buildTreeRecursively(leftData, numOfFeatures);
        DecisionNode rightChild = buildTreeRecursively(rightData, numOfFeatures);

        //set left and right children of the current node
        currentNode.setLeft(leftChild);
        currentNode.setRight(rightChild);


        return currentNode; 

    }

    private int averageThreshold(DataContainer data, int featureIndex) {
        //calculate average for threshold
        int sum = 0;
        for (int r = 0; r < data.getRows(); r++) {
            sum += Integer.parseInt(data.getValue(r, featureIndex));
        }
        return sum / data.getRows();
    }

    private String modeThreshold(DataContainer data, int featureIndex) {
        //count the feature values
        HashMap<String, Integer> count = new HashMap<>();
        for (int r = 0; r < data.getRows(); r++) {
            String value = data.getValue(r, featureIndex);
            if (!count.containsKey(value)) count.put(value, 1);  
            else count.put(value, count.get(value) + 1);        //increment count if exists already
        }
        
        //calculate mode
        String modeThreshold = null;
        int max = 0;
        for (String value : count.keySet()) {
            if (count.get(value) > max) {
                max = count.get(value);
                modeThreshold = value; 
            } 
        }

        return modeThreshold;
    }


    private Double calculateInfoGainFromNumericData(DataContainer data, int featureIndex, int averageThreshold) {
        
        int leftCountLabel0 = 0, leftCountLabel1 = 0;
        int rightCountLabel0 = 0, rightCountLabel1 = 0;
       
        //use averagethreshold to count the labels for a potential split
        for (int r = 0; r < data.getRows(); r++) {
            if (Integer.parseInt(data.getValue(r, featureIndex)) <= averageThreshold) {
                if (data.getLabel(r).equals("0")) leftCountLabel0++;
                else if (data.getLabel(r).equals("1")) leftCountLabel1++;
            }
            else {
                if (data.getLabel(r).equals("0")) rightCountLabel0++;
                else if (data.getLabel(r).equals("1")) rightCountLabel1++;
            }
        }

        //calculate giniImpurities
        double giniImpurityParent = calculateGiniImpurity(data.getLabelCount(0), data.getLabelCount(1));
        double giniImpurityLeft = calculateGiniImpurity(leftCountLabel0, leftCountLabel1);
        double giniImpurityRight = calculateGiniImpurity(rightCountLabel0, rightCountLabel1);

        //calculate IG based on giniImpurities
        int totalSamples = leftCountLabel0 + leftCountLabel1 + rightCountLabel0 + rightCountLabel1;
        double informationGain = giniImpurityParent - ((double) (leftCountLabel0 + leftCountLabel1) / totalSamples * giniImpurityLeft 
                                 + (double) (rightCountLabel0 + rightCountLabel1) / totalSamples * giniImpurityRight);


        return informationGain;
    }

    private Double calculateInfoGainFromCategoricalData(DataContainer data, int featureIndex, String modeThreshold) {
        
        int leftCountLabel0 = 0, leftCountLabel1 = 0;
        int rightCountLabel0 = 0, rightCountLabel1 = 0;
       
        //use mode to count the labels for a potential split
        for (int r = 0; r < data.getRows(); r++) {
            if (data.getValue(r, featureIndex).equals(modeThreshold)) {
                if (data.getLabel(r).equals("0")) leftCountLabel0++;
                else if (data.getLabel(r).equals("1")) leftCountLabel1++;
            }
            else {
                if (data.getLabel(r).equals("0")) rightCountLabel0++;
                else if (data.getLabel(r).equals("1")) rightCountLabel1++;
            }
        }

        //calculate giniImpurities
        double giniImpurityParent = calculateGiniImpurity(data.getLabelCount(0), data.getLabelCount(1));
        double giniImpurityLeft = calculateGiniImpurity(leftCountLabel0, leftCountLabel1);
        double giniImpurityRight = calculateGiniImpurity(rightCountLabel0, rightCountLabel1);

        //calculate IG based on giniImpurities
        int totalSamples = leftCountLabel0 + leftCountLabel1 + rightCountLabel0 + rightCountLabel1;
        double informationGain = giniImpurityParent - ((double) (leftCountLabel0 + leftCountLabel1) / totalSamples * giniImpurityLeft 
                                 + (double) (rightCountLabel0 + rightCountLabel1) / totalSamples * giniImpurityRight);

        return informationGain;
    }


    private double calculateGiniImpurity(int countLabel0, int countLabel1) {
        // Total number of samples in the subset
        int totalSamples = countLabel0 + countLabel1;

        // Calculate the probabilities
        double probability0 = (double) countLabel0 / totalSamples;
        double probability1 = (double) countLabel1 / totalSamples;

        // Calculate Gini impurity
        double giniImpurity = 1.0 - (Math.pow(probability0, 2) + Math.pow(probability1, 2));

        return giniImpurity;
    }


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