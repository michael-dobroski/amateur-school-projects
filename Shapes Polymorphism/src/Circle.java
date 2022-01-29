/**
 * Contains circle object and methods
 * @author Michael Dobroski
 */
public class Circle implements TwoDimensionalShape {

    /**
     * Radius of the circle
     */
    private final double radius;

    /**
     * Constructor
     * @param radius radius of teh circle
     */
    public Circle(double radius) {
        this.radius = radius;
    }

    /**
     * Calculates area of circle
     * @return area of the circle
     */
    @Override
    public double getArea() {
        return PI * (radius * radius); // area eq
    }

    /**
     * Prints important info about the circle in a neat manner
     */
    @Override
    public void print() {
        System.out.println("~ Circle of radius " + radius + "'s specs ~");
        System.out.println("Area: " + getArea() + "\n");
    }

}