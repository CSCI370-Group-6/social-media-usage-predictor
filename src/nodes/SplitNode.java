package nodes;
public class SplitNode extends TreeNode {
    private TreeNode leftNode;
    private TreeNode rightNode;
    private double threshold;
    private int feature;

    public SplitNode(int feature, double threshold, TreeNode leftNode, TreeNode rightNode){
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

    public TreeNode getRight(){
        return this.rightNode;
    }
    public TreeNode getLeft(){
        return this.leftNode;
    }
}
