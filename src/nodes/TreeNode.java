package nodes;
public abstract class TreeNode{
    private int depth = 0;

    public TreeNode(){
        this.depth = 0;
    }
    
    public int getDepth(){
        return depth;
    }
    public void setDepth(int newDepth){
        this.depth = newDepth;
    }

}