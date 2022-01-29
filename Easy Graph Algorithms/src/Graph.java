import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

// Found the "for (Map.Entry<String, String[]> e : adjList.entrySet())" here... https://www.geeksforgeeks.org/java-util-hashmap-in-java-with-examples/#:~:text=HashMap%20is%20a%20part%20of%20java.,implements%20Cloneable%20and%20Serializable%20interface.&text=HashMap%20allows%20null%20key%20also%20but%20only%20once%20and%20multiple%20null%20values.

/**
 * Creates an adjacency list and holds various methods for analyzing it
 */
public class Graph {

    /**
     * HashMap representing the adjacency list of words in which there's a one letter difference
     */
    private final HashMap<String, String[]> adjList;

    /**
     * Constructor for graph class, creates adjacency list for a given file of words where edges are created when there's a one letter difference
     * @param filename name of file containing list of words
     * @throws IllegalAccessException in case of read in error
     */
    public Graph(String filename) throws IllegalAccessException {

        ArrayList<String> words = new ArrayList<>(); // arraylist of every word in the file
        try {
            File orderFile = new File("oral_exam2" + File.separator + "S35_GraphAlgos_Easy" + File.separator + filename); // set up reading desired file
            Scanner scan = new Scanner(orderFile);
            while (scan.hasNextLine()) {
                String data = scan.nextLine();
                words.add(data);
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        adjList = new HashMap<>();
        for (int i=0; i<words.size(); i++) {
            ArrayList<String> edgeList = new ArrayList<>();
            for (int j=0; j<words.size(); j++) {
                if (isNeighbor(words.get(i), words.get(j))) { // if they're neighbors
                    edgeList.add(words.get(j)); // add to list of edges
                }
            }
            String[] edges = new String[edgeList.size()];
            for (int j=0; j<edgeList.size(); j++) { // make arraylist of edges into string array for hashmap
                edges[j] = edgeList.get(j);
            }
            adjList.put(words.get(i), edges);
        }

    }

    /**
     * Determines if two words are neighbors by counting number of differing characters
     * @param A first string
     * @param B second string
     * @return boolean of when difference in characters is equal to one, true for yes
     * @throws IllegalAccessException if they're not the same length
     */
    private boolean isNeighbor(String A, String B) throws IllegalAccessException {

        if (A.length() != B.length()) { // exception if different lengths
            throw new IllegalAccessException("Inputted strings need to be the same length.");
        }

        int difCount = 0;

        for (int i=0; i<A.length(); i++) { // iterate through length of a to add up everytime the characters don't match
            if(A.charAt(i) != B.charAt(i)) {
                difCount++;
            }
        }

        return difCount==1; // true if difference in characters is one AKA neighbor

    }

    /**
     * Neatly prints the entire adjacency list, used mainly in testing
     */
    public void print() {

        for (Map.Entry<String, String[]> e : adjList.entrySet()) {
            System.out.println(e.getKey() + " " + Arrays.toString(e.getValue()));
        }

    }

    /**
     * Iterates through adjacency list and returns number of vertices without any edges
     * @return number of vertices with no edges
     */
    public int numVerticesNoEdges() {

        int noEdgeCount = 0;
        for (Map.Entry<String, String[]> e : adjList.entrySet()) {
            if (e.getValue().length == 0) { // if length of String[] array associated with each key is zero
                noEdgeCount++;
            }
        }

        return noEdgeCount;

    }

    /**
     * Prints the word, its number of edges, and the edges of a node if it has more than twenty-one edges
     */
    public void mostEdges() {

        int max = 0;
        boolean first = true;
        for (Map.Entry<String, String[]> e : adjList.entrySet()) { // finds max number of edges
            if (first) {
                max = e.getValue().length;
                first = false;
            } else {
                if (e.getValue().length > max) {
                    max = e.getValue().length;
                }
            }
        }

        for (Map.Entry<String, String[]> e : adjList.entrySet()) { // prints out edges if it has length equal to max
            if (e.getValue().length == max) {
                System.out.println(e.getKey() + " with " + e.getValue().length + " edges ~> " + Arrays.toString(e.getValue()));
            }
        }

    }

    /**
     * Calculates average number of edges for a node and returns it
     * @return average number of edges for a node
     */
    public double averageEdges() {

        double totalEdges = 0;
        for (Map.Entry<String, String[]> e : adjList.entrySet()) {
            totalEdges += e.getValue().length;
        }

        return totalEdges / adjList.size();

    }

}
