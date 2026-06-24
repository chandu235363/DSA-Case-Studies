public class LogisticsKnapsack {

    public static void main(String[] args) {

        String[] items = { "A", "B", "C", "D", "E", "F", "G", "H" };
        int[] weights = { 5, 8, 3, 10, 4, 7, 6, 2 };
        int[] values = { 40, 50, 20, 70, 30, 45, 35, 15 };

        int capacity = 24;
        int n = weights.length;

        int[][] dp = new int[n + 1][capacity + 1];

        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= capacity; w++) {
                if (weights[i - 1] <= w) {
                    dp[i][w] = Math.max(
                            values[i - 1] + dp[i - 1][w - weights[i - 1]],
                            dp[i - 1][w]);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        System.out.println("Maximum Value = " + dp[n][capacity]);

        System.out.println("\nSelected Items:");

        int w = capacity;

        for (int i = n; i > 0 && w > 0; i--) {
            if (dp[i][w] != dp[i - 1][w]) {
                System.out.println(
                        items[i - 1] +
                                " (Weight = " + weights[i - 1] +
                                ", Value = " + values[i - 1] + ")");
                w -= weights[i - 1];
            }
        }
    }
}