/**
 * Contains cube object and methods
 * @author Michael Dobroski
 */
public class Cube implements ThreeDimensionalShape {

    /**
     * Length of the cube, all lengths are the same b/c it's a cube
     */
    private final double length;

    /**
     * Constructor
     * @param length length of the cube
     */
    public Cube(double length) {
        this.length = length;
    }

    /**
     * Calcs volume of the cube
     * @return volume of cube
     */
    @Override
    public double getVolume() {
        return length * length * length; // volume eq
    }

    /**
     * Calcs surface area of the cube
     * @return surface area of cube
     */
    @Override
    public double getArea() {
        return 6 * length * length; // surface area eq
    }

    /**
     * Prints important info about the cube in a neat manner
     */
    @Override
    public void print() {
        System.out.println("~ Cube of length " + length + "'s specs ~");
        System.out.println("Surface area: " + getArea());
        System.out.println("Volume: " + getVolume() + "\n");
    }

}