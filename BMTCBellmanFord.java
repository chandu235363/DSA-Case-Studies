import java.util.*;

class Edge {
    int u, v, w;

    Edge(int u, int v, int w) {
        this.u = u;
        this.v = v;
        this.w = w;
    }
}

public class BMTCBellmanFord {

    static final int INF = 999999;

    static void dijkstra(int[][] graph, int src, String[] hubs) {
        int n = graph.length;
        int[] dist = new int[n];
        boolean[] visited = new boolean[n];

        Arrays.fill(dist, INF);
        dist[src] = 0;

        for (int i = 0; i < n - 1; i++) {
            int u = -1;
            int min = INF;

            for (int j = 0; j < n; j++) {
                if (!visited[j] && dist[j] < min) {
                    min = dist[j];
                    u = j;
                }
            }

            visited[u] = true;

            for (int v = 0; v < n; v++) {
                if (!visited[v] && graph[u][v] != INF &&
                        dist[u] + graph[u][v] < dist[v]) {
                    dist[v] = dist[u] + graph[u][v];
                }
            }
        }

        System.out.println("Dijkstra Shortest Distances:");
        for (int i = 0; i < n; i++) {
            System.out.println(hubs[i] + " : " + dist[i]);
        }
    }

    static void bellmanFord(List<Edge> edges, int n, int src, String[] hubs) {
        int[] dist = new int[n];

        Arrays.fill(dist, INF);
        dist[src] = 0;

        for (int i = 1; i < n; i++) {
            for (Edge e : edges) {
                if (dist[e.u] != INF &&
                        dist[e.u] + e.w < dist[e.v]) {
                    dist[e.v] = dist[e.u] + e.w;
                }
            }
        }

        boolean negativeCycle = false;

        for (Edge e : edges) {
            if (dist[e.u] != INF &&
                    dist[e.u] + e.w < dist[e.v]) {
                negativeCycle = true;
                break;
            }
        }

        if (negativeCycle) {
            System.out.println("\nNegative Cycle Detected");
        } else {
            System.out.println("\nBellman-Ford Shortest Distances:");
            for (int i = 0; i < n; i++) {
                System.out.println(hubs[i] + " : " + dist[i]);
            }
        }
    }

    public static void main(String[] args) {

        String[] hubs = {
                "MJC", "KEM", "JAY",
                "KOR", "WHF", "HBR", "MRT"
        };

        int n = 7;

        int[][] graph = new int[n][n];

        for (int i = 0; i < n; i++) {
            Arrays.fill(graph[i], INF);
            graph[i][i] = 0;
        }

        List<Edge> edges = new ArrayList<>();

        edges.add(new Edge(0, 1, 8));
        edges.add(new Edge(0, 2, 5));
        edges.add(new Edge(1, 3, 1));
        edges.add(new Edge(1, 4, 10));
        edges.add(new Edge(1, 5, 7));
        edges.add(new Edge(2, 3, 4));
        edges.add(new Edge(3, 4, 6));
        edges.add(new Edge(3, 6, 6));
        edges.add(new Edge(4, 6, -3));
        edges.add(new Edge(5, 6, 2));
        edges.add(new Edge(2, 5, 10));

        graph[0][1] = 8;
        graph[0][2] = 5;
        graph[1][3] = 1;
        graph[1][4] = 10;
        graph[1][5] = 7;
        graph[2][3] = 4;
        graph[3][4] = 6;
        graph[3][6] = 6;
        graph[4][6] = 0;
        graph[5][6] = 2;
        graph[2][5] = 10;

        dijkstra(graph, 0, hubs);

        bellmanFord(edges, n, 0, hubs);
    }
}