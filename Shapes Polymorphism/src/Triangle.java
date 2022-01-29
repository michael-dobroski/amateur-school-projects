/**
 * Contains triangle object and methods
 * @author Michael Dobroski
 */
public class Triangle implements TwoDimensionalShape {

    /**
     * Length of the triangle (x axis)
     */
    private final double length;

    /**
     * Height of the triangle (y axis)
     */
    private final double height;

    /**
     * Constructor
     * @param length length of triangle
     * @param height height of triangle
     */
    public Triangle(double length, double height) {
        this.length = length;
        this.height = height;
    }

    /**
     * Calcs area of the triangle
     * @return area of triangle
     */
    @Override
    public double getArea() {
        return 0.5 * length * height; // area eq
    }

    /**
     * Prints important info about the triangle in a neat manner
     */
    @Override
    public void print() {
        System.out.println("~ Triangle of length " + length + " and height " + height + "'s specs ~");
        System.out.println("Area: " + getArea() + "\n");
    }

}