/**
 * Tree class definition, contains tree as a whole
 * @param <T>
 */
public class Tree<T extends Comparable<T>> {

    /**
     * Root of the tree
     */
    private TreeNode root;

    /**
     * Used by printTree to keep track of spaces to be printed
     */
    private int totalSpaces = 0;

    /**
     * Constructor initializes an empty Tree of integers
     */
    public Tree() {
        root = null;
    }

    /**
     * Insert a new node in the binary search tree
     * @param insertValue value of node to be inserted
     */
    public void insertNode(T insertValue) {
        if (root == null) {
            root = new TreeNode<T>(insertValue); // create root node
        } else {
            root.insert(insertValue); // call the insert method
        }
    }

    /**
     * Called by driver to print tree, uses printTreeHelper
     */
    public void printTree() {
        printTreeHelper(root, totalSpaces);
    }

    /**
     * Actaully does the printing of the tree
     * @param node current node for recursive process
     * @param totalSpaces spaces to print for neatly displaying tree
     */
    private void printTreeHelper(TreeNode<T> node, int totalSpaces) {

//      While the reference to the current node is not null, perform the following:
//      Recursively call outputTree with the right subtree of the current node and
//          totalSpaces + 5.
//      Use a for statement to count from 1 to totalSpaces and output spaces.
//      Output the value in the current node.
//      Set the reference to the current node to refer to the left subtree of the current node.
//      Increment totalSpaces by 5.

        if (node == null) {
            return;
        }

        printTreeHelper(node.rightNode, totalSpaces + 5);

        for (int i = 0; i < totalSpaces; i++) {
            System.out.print(" ");
        }

        System.out.println(node.data);

        printTreeHelper(node.leftNode, totalSpaces + 5);

    }

}