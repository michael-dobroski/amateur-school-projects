import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.valueOf;
import static org.junit.jupiter.api.Assertions.*;

// I used the site http://tlarsen2.tripod.com/thomaslarsen/easterdates.html and copied it into easterdates.txt
// At the bottom, they mention "Courtesy: Astronomical Society of South Australia"
// That Courtesy is a hyperlink to here... https://www.assa.org.au/edm/

// Used this stack overflow page for guidance on getting rid of whitespace...
// https://stackoverflow.com/questions/2932392/java-how-to-replace-2-or-more-spaces-with-single-space-in-string-and-delete-lead#:~:text=To%20eliminate%20spaces%20at%20the,)%2B%22%2C%20%22%20%22)%20.&text=You%20can%20first%20use%20String.

// Used this page for advice on stripping letters in a string
// https://alvinalexander.com/java/java-strip-characters-string-letters-numbers-replace/

/**
 * JUnit 5 testing of the findEaster() method in Computus.java
 */
public class ComputusJUnit {
    /**
     * Reads in text file containing data with the confirmed easter dates for each year between 1700 and 2299 then takes
     * that data and compares it to the output that Computus.findeaster() would come up with for each corresponding year.
     * @throws IllegalAccessException
     */
    @Test
    void testFindEaster() throws IllegalAccessException {

        // create ArrayList of int arrays that contain [day, month, year] for every year 1700-2299
        ArrayList<Integer[]> easterDatesTestList = new ArrayList<>();
        int[] currEaster = new int[2]; // so that you don't have to run findEaster() twice on every year
        for (int i=1700; i<2300; i++) { // starts at year 1700, ends at 2299 just like in easterdates.txt
            Integer[] dayMonthYear = new Integer[3];
            currEaster = Computus.findEaster(i); // currEaster is year i's [month, day]
            dayMonthYear[0] = currEaster[1]; // so we gotta switch em
            dayMonthYear[1] = currEaster[0]; // done switchin em
            dayMonthYear[2] = i; // set year
            // System.out.println(Arrays.toString(dayMonthYear)); // print out, debugging
            easterDatesTestList.add(dayMonthYear);
        }

        // pre-process easterdates.txt into list of strings, splitting day, month and year up
        File orderFile = new File("easterdates.txt");
        List<String> splits; // tool for reading in
        ArrayList<String> knownEasterData = new ArrayList<>(); // tool for reading in, won't contain whitespace
        try { // for the sake of catching if reading in failed
            Scanner input = new Scanner(orderFile); // sets up scanner for reading in
            while (input.hasNextLine()) { // while there's still text left to read in
                splits = Arrays.asList(input.nextLine().split(" ")); // split data at space
                for (String split : splits) { // iterate through splits
                    String noWhite = split.trim().replaceAll(" +", " "); // removes whitespace, check documentation for source
                    if (!(noWhite.isEmpty())) { // I found this String method just by scrolling through the list of available methods
                        knownEasterData.add(noWhite); // will only add if it's not blank
                    }
                }
                // for (String split : knownEasterData) { // print one splits list and the lengths, used in debugging
                //     System.out.println(split);
                //     System.out.println(split.length());
                // }
                // break;
            }
        } catch (Exception e) {
            System.out.printf("Exception: %s%n", e.getMessage()); // throw exception
        }

        // continue pre-process to create ArrayList similar to that of easterDatesTestList where each array is [day, month, year]
        ArrayList<Integer[]> easterDatesKnownList = new ArrayList<>();
        Integer[] dayMonthYear = new Integer[3];
        int iterCount = 0; // counter to tell if we're on a day, month, or year
        for (String data : knownEasterData) {
            if (iterCount % 3 == 0) {  // if 1 mod 3 is zero then we are on the first rotation meaning we're dealing with the day
                String dayNoOrdinal = data.replaceAll("[^0-9]",""); // will strip away all letters, check documentation
                dayMonthYear[0] = valueOf(dayNoOrdinal); // cast string to Integer
            }
            else if (iterCount % 3 == 1) { // if 1 mod 3 is one then we are on the second rotation meaning we're dealing with the month
                if (data.equals("March")) {
                    dayMonthYear[1] = 3;
                } else {
                    dayMonthYear[1] = 4;
                }
            }
            else { // if 1 mod 3 isn't one or zero then it must be two meaning we're on third rotation which is year, very easy
                dayMonthYear[2] = Integer.parseInt(data);
                Integer[] dayMonthYearToAdd = new Integer[3]; // we have to make a new one every three times because objects lose scope
                dayMonthYearToAdd[0] = dayMonthYear[0];
                dayMonthYearToAdd[1] = dayMonthYear[1];
                dayMonthYearToAdd[2] = dayMonthYear[2];
                easterDatesKnownList.add(dayMonthYearToAdd);
            }
            iterCount++;
        }

        // sort easterDatesKnownList by year
        ArrayList<Integer[]> easterDatesKnownListSorted = new ArrayList<>();
        int numDates = easterDatesKnownList.size();
        for (int j=0; j<numDates; j++) {
            Integer[] min = easterDatesKnownList.get(0);
            for (int i=1; i<easterDatesKnownList.size(); i++) { // finds current min
                if (easterDatesKnownList.get(i)[2] < min[2]) {
                    min = easterDatesKnownList.get(i);
                }
            }
            easterDatesKnownList.remove(min);
            easterDatesKnownListSorted.add(min);
        }

        // iterate through number of dates and do JUnit testing element-wise
        for (int i=0; i<numDates; i++) {
            assertEquals(easterDatesTestList.get(i)[0],easterDatesKnownListSorted.get(i)[0]);
            assertEquals(easterDatesTestList.get(i)[1],easterDatesKnownListSorted.get(i)[1]);
            assertEquals(easterDatesTestList.get(i)[2],easterDatesKnownListSorted.get(i)[2]);
        }
    }
}
























