import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

//NOTE: Instead of declaring the attributes, I've made a general 2D String data array to keep it concise
//this can be used for either user input data [0][12] or any number bootstrapped/feature selected data [1000][3]

//in the loadDataset method, i am assuming for now that if you spend 4 or more hours, you are at risk of social media addiction, this can be changed later
//this label gets appended to the dataset[][12] at index 12

public class DataContainer {


    //FIELDS -----------------------------------------------------------------------------

    private static String[][] dataset; //static initialized
    static {
        try {
            dataset = loadDataset("dummy_data.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String[][] data; //userInput data or bootstrapped/feature selected data, for ex) userData = data[0][12] 
    private int rows;
    private int columns;



    //CONSTRUCTORS -----------------------------------------------------------------------

    //constructor for just the dataset if only dataset is just needed
    public DataContainer() {
        data = dataset;
        rows = 1000;
        columns = 13; //last index will contain the label
    }

    //constructor for user input
    public DataContainer(String userInput)  {
        rows = 1;
        columns = 12;
        data = new String[rows][columns];
        readUserInput(userInput);
    }

    //constructor for bootstrapping & feature selection
    public DataContainer(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        data = new String[rows][columns];
        loadRandomData();
    }

   

    //METHODS ----------------------------------------------------------------------------

    //helper for loading dataset
    private static String[][] loadDataset(String source) throws IOException {
        
        //TODO: HANDLE MISSING DATA FROM DATASET
        //ASSUME FOR NOW THAT THE DATASET IS NOT MISSING ANY DATA
        try (BufferedReader br = new BufferedReader(new FileReader(source))) {
            br.readLine(); //consume header to skip
            String line;
            int row = 0;
            String temp[][] = new String[1000][13]; //ADDED 13 here for label assume for now that just timespent >= 4 is a 1.
            while ((line = br.readLine()) != null && row < 1000) {
                String[] rowData = line.split(","); 
                for (int col = 0; col < temp[row].length && col < rowData.length; col++) {
                    temp[row][col] = rowData[col];
                }
                //ASSIGN LABEL ON TIMESPENT FOR NOW
                int timeSpent = Integer.parseInt(rowData[2]);
                if (timeSpent >= 4) temp[row][12] = "1";
                else temp[row][12] = "0";
                row++;
            }
        return temp;
        }
    }


    //helper for reading in user input
    private void readUserInput(String userInput)  {
        String[] rowData = new String[12];

        //kind of a band-aid solution to the .split() ignoring very last ','
        if (userInput.endsWith(",")) {
            String[] temp = new String[12];
            temp = userInput.split(",");
            for (int i = 0; i < temp.length; i++) {
                rowData[i] = temp[i];
            }
        }
        else {
            rowData = userInput.split(",");
        }        
        
        for (int c = 0; c < rowData.length; c++) {
            if (rowData[c] == null || rowData[c].isEmpty()) {   //if column missing, then get mode of missing column
                String mode = dataImputation(c);                
                data[0][c] = mode;
            } 
            else {
                data[0][c] = rowData[c];
            }
        }  
    }


    //NOTE: may be better to do average for numerical features? and mode for rest?
    //TODO: also prof said to check if col is empty
    private String dataImputation(int c) {

        HashMap<String, Integer> count = new HashMap<>();
        for (int r = 0; r < 1000; r++) {                        //iterate through values of that column
            String value = dataset[r][c];
            if (!count.containsKey(value)) count.put(value, 1);  
            else count.put(value, count.get(value) + 1);        //increment count if exists already
        }

        //FOR TESTING ONLY-----------------------
        for (String value : count.keySet()) {
            int occurrences = count.get(value);
            System.out.println(value + ": " + occurrences);
        }
        //---------------------------------------

        String mode = null;
        int max = 0;
        for (String value : count.keySet()) {
            if (count.get(value) > max) {
                max = count.get(value);
                mode = value; 
            } 
        }

        return mode;
    }

    private void loadRandomData() {
        Random rand = new Random();
        int randomRow;   
        for (int r = 0; r < rows; r++) {
            randomRow = rand.nextInt(1000);
            for (int c = 0; c < columns; c++) {
                data[r][c] = dataset[randomRow][c]; //load into data
            }
        }
   
    }


    public String getValue(int r, int c) {
    	return data[r][c];
    }


    public void print() {
        for (int r = 0; r < this.rows; r++) {
            for (int c = 0; c < this.columns; c++) {
                System.out.print(data[r][c] + " ");
            }
            System.out.println();
        }
    }


    public void printDataset() {
        for (int r = 0; r < 1000; r++) {
            for (int c = 0; c < 13; c++) {
                System.out.print(dataset[r][c] + " ");
            }
            System.out.println();
        }
    }

}
