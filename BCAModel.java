import java.util.Arrays;
import java.util.Scanner;

public class BCAModel extends Thread {

    static private int[] X;
    static private int f_best;
    static private int maxLoad;
    static private int n;
    static private int m;
    static private boolean[][] conflict;
    static private boolean[][] teach;

    public static boolean check(int k) {

        for (int i = 1; i <= k; i++) {
            if (!teach[X[i]][i]) {
                return false;
            }
            for (int j = i; j <= k; j++) {
                if (X[i] == X[j] && conflict[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void tryValue(int k) {
        for (int v = 1; v <= n; v++) {
            X[k] = v;
            if (!check(k)) {
                continue;
            }
            if (k == m) {
                int[] count = new int[n + 1];
                for (int i = 1; i <= m; i++) {
                    count[X[i]]++;
                }
                Arrays.sort(count);
                if (count[n] - count[1] < f_best) {
                    f_best = count[n] - count[1];
                    maxLoad = count[n];
                }
                if (count[n] - count[1] == f_best&& count[n]>maxLoad) {
                    f_best = count[n] - count[1];
                    maxLoad = count[n];
                }

            } else {
                tryValue(k + 1);
            }
        }

    }

    static public void solve() {
        tryValue(1);
        printSolution();
    }

    static public void printSolution() {
        if (maxLoad == 0) {
            System.out.print(-1);
            return;
        }
        System.out.print(maxLoad);
    }

    public static void loadData() {
        try {
            Scanner scanner = new Scanner(System.in);
            n = scanner.nextInt();
            m = scanner.nextInt();
            teach = new boolean[n + 1][m + 1];
            conflict = new boolean[m + 1][m + 1];
            X = new int[m + 1];
            f_best = 999;
            maxLoad = 0;
            for (int i = 1; i <= n; i++) {
                int courseNum = scanner.nextInt();
                for (int j = 1; j <= courseNum; j++) {
                    int courseId = scanner.nextInt();
                    teach[i][courseId] = true;
                }
            }
            int k = scanner.nextInt();
            for (int i = 0; i < k; i++) {
                int courseId = scanner.nextInt();
                int conflictId = scanner.nextInt();
                if (conflictId == courseId) {
                    continue;
                }
                conflict[courseId][conflictId] = true;
                conflict[conflictId][courseId] = true;
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        loadData();
        solve();
    }
}
