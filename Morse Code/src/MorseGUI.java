import javax.swing.*;

/**
 * Handles everything related to the Morse-English GUI
 * @author mdobroski
 */
public class MorseGUI {

    /**
     * Builds initial GUI
     */
    public MorseGUI() {

        MorseFrame morseFrame = new MorseFrame(); // custom object we created in MorseFrame.java
        morseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        morseFrame.pack();
        morseFrame.setVisible(true);

    }

}