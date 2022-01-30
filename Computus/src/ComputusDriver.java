import java.util.Arrays;

/**
 * Used for testing Computus.java
 * @author mdobroski
 */
public class ComputusDriver {
    public static void main(String[] arg) {
        Computus.findEasterConsole();
        System.out.println();

        System.out.println("Computus.findEaster(2020) will print out [4, 12] because that is the corresponding month and day of this year's easter");
        System.out.println("Computus.findEaster(2020) = " + Arrays.toString(Computus.findEaster(2020)));
        System.out.println("Computus.findEaster(2008) will print out [3, 23] because that is the corresponding month and day of 2008's easter");
        System.out.println("Computus.findEaster(2008) = " + Arrays.toString(Computus.findEaster(2008)));
        System.out.println();

        System.out.println("Computus.printAllEasters() will print out a list of the frequencies of every possible easter date, the first should be March 22 - 27550 and the last should be April 25 - 42000");
        System.out.println("Running Computus.printAllEasters()...\n");
        Computus.printAllEasters();
    }
}
