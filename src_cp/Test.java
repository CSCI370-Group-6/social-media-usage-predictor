import java.io.IOException;
import java.lang.Math;
import java.util.HashMap;




public class Test {

 
    

    public static void main(String[] args) throws IOException {
    

    //tree testing (IN PROGRESS) ---------------------------------------------------

    DataContainer dataset = new DataContainer();
    DecisionTree root = new DecisionTree(dataset);
    root.buildTree(4); //num of features passed in
    //root.print();



    //decision node test cases (Tree class will handle) --------------------------------------

    // DataContainer dataset = new DataContainer();
    // DecisionNode root = new DecisionNode(dataset);
    // //root.print();
    // System.out.println(root.getLabel(0));
    // System.out.println(root.getLabel(1));
    // System.out.println(root.getLabel(2));
    // System.out.println(root.getLabel(3));
    // System.out.println(root.getLabel(4));

    // // just testing left and right w/ random data, SPLITTINGCRITERIA() will be the one using this
    // DataContainer dataLeft = new DataContainer(10, 13);
    // DataContainer dataRight = new DataContainer(5, 13);
    
    // DecisionNode nodeLeft = new DecisionNode(dataLeft);
    // DecisionNode nodeRight = new DecisionNode(dataRight);

    // root.setLeft(nodeLeft);
    // root.setRight(nodeRight);


    // System.out.println("I am roots left child----");
    // root.getLeft().getData().print();
    // System.out.println("\nI am roots right child----");
    // root.getRight().getData().print();

    // System.out.println(root.isLeaf());
    // System.out.println(nodeLeft.isLeaf());
    // System.out.println(nodeRight.isLeaf());

    //Testing out label counts, will be used to find impurity/IG/giniIndex
    // DataContainer bootstrappedData = new DataContainer(10, 5);
    // bootstrappedData.print();


    //data container label tests ----------------------------

    // DataContainer bootstrappedData = new DataContainer(5, 5);
    // bootstrappedData.print();
    // System.out.println("\nLabel 0 = " + bootstrappedData.getLabelCount(0));
    // System.out.println("Label 1 = " + bootstrappedData.getLabelCount(1));
    // DecisionNode node1 = new DecisionNode(bootstrappedData);
    // System.out.println("Leaf = " + node1.isLeaf()); //may not need
    // System.out.println("Pure = " + node1.isPure());
    // System.out.println("data class is pure = " + bootstrappedData.isPure());
    /*
        18 non-binary 5 YouTube 1 
        27 male 4 YouTube 1 
        28 female 7 Instagram 1 
        51 non-binary 4 Facebook 1 

        Label 0 = 0
        Label 1 = 5
        Leaf = true
        Pure = true
        data class is pure = true
    */


    //dataset test case ---------------------------------------------

    // DataContainer dataset = new DataContainer();
    // System.out.println(dataset.getValue(0, 0));
    // System.out.println(dataset.getValue(999, 11));
    // dataset.print();



    //bootstrap & feature project test case -------------------------

    // DataContainer bootstrappedData = new DataContainer(1000, 13);
    // bootstrappedData.print();           
    //bootstrappedData.printDataset();    //prints whole dataset off any object



    //data imputation & user input test cases ------------------------------------

    //no inputs removed
        // String input = "28,male,2,Instagram,Sports,Australia,Sub_Urban,Marketer Manager,10223,True,False,True"; 
        // DataContainer userInput = new DataContainer(input); 
        // userInput.print();     

    //end removed
        // String input = "28,male,2,Instagram,Sports,Australia,Sub_Urban,Marketer Manager,10223,True,False,"; 
        // DataContainer userInput = new DataContainer(input); //true was inputted correctly
        // userInput.print();

    //first input removed
        // String input = ",male,2,Instagram,Sports,Australia,Sub_Urban,Marketer Manager,10223,True,False,True"; 
        // DataContainer userInput = new DataContainer(input); //43 was inputted correctly
        // userInput.print();

    //middle input "country" removed
        // String input = "28,male,2,Instagram,Sports,,Sub_Urban,Marketer Manager,10223,True,False,True"; 
        // DataContainer userInput = new DataContainer(input); 
        // userInput.print();

    //beginning & end removed
        // String input = ",male,2,Instagram,Sports,Australia,Sub_Urban,Marketer Manager,10223,True,False,"; 
        // DataContainer userInput = new DataContainer(input); 
        // userInput.print();

    //mixed removed
        // String input = ",male,2,,Sports,,Sub_Urban,,10223,True,False,"; 
        // DataContainer userInput = new DataContainer(input); 
        // userInput.print();

    }
}
