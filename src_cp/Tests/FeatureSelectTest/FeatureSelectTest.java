import java.util.Random;
import java.util.HashSet;

public class FeatureSelectTest {

    public static int[] featureSelect(int numOfFeatures) {
        Random rand = new Random();
        int[] randomColumns = new int[numOfFeatures];
        for (int i = 0; i < numOfFeatures; i++) {
            int randCol;
            boolean isUnique;
            do {
                randCol = rand.nextInt(12);
                isUnique = true;
                for (int j = 0; j < numOfFeatures; j++) {
                    if (randomColumns[j] == randCol) {
                        isUnique = false;
                        break;
                    }
                }
            } while (!isUnique);
            randomColumns[i] = randCol;
        }
        return randomColumns;
    }

    public static boolean isUnique(int[] randomColumns) {
        HashSet<Integer> set = new HashSet<>();
        for (int i: randomColumns) {
            if (set.contains(i)) return false; 
            set.add(i); 
        }
        return true; 
    }

 
    public static void main(String[] args)  {
            
        System.out.println("THE FOLLOWING SHOULD BE TRUE:");

        for (int i = 0; i < 100; i++) {
            int[] randomColumns = featureSelect(3); //collect random features indices (3 in this case)
            System.out.print("[");
            for (int j = 0; j < randomColumns.length - 1; j++) {
                System.out.print(randomColumns[j] + ", ");
            }
            System.out.print(randomColumns[randomColumns.length - 1] + "]");

            System.out.println("\t" + isUnique(randomColumns));
        }

        System.out.println("\nTHE FOLLOWING SHOULD BE FALSE:");

        int notRandom1[] = {1, 1, 3};
        int notRandom2[] = {1, 3, 3};
        int notRandom3[] = {1, 1};
        int notRandom4[] = {1, 1, 3, 5, 6};
        System.out.println("[1, 1, 3]\t" + isUnique(notRandom1));
        System.out.println("[1, 3, 3]\t" + isUnique(notRandom1));
        System.out.println("[1, 1]\t\t" + isUnique(notRandom1));
        System.out.println("[1, 1, 3, 5, 6]\t" + isUnique(notRandom1));

	}
}