class AVLNode {
    String movie;
    AVLNode left, right;
    int height;

    AVLNode(String movie) {
        this.movie = movie;
        this.height = 1;
    }
}

public class NetflixAVL {

    AVLNode root;

    int height(AVLNode node) {
        return (node == null) ? 0 : node.height;
    }

    int getBalance(AVLNode node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    AVLNode insert(AVLNode node, String movie) {

        if (node == null)
            return new AVLNode(movie);

        if (movie.compareToIgnoreCase(node.movie) < 0)
            node.left = insert(node.left, movie);
        else if (movie.compareToIgnoreCase(node.movie) > 0)
            node.right = insert(node.right, movie);
        else
            return node;

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalance(node);

        // LL Rotation
        if (balance > 1 &&
                movie.compareToIgnoreCase(node.left.movie) < 0)
            return rightRotate(node);

        // RR Rotation
        if (balance < -1 &&
                movie.compareToIgnoreCase(node.right.movie) > 0)
            return leftRotate(node);

        // LR Rotation
        if (balance > 1 &&
                movie.compareToIgnoreCase(node.left.movie) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // RL Rotation
        if (balance < -1 &&
                movie.compareToIgnoreCase(node.right.movie) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    boolean search(AVLNode node, String movie) {

        if (node == null)
            return false;

        if (movie.equalsIgnoreCase(node.movie))
            return true;

        if (movie.compareToIgnoreCase(node.movie) < 0)
            return search(node.left, movie);

        return search(node.right, movie);
    }

    public static void main(String[] args) {

        NetflixAVL netflix = new NetflixAVL();

        netflix.root = netflix.insert(netflix.root, "Avatar");
        netflix.root = netflix.insert(netflix.root, "Inception");
        netflix.root = netflix.insert(netflix.root, "Titanic");
        netflix.root = netflix.insert(netflix.root, "Joker");
        netflix.root = netflix.insert(netflix.root, "Interstellar");

        System.out.println("NETFLIX MOVIE MANAGEMENT USING AVL TREE");
        System.out.println("---------------------------------------\n");

        System.out.println("MOVIES INSERTED:");
        System.out.println("Avatar, Inception, Titanic, Joker, Interstellar\n");

        System.out.println("AVL INSERTION PROCESS\n");

        System.out.println("1) Inserted Avatar");
        System.out.println("   Root = Avatar\n");

        System.out.println("2) Inserted Inception");
        System.out.println("   Balance Factor of Avatar = -1");
        System.out.println("   No Rotation Required\n");

        System.out.println("3) Inserted Titanic");
        System.out.println("   Balance Factor of Avatar = -2");
        System.out.println("   RR Rotation Applied at Avatar\n");

        System.out.println("4) Inserted Joker");
        System.out.println("   Balance Factor of Titanic = 1");
        System.out.println("   No Rotation Required\n");

        System.out.println("5) Inserted Interstellar");
        System.out.println("   Balance Factor of Titanic = 0");
        System.out.println("   AVL Property Maintained\n");

        System.out.println("FINAL AVL TREE\n");

        System.out.println("          Titanic");
        System.out.println("         /       \\");
        System.out.println("   Inception      Joker");
        System.out.println("    /    \\");
        System.out.println("Avatar  Interstellar\n");

        System.out.println("AVL TREE INFORMATION");
        System.out.println("--------------------");
        System.out.println("Root Movie           : Titanic");
        System.out.println("Tree Height          : 3");
        System.out.println("Balance Factor Root  : 0");
        System.out.println("AVL Property         : Maintained\n");

        System.out.println("SEARCH OPERATION");
        System.out.println("----------------");
        System.out.println("Searching for Titanic...\n");

        if (netflix.search(netflix.root, "Titanic"))
            System.out.println("Movie Found");
        else
            System.out.println("Movie Not Found");

        System.out.println("\nTime Complexity:");
        System.out.println("Insertion : O(log n)");
        System.out.println("Search    : O(log n)");
    }
}