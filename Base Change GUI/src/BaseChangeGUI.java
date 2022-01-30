import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

// JLabel subscripts using HTML https://stackoverflow.com/questions/26671535/subscript-in-jlabel

/**
 * our GUI class, handles everything base change related and shows it to user
 * @author Michael Dobroski
 */
public class BaseChangeGUI implements ActionListener {

    /**
     * text field for user to enter number that will be base changed
     */
    private JTextField numberField;

    /**
     * text field for user to enter current base of number previously entered
     */
    private JTextField currentBaseField;

    /**
     * text field for user to enter desired base of output number
     */
    private JTextField desiredBaseField;

    /**
     * label that displays to user the outcome of their base change
     */
    private JLabel outputLabel;

    /**
     * method accessed by driver class in order to call GUI constructor
     */
    public static void makeGUI() { new BaseChangeGUI(); }

    /**
     * GUI constructor, initializes all of the elements of the GUI and launches it
     */
    private BaseChangeGUI() {

        JPanel main = new JPanel();
        main.setLayout(null); // I want to create my own layout

        // indicate to user where to type in number to be base changed
        JLabel numberLabel = new JLabel("Number");
        numberLabel.setBounds(10,20,80,25);
        main.add(numberLabel);

        // where user enters number to be base changed
        numberField = new JTextField(20);
        numberField.setBounds(120,20,165,25);
        main.add(numberField);

        // indicate to user where to type in current base
        JLabel currentBaseLabel = new JLabel("Current Base");
        currentBaseLabel.setBounds(10,50,150,25);
        main.add(currentBaseLabel);

        // where user enters current base
        currentBaseField = new JTextField(20);
        currentBaseField.setBounds(120,50,165,25);
        main.add(currentBaseField);

        // indicate to user where to type in desired base
        JLabel desiredBaseLabel = new JLabel("Desired Base");
        desiredBaseLabel.setBounds(10,80,150,25);
        main.add(desiredBaseLabel);

        // where user enters desired base
        desiredBaseField = new JTextField(20);
        desiredBaseField.setBounds(120,80,165,25);
        main.add(desiredBaseField);

        // takes info from text fields and displays to user the output
        JButton convertButton = new JButton("Convert");
        convertButton.addActionListener(this);
        convertButton.setBounds(300,50,100,25);
        main.add(convertButton);

        // where the output will be displayed
        outputLabel = new JLabel();
        outputLabel.setBounds(420, 50, 500, 25);
        main.add(outputLabel);

        JFrame frame = new JFrame("Base Change");
        frame.add(main);
        frame.setSize(675,165);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    /**
     * listener method for convert button, implemented from listener interface
     * @param e for listener interface's sake, code is hidden to us so no need to worry about here
     */
    public void actionPerformed(ActionEvent e) {
        if (Integer.parseInt(currentBaseField.getText()) > 32) { // display to user that current base is too large
            outputLabel.setText("< CURRENT BASE TOO LARGE >");
        } else if (Integer.parseInt(currentBaseField.getText()) < 2) { // display to user that current base is too small
            outputLabel.setText("< CURRENT BASE TOO SMALL >");
        } else if (Integer.parseInt(desiredBaseField.getText()) > 32) { // display to user that desired base is too large
            outputLabel.setText("< DESIRED BASE TOO LARGE >");
        } else if (Integer.parseInt(desiredBaseField.getText()) < 2) { // display to user that desired base is too small
            outputLabel.setText("< DESIRED BASE TOO SMALL >");
        } else if (!BaseChange.isValid(numberField.getText(), Integer.parseInt(currentBaseField.getText()))) {
            outputLabel.setText("< INVALID NUMBER/BASE COMBO >");
        } else if (BaseChange.anyBaseToDecimal(numberField.getText(), Integer.parseInt(currentBaseField.getText())).equals("2147483647")) { // 2147483647 is int cap, display to user to decrease their input if this is true
            outputLabel.setText("< INPUT TOO LARGE >");
        } else {
            String output = BaseChange.decimalToAnyBase(Integer.parseInt(BaseChange.anyBaseToDecimal(numberField.getText(), Integer.parseInt(currentBaseField.getText()))), Integer.parseInt(desiredBaseField.getText())); // turn into decimal first, then switch to desired base
            outputLabel.setText("<html> ("+numberField.getText()+")<sub>"+currentBaseField.getText()+"</sub> = ("+output+")<sub>"+desiredBaseField.getText()+"</sub> </html>"); // neatly set output label to display result, check documentation for HTML subscripts
        }
    }
}