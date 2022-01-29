/**
 * Contains sphere object and methods
 * @author Michael Dobroski
 */
public class Sphere implements ThreeDimensionalShape {

    /**
     * Radius of the sphere
     */
    private final double radius;

    /**
     * Constructor
     * @param radius radius of the sphere
     */
    public Sphere(double radius) {
        this.radius = radius;
    }

    /**
     * Calcs volume of the sphere
     * @return volume of sphere
     */
    @Override
    public double getVolume() {
        return getArea() * (1.0 / 3.0) * radius; // volume eq
    }

    /**
     * Calcs surface area of the sphere
     * @return surface area of the sphere
     */
    @Override
    public double getArea() {
        return 4 * PI * (radius * radius); // surface area eq
    }

    /**
     * Prints important info about the sphere in a neat manner
     */
    @Override
    public void print() {
        System.out.println("~ Sphere of radius " + radius + "'s specs ~");
        System.out.println("Surface area: " + getArea());
        System.out.println("Volume: " + getVolume() + "\n");
    }

}