/**
 * Driver class for testing Graph class
 * @author Michael Dobroski
 */
public class GraphDriver {

    /**
     * Main method for driver class
     * @param args java syntax for running in terminal with parameters
     * @throws IllegalAccessException in case of different sized strings in hasNeighbor()
     */
    public static void main(String[] args) throws IllegalAccessException {

        Graph test = new Graph("words.dat");

        // test.print();

        System.out.println("The number of nodes with no edges ~> " + test.numVerticesNoEdges() + "\n");

        System.out.println("The node(s) with the most amount of edges include...");
        test.mostEdges();

        System.out.println("\nThe average number of edges per node ~> " + test.averageEdges());

    }

}
