import java.io.IOException;

public class DCTest {

    public static void main(String[] args) throws IOException {
    

    //dataset test case ---------------------------------------------

    // DataContainer dataset = new DataContainer();
    // System.out.println(dataset.getValue(0, 0));
    // System.out.println(dataset.getValue(999, 11));
    // dataset.print();



    //bootstrap & feature project test case -------------------------

    // DataContainer bootstrappedData = new DataContainer(1000, 3);
    // bootstrappedData.print();           //prints null w/ correct amount of columns
    // bootstrappedData.printDataset();    //prints whole dataset off any object



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
