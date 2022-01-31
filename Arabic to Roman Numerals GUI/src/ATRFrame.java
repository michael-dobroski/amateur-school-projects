import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Custom class object that inherits from JFrame handles pretty much everything in the GUI
 * @author mdobroski
 */
public class ATRFrame extends JFrame {

    // our two text areas, non-local instances because we want to customize them in other methods
    private final JTextArea arabicTextArea;
    private final JTextArea romanTextArea;

    /**
     * No argument constructor
     */
    public ATRFrame() {
        super("Arabic To Roman Numerals Converter"); // frame title
        Box box = Box.createHorizontalBox(); // create box

        arabicTextArea = new JTextArea(10, 15);
        box.add(new JScrollPane(arabicTextArea)); // add scrollpane

        // updates in real time
        arabicTextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                romanTextArea.setText(arabicToRoman(arabicTextArea.getText()));
            }
        });

        JLabel toRomanLabel = new JLabel("   <~ Arabic          Roman ~>   ");
        box.add(toRomanLabel);

        romanTextArea = new JTextArea(10,15);
        box.add(new JScrollPane(romanTextArea)); // add scrollpane

        // updates in real time
        romanTextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                arabicTextArea.setText(romanToArabic(romanTextArea.getText()));
            }
        });

        add(box); // add box to frame
    }

    /**
     * Converts arabic numbers into roman numerals
     * @param arabic arabic number in the form of a String to be converted
     * @return roman numeral in the form of a string after being converted
     */
    private String arabicToRoman(String arabic) {
        ArrayList<Character> roman = new ArrayList<>(); // will hold all of our numerals, great because it changes size dynamically
        String[] unitVal = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"}; // correspond to given decimal number, same for rest
        String[] tenVal = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] hundredVal = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] thousandVal = {"", "M", "MM", "MMM"};

        // if it's one number long, we only have to worry about the unit
        if (arabic.length() == 1) {
            String units = unitVal[Character.getNumericValue(arabic.charAt(0))]; // fetches corresponding numeral
            for (int i=0; i<units.length(); i++) { // keep track of them
                roman.add(units.charAt(i));
            }
        }

        // two numbers long, we worry about unit and tens
        else if (arabic.length() == 2) {
            String tens = tenVal[Character.getNumericValue(arabic.charAt(0))];
            for (int i=0; i<tens.length(); i++) {
                roman.add(tens.charAt(i));
            }
            String units = unitVal[Character.getNumericValue(arabic.charAt(1))];
            for (int i=0; i<units.length(); i++) {
                roman.add(units.charAt(i));
            }
        }

        // three numbers long, we worry about units, tens, and hundreds
        else if (arabic.length() == 3) {
            String hundreds = hundredVal[Character.getNumericValue(arabic.charAt(0))];
            for (int i=0; i<hundreds.length(); i++) {
                roman.add(hundreds.charAt(i));
            }
            String tens = tenVal[Character.getNumericValue(arabic.charAt(1))];
            for (int i=0; i<tens.length(); i++) {
                roman.add(tens.charAt(i));
            }
            String units = unitVal[Character.getNumericValue(arabic.charAt(2))];
            for (int i=0; i<units.length(); i++) {
                roman.add(units.charAt(i));
            }
        }

        // four numbers long and smaller than 4000, we worry about units, tens, hundreds, and thousands
        else if (arabic.length() == 4 && Integer.parseInt(String.valueOf(arabic.charAt(0)))<4) {
            String thousands = thousandVal[Character.getNumericValue(arabic.charAt(0))];
            for (int i=0; i<thousands.length(); i++) {
                roman.add(thousands.charAt(i));
            }
            String hundreds = hundredVal[Character.getNumericValue(arabic.charAt(1))];
            for (int i=0; i<hundreds.length(); i++) {
                roman.add(hundreds.charAt(i));
            }
            String tens = tenVal[Character.getNumericValue(arabic.charAt(2))];
            for (int i=0; i<tens.length(); i++) {
                roman.add(tens.charAt(i));
            }
            String units = unitVal[Character.getNumericValue(arabic.charAt(3))];
            for (int i=0; i<units.length(); i++) {
                roman.add(units.charAt(i));
            }
        }

        else { // means it's larger than 3900 so we output invalid
            return "> INVALID <";
        }

        // restructure to char array for easy switch to string
        char[] romanChar = new char[roman.size()];
        for (int i=0; i<roman.size(); i++) {
            romanChar[i] = roman.get(i);
        }

        return new String(romanChar); // outputs as string for easy display in textarea
    }

    /**
     * Converts roman numbers to arabic
     * @param roman roman numerals input in the String format
     * @return arabic number equivalent in String format
     */
    private String romanToArabic(String roman) {
        HashMap<String, Integer> numeralEquiv = new HashMap<>(); // keeps track of numerical equivalent of numerals
        String[] numerals = {"I", "V", "X", "L", "C", "D", "M"};
        String[] numeralsTwo = {"IV", "IX", "XL", "XC", "CD", "CM"};
        numeralEquiv.put("I", 1); numeralEquiv.put("IV", 4); numeralEquiv.put("V", 5); numeralEquiv.put("IX", 9); numeralEquiv.put("X", 10);
        numeralEquiv.put("XL", 40); numeralEquiv.put("L", 50); numeralEquiv.put("XC", 90); numeralEquiv.put("C", 100);
        numeralEquiv.put("CD", 400); numeralEquiv.put("D", 500); numeralEquiv.put("CM", 900); numeralEquiv.put("M", 1000);
        Integer arabic = 0;
        boolean twoMatchFound;

        for (int i=0; i<roman.length(); i++) {
            twoMatchFound = false; // checks for match with double numeral elements
            if (i != roman.length()-1) {
                for (String numeral : numeralsTwo) {
                    if (numeral.charAt(0)==roman.charAt(i) && numeral.charAt(1)==roman.charAt(i+1)) { // if there's a match
                        arabic += numeralEquiv.get(numeral); // adds that value
                        twoMatchFound = true;
                        i++;
                        break; // save runtime
                    }
                }
            }
            if (!twoMatchFound) {
                for (String numeral : numerals) {
                    if (numeral.charAt(0) == roman.charAt(i)) { // if there's a match
                        arabic += numeralEquiv.get(numeral); // adds that value
                        break; // save runtime
                    }
                }
            }
        }

        // if it's invalid, return invalid. if not, return value
        if (!isValid(roman)) {
            return "> INVALID <";
        }
        else {
            return Integer.toString(arabic);
        }
    }

    /**
     * Will check to see if a roman numeral is valid
     * @param numerals String of roman numerals to check
     * @return boolean, true if valid, false if invalid
     */
    private boolean isValid(String numerals) {
        numerals = numerals.toUpperCase();

        // never more than three of each of these in a row
        String[] neverMoreThanThree = {"MMMM", "CCCC", "XXXX", "IIII"};
        for (int i=0; i<numerals.length()-3; i++) {
            for (String forbid : neverMoreThanThree) {
                if (numerals.charAt(i) == forbid.charAt(0) && numerals.charAt(i+1) == forbid.charAt(1) && numerals.charAt(i+2) == forbid.charAt(2) && numerals.charAt(i+3) == forbid.charAt(3)) {
                    System.out.println("1");
                    return false;
                }
            }
        }

        // never more than one of each of these in a row
        String[] neverMoreThanOne = {"VV", "LL", "DD"};
        for (int i=0; i<numerals.length()-1; i++) {
            for (String forbid : neverMoreThanOne) {
                if (numerals.charAt(i) == forbid.charAt(0) && numerals.charAt(i+1) == forbid.charAt(1)) {
                    System.out.println("2");
                    return false;
                }
            }
        }

        // V, L, and D are never written to the left of a symbol of greater value
        char[] greaterThanV = {'X', 'L', 'C', 'D', 'M'};
        char[] greaterThanL = {'C', 'D', 'M'};
        for (int i=0; i < numerals.length(); i++) {
            if (numerals.charAt(i) == 'V') {
                for (int j=i; j < numerals.length(); j++) {
                    for (char nono : greaterThanV) {
                        if (numerals.charAt(j) == nono) {
                            System.out.println("3");
                            return false;
                        }
                    }
                }
            }
            if (numerals.charAt(i) == 'L') {
                for (int j=i; j < numerals.length(); j++) {
                    for (char nono : greaterThanL) {
                        if (numerals.charAt(j) == nono) {
                            System.out.println("4");
                            return false;
                        }
                    }
                }
            }
            if (numerals.charAt(i) == 'D') {
                for (int j=i; j < numerals.length(); j++) {
                    if (numerals.charAt(j) == 'M') {
                        System.out.println("5");
                        return false;
                    }
                }
            }
        }

        // a symbol cannot be subtracted more than once from a particular symbol of greater value
        String[] threeLetterForbid = {"CCM", "CCD", "XXC", "XXL", "IIX", "IIV"};
        String[] fourLetterForbid = {"CCCM", "CCCD", "XXXC", "XXXL", "IIIX", "IIIV"};
        for (int i=0; i<numerals.length()-2; i++) {
            for (String forbid : threeLetterForbid) {
                if (numerals.charAt(i) == forbid.charAt(0) && numerals.charAt(i+1) == forbid.charAt(1) && numerals.charAt(i+2) == forbid.charAt(2)) {
                    System.out.println("6");
                    return false;
                }
            }
        }
        for (int i=0; i<numerals.length()-3; i++) {
            for (String forbid : fourLetterForbid) {
                if (numerals.charAt(i) == forbid.charAt(0) && numerals.charAt(i+1) == forbid.charAt(1) && numerals.charAt(i+2) == forbid.charAt(2) && numerals.charAt(i+3) == forbid.charAt(3)) {
                    System.out.println("7");
                    return false;
                }
            }
        }

        return true;
    }
}