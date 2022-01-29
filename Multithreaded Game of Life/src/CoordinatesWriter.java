import java.lang.Runnable;

/**
 * CoordinatesWriter object implements Runnable interface in order to use run() command and be used by Executor
 * @author Michael Dobroski
 */
public class CoordinatesWriter implements Runnable {

    /**
     * Shared coordinates object to be used in synchronization with other threads
     */
    private final Coordinates sharedCoordinates;

    /**
     * LifeGUI object assigned in constructor to be used in run() method
     */
    private final LifeGUI GUI;

    /**
     * Constructor for Runnable subclass
     * @param sharedCoordinates the shared coordinates object between threads
     * @param GUI the LifeGUI used
     */
    public CoordinatesWriter(Coordinates sharedCoordinates, LifeGUI GUI) {
        this.sharedCoordinates = sharedCoordinates; // keep track of shared coordinates object
        this.GUI = GUI; // keep track of GUI for updating
    }

    /**
     * run() method accessed by executor
     */
    public void run() {

        while (true) {
            try {
                Thread.sleep(GUI.getTickSpeed());
                sharedCoordinates.updateSubGrid(); // updates grid subgrid by subgrid, that's why we did four threads
                GUI.update(sharedCoordinates.getLife()); // update GUI
                if (GUI.configChange()) { // if config is changed through radio buttons
                    sharedCoordinates.setLife(GUI.getConfig());
                    GUI.update(GUI.getConfig()); // so that there's no possible error in switching seeds
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
