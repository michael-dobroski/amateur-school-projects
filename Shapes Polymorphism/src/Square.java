/**
 * Contains square object and methods
 * @author Michael Dobroski
 */
public class Square implements TwoDimensionalShape {

    /**
     * Width of the square
     */
    private final double width;

    /**
     * Constructor
     * @param width width of the square
     */
    public Square(double width) {
        this.width = width;
    }

    /**
     * Calcs area of the square
     * @return area of the square
     */
    @Override
    public double getArea() {
        return width * width; // area eq
    }

    /**
     * Prints important info about the square in a neat manner
     */
    @Override
    public void print() {
        System.out.println("~ Square of width " + width + "'s specs ~");
        System.out.println("Area: " + getArea() + "\n");
    }

}