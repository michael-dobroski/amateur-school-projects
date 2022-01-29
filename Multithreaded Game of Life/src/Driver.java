import java.util.concurrent.*;

/**
 * Driver class contains main function to be run by user
 * @author Michael Dobroski
 */
public class Driver {

    /**
     * Main function ran by user
     * @param args java syntax
     */
    public static void main(String[] args) {

        LifeGUI GUI = new LifeGUI(); // calls GUI constructor

        // construct the shared coordinates object
        Coordinates sharedCoordinates = new Coordinates();
        GUI.update(sharedCoordinates.getLife());

        // create four tasks to write to the shared coordinates object
        CoordinatesWriter subGrid1 = new CoordinatesWriter(sharedCoordinates, GUI);
        CoordinatesWriter subGrid2 = new CoordinatesWriter(sharedCoordinates, GUI);
        CoordinatesWriter subGrid3 = new CoordinatesWriter(sharedCoordinates, GUI);
        CoordinatesWriter subGrid4 = new CoordinatesWriter(sharedCoordinates, GUI);

        // schedule each subgrid task to using executor class
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.execute(subGrid1);
        executorService.execute(subGrid2);
        executorService.execute(subGrid3);
        executorService.execute(subGrid4);

        while (true) { // won't end until break
            if (!GUI.isWindowOpen()) { // if GUI is closed
                executorService.shutdown(); // terminate threads
                try { // ensures that threads terminate in the case of an error during shutdown
                    if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                        executorService.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    executorService.shutdownNow();
                }
                break;
            }
        }

    }

}
