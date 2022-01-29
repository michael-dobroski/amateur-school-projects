import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Client side of program
 */
public class Client extends JFrame {

   /**
    * Enters information from user
    */
   private JTextField enterField;

   /**
    * Display informatio to user
    */
   private JTextArea displayArea;

   /**
    * Saves changes of file being edited
    */
   private JButton saveChanges;

   /**
    * Output stream to server
    */
   private ObjectOutputStream output;

   /**
    * Input stream from server
    */
   private ObjectInputStream input;

   /**
    * Message from server
    */
   private String message = "";

   /**
    * Host server for this application
    */
   private String chatServer;

   /**
    * Socket to communicate with server
    */
   private Socket client;

   /**
    * Path to file currently being edited
    */
   private String currentPath;

   /**
    * Initialize file editing server and create GUI
    * @param host name of server host
    */
   public Client(String host) {

      super("Client");

      chatServer = host; // set server to which this client connects

      enterField = new JTextField(); // create enterField
      enterField.setEditable(false);
      enterField.addActionListener(new ActionListener() {
            // send message to server
            public void actionPerformed(ActionEvent event)
            {
               sendPath(event.getActionCommand());
               enterField.setText("");
            } 
         } 
      ); 

      add(enterField, BorderLayout.NORTH);

      displayArea = new JTextArea(); // create displayArea
      add(new JScrollPane(displayArea), BorderLayout.CENTER);

      saveChanges = new JButton();
      saveChanges.setText("Save Changes");
      saveChanges.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            sendFileContents();
         }
      });
      add(saveChanges, BorderLayout.SOUTH);

      setSize(500, 1000); // set size of window
      setVisible(true); // show window

   }

   /**
    * Connect to server and process messages from server
    */
   public void runClient() {

      try // connect to server, get streams, process connection
      {
         connectToServer(); // create a Socket to make connection
         getStreams(); // get the input and output streams
         processConnection(); // process connection
      } 
      catch (EOFException eofException) 
      {
         displayMessage("\nClient terminated connection");
      } 
      catch (IOException ioException) 
      {
         ioException.printStackTrace();
      } 
      finally 
      {
         closeConnection(); // close connection
      }

   }

   /**
    * Connect to server
    * @throws IOException input output exception
    */
   private void connectToServer() throws IOException {

      displayMessage("Attempting connection\n");

      // create Socket to make connection to server
      client = new Socket(InetAddress.getByName(chatServer), 23920); // CHANGE THIS PORT

      // display connection information
      displayMessage("Connected to: " + 
         client.getInetAddress().getHostName());

   }

   /**
    * Get streams to send and receive data
    * @throws IOException input output exception
    */
   private void getStreams() throws IOException {

      // set up output stream for objects
      output = new ObjectOutputStream(client.getOutputStream());      
      output.flush(); // flush output buffer to send header information

      // set up input stream for objects
      input = new ObjectInputStream(client.getInputStream());

      displayMessage("\nGot I/O streams\n");

   }

   /**
    * Process connection with server
    * @throws IOException input output exception
    */
   private void processConnection() throws IOException {

      // enable enterField so client user can send messages
      setTextFieldEditable(true);

      do { // process messages sent from server
         try { // read message and display it
            message = (String) input.readObject(); // read new message
            if (message.equals(">>>CLEAR<<<")) {
               displayArea.setText(""); // clears displayArea
            } else {
               displayMessage("\n" + message); // display message
            }
         } catch (ClassNotFoundException classNotFoundException) {
            displayMessage("\nUnknown object type received");
         } 

      } while (!message.equals("SERVER>>> TERMINATE"));

   }

   /**
    * Close streams and socket
    */
   private void closeConnection() {

      displayMessage("\nClosing connection");
      setTextFieldEditable(false); // disable enterField

      try {
         output.close(); // close output stream
         input.close(); // close input stream
         client.close(); // close socket
      } catch (IOException ioException) {
         ioException.printStackTrace();
      }

   }

   /**
    * Send path of edited file to server
    * @param path path of file being edited
    */
   private void sendPath(String path) {

      currentPath = path;

      try {
         output.writeObject(path);
         output.flush();
         displayMessage("\nAttempting to access file '" + path + "'...");
      } catch (IOException ioException) {
         displayArea.append("\nError writing object.");
      }

   }

   /**
    * Sends contents of file being edited to server to be saved be server
    */
   private void sendFileContents() {

      try {
         output.writeObject(">>>SENDING FILE CONTENTS>>>");
         output.flush();
         output.writeObject(currentPath);
         output.flush();
//         String[] splits = displayArea.getText().split("\n");
//         System.out.println(Arrays.toString(splits));
         output.writeObject(displayArea.getText());
         output.flush();
         displayArea.setText("");
      } catch (IOException ioException) {
         ioException.printStackTrace();
      }

   }

   /**
    * Manipulates displayArea in the event-dispatch thread
    * @param messageToDisplay message to be displayed
    */
   private void displayMessage(final String messageToDisplay) {

      SwingUtilities.invokeLater(
         new Runnable()
         {
            public void run() // updates displayArea
            {
               displayArea.append(messageToDisplay);
            }
         }  
      );

   }

   /**
    * Manipulates enterField in the event-dispatch thread
    * @param editable true for editable, false for not editable
    */
   private void setTextFieldEditable(final boolean editable) {

      SwingUtilities.invokeLater(
         new Runnable() 
         {
            public void run() // sets enterField's editability
            {
               enterField.setEditable(editable);
            }
         } 
      );

   } 
}