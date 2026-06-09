import java.util.ArrayList;
import java.util.List;

class BPlusNode {
    boolean isLeaf;
    List<Integer> keys;
    List<BPlusNode> children;
    BPlusNode parent;
    BPlusNode next;

    BPlusNode(boolean isLeaf) {
        this.isLeaf = isLeaf;
        keys = new ArrayList<>();
        children = new ArrayList<>();
        parent = null;
        next = null;
    }
}

public class FlipkartProductSearchBPlusTree {

    private static final int ORDER = 3;
    private BPlusNode root;

    public FlipkartProductSearchBPlusTree() {
        root = new BPlusNode(true);
    }

    public void insert(int key) {

        if (search(key))
            return;

        BPlusNode leaf = findLeaf(root, key);

        int i = 0;
        while (i < leaf.keys.size() && key > leaf.keys.get(i))
            i++;

        leaf.keys.add(i, key);

        if (leaf.keys.size() == ORDER)
            splitLeaf(leaf);
    }

    private BPlusNode findLeaf(BPlusNode node, int key) {

        if (node.isLeaf)
            return node;

        int i = 0;
        while (i < node.keys.size() && key >= node.keys.get(i))
            i++;

        return findLeaf(node.children.get(i), key);
    }

    private void splitLeaf(BPlusNode leaf) {

        int mid = leaf.keys.size() / 2;

        BPlusNode newLeaf = new BPlusNode(true);

        while (leaf.keys.size() > mid)
            newLeaf.keys.add(leaf.keys.remove(mid));

        newLeaf.next = leaf.next;
        leaf.next = newLeaf;
        newLeaf.parent = leaf.parent;

        insertIntoParent(leaf, newLeaf.keys.get(0), newLeaf);
    }

    private void insertIntoParent(BPlusNode left, int key, BPlusNode right) {

        if (left == root) {

            BPlusNode newRoot = new BPlusNode(false);

            newRoot.keys.add(key);

            newRoot.children.add(left);
            newRoot.children.add(right);

            left.parent = newRoot;
            right.parent = newRoot;

            root = newRoot;
            return;
        }

        BPlusNode parent = left.parent;
        int index = parent.children.indexOf(left);

        parent.keys.add(index, key);
        parent.children.add(index + 1, right);
        right.parent = parent;

        if (parent.keys.size() == ORDER)
            splitInternal(parent);
    }

    private void splitInternal(BPlusNode node) {

        int mid = node.keys.size() / 2;
        int promoteKey = node.keys.get(mid);

        BPlusNode newInternal = new BPlusNode(false);
        newInternal.parent = node.parent;

        for (int i = mid + 1; i < node.keys.size(); i++)
            newInternal.keys.add(node.keys.get(i));

        for (int i = mid + 1; i < node.children.size(); i++) {
            BPlusNode child = node.children.get(i);
            newInternal.children.add(child);
            child.parent = newInternal;
        }

        while (node.keys.size() > mid)
            node.keys.remove(node.keys.size() - 1);

        while (node.children.size() > mid + 1)
            node.children.remove(node.children.size() - 1);

        insertIntoParent(node, promoteKey, newInternal);
    }

    public boolean search(int key) {

        BPlusNode leaf = findLeaf(root, key);

        return leaf.keys.contains(key);
    }

    void displayTree() {

        System.out.println("\nB+ TREE STRUCTURE\n");

        List<BPlusNode> level = new ArrayList<>();
        level.add(root);
        int levelNumber = 0;

        while (!level.isEmpty()) {
            List<BPlusNode> nextLevel = new ArrayList<>();

            System.out.print("Level " + levelNumber + ": ");

            for (BPlusNode node : level) {
                System.out.print(node.keys + " ");

                if (!node.isLeaf)
                    nextLevel.addAll(node.children);
            }

            System.out.println();
            level = nextLevel;
            levelNumber++;
        }

        System.out.println("\nLeaf Node Traversal:");

        BPlusNode leaf = leftMostLeaf();
        StringBuilder traversal = new StringBuilder();

        while (leaf != null) {
            for (int key : leaf.keys) {
                if (traversal.length() > 0)
                    traversal.append(" -> ");

                traversal.append(key);
            }

            leaf = leaf.next;
        }

        System.out.println(traversal);
    }

    private BPlusNode leftMostLeaf() {

        BPlusNode node = root;

        while (!node.isLeaf)
            node = node.children.get(0);

        return node;
    }

    public static void main(String[] args) {

        FlipkartProductSearchBPlusTree tree = new FlipkartProductSearchBPlusTree();

        int[] products = { 1001, 1005, 1010, 1015, 1020, 1025, 1030 };

        for (int p : products)
            tree.insert(p);

        System.out.println("FLIPKART PRODUCT SEARCH USING B+ TREE");
        System.out.println("-------------------------------------");

        System.out.println("\nProducts Inserted:");
        System.out.println("1001, 1005, 1010, 1015, 1020, 1025, 1030");

        tree.displayTree();

        int searchKey = 1020;

        System.out.println("\nSearching Product ID " +
                searchKey + "...");

        if (tree.search(searchKey))
            System.out.println("Product Found");
        else
            System.out.println("Product Not Found");

        System.out.println("\nB+ Tree Index Created Successfully");

        System.out.println("\nTime Complexity:");
        System.out.println("Insert : O(log n)");
        System.out.println("Search : O(log n)");
    }
}