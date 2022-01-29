import javax.swing.*;
import java.awt.*;

/**
 * Draws the hangman using swing graphics which behaves like a JPanel when you use the constructor because it inherits it
 * @author mdobroski
 */
public class Hangman extends JPanel {

    private int bodyPart = 6;

    /**
     * Setter method for int bodypart instance. This allows us to set what part of the hangman we want to draw
     * from the HangmanGUI class
     * @param bodyPart is an integer from 0 to 6 each representing an extra body part to draw determined by the number
     *                 of misses the player has
     */
    public void setBodyPart(int bodyPart) { this.bodyPart = bodyPart; }

    /**
     * Constructor. Allows you to set width and height of the panel as well
     * @param width is the width of the panel in pixels
     * @param height is the height of the panel in pixels
     */
    Hangman(int width, int height) {
        setPreferredSize(new Dimension(width, height));
    }

    /**
     * Uses superclass to paint the input graphics
     * @param g graphics object used be superclass
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // will paint whatever we customize g to
        this.setBackground(Color.WHITE);

        // draw thing that hangs body
        g.setColor(Color.BLACK);
        g.fillRect(100, 220, 100, 4); // bottom part
        g.fillRect(175, 75, 4, 146); // stand part
        g.fillRect(125, 75, 50, 4); // top part
        g.fillRect(125, 75, 4, 20); // last little hanging part

        // draw head
        if (bodyPart <= 5) {
            g.drawOval(115, 95, 24, 24);
        }
        // draw body
        if (bodyPart <= 4) {
            g.drawLine(127, 119, 127, 170);
        }
        // draw left arm
        if (bodyPart <= 3) {
            g.drawLine(127, 140, 100, 120);
        }
        // draw right arm
        if (bodyPart <= 2) {
            g.drawLine(127, 140, 154, 120);
        }
        // draw left leg
        if (bodyPart <= 1) {
            g.drawLine(127, 170, 154, 190);
        }
        // draw right leg and dead face
        if (bodyPart == 0) {
            g.drawLine(127, 170, 100, 190);
            g.drawLine(121, 102, 125, 106);
            g.drawLine(125, 102, 121, 106);
            g.drawLine(129, 102, 133, 106);
            g.drawLine(133, 102, 129, 106);
        }
    }

}