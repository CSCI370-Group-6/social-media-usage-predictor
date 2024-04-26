public class DecisionNode {

	private DataContainer data;
	private DecisionNode left;
	private DecisionNode right;

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

	//if not a leaf node, impurity found, (i havent used this, maybe dont need)
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

    public void setRight(DecisionNode newRight){
        this.right = newRight;
    }

    public void setLeft(DecisionNode newLeft){
        this.left = newLeft;
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
    	return ((this.right == null && this.left == null) && (this.data.getLabelCount(0) == 0 || this.data.getLabelCount(1) == 0));
    }

    public String getLabel(int row) {
    	return this.data.getValue(row, 12);
    }

}