/**
 * Class containing main driver of the project
 * @author Michael Dobroski
 */
public class ShapeDriver {

    /**
     * Main driver of the project
     * @param args java main syntax
     */
    public static void main(String[] args) {

        Shape[] shapeArray = new Shape[6];
        shapeArray[0] = new Circle(2.46);
        shapeArray[1] = new Square(8.94);
        shapeArray[2] = new Triangle(8.37, 4.84);
        shapeArray[3] = new Sphere(6.36);
        shapeArray[4] = new Cube(13.11);
        shapeArray[5] = new Tetrahedron(9.49);

        for (Shape currShape : shapeArray) { // iterate through array of type shape
            currShape.print(); // will call corresponding overridden print method
        }

    }

}