import nodes.*;

public class test {
    public static void main(String[] args) {
        LeafNode x = new LeafNode(.1234);
        System.out.println(x.getDepth());
        System.out.println(x.getPrediction());
    }
}
