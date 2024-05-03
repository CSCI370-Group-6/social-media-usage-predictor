import java.util.Random;
import java.lang.Math;
import java.util.HashMap;

public class DecisionTree {
	
	private DecisionNode root;

	public DecisionTree() {
        this.root = null;
	}

	//build the tree from the root
    public void buildTree(int numOfFeatures) {
        DataContainer bootstrappedData = new DataContainer(1000, 13); //bootstrap first
        this.root = new DecisionNode(bootstrappedData);
        buildTreeRecursively(this.root, bootstrappedData, numOfFeatures);
    }

    //helper function to recursively build the tree from the root
    private DecisionNode buildTreeRecursively(DecisionNode parentNode, DataContainer data, int numOfFeatures) {
    	if (data.isPure()) return new DecisionNode(data); //base case when pure, is return correct?

    	int[] randomColumns = featureSelect(numOfFeatures); //collect random features into an array, about 3

        //find the best feature to split on
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

            //FOR TESTING, seems correct
            // System.out.println("\nthreshold = " + threshold);
            // System.out.println("feature index = " + featureIndex);
            // System.out.println("infoGain = " + infoGain);

            if (infoGain > maxInfoGain) {
	            maxInfoGain = infoGain;
	            bestFeatureIndexToSplit = featureIndex;
                bestThreshold = threshold;
	        }
	    }
        
        //if no valid feature is found, return.
        if (bestFeatureIndexToSplit == -1) {
            return new DecisionNode(data); 
        }


        //FOR TESTING
        // System.out.println("\nMAX INFO GAIN = " + maxInfoGain);
        // System.out.println("bestFeatureIndexToSplit = " + bestFeatureIndexToSplit);
        // System.out.println("bestThreshold = " + bestThreshold);
        

        //set the parents nodes feature index that we will split on
        parentNode.setFeatureIndex(bestFeatureIndexToSplit);

        //need to split data first, then make nodes
        DataContainer leftData = data.split(true, bestFeatureIndexToSplit, bestThreshold);
        DataContainer rightData = data.split(false, bestFeatureIndexToSplit, bestThreshold);

        //FOR TESTING
        // System.out.println("\nI AM THE LEFT CHILD, I SPLIT ON " + bestThreshold + " which is at index of " + parentNode.getFeatureIndex());
        // leftData.print();
        // System.out.println("\n\n\n\n\n------------------");
        // System.out.println("I AM THE RIGHT CHILD, I SPLIT ON NOT OF " + bestThreshold + " which is at index of " + parentNode.getFeatureIndex());
        // rightData.print();
       

        //create children and input the split data
        DecisionNode leftChild = new DecisionNode(leftData);
        DecisionNode rightChild = new DecisionNode(rightData);

        //set the parent's node to the left and right children (THIS IS NOT WORKING???)
        parentNode.setLeft(leftChild);
        parentNode.setRight(rightChild);

        //call function again to build left and right children
        buildTreeRecursively(leftChild, leftData, numOfFeatures);
        buildTreeRecursively(rightChild, rightData, numOfFeatures);

        return parentNode; 
    }

    //calculate average from numeric data for the threshold value
    private int averageThreshold(DataContainer data, int featureIndex) {
        int sum = 0;
        for (int r = 0; r < data.getRows(); r++) {
            sum += Integer.parseInt(data.getValue(r, featureIndex));
        }
        return sum / data.getRows();
    }

    
    //calculate the mode from categorical data for the threshold value
    private String modeThreshold(DataContainer data, int featureIndex) {
        HashMap<String, Integer> count = new HashMap<>();
        for (int r = 0; r < data.getRows(); r++) {
            String value = data.getValue(r, featureIndex);
            if (!count.containsKey(value)) count.put(value, 1); //put in if it doesn't exist
            else count.put(value, count.get(value) + 1);        //increment count if exists already
        }
        
        //iterate through and count to find mode
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


    //uses average of that column to determine the information gain of this potential split
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

    //uses the mode of that column to determine the information gain of this potential split
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


    //helper function used to calculate information gain
    private double calculateGiniImpurity(int countLabel0, int countLabel1) {
        //total number of labels
        int totalSamples = countLabel0 + countLabel1; 

        //probabilities of each label
        double probability0 = (double) countLabel0 / totalSamples;
        double probability1 = (double) countLabel1 / totalSamples;

        //giniImpurity formula
        double giniImpurity = 1.0 - (Math.pow(probability0, 2) + Math.pow(probability1, 2));

        return giniImpurity;
    }


    //randomly select different columns of the bootstrapped data
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


    //prints the roots data
    public void print() {
    	this.root.print();
    }

    //get the roots left child
    public DecisionNode getLeft() {
        return this.root.getLeft();
    }

    //get the roots right child 
    public DecisionNode getRight() {
        return this.root.getRight();
    }




}