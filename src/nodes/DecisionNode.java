package nodes;
public class DecisionNode extends TreeNode {
    private TreeNode leftNode;
    private TreeNode rightNode;
    private double threshold;
    private int feature;

    public DecisionNode(int feature, double threshold){
        this.feature = feature;
        this.threshold = threshold;
        this.rightNode = null;
        this.leftNode = null;
    }

    public DecisionNode(int feature, double threshold, TreeNode leftNode, TreeNode rightNode){
        this.feature = feature;
        this.threshold = feature;
        this.rightNode = rightNode;
        this.leftNode = leftNode;
    }

    public TreeNode decideNode(double[] features){
        if (features[this.feature] <= this.threshold){
            return this.leftNode;
        }
        else {
            return this.rightNode;
        }
    }

    public int getFeatureIndex(){
        return this.feature;
    }

    public double getSplitThreshold(){
        return this.threshold;
    }

    public TreeNode getRight(){
        return this.rightNode;
    }
    public TreeNode getLeft(){
        return this.leftNode;
    }
    public void setRight(TreeNode newRight){
        this.rightNode = newRight;
    }
    public void setLeft(TreeNode newLeft){
        this.leftNode = newLeft;
    }
}
