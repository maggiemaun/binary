/*
 * Maggie Maun
 * ICS4U
 * Main.java
 * Binary Trees
 *  This program contains a data structure in which a record is linked to two successor records,
 *  referred to as the left branch when greater and the right when less than the previous record,
 *  and other implementation methods using the new data structure.
 */

public class Main {
    public static void main(String[] args) {
        //Binary Tree Tester
        BTree treez = new BTree();
        treez.add(52);
        treez.add(73);
        treez.add(40);
        treez.add(20);
        treez.add(61);
        treez.add(31);
        treez.add(9);
        //PRINT
        System.out.println(treez);
        System.out.println(treez.toString());
        //FIND
        System.out.println(treez.find(20).getVal());
        //DEPTH
        System.out.println(treez.depth(9));
        //DISPLAY
        treez.display(BTree.IN);
        treez.display(BTree.PRE);
        treez.display(BTree.POST);
        //COUNTLEAVES
        System.out.println(treez.countLeaves());
        //HEIGHT
        System.out.println(treez.height());
        //ISANCESTOR
        System.out.println(treez.isAncestor(73, 61));
        //DELETE
        treez.delete(61);
        System.out.println(treez);
        //ISBALANCED
        System.out.println(treez.isBalanced());
        //ADD
        BNode addTest = new BNode(7);
        treez.add(addTest);
        BTree treeTest = new BTree();
        treeTest.add(treez);
        treeTest.delete(7);
        System.out.println(treeTest);
        System.out.println(treez);
    }
}

class BTree {
    //The integers of display are defined as constants in the BTree class
    public static final int IN = 0;
    public static final int PRE = 1;
    public static final int POST = 2;
    private BNode root; //The top node in a tree

    //Constructor for the class
    public BTree() {
        root = null;
    }

    // HELPERS
    //ADD - adds a new BNode to the BTree.
    public void add(int v) {
        if (root == null) { //If the BTree is empty, creates new BNode object.
            root = new BNode(v);
        } else {
            add(v, root);
        }
    }

    public void add(int v, BNode branch) {
        if (v < branch.getVal()) { //If the value added is less than the branch's value
            if (branch.getLeft() == null) { //it goes to the left side of the branch
                branch.setLeft(new BNode(v)); //if it is empty, it will set the left side to be the new BNode
            } else {
                add(v, branch.getLeft()); //otherwise, it calls the function until you've reached an open spot
            }
        } else if (v > branch.getVal()) { //Same as above, instead if the value is greater it goes to the right.
            if (branch.getRight() == null) {
                branch.setRight(new BNode(v));
            } else {
                add(v, branch.getRight());
            }
        }
    }

    //TOSTRING - Creates a string that contains all the BNode objects so that it can be represented simply.
    public String toString() {
        return toString(root);
    } //takes in reference to the root

    public String toString(BNode branch) {
        if (branch == null) {
            return ""; //If empty, returns an empty string.
        } else {
            return toString(branch.getLeft()) + branch.getVal() + " " + toString(branch.getRight()); //Otherwise gives the string in sorted order.
        }
    }

    //FIND - Finds a value within the BTree and returns the BNode object.
    public BNode find(int v) {
        return find(v, root);
    }

    public BNode find(int v, BNode branch) {
        if (branch == null || v == branch.getVal()) {
            return branch; //returns the value if empty/not in the BTree or if the value is reached
        } else {
            if (v < branch.getVal()) { //if the number is smaller than the branch's value, the found value will be to the left
                return find(v, branch.getLeft());
            } else { //if the number is larger than the branch's value, the found value will be to the right
                return find(v, branch.getRight());
            }
        }
    }

    //ASSIGNMENT ----
    //DEPTH - Takes an integer and returns the depth where integer is found. Returns -1 if not found
    public int depth(int v) {
        if (find(v) == null) { //if integer is not found, returns -1
            return -1;
        } else { //otherwise, runs the program
            return depth(v, root, 1);
        }
    }

    public int depth(int v, BNode branch, int count) {
        //Base Case: if the branch's value is equal to the inserted value
        if (branch.getVal() == v || branch.getVal() == v) {
            return count; //returns the counter
        } else {
            count++; //otherwise, counter's value increases
            if (branch.getVal() > v) {
                return depth(v, branch.getLeft(), count); //goes left if the branch's value is greater than v
            } else if (branch.getVal() < v) {
                return depth(v, branch.getRight(), count); //goes right if the branch's value is less than v
            }
        }
        return count; //returns count
    }

    //DISPLAY - Prints all elements in the tree.
    // It is overloaded to take either no parameters or a single integer.
    // Constants for parameters are IN, PRE and POST and control if the tree is to be displayed in-order, pre-order or post-order.
    // If no parameter is specified then display the tree in-order.
    public void display(int order) {
        display(order, root); //calls the recursive function
        System.out.println();
    }

    public void display() { //overloaded function; if no parameter is specified, the tree is displayed in-order
        display(IN);
    }

    public void display(int order, BNode branch) {
        if (branch == null) { //terminates the method if branch becomes null
            return;
        }
        switch (order) {
            case (IN): //Traverses the left subtree first, then the root, then the right subtree
                display(IN, branch.getLeft());
                System.out.print(branch.getVal() + " ");
                display(IN, branch.getRight());
                break;
            case (PRE): //Visits the root, then traverses to the left and right subtrees
                System.out.print(branch.getVal() + " ");
                display(PRE, branch.getLeft());
                display(PRE, branch.getRight());
                break;
            case (POST): //Traverses the left and right subtrees, then visits the root
                display(POST, branch.getLeft());
                display(POST, branch.getRight());
                System.out.print(branch.getVal() + " ");
                break;
            default: //if anything else is specified, it just takes it inorder
                display(IN, branch.getLeft());
                System.out.print(branch.getVal() + " ");
                display(IN, branch.getRight());
                break;
        }
    }

    //COUNTLEAVES - Returns the number of leaves in the tree.
    public int countLeaves() {
        return countLeaves(root); //calls the function
    }

    public int countLeaves(BNode branch) {
        if (branch == null) {
            return 0;
        }  //in an empty tree, returns 0
        if (branch.getRight() == null && branch.getLeft() == null) {
            return 1;
        } //gives 1 if the right and left are null, since it's a leaf
        else {
            return countLeaves(branch.getLeft()) + countLeaves(branch.getRight());
        } //adds the sum of the left and right branches
    }

    //HEIGHT - Returns the height of the tree.
    public int height() {
        if (root != null) {
            return height(root); //calls the recursive function as long as the tree is not empty
        } else {
            return -1;
        } //if there is an empty tree, returns 0.
    }

    public int height(BNode branch) {
        if (branch == null) return 0; //when the branch is null, gives 0.
        return 1 + Math.max(height(branch.getLeft()), height(branch.getRight())); //otherwise, finds the max distance away from the root and takes the largest distance (whether it is Left or Right side)
    }

    //ISANCESTOR - Takes two integers and tells if the first integer is an ancestor of the second integer.
    public boolean isAncestor(int ancestor, int descendant) {
        return (find(descendant, find(ancestor)) != null); //finds if the descendant comes after ancestor
    }

    //DELETE - Takes an integer as the parameter and removes the node from the tree.
    public void delete(int v) {
        if (root != null) { //only runs if there are items in the tree
            if (v == root.getVal()) { //if you delete the root, it automatically takes the left side
                BNode new_Node = root.getRight();
                root = root.getLeft();
                add(new_Node);
            } else {
                delete(v, root); //if not the root, runs the normal function
            }
        }
    }

    public void delete(int v, BNode branch) {
        BNode twig = null; //sets a temporary BNode to null
        //Tree Traversal where branch will always point to the parent of twig
        if (v < branch.getVal()) {
            twig = branch.getLeft();
        } else if (v > branch.getVal()) {
            twig = branch.getRight();
        }
        if (twig != null) {
            if (twig.getVal() == v) { //twig finds the value
                if (twig.getVal() < branch.getVal()) { //if the child is less than the parent
                    //if there are no children
                    if (twig.getLeft() == null && twig.getRight() == null) {
                        branch.setLeft(null); //just sets the left(where the child is) to null
                    }
                    //if there is a single child being pointed to
                    else if (twig.getLeft() == null && twig.getRight() != null) {
                        branch.setLeft(twig.getRight()); //parent instead points to the grandchild of twig
                    } else if (twig.getLeft() != null && twig.getRight() == null) {
                        branch.setLeft(twig.getLeft()); //parent instead points to the grandchild of twig
                    }
                    //if the parent has two children
                    else if (twig.getLeft() != null && twig.getRight() != null) {
                        branch.setLeft(twig.getLeft()); //automatically chooses the left grandchild
                        add(twig.getRight()); //re-adds the right grandchild into the tree
                    }
                }
                if (twig.getVal() > branch.getVal()) { //if the child is larger than the parent
                    //if there are no children
                    if (twig.getLeft() == null && twig.getRight() == null) {
                        branch.setRight(null);
                    }
                    //if there is a single child being pointed to
                    else if (twig.getLeft() == null && twig.getRight() != null) {
                        branch.setRight(twig.getRight());
                    } else if (twig.getLeft() != null && twig.getRight() == null) {
                        branch.setRight(twig.getLeft());
                    }
                    //if the parent has two children
                    else if (twig.getLeft() != null && twig.getRight() != null) {
                        branch.setRight(twig.getLeft());
                        add(twig.getRight());
                    }
                }
            } else {
                delete(v, twig); //otherwise, if twig did not find the value, traverses through the tree
            }
        }
    }

    //ISBALANCED - Returns true if the tree is balanced and false otherwise.
    public boolean isBalanced() {
        return isBalanced(root);
    }

    public boolean isBalanced(BNode branch) {
        if (branch == null) return true; //an empty branch will return true
        //finds the left and right height of subtrees and finds the absolute value difference between them and returns true if the difference is less than or equal to 1, and if both sides are balanced
        return (Math.abs((height(branch.getLeft())) - (height(branch.getRight())))) <= 1 && isBalanced(branch.getLeft()) && isBalanced(branch.getRight());
    }

    //ADD - Overloads add.
    public void add(BNode branch) { //adds a BNode
        if (branch == null) { //terminates the method if branch is empty
            return;
        }
        //otherwise, adds the value, the left, and the right to the tree
        add(branch.getVal());
        add(branch.getLeft());
        add(branch.getRight());
    }

    public void add(BTree tree) { //Adds copies of all of the nodes of the BTree parameter to the current tree.
        add(tree.root); //adds the tree at its root to the new tree.
    }
}

//THE BNODE CLASS ----
//Constructed by having a tree node structure which contains a value and references to its left child and its right child
class BNode {
    private int val;
    private BNode left, right;

    //Constructor for the class
    public BNode(int v) {
        val = v;
        left = null;
        right = null;
    }

    //Setter/getter methods
    public int getVal() {
        return val;
    }

    public void setVal(int n) {
        val = n;
    }

    public BNode getRight() {
        return right;
    }

    public void setRight(BNode n) {
        right = n;
    }

    public BNode getLeft() {
        return left;
    }

    public void setLeft(BNode n) {
        left = n;
    }
}