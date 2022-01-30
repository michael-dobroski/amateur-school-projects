import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * used for JUnit testing our back end code
 * @author Michael Dobroski
 */
public class BaseChangeJUnit {

    /**
     * tests all three of our methods with random numbers by comparing them with the outputs given by built in functions
     */
    @Test
    void test() {

        Random rand = new Random();
        int randNum;
        int randBase;
        String ourOutput;
        String parseOutput;

        for (int i=0; i<60; i++) { // 60 random cases

            randNum = rand.nextInt(100000); // select random number
            randBase = rand.nextInt(31)+2; // select random base

            ourOutput = BaseChange.decimalToAnyBase(randNum, randBase); // designated random number in designated random base format using our decimalToAnyBase function
            parseOutput = (Integer.toString(Integer.parseInt(Integer.toString(randNum), 10), randBase)).toUpperCase(); // designated random number in designated random base format using built in java functions

            for (int j=0; j<ourOutput.length(); j++) { // we have to go char by char because there is no assertEquals of typing String and String
                assertEquals(ourOutput.charAt(j), parseOutput.charAt(j));
            }

            assertTrue(BaseChange.isValid(ourOutput, randBase)); // check isValid() method

            ourOutput = BaseChange.anyBaseToDecimal(ourOutput, randBase); // back to base 10 (decimal) using our anyBaseToDecimal function
            parseOutput = (Integer.toString(Integer.parseInt(parseOutput, randBase), 10)).toUpperCase(); // back to base 10 (decimal) using built in java functions

            for (int j=0; j<ourOutput.length(); j++) { // we have to go char by char because there is no assertEquals of typing String and String
                assertEquals(ourOutput.charAt(j), parseOutput.charAt(j));
            }

            assertTrue(BaseChange.isValid(ourOutput,10)); // check isValid() method

        }

    }

}
