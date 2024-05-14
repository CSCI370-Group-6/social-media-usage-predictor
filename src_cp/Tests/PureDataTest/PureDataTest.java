import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PureDataTest {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("test_input.txt"))) { 
            String line;
            boolean isPure = true;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("start")) { //start of new input
                    isPure = true;
                    System.out.println(line);
                } else if (line.startsWith("end")) { //end of input, print if it is pure or not
                    System.out.println(line + " - [" + isPure + "]");
                } else { //else we can now check the input
                    String[] values = line.split(" ");
                    for (String value : values) {
                        if (!value.equals(values[0])) { //check against first label, if different then it is false
                            isPure = false;
                            break;
                        }
                    }
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
