import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

// WindowListener documentation ~ http://www.java2s.com/Tutorials/Java/Swing_How_to/JFrame/Handle_JFrame_window_listener.htm

/**
 * GUI of the project, handles all GUI instances and events
 * @author Michael Dobroski
 */
public class LifeGUI implements WindowListener, ChangeListener, ActionListener {

    /**
     * Grid object from custom Grid class that extends from JPanel, handles the grid element of the game
     */
    private final Grid grid = new Grid(962,962); // 64 x 15 + 2 pixels

    /**
     * Only frame in this GUI
     */
    private final JFrame frame = new JFrame();

    /**
     * Accessed by windowClosing() method and driver to tell if GUI has been terminated in order to terminate threads
     */
    private boolean windowOpen = true;

    /**
     * Delay in between running of threads, represents time between each generation
     */
    private int tickSpeed = 10;

    /**
     * Current configuration of the alive/dead cells on the grid
     */
    private final boolean[][] config = new boolean[64][64];

    /**
     * Tells the driver if there's a new configuration from user
     */
    private boolean newConfig = false;

    /**
     * Accessed by driver to tell if window has closed or not in order to terminate threads
     * @return true or false based on open-ness of window
     */
    boolean isWindowOpen() { return windowOpen; }

    /**
     * Return tick speed set by tick speed slider
     * @return milliseconds in between ticks
     */
    int getTickSpeed() { return tickSpeed; }

    /**
     * Returns current configuration of the cells
     * @return current configuration
     */
    boolean[][] getConfig() { return config; }

    /**
     * Tells the driver if there has been a change in the configuration in order to update sharedCoordinates
     * @return true or false based on if it's recently been updated
     */
    boolean configChange() {
        if (newConfig) { // once it's called while true, automatically set to false so we don't need a setter
            newConfig = false;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Constructor of the GUI
     */
    public LifeGUI() { // don't need EXIT_ON_CLOSE because our overridden windowCLosing event disposes of the frame

        JPanel gridPanel = new JPanel(); // handles the panel containing the grid

        // handles the panel containing all of the options like configurations and tickspeed
        JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        optionsPanel.setLayout(new GridLayout(0,1));
        JSlider tickSpeedSlider = new JSlider(JSlider.HORIZONTAL, 0, 250, 50);
        optionsPanel.add(tickSpeedSlider);

        // buttons for viewing various configurations
        JRadioButton block = new JRadioButton("Block");
        block.addActionListener(this);
        optionsPanel.add(block);
        JRadioButton beehive = new JRadioButton("Beehive");
        beehive.addActionListener(this);
        optionsPanel.add(beehive);
        JRadioButton loaf = new JRadioButton("Loaf");
        loaf.addActionListener(this);
        optionsPanel.add(loaf);
        JRadioButton boat = new JRadioButton("Boat");
        boat.addActionListener(this);
        optionsPanel.add(boat);
        JRadioButton tub = new JRadioButton("Tub");
        tub.addActionListener(this);
        optionsPanel.add(tub);
        JRadioButton blinker = new JRadioButton("Blinker");
        blinker.addActionListener(this);
        optionsPanel.add(blinker);
        JRadioButton toad = new JRadioButton("Toad");
        toad.addActionListener(this);
        optionsPanel.add(toad);
        JRadioButton beacon = new JRadioButton("Beacon");
        beacon.addActionListener(this);
        optionsPanel.add(beacon);
        JRadioButton pulsar = new JRadioButton("Pulsar");
        pulsar.addActionListener(this);
        optionsPanel.add(pulsar);
        JRadioButton pentaDecathlon = new JRadioButton("Penta-decathlon");
        pentaDecathlon.addActionListener(this);
        optionsPanel.add(pentaDecathlon);
        JRadioButton gosper = new JRadioButton("Gosper's Glider Gun");
        gosper.addActionListener(this);
        optionsPanel.add(gosper);

        // so that only one can be selected at a time
        ButtonGroup group = new ButtonGroup();
        group.add(gosper);
        group.add(block);
        group.add(beehive);
        group.add(loaf);
        group.add(boat);
        group.add(tub);
        group.add(blinker);
        group.add(toad);
        group.add(beacon);
        group.add(pulsar);
        group.add(pentaDecathlon);

        // configure tick speed slider to my preferences
        tickSpeedSlider.addChangeListener(this);
        tickSpeedSlider.setMajorTickSpacing(50);
        tickSpeedSlider.setMinorTickSpacing(10);
        tickSpeedSlider.setPaintTicks(true);
        tickSpeedSlider.setPaintLabels(true);
        tickSpeedSlider.setValue(10);

        // put it all together
        gridPanel.add(grid);
        frame.add(gridPanel, BorderLayout.WEST);
        frame.add(optionsPanel, BorderLayout.EAST);
        frame.setTitle("Threaded Game of Life");
        frame.addWindowListener(this);
        frame.pack();
        frame.setVisible(true);

    }

    /**
     * Updates grid based on the inputted coordinates representing the desired configuration
     * @param coords desired configuration
     */
    public void update(boolean[][] coords) {
        grid.setCoords(coords);
        grid.repaint();
    }

    /**
     * Tells the driver if the GUI has closed in order to tell it when to terminate threads
     * @param e for superclass sake
     */
    public void windowClosing(WindowEvent e) {
        windowOpen = false; // accessed by isWindowOpen() getter in order to check if window is closed to terminate threads
        frame.dispose(); // check documentation
        System.exit(0); // check documentation
    }

    /**
     * Handles event of slider changing value and adjusts accordingly
     * @param e for superclass sake
     */
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        if (!source.getValueIsAdjusting()) {
            tickSpeed = source.getValue();
        }
    }

    /**
     * Handles radio button events
     * @param e for superclass sake
     */
    public void actionPerformed(ActionEvent e) {
        String buttonText = e.getActionCommand(); // identify the button
        for (int i=0; i<64; i++) { // clear current configuration in preparation for new one
            for (int j=0; j<64; j++) {
                config[i][j] = false;
            }
        }
        if (buttonText.equals("Gosper's Glider Gun")) {
            config[1][5] = true; config[1][6] = true; config[2][5] = true; config[2][6] = true;
            config[11][5] = true; config[11][6] = true; config[11][7] = true; config[12][8] = true;
            config[13][9] = true; config[14][9] = true; config[12][4] = true; config[13][3] = true;
            config[14][3] = true; config[15][6] = true; config[16][4] = true; config[16][8] = true;
            config[17][5] = true; config[17][6] = true; config[17][7] = true; config[18][6] = true;
            config[21][3] = true; config[21][4] = true; config[21][5] = true; config[22][3] = true;
            config[22][4] = true; config[22][5] = true; config[23][2] = true; config[23][6] = true;
            config[25][1] = true; config[25][2] = true; config[25][6] = true; config[25][7] = true;
            config[35][3] = true; config[35][4] = true; config[36][3] = true; config[36][4] = true;
        } if (buttonText.equals("Block")) {
            config[1][1] = true; config[1][2] = true; config[2][1] = true; config[2][2] = true;
        } if (buttonText.equals("Beehive")) {
            config[1][2] = true; config[2][1] = true; config[3][1] = true; config[4][2] = true;
            config[2][3] = true; config[3][3] = true;
        } if (buttonText.equals("Loaf")) {
            config[1][2] = true; config[2][3] = true; config[3][4] = true; config[4][3] = true;
            config[2][1] = true; config[3][1] = true; config[4][2] = true;
        } if (buttonText.equals("Boat")) {
            config[1][1] = true; config[1][2] = true; config[2][1] = true; config[3][2] = true;
            config[2][3] = true;
        } if (buttonText.equals("Tub")) {
            config[1][2] = true; config[2][3] = true; config[2][1] = true; config[3][2] = true;
        } if (buttonText.equals("Blinker")) {
            config[1][2] = true; config[2][2] = true; config[3][2] = true;
        } if (buttonText.equals("Toad")) {
            config[1][3] = true; config[2][3] = true; config[3][3] = true; config[2][2] = true;
            config[3][2] = true; config[4][2] = true;
        } if (buttonText.equals("Beacon")) {
            config[1][1] = true; config[1][2] = true; config[2][1] = true; config[2][2] = true;
            config[3][3] = true; config[3][4] = true; config[4][3] = true; config[4][4] = true;
        } if (buttonText.equals("Pulsar")) {
            config[4][2] = true; config[5][2] = true; config[6][2] = true; config[7][4] = true;
            config[7][5] = true; config[7][6] = true; config[9][4] = true; config[9][5] = true;
            config[9][6] = true; config[10][2] = true; config[11][2] = true; config[12][2] = true;
            config[14][4] = true; config[14][5] = true; config[14][6] = true; config[12][7] = true;
            config[11][7] = true; config[10][7] = true; config[10][9] = true; config[11][9] = true;
            config[12][9] = true; config[14][10] = true; config[14][11] = true; config[14][12] = true;
            config[2][4] = true; config[2][5] = true; config[2][6] = true; config[4][7] = true;
            config[5][7] = true; config[6][7] = true; config[4][9] = true; config[5][9] = true;
            config[6][9] = true; config[2][10] = true; config[2][11] = true; config[2][12] = true;
            config[4][14] = true; config[5][14] = true; config[6][14] = true; config[7][12] = true;
            config[7][11] = true; config[7][10] = true; config[9][10] = true; config[9][11] = true;
            config[9][12] = true; config[10][14] = true; config[11][14] = true; config[12][14] = true;
        } if (buttonText.equals("Penta-decathlon")) {
            config[4][5] = true; config[5][5] = true; config[6][5] = true; config[4][6] = true;
            config[6][6] = true; config[4][7] = true; config[5][7] = true; config[6][7] = true;
            config[4][8] = true; config[5][8] = true; config[6][8] = true; config[4][9] = true;
            config[5][9] = true; config[6][9] = true; config[4][10] = true; config[5][10] = true;
            config[6][10] = true; config[4][11] = true; config[6][11] = true; config[4][12] = true;
            config[5][12] = true; config[6][12] = true;
        }
        newConfig = true; // tells driver that there's a new configuration
    }

    /**
     * Unused, forced to implement because of superclass being an interface
     * @param e for superclass interface
     */
    public void windowOpened(WindowEvent e) { }

    /**
     * Unused, forced to implement because of superclass being an interface
     * @param e for superclass interface
     */
    public void windowClosed(WindowEvent e) { }

    /**
     * Unused, forced to implement because of superclass being an interface
     * @param e for superclass interface
     */
    public void windowIconified(WindowEvent e) { }

    /**
     * Unused, forced to implement because of superclass being an interface
     * @param e for superclass interface
     */
    public void windowDeiconified(WindowEvent e) { }

    /**
     * Unused, forced to implement because of superclass being an interface
     * @param e for superclass interface
     */
    public void windowActivated(WindowEvent e) { }

    /**
     * Unused, forced to implement because of superclass being an interface
     * @param e for superclass interface
     */
    public void windowDeactivated(WindowEvent e) { }

}
