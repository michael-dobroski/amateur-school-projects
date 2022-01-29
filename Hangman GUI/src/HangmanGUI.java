import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// guidance on hiding enterWord like a password https://stackoverflow.com/questions/19755259/hide-show-password-in-a-jtextfield-java-swing
// guidance on changing font/size https://www.tutorialspoint.com/how-to-change-jlabel-font-in-java

/**
 * Handles everything related to the Hangman game and its GUI
 * @author mdobroski
 */
public class HangmanGUI implements ActionListener {

    // initialize private instances
    private JFrame frame = new JFrame();
    private Hangman hangman = new Hangman(300, 300);
    private JLabel wordLabel = new JLabel("Word: ");
    private JLabel missesLabel = new JLabel("Misses: ");
    // private JLabel guessesLeftLabel = new JLabel("6 Guesses Left"); <~ unnecessary now that we've drawn the hangman
    private JPasswordField enterWord = new JPasswordField(16);
    private JTextField enterLetter = new JTextField(16);
    private int guessesLeft = 6;
    private ArrayList<char[]> word = new ArrayList<>();
    private ArrayList<Character> misses = new ArrayList<>();
    private boolean continueGuessing = true;

    /**
     * Accessible by driver class, starts the process of creating the GUI
     */
    public static void makeGUI() { new HangmanGUI(); }

    /**
     * Builds initial GUI
     */
    private HangmanGUI() {

        enterWord.setEchoChar('*'); // will hide hangman word when setting new game

        // add action listeners for when the player hits the new game or guess letter buttons
        JButton newGame = new JButton("New Game");
        newGame.addActionListener(this);
        JButton guessLetter = new JButton("Guess Letter");
        guessLetter.addActionListener(this);

        // contains all info including the word and missed letters
        JPanel infoPanel = new JPanel();
        infoPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
        infoPanel.setLayout(new GridLayout(0, 1));
        infoPanel.add(wordLabel);
        infoPanel.add(missesLabel);
        // infoPanel.add(guessesLeftLabel); <~ unnecessary now that we've drawn the hangman
        infoPanel.setBackground(Color.WHITE);

        // custom graphics frame that will display the hangman
        hangman.setBorder(BorderFactory.createEmptyBorder(200, 200, 100, 100));

        // includes all of the information that the user will enter in like the word and guessing letters
        JPanel enterWordPanel = new JPanel();
        enterWordPanel.setBorder(BorderFactory.createEmptyBorder(200, 200, 100, 100));
        enterWordPanel.setLayout(new GridLayout(0,2));
        enterWordPanel.add(enterWord);
        enterWordPanel.add(enterLetter);
        enterWordPanel.add(newGame);
        enterWordPanel.add(guessLetter);
        enterWordPanel.setBackground(Color.WHITE);

        // add everything to frame, will put everything together
        frame.add(infoPanel, BorderLayout.EAST);
        frame.add(hangman, BorderLayout.WEST);
        frame.add(enterWordPanel, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Hangman");
        frame.pack();
        frame.setVisible(true);

    }

    /**
     * Processes the button clicks like "New Game" and "Guess Letter" and does the corresponding functions that would
     * occur in a round of hangman
     * @param e event that gets handled by superclass, not important in this set of code
     */
    public void actionPerformed(ActionEvent e) {
        String buttonText = e.getActionCommand();
        boolean matchFound; // to keep track if entered letter is in the hidden word
        if (buttonText.equals("New Game")) { // if the new game button is pressed
            word.clear(); // clears our hidden word instance
            misses.clear(); // resets misses to empty arraylist of chars
            missesLabel.setText("Misses: ");
            continueGuessing = true;
            guessesLeft = 6;
            // guessesLeftLabel.setText("6 Guesses Left");
            hangman.setBodyPart(guessesLeft); // will draw only the stand when .repaint() is called
            hangman.repaint();

            char[] newWord = enterWord.getPassword(); // retrieves word in char array form, not in "*"
            for (char letter : newWord) { // basic layout for word is ArrayList<[Letter, _ or letter], ... >
                char[] letterPlusHyphen = new char[2];
                letterPlusHyphen[0] = Character.toLowerCase(letter);
                letterPlusHyphen[1] = "_".charAt(0);
                word.add(letterPlusHyphen);
            }

            updateHiddenWord(); // calls method that will refresh word display
            enterWord.setText(""); // clears text field, good GUI practice
        }
        if (buttonText.equals("Guess Letter") && continueGuessing) { // if guess letter button is clicked and it's the right time to punch things in
            matchFound = newGuess(enterLetter.getText().toLowerCase()); // calls method to tell you if character is in the hangman word
            updateHiddenWord();

            if (!matchFound) { // no match is found
                if (!misses.isEmpty()) { // formats it to look like a, b, c, d...
                    misses.add(",".toCharArray()[0]);
                    misses.add(" ".toCharArray()[0]);
                }
                misses.add(enterLetter.getText().toLowerCase().charAt(0));

                // turns misses into char array as supposed to ArrayList<Character[]> for easier printing to GUI
                char[] newMisses = new char[misses.size()];
                for (int i = 0; i< misses.size(); i++) {
                    newMisses[i] = misses.get(i);
                }

                // now it's in string form which is even easier for printing to GUI
                String printGuesses = String.valueOf(newMisses);
                missesLabel.setText("Misses: " + printGuesses.toUpperCase());

                guessesLeft--; // there was no match, so we lose a guess
                // guessesLeftLabel.setText(String.valueOf(guessesLeft) + " Guesses Left"); <~ unnecessary now that we've drawn the hangman
                hangman.setBodyPart(guessesLeft); // will draw new respective hangman body part when .repaint() is called
                hangman.repaint();
            }

            if (guessesLeft == 0) { // you've ran out of guesses and now the game over screen will print
                JDialog loseDialog = new JDialog(frame, "this is so cringe :(");

                JLabel youLost = new JLabel("You lost.");
                youLost.setBounds(50,50,100,100);
                youLost.setFont(new Font("Sans Serif", Font.BOLD, 100));
                loseDialog.add(youLost);
                loseDialog.setSize(500,500);
                loseDialog.getContentPane().setBackground(Color.RED);
                loseDialog.setVisible(true);

                continueGuessing = false; // so that you can't continue entering values for guess letter
            }

            if (gameWon()) { // all "_"'s were eliminated and now the congrats screen will print
                JDialog winDialog = new JDialog(frame, "pog");

                JLabel youWin = new JLabel("You win!");
                youWin.setBounds(50,50,100,100);
                youWin.setFont(new Font("Sans Serif", Font.BOLD, 100));
                winDialog.add(youWin);
                winDialog.setSize(500,500);
                winDialog.getContentPane().setBackground(Color.GREEN);
                winDialog.setVisible(true);

                continueGuessing = false; // so that you can't continue entering values for guess letter
            }

            enterLetter.setText(""); // clears text field, good GUI practice
        }
    }

    /**
     * Will update the hangman word by iterating through and cleanly printing out the word with respect to what must
     * still be hidden by hiding those with a "_"
     */
    private void updateHiddenWord() {
        char[] hyphensAndOrLetters = new char[3*word.size()];

        for (int i=0; i<word.size(); i++) {
            hyphensAndOrLetters[3*i] = word.get(i)[1];
            hyphensAndOrLetters[3*i+1] = " ".charAt(0);
            hyphensAndOrLetters[3*i+2] = " ".charAt(0);
        }

        String displayWord = String.valueOf(hyphensAndOrLetters);

        wordLabel.setText("Word: " + displayWord.toUpperCase());
    }

    /**
     * A new letter has been guessed so this function will go through and see if it's a match. If it is, it will update
     * the word array list by changing the "_"'s to their respective letters and return true for yes, there was a match.
     * In the case that there isn't a match, the function will just return false so that the original code can use that
     * information and add a body part to the hangman.
     * @param guessLetter the letter that is being guessed by the player
     * @return true if there is a match and false if not
     */
    private boolean newGuess(String guessLetter) {
        char match = 0;
        boolean matchFound = false;
        for (char[] chars : word) {
            if (chars[0] == guessLetter.charAt(0)) {
                match = chars[0];
                matchFound = true;
                break;
            }
        }
        if (matchFound) {
            ArrayList<char[]> backup = new ArrayList<>(word);
            word.clear();
            for (char[] chars : backup) {
                char[] newLetterHyphen = new char[2];
                newLetterHyphen[0] = chars[0];
                if (chars[0] == match) {
                    newLetterHyphen[1] = match;
                } else {
                    newLetterHyphen[1] = chars[1];
                }
                word.add(newLetterHyphen);
            }
        }
        return matchFound;
    }

    /**
     * Iterates through word to discover if there are still any "_"'s which will conclude if the game has been won
     * @return true if yes you won and false if not yet
     */
    private boolean gameWon() {
        for (char[] chars : word) {
            if (chars[1] == "_".charAt(0)) {
                return false;
            }
        }
        return true;
    }
}


























