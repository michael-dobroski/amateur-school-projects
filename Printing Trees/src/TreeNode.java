/**
 * TreeNode class contains info on the node of a tree
 * @param <T>
 */
class TreeNode<T extends Comparable<T>> {

    // package access members
    TreeNode<T> leftNode;
    T data;
    TreeNode<T> rightNode;

    /**
     * Constructor initializes data and makes this a leaf node
     * @param nodeData number contained in node
     */
    public TreeNode(T nodeData) {
        data = nodeData;
        leftNode = rightNode = null; // node has no children
    }

    /**
     * Locate insertion point and insert new node; ignore duplicate values
     * @param insertValue value to be inserted
     */
    public void insert(T insertValue) {

        // insert in left subtree
        if (insertValue.compareTo(data) < 0) {

            if (leftNode == null) {
                leftNode = new TreeNode<T>(insertValue);
            } else {
                leftNode.insert(insertValue);
            }

        } else if (insertValue.compareTo(data) > 0) {

            if (rightNode == null) {
                rightNode = new TreeNode<T>(insertValue);
            } else {
                rightNode.insert(insertValue);
            }

        }

    }

}