import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;

/**
 * Handles GUI and main of Display Events project
 * @author Michael Dobroski
 */
public class DisplayEventsGUI implements ActionListener, ItemListener, ListSelectionListener, MouseListener, MouseMotionListener, KeyListener {

    /**
     * Displays the events to user
     */
    private JLabel displayEvents = new JLabel();

    /**
     * Button for testing actionPerformed event
     */
    private JButton pressThisButton = new JButton("Press this button for actionPerformed");

    /**
     * Checkbox for testing itemStateChanged event
     */
    private JCheckBox itemStateChangedBox = new JCheckBox("Check/uncheck this for itemStateChanged");

    /**
     * Textfield for testing keyListener events
     */
    private JTextField keyListenerTextField = new JTextField("Press keys here");

    /**
     * Array of strings to put in JList for testing valueChanged event
     */
    private String[] stuff = {"uhh", "yea"};

    /**
     * List for testing valueChanged event
     */
    private JList list = new JList(stuff);

    /**
     * Main driver
     * @param args java terminal syntax
     */
    public static void main(String[] args) {
        new DisplayEventsGUI();
    }

    /**
     * Constructor, sets up GUI and listeners
     */
    public DisplayEventsGUI() {

        JPanel main = new JPanel();
        main.setLayout(null);

        displayEvents.setBounds(0, 0, 1000, 20);
        main.add(displayEvents);

        pressThisButton.setBounds(0, 20, 300, 20);
        pressThisButton.addActionListener(this);
        main.add(pressThisButton);

        itemStateChangedBox.setBounds(0, 40, 300, 20);
        itemStateChangedBox.addItemListener(this);
        main.add(itemStateChangedBox);

        keyListenerTextField.setBounds(0, 60, 300, 20);
        keyListenerTextField.addKeyListener(this);
        main.add(keyListenerTextField);

        list.setBounds(0, 80, 300, 40);
        list.addListSelectionListener(this);
        main.add(list);

        main.addKeyListener(this);
        main.addMouseListener(this);

        JFrame frame = new JFrame("Display Events");
        frame.add(main);
        frame.setSize(1000, 160);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    /**
     * Tests actionPerformed events such as button press in this case
     * @param e necessary event inflicted
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonText = e.getActionCommand();
        if (buttonText.equals("Press this button for actionPerformed")) {
            displayEvents.setText(e.toString());
        }
    }

    /**
     * Tests itemStateChanged events
     * @param e necessary event inflicted
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        displayEvents.setText(e.toString());
    }

    /**
     * Tests keyTyped events
     * @param e necessary event inflicted
     */
    @Override
    public void keyTyped(KeyEvent e) {
        displayEvents.setText(e.toString());
    }

    /**
     * Tests keyPressed events
     * @param e necessary event inflicted
     */
    @Override
    public void keyPressed(KeyEvent e) {
        displayEvents.setText(e.toString());
    }

    /**
     * Tests keyReleased events
     * @param e necessary event inflicted
     */
    @Override
    public void keyReleased(KeyEvent e) {
        displayEvents.setText(e.toString());
    }

    /**
     * Tests mouseClicked events
     * @param e necessary event inflicted
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        displayEvents.setText(e.toString());
    }

    /**
     * Tests mousePressed events
     * @param e necessary event inflicted
     */
    @Override
    public void mousePressed(MouseEvent e) {
        displayEvents.setText(e.toString());
    }

    /**
     * Tests mouseReleased events
     * @param e necessary event inflicted
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        displayEvents.setText(e.toString());
    }

    /**
     * Tests mouseEntered events
     * @param e necessary event inflicted
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        displayEvents.setText(e.toString());
    }

    /**
     * Tests mouseExited events
     * @param e necessary event inflicted
     */
    @Override
    public void mouseExited(MouseEvent e) {
        displayEvents.setText(e.toString());
    }

    /**
     * Tests mouseDragged events
     * @param e necessary event inflicted
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        displayEvents.setText(e.toString());
    }

    /**
     * Tests mouseMoved events
     * @param e necessary event inflicted
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        displayEvents.setText(e.toString());
    }

    /**
     * Tests valueChanged events
     * @param e necessary event inflicted
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        displayEvents.setText(e.toString());
    }

}