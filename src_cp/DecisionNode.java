public class DecisionNode {

	private DataContainer data;
	private DecisionNode left;
	private DecisionNode right;
	//will contain a condition to split the nodes (maybe not actually? should be based on the features selected)


	public DecisionNode() {
		this.data = null;
		this.left = null;
		this.right = null;
	}

	//if root or leaf node
	public DecisionNode(DataContainer data) {
		this.data = data;
		this.left = null;
		this.right = null;
	}

	//if not a leaf node, impurity found
	public DecisionNode(DataContainer data, DecisionNode left, DecisionNode right) {
		this.data = data;
		this.left = left;
		this.right = right;
	}

	public DecisionNode getRight(){
        return this.right;
    }

    public DecisionNode getLeft(){
        return this.left;
    }

    public void setRight(DecisionNode right){
        this.right = right;
    }

    public void setLeft(DecisionNode left){
        this.left = left;
    }

    public void setData(DataContainer data) {
    	this.data = data;
    }

    public DataContainer getData() {
    	return this.data;
    }

    public void print() {
    	this.data.print();
    }

    public boolean isLeaf() {
    	return (this.right == null && this.left == null);
    }

    public String getLabel(int row) {
    	return this.data.getValue(row, 12);
    }


    //TODO
    //public boolean isPure() {}




}