public class RandomForest {
	
	private DecisionTree[] trees;

	public RandomForest() {
		trees = null;
	}

	//constructor for training the forest
	public RandomForest(int numOfTrees) {
		train(numOfTrees);
	}

	//train the forest by creating n number of trees
	private void train(int numOfTrees) {
		trees = new DecisionTree[numOfTrees];
		for (int i = 0; i < numOfTrees; i++) {
        	DecisionTree newTree = new DecisionTree();
        	newTree.buildTree(4); 
			trees[i] = newTree;
		}
	}


	//for testing purposes
	public void print() {
		for (int i = 0; i < trees.length; i++) {
			System.out.println(trees[i]);
		}
	}

	//IP - returning double for now to see a percentage
	public double aggregate(DataContainer userInput) {
		int positive = 0;
		for (int i = 0; i < trees.length; i++) {
			//get feature index of each node you visit, look up that feature index in user input and traverse until the node/data is pure
		}

	}




}