import java.util.*;

class Delivery {
    int over;
    int ball;

    Delivery(int over, int ball) {
        this.over = over;
        this.ball = ball;
    }

    @Override
    public String toString() {
        return "(" + over + "," + ball + ")";
    }
}

public class CricketCountingSort {

    static void countingSortByBall(Delivery arr[]) {
        int n = arr.length;
        Delivery output[] = new Delivery[n];

        int maxBall = 6;
        int count[] = new int[maxBall + 1];

        for (Delivery d : arr)
            count[d.ball]++;

        for (int i = 1; i <= maxBall; i++)
            count[i] += count[i - 1];

        for (int i = n - 1; i >= 0; i--) {
            output[count[arr[i].ball] - 1] = arr[i];
            count[arr[i].ball]--;
        }

        System.arraycopy(output, 0, arr, 0, n);
    }

    static void countingSortByOver(Delivery arr[]) {
        int n = arr.length;
        Delivery output[] = new Delivery[n];

        int maxOver = 50;
        int count[] = new int[maxOver + 1];

        for (Delivery d : arr)
            count[d.over]++;

        for (int i = 1; i <= maxOver; i++)
            count[i] += count[i - 1];

        for (int i = n - 1; i >= 0; i--) {
            output[count[arr[i].over] - 1] = arr[i];
            count[arr[i].over]--;
        }

        System.arraycopy(output, 0, arr, 0, n);
    }

    static void printArray(Delivery arr[]) {
        for (Delivery d : arr)
            System.out.print(d + " ");
        System.out.println();
    }

    public static void main(String[] args) {

        Delivery arr[] = {
                new Delivery(2, 4),
                new Delivery(1, 1),
                new Delivery(3, 6),
                new Delivery(1, 5),
                new Delivery(2, 2),
                new Delivery(3, 1),
                new Delivery(1, 3),
                new Delivery(2, 6),
                new Delivery(3, 4),
                new Delivery(1, 2)
        };

        System.out.println("Original:");
        printArray(arr);

        countingSortByBall(arr);
        System.out.println("\\nAfter Sorting by Ball:");
        printArray(arr);

        countingSortByOver(arr);
        System.out.println("\\nFinal Sorted by (Over, Ball):");
        printArray(arr);
    }
}