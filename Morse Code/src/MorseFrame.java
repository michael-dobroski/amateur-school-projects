import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Custom class object that inherits from JFrame handles pretty much everything in the GUI
 * @author Michael Dobroski
 */
public class MorseFrame extends JFrame {

    /**
     * Text area containing english input to be converted to morse
     */
    private final JTextArea englishTextArea;

    /**
     * Text area containing morse code input to be converted to english
     */
    private final JTextArea morseTextArea;

    /**
     * Hashmap defining translation between english and morse code
     */
    private final HashMap<String, String> ENGLISH_TO_MORSE = new HashMap<>();

    /**
     * No argument constructor, builds GUI and hashmap for morse - english equivalence
     */
    public MorseFrame() {

        super("Morse - English Converter"); // frame title
        Box box = Box.createHorizontalBox(); // create box

        englishTextArea = new JTextArea(10, 15);
        box.add(new JScrollPane(englishTextArea)); // add scrollpane

        // updates in real time
        englishTextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                morseTextArea.setText(englishToMorse(englishTextArea.getText()));
            }
        });

        JLabel toMorseLabel = new JLabel("   <~ English          Morse ~>   ");
        box.add(toMorseLabel);

        morseTextArea = new JTextArea(10,15);
        box.add(new JScrollPane(morseTextArea)); // add scrollpane

        // updates in real time
        morseTextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                englishTextArea.setText(morseToEnglish(morseTextArea.getText()));
            }
        });

        add(box); // add box to frame

        // define english to morse dictionary, done in constructor so that it's set only once to preserve running time
        ENGLISH_TO_MORSE.put("A", ".-"); ENGLISH_TO_MORSE.put("B", "-..."); ENGLISH_TO_MORSE.put("C", "-.-.");
        ENGLISH_TO_MORSE.put("D", "-.."); ENGLISH_TO_MORSE.put("E", "."); ENGLISH_TO_MORSE.put("F", "..-.");
        ENGLISH_TO_MORSE.put("G", "--."); ENGLISH_TO_MORSE.put("H", "...."); ENGLISH_TO_MORSE.put("I", "..");
        ENGLISH_TO_MORSE.put("J", ".---"); ENGLISH_TO_MORSE.put("K", "-.-"); ENGLISH_TO_MORSE.put("L", ".-..");
        ENGLISH_TO_MORSE.put("M", "--"); ENGLISH_TO_MORSE.put("N", "-."); ENGLISH_TO_MORSE.put("O", "---");
        ENGLISH_TO_MORSE.put("P", ".--."); ENGLISH_TO_MORSE.put("Q", "--.-"); ENGLISH_TO_MORSE.put("R", ".-.");
        ENGLISH_TO_MORSE.put("S", "..."); ENGLISH_TO_MORSE.put("T", "-"); ENGLISH_TO_MORSE.put("U", "..-");
        ENGLISH_TO_MORSE.put("V", "...-"); ENGLISH_TO_MORSE.put("W", ".--"); ENGLISH_TO_MORSE.put("X", "-..-");
        ENGLISH_TO_MORSE.put("Y", "-.--"); ENGLISH_TO_MORSE.put("Z", "--.."); ENGLISH_TO_MORSE.put("1", ".----");
        ENGLISH_TO_MORSE.put("2", "..---"); ENGLISH_TO_MORSE.put("3", "...--"); ENGLISH_TO_MORSE.put("4", "....-");
        ENGLISH_TO_MORSE.put("5", "....."); ENGLISH_TO_MORSE.put("6", "-...."); ENGLISH_TO_MORSE.put("7", "--...");
        ENGLISH_TO_MORSE.put("8", "---.."); ENGLISH_TO_MORSE.put("9", "----."); ENGLISH_TO_MORSE.put("0", "-----");

    }

    /**
     * Translates from English to Morse code
     * @param english String containing the sentence intended to be translated to morse
     * @return morse code equivalent of the inputted English
     */
    private String englishToMorse(String english) {

        StringBuilder morse = new StringBuilder(); // what our answer will get appended to and eventually outputted

        for (int i = 0; i < english.length(); i++) { // at each index, if it's not a space, find equivalent in hashmap and append to morse

            if (english.charAt(i) == ' ') {
                morse.append("   ");
            } else {
                morse.append(ENGLISH_TO_MORSE.get((Character.toString(english.charAt(i))).toUpperCase()));
                morse.append(" ");
            }

        }

        return morse.toString();

    }

    /**
     * Translates from Morse code to English
     * @param morse String containing the Morse code intended to be translated to English
     * @return English equivalent of the inputted Morse code
     */
    private String morseToEnglish(String morse) {

        StringBuilder english = new StringBuilder(); // eventually our output, everything gets appended to this
        StringBuilder letter = new StringBuilder(); // for individual english letter equivalents
        int spaceCount = 0; // counter for when to add a space

        for (int i = 0; i < morse.length(); i++) {

            if (morse.charAt(i) == ' ') {
                if (spaceCount == 0) {
                    String letterString = letter.toString();
                    for (Map.Entry<String, String> e : ENGLISH_TO_MORSE.entrySet()) { // finds right key in hashmap and appends to english output
                        if (letterString.equals(e.getValue())) {
                            english.append(e.getKey());
                            break;
                        }
                    }
                    letter.delete(0, letter.length()); // clears letter StringBuffer
                    spaceCount++;
                } else if (spaceCount == 2) { // three total spaces means one space in english
                    english.append(" ");
                } else {
                    spaceCount++;
                }
            } else {
                letter.append(morse.charAt(i)); // building letter
                spaceCount = 0;
            }

        }

        // in the case that our input doesn't end in a space
        String letterString = letter.toString();
        for (Map.Entry<String, String> e : ENGLISH_TO_MORSE.entrySet()) {
            if (letterString.equals(e.getValue())) {
                english.append(e.getKey());
                break;
            }
        }

        return english.toString();

    }

}
