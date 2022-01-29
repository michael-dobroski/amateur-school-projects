/**
 * Shape superclass that every other class inherits from
 * @author Michael Dobroski
 */
public interface Shape {

    /**
     * Pi constant for various calculations
     */
    public final double PI = 3.141592653589793238;

    /**
     * Get area function that every shape will implement
     * @return area of two dimensional shape and surface area of three dimensional shape
     */
    public double getArea();

    /**
     * Prints all important info about respective object
     */
    public void print();

}