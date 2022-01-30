import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 * Contains methods involving calculating the date of Easter algorithmically
 */
public class Computus {

    /**
     * Takes input from user and prints corresponding easter date for the year that the user types in. This loop
     * continues until the user types in "quit" and the method terminates.
     */
    public static void findEasterConsole() {

        // loop allowing us to continue asking for values until we chose to quick
        while (true) { // will keeping looping until there's a break
            System.out.println("Please enter a year. If you would like to quit, please type \"quit\".");
            Scanner in = new Scanner(System.in);
            String input = in.nextLine();
            if (input.equals("quit")) { // if we type in quit, break and exit
                System.out.println("Goodbye.");
                break;
            }
            else { // if we type in a year, calculates and prints easter based off that
                int Y = Integer.parseInt(input);

                // Meeus/Jones/Butcher Gregorian algorithm
                int a = Y % 19;
                int b = Y / 100;
                int c = Y % 100;
                int d = b / 4;
                int e = b % 4;
                int f = (b + 8) / 25;
                int g = (b - f + 1) / 3;
                int h = (19 * a + b - d - g + 15) % 30;
                int i = c / 4;
                int k = c % 4;
                int l = (32 + 2 * e + 2 * i - h - k) % 7;
                int m = (a + 11 * h + 22 * l) / 451;
                int month = (h + l - 7 * m + 114) / 31;
                int day = ((h + l - 7 * m + 114) % 31) + 1;

                String[] monthNames = {"January", "February", "March", "April"};
                System.out.println("Easter lies on " + monthNames[month-1] + " " + day + ", " + Y);
            }
        }
    }

    /**
     * When given year input, will output corresponding easter date in form of [month, day] int array.
     * @param Y the year that you're trying to find the corresponding easter date to
     * @return new int[]{month, day} the corresponding easter date to year input
     */
    public static int[] findEaster(int Y) {

        // Meeus/Jones/Butcher Gregorian algorithm
        int a = Y % 19;
        int b = Y / 100;
        int c = Y % 100;
        int d = b / 4;
        int e = b % 4;
        int f = (b + 8) / 25;
        int g = (b - f + 1) / 3;
        int h = (19 * a + b - d - g + 15) % 30;
        int i = c / 4;
        int k = c % 4;
        int l = (32 + 2 * e + 2 * i - h - k) % 7;
        int m = (a + 11 * h + 22 * l) / 451;
        int month = (h + l - 7 * m + 114) / 31;
        int day = ((h + l - 7 * m + 114) % 31) + 1;

        return new int[]{month, day};
    }

    /**
     * Will print every possible easter date and its corresponding frequency in a 5,700,000 year cycle.
     */
    public static void printAllEasters() {
        ArrayList<int[]> easterDates = new ArrayList<>(); // array list of 5,700,000 [month, day, frequency]s
        ArrayList<int[]> easterDateFreq = new ArrayList<>(); // array list of [month, day, frequency]s
        boolean matchFound;

        // calculate frequency of dates
        for (int i=0; i<5700000; i++) {
            easterDates.add(findEaster(i)); // adds new easter to arraylist of all easters
            int[] datePlusCount = new int[3]; // [month, day, frequency]
            datePlusCount[0] = easterDates.get(i)[0]; // set month
            datePlusCount[1] = easterDates.get(i)[1]; // set day
            if (i==0) { // we need an initial set so that we can run the next for loop properly
                datePlusCount[2] = 1; // set frequency
                easterDateFreq.add(datePlusCount);
            } else { // executes every iteration other than the first
                matchFound = false;
                for (int j=0; j<easterDateFreq.size(); j++) { // iterate through list of dates plus their frequency
                    if (datePlusCount[0] == easterDateFreq.get(j)[0] && datePlusCount[1] == easterDateFreq.get(j)[1]) { // if month and day match
                        datePlusCount[2] = easterDateFreq.get(j)[2] + 1; // increase frequency by one because we found a match
                        easterDateFreq.remove(j); // remove original date & freq array
                        easterDateFreq.add(datePlusCount); // add the one with the added frequency
                        matchFound = true; // program will remember that we found a match
                        break; // saves runtime because there will only be on match and that's all we care about
                    }
                }
                if (!matchFound) { // in the case that we don't find a match, we want to make a new array and set its freq to 1
                    datePlusCount[2] = 1;
                    easterDateFreq.add(datePlusCount);
                }
            }
        }

        // sort results
        ArrayList<int[]> easterDateFreqSorted = new ArrayList<>();
        int iter = easterDateFreq.size();
        for (int j=0; j<iter; j++) { // will do this as many times as there are possible dates
            int[] min = new int[3];
            for (int i=0; i<easterDateFreq.size(); i++) { // for loop will find min of easterDateFreq based off date
                int[] currentDateFreq = easterDateFreq.get(i);
                if (i==0) { // initial min set
                    min = currentDateFreq;
                } else {
                    if (currentDateFreq[0] < min[0]) { // if month is smaller than min
                        min = currentDateFreq;
                    }
                    if (currentDateFreq[0] == min[0]) { // month is the same
                        if (currentDateFreq[1] < min[1]) { // but date is smaller
                            min = currentDateFreq;
                        }
                    }
                }
            }
            easterDateFreqSorted.add(min);
            easterDateFreq.remove(min);
        }

        // print out results
        String[] monthNames = {"January", "February", "March", "April"};
        for (int[] ints : easterDateFreqSorted) {
            System.out.println(monthNames[ints[0]-1] + " " + ints[1] + " - " + ints[2]);
        }
    }
}