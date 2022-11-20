import java.util.*;

public class Belmanford {
    private int D[];
    private int n;
    public static final int max_value = 999;

    public Belmanford(int n) {
        this.n = n;
        D = new int[n + 1];
    }

    public void shortest(int s, int a[][]) {
        for (int i = 1; i <= n; i++) {
            D[i] = max_value;
        }
        D[s] = 0;
        for (int k = 1; k <= n - 1; k++) {
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    if (a[i][j] != max_value) {
                        if (D[j] > D[i] + a[i][j])
                            D[j] = D[i] + a[i][j];
                    }
                }
            }
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (a[i][j] != max_value) {
                    if (D[j] > D[i] + a[i][j]) {
                        System.out.println("the graph contains -ve edge cycle");
                        return;
                    }
                }
            }
        }
        for (int i = 1; i <= n; i++) {
            System.out.println("distance of source" + s + "to" + i + "is" + D[i]);
        }
    }

    public static void main(String[] args) {
        int n = 0, s;
        Scanner sc = new Scanner(System.in);
        System.out.println("enter the no.of values");
        n = sc.nextInt();
        int a[][] = new int[n + 1][n + 1];
        System.out.println("enter the weighted matrix:");
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                a[i][j] = sc.nextInt();
                if (i == j) {
                    a[i][j] = 0;
                    continue;
                }
                if (a[i][j] == 0)
                    a[i][j] = max_value;
            }
        }
        System.out.println("enter the source vertex:");
        s = sc.nextInt();
        Belmanford b = new Belmanford(n);
        b.shortest(s, a);
        sc.close();
    }
}


/*Output1
enter the no.of values
4
enter the weighted matrix:
0 999 999 999
5 0 3 4
999 999 0 2
999 999 999 0
enter the source vertex:
2
distance of source 2 to1 is 5
distance of source 2 to 2 is 0
distance of source 2 to 3 is 3
distance of source 2 to 4 is 4
Output2:
enter the no.of values
4
enter the weighted matrix:
0 4 999 5
999 0 999 999
999 -10 0 999

Computer Network Laboratory 18CSL57

DEPT OF CSE, ATMECE Page 67
999 999 3 0
enter the source vertex:
1
distance of source 1to 1 is 0
distance of source 1 to 2 is-2
distance of source 1 to 3 is 8
distance of source 1 to 4 is 5
Output3
enter the no.of values
4
enter the weighted matrix:
0 4 5 999
999 0 999 7
999 7 0 999
999 999 -15 0
enter the source vertex:
1
the graph contains -ve edge cycle*/
