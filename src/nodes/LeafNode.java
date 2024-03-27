package nodes;
public class LeafNode extends TreeNode {

    private double prediction;
    
    public LeafNode(double prediction){
        super();
        this.prediction = prediction;
    }

    public double getPrediction(){
        return prediction;
    }
}
