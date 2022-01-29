import javax.swing.*;
import java.awt.*;

/**
 * Grid class that handles the grid that handles displaying all of the dead and alive cells
 * @author Michael Dobroski
 */
public class Grid extends JPanel {

    /**
     * 64 x 64 boolean array representing every cell in the grid with true being alive and initialized all to false (dead)
     */
    private boolean[][] coords = new boolean[64][64]; // true for alive, initialized all to false

    /**
     * Used in update() in LifeGUI to update grid based on the boolean cell array
     * @param coords boolean cell array representing alive and dead cells in grid
     */
    public void setCoords(boolean[][] coords) { this.coords = coords; }

    /**
     * Constructor
     * @param width width for setting size
     * @param height height for settin size
     */
    Grid(int width, int height) { setPreferredSize(new Dimension(width, height)); }

    /**
     * Overriden method that paints a graphic
     * @param g for superclass sake
     */
    public void paintComponent(Graphics g) {

        super.paintComponent(g); // accesses super-class's constructor
        this.setBackground(Color.WHITE);

        // draw grid using graphics
        g.setColor(Color.LIGHT_GRAY);
        for (int i=0; i<970; i+=15) { // vertical grid lines
            g.fillRect(i,0,2,962);
        } for (int i=0; i<970; i+=15) { // horizontal grid lines
            g.fillRect(0,i,962,2);
        }

        // draw alive cells as black and leave dead cells empty [white]
        g.setColor(Color.BLACK);
        for (int i=0; i<64; i++) {
            for (int j=0; j<64; j++) {
                if (coords[i][j]) { // if alive
                    g.fillRect(2 + 15 * i, 2 + 15 * j, 13, 13);
                }
            }
        }

    }

}
