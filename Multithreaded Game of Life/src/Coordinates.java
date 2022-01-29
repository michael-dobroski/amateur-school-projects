import java.util.Arrays;

/**
 * Class that manages a boolean array to be shared by multiple threads with synchronization
 * @author Michael Dobroski
 */
public class Coordinates {

    /**
     * Current configuration of alive and dead cells
     */
    private boolean[][] life = new boolean[64][64];

    /**
     * 0-3, rotates through threads and determines which subgrid to perform on
     */
    private int subGrid = 0;

    /**
     * Copy of life, so that new generation is a function of only the last
     */
    private final boolean[][] copy = new boolean[64][64];

    /**
     * Constructor, also creates first copy
     */
    public Coordinates() { for (int k=0; k<64; k++) { copy[k] = Arrays.copyOf(life[k], life[k].length); } }

    /**
     * Returns current configuration of alive and dead cells
     * @return current config
     */
    public boolean[][] getLife() { return life; }

    /**
     * Setter for life config
     * @param life config
     */
    public void setLife(boolean[][] life) {
        this.life = life;
        subGrid = 0;
        for (int k=0; k<64; k++) { copy[k] = Arrays.copyOf(life[k], life[k].length); }
    }

    /**
     * Logic of actual game
     */
    synchronized void updateSubGrid() {

        // assign and keep track of subgrid for corresponding thread
        int i; int j;
        if (subGrid == 0) { i = 0; j = 0; } // top left subgrid
        else if (subGrid == 1) { i = 32; j = 0; } // top right subgrid
        else if (subGrid == 2) { i = 0; j = 32; } // bottom left subgrid
        else { i = 32; j = 32; } // bottom right subgrid

        // main game logic
        for (int k=i; k<32+i; k++) {
            for (int n=j; n<32+j; n++) {
                if (copy[k][n]) { // cell is alive
                    if ((liveNeighbors(copy, k, n) < 2) || (liveNeighbors(copy, k, n) > 3)) { // underpopulation and overpopulation
                        life[k][n] = false;
                    }
                } else if (liveNeighbors(copy, k, n) == 3){ // reproduction
                    life[k][n] = true;
                }
            }
        }

        // rotate through sub grids and create new copy after fourth subgrid
        if (subGrid == 3) {
            subGrid = 0;
            for (int k=0; k<64; k++) { copy[k] = Arrays.copyOf(life[k], life[k].length); }
        } else { subGrid++; }

    }

    /**
     * Calculates number of alive neighbors in grid
     * @param cells last generation's configuration
     * @param i x axis index
     * @param j y axis index
     * @return number of neighbors
     */
    private int liveNeighbors(boolean[][] cells, int i, int j) {
        int count = 0;
        if (j+1>=0 && j+1<=63 && cells[i][j+1]) { count++; }
        if (i+1>=0 && i+1<=63 && j+1>=0 && j+1<=63 && cells[i+1][j+1]) { count++; }
        if (i+1>=0 && i+1<=63 && cells[i+1][j]) { count++; }
        if (i+1>=0 && i+1<=63 && j-1>=0 && j-1<=63 && cells[i+1][j-1]) { count++; }
        if (j-1>=0 && j-1<=63 && cells[i][j-1]) { count++; }
        if (i-1>=0 && i-1<=63 && j-1>=0 && j-1<=63 && cells[i-1][j-1]) { count++; }
        if (i-1>=0 && i-1<=63 && cells[i-1][j]) { count++; }
        if (i-1>=0 && i-1<=63 && j+1<=63 && cells[i-1][j+1]) { count++; }
        return count;
    }

}
