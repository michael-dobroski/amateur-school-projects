import java.util.ArrayList;

/**
 * handles the back end code for our base change GUI
 * @author Michael Dobroski
 */
public class BaseChange {

    /**
     * when given a number and its base as input, will output the same number in base 10 (decimal) format by taking
     * advantage of modulus and ASCII values
     * @param num number in the form of a String that has base fromBase
     * @param fromBase original base on num
     * @return inputted number in base 10 (decimal) format
     */
    public static String anyBaseToDecimal(String num, int fromBase) {
        int decimal = 0;
        for (int i=0; i<num.length(); i++) { // algorithm follows base change principles from Discrete Structures and char ASCII values
            double pow = StrictMath.pow(fromBase, num.length() - 1 - i);
            if ((int) num.charAt(i) > 47 && (int) num.charAt(i) < 58) {
                decimal += pow * ((int) num.charAt(i) - 48);
            }
            if ((int) num.charAt(i) > 64 && (int) num.charAt(i) < 87) {
                decimal += pow * ((int) num.charAt(i) - 55);
            }
        }
        return String.valueOf(decimal);
    }

    /**
     * when given a base 10 (decimal) number and its desired base change as input, will output the same number in
     * the format of its desired base toBase
     * @param num decimal number that desires a base change to toBase
     * @param toBase desired base to change original decimal number to
     * @return inputted number in base toBase format
     */
    public static String decimalToAnyBase(int num, int toBase) {
        ArrayList<Character> outputRev = new ArrayList<>(); // so that we can easily append, will be reversed correctly later
        while (num != 0) { // algorithm once again follows base change principles from Discrete Structures and char ASCII values
            if (num % toBase < 10) {
                outputRev.add((char) ((num % toBase) + 48));
            } else {
                outputRev.add((char) ((num % toBase) + 55));
            }
            num = num / toBase;
        }
        char[] output = new char[outputRev.size()];
        for (int i=0; i<outputRev.size(); i++) { // reverses our original ArrayList<Character> data structure because it's in the opposite order as intended
            output[i] = outputRev.get(outputRev.size()-1-i);
        }
        return String.valueOf(output);
    }

    /**
     * when given a number and its "base", will tell you if it's a valid number for that base
     * @param num number of base "base"
     * @param base supposed base of the inputted number
     * @return true if it's a valid base for the number, false if not
     */
    public static boolean isValid(String num, int base) {
        char[] validDig = new char[base]; // array of valid digits for a given base
        if (base < 11) { // bases 2-10
            for (int i=0; i<base; i++) {
                validDig[i] = (char) (i + 48);
            }
        } else { // bases 11-32
            for (int i=0; i<10; i++) { // digits 0-9
                validDig[i] = (char) (i + 48);
            }
            for (int i=10; i<base; i++) { // any alphabetical digit
                validDig[i] = (char) (i + 55);
            }
        }
        boolean valid;
        for (int i=0; i<num.length(); i++) { // check to see that every digit in num is a digit in validDig
            valid = false;
            for (char dig : validDig) {
                if (num.charAt(i) == dig) {
                    valid = true;
                    break;
                }
            }
            if (!valid) {
                return false;
            }
        }
        return true;
    }

}
