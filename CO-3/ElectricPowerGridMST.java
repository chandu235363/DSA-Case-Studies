import java.util.*;

class Edge implements Comparable<Edge> {
    int src, dest, weight;

    Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }

    public int compareTo(Edge other) {
        return this.weight - other.weight;
    }
}

public class ElectricPowerGridMST {

    static int[] parent;

    static int find(int i) {
        if (parent[i] == i)
            return i;
        return parent[i] = find(parent[i]);
    }

    static void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        parent[rootX] = rootY;
    }

    public static void main(String[] args) {

        int vertices = 4;

        Edge[] edges = {
                new Edge(0, 1, 4), // A-B
                new Edge(0, 2, 2), // A-C
                new Edge(0, 3, 5), // A-D
                new Edge(1, 3, 6), // B-D
                new Edge(2, 3, 3) // C-D
        };

        Arrays.sort(edges);

        parent = new int[vertices];

        for (int i = 0; i < vertices; i++)
            parent[i] = i;

        int totalCost = 0;

        System.out.println("ELECTRIC POWER GRID OPTIMIZATION USING MST");
        System.out.println("------------------------------------------");

        System.out.println("\nInput Graph:");
        System.out.println();
        System.out.println("A -----4----- B");
        System.out.println("| \\           |");
        System.out.println("2  5          6");
        System.out.println("|    \\        |");
        System.out.println("C -----3----- D");

        System.out.println("\nSelected Edges in MST:");

        for (Edge edge : edges) {

            int x = find(edge.src);
            int y = find(edge.dest);

            if (x != y) {

                char source = (char) ('A' + edge.src);
                char destination = (char) ('A' + edge.dest);

                System.out.println(
                        source + " - " +
                                destination + " = " +
                                edge.weight);

                totalCost += edge.weight;

                union(x, y);
            }
        }

        System.out.println("\nMST Structure:");
        System.out.println();
        System.out.println("      A");
        System.out.println("     / \\");
        System.out.println("    2   4");
        System.out.println("   /     \\");
        System.out.println("  C---3---D");
        System.out.println();
        System.out.println("B connected through A");

        System.out.println("\nMinimum Cost = " + totalCost);

        System.out.println("\nTime Complexity: O(E log E)");
    }
}