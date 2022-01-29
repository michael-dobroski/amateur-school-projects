// Fig. 28.3: Server.java
// Server portion of a client/server stream-socket connection. 

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Server side of program
 */
public class Server extends JFrame {

   private JTextField enterField; // inputs message from user
   private JTextArea displayArea; // display information to user
   private ObjectOutputStream output; // output stream to client
   private ObjectInputStream input; // input stream from client
   private ServerSocket server; // server socket
   private Socket connection; // connection to client
   private int counter = 1; // counter of number of connections

   /**
    * Set up GUI
    */
   public Server() {

      super("Server");

      enterField = new JTextField(); // create enterField
      enterField.setEditable(false);
      enterField.addActionListener(
         new ActionListener() 
         {
            // send message to client
            public void actionPerformed(ActionEvent event)
            {
               sendData(event.getActionCommand());
               enterField.setText("");
            } 
         } 
      ); 

      add(enterField, BorderLayout.NORTH);

      displayArea = new JTextArea(); // create displayArea
      add(new JScrollPane(displayArea), BorderLayout.CENTER);

      setSize(300, 300); // set size of window
      setVisible(true); // show window

   }

   /**
    * Set up and run server
    */
   public void runServer() {

      try { // set up server to receive connections; process connections

         server = new ServerSocket(23920, 100); // create ServerSocket CHANGE THIS PORT TOO

         while (true) {
            try {
               waitForConnection(); // wait for a connection
               getStreams(); // get input & output streams
               processConnection(); // process connection
            } catch (EOFException eofException) {
               displayMessage("\nServer terminated connection");
            } finally {
               closeConnection(); //  close connection
               ++counter;
            } 
         }
      } catch (IOException ioException) {
         ioException.printStackTrace();
      }

   }

   /**
    * Wait for connection to arrive, then display connection info
    * @throws IOException input output exception
    */
   private void waitForConnection() throws IOException {

      displayMessage("Waiting for connection\n");
      connection = server.accept(); // allow server to accept connection            
      displayMessage("Connection " + counter + " received from: " +
         connection.getInetAddress().getHostName());

   }

   /**
    * Get streams to send and receive data
    * @throws IOException input output exception
    */
   private void getStreams() throws IOException {

      // set up output stream for objects
      output = new ObjectOutputStream(connection.getOutputStream());
      output.flush(); // flush output buffer to send header information

      // set up input stream for objects
      input = new ObjectInputStream(connection.getInputStream());

      displayMessage("\nGot I/O streams\n");

   }

   /**
    * Process connection with client
    * @throws IOException input output exception
    */
   private void processConnection() throws IOException {

      String message = "Connection successful";
      sendData(message); // send connection successful message

      // enable enterField so server user can send messages
      setTextFieldEditable(true);

      do { // process messages sent from client
         try { // read message and display it
            if (message.equals(">>>SENDING FILE CONTENTS>>>")) {
               String currentPath = (String) input.readObject();
               PrintWriter tmp = new PrintWriter("oral_exam2" + File.separator + "28-14_ModifyFile_Medium" + File.separator + currentPath);
               tmp.print("");
               tmp.close();
               String fileContents = (String) input.readObject();
               FileWriter myWriter = new FileWriter("oral_exam2" + File.separator + "28-14_ModifyFile_Medium" + File.separator + currentPath);
               myWriter.write(fileContents);
               myWriter.close();
            }
            message = (String) input.readObject(); // read new message
            displayMessage("\nSending file '" + message + "'..."); // display message
            sendFileContents(message);
         } catch (ClassNotFoundException classNotFoundException) {
            displayMessage("\nUnknown object type received");
         } 

      } while (!message.equals("CLIENT>>> TERMINATE"));

   }

   /**
    * Sends contents of file to client
    * @param path path to file to be edited by client
    * @throws IOException input output exception
    */
   private void sendFileContents(String path) throws IOException {

      File orderFile = new File("oral_exam2" + File.separator + "28-14_ModifyFile_Medium" + File.separator + path);
      try {
         Scanner input = new Scanner(orderFile);
         output.writeObject(">>>CLEAR<<<");
         output.flush();
         while (input.hasNextLine()) {
            try {
               output.writeObject(input.nextLine());
               output.flush();
            } catch (IOException ioException) {
               displayArea.append("\nError writing object");
            }
         }
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      }

   }

   /**
    * Close streams and socket
    */
   private void closeConnection() {

      displayMessage("\nTerminating connection\n");
      setTextFieldEditable(false); // disable enterField

      try {
         output.close(); // close output stream
         input.close(); // close input stream
         connection.close(); // close socket
      } catch (IOException ioException) {
         ioException.printStackTrace();
      }

   }

   /**
    * Send message to client
    * @param message message
    */
   private void sendData(String message) {

      try { // send object to client
         output.writeObject("SERVER>>> " + message);
         output.flush(); // flush output to client
         displayMessage("\nSERVER>>> " + message);
      } catch (IOException ioException) {
         displayArea.append("\nError writing object");
      }

   }

   /**
    * Manipulates displayArea in the event-dispatch thread
    * @param messageToDisplay message to be displayed server side
    */
   private void displayMessage(final String messageToDisplay) {

      SwingUtilities.invokeLater(
         new Runnable() 
         {
            public void run() // updates displayArea
            {
               displayArea.append(messageToDisplay); // append message
            } 
         } 
      );

   }

   /**
    * Manipulates enterField in the event-dispatch thread
    * @param editable true for editable, false for not
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