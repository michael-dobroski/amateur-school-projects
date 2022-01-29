/**
 * Contains tetrahedron object and methods
 * @author Michael Dobroski
 */
public class Tetrahedron implements ThreeDimensionalShape {

    /**
     * Length of the tetrahedron, all sides are the same, assuming symmetrical
     */
    private final double length;

    /**
     * Constructor
     * @param length length of the tetrahedron
     */
    public Tetrahedron(double length) {
        this.length = length;
    }

    /**
     * Calcs volume of the tetrahedron
     * @return volume of tetrahedron
     */
    @Override
    public double getVolume() {
        return StrictMath.pow(length, 3) / (6 * StrictMath.pow(2, 1.0/2.0)); // volume eq
    }

    /**
     * Calcs surface area of the tetrahedron
     * @return surface area of tetrahedron
     */
    @Override
    public double getArea() {
        return StrictMath.pow(3, 1.0/2.0) * length * length; // surface area eq
    }

    /**
     * Prints important info about the tetrahedron in a neat manner
     */
    @Override
    public void print() {
        System.out.println("~ Tetrahedron of length " + length + "'s specs ~");
        System.out.println("Surface area: " + getArea());
        System.out.println("Volume: " + getVolume() + "\n");
    }

}