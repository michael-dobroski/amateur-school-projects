import javax.swing.*;

/**
 * Handles everything related to the Hangman game and its GUI
 * @author mdobroski
 */
public class ArabicToRomanGUI {

    /**
     * Accessible by driver class, starts the process of creating the GUI
     */
    public static void makeGUI() { new ArabicToRomanGUI(); }

    /**
     * Builds initial GUI
     */
    private ArabicToRomanGUI() {
        ATRFrame ArabicToRomanFrame = new ATRFrame(); // custom object we created in ATRFrame.java
        ArabicToRomanFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ArabicToRomanFrame.pack();
        ArabicToRomanFrame.setVisible(true);
    }
}
