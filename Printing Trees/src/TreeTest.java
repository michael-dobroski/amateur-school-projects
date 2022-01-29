import java.security.SecureRandom;

/**
 * Contains main for the project
 */
public class TreeTest {

    /**
     * Main function
     * @param args java terminal syntax
     */
    public static void main(String[] args) {

        Tree<Integer> tree = new Tree<>();
        SecureRandom randomNumber = new SecureRandom();

        System.out.println("Inserting the following values: ");

        // insert 10 random integers from 0-99 in tree
        for (int i = 1; i <= 10; i++) {
            int value = randomNumber.nextInt(100);
            System.out.printf("%d ", value);
            tree.insertNode(value);
        }

        System.out.println();
        tree.printTree();
        System.out.println();

    }

}
