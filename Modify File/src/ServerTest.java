// Fig. 28.4: ServerTest.java
// Test the Server application.

import javax.swing.*;

/**
 * Driver for server side of program
 */
public class ServerTest {

   /**
    * Main that launches GUI
    * @param args java syntax, used in terminal to specify IP if not localhost
    */
   public static void main(String[] args) {

      Server application = new Server(); // create server
      application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      application.runServer(); // run server application

   }

}