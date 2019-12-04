/*
 * Match.java
 * By: Jaewoo Chung
*/
//package cs310;
import java.util.Scanner;

public class Match {
    /*
     * This method needs to calc
     * opt[i][j].cost / opt[i][j].next / opt[i][j].col / opt[i][j].row
     * When fully computed the Path object at opt[0][0] will have the most optimal path (red in pdf)
    */
    public static Path match(String a, String b) {
        // Fill code in here
        /* create a 2D array of type Path objects */
        Path[][] opt = new Path[a.length()+1][b.length()+1]; // Add the +1 because of the extra space at the end

        /* Reverse string */
        String aReverse = new StringBuilder(a).reverse().toString();
        String bReverse = new StringBuilder(b).reverse().toString();

        Path path1 = new Path();
        path1.cost = 0;
        path1.col = 0;
        path1.row = 0;
        path1.next = null;
        opt[0][0] = path1;
        // base case...
        for (int i = 1; i <= a.length(); i++) {
            Path path = new Path();
            path.row = i;
            path.col = 0;

            path.cost = opt[i-1][0].cost + 2;
            path.next = null;
            opt[i][0] = path;
        }
        // base case...
        for (int j = 1; j <= b.length(); j++) {
            Path path = new Path();
            path.row = 0;
            path.col = j;

            path.cost = opt[0][j-1].cost + 2;
            path.next = null;
            opt[0][j] = path;
        }

        /* Nested for loop to calc opt[][] variables */
        // int M = a.length(); // Variables to go backwards in the 2D array
        // int N = b.length(); // Same thing ^
        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                Path path = new Path();
                path.row = i;
                path.col = j;

                int diag;

                if (aReverse.charAt(i-1) == bReverse.charAt(j-1)) {
                    diag = opt[i-1][j-1].cost + 0;  // They match
                }
                else {
                    diag = opt[i-1][j-1].cost + 1;  // They are mismatches
                }
                int left = opt[i][j-1].cost + 2;    // Gap
                int upper = opt[i-1][j].cost + 2;   // Gap
                path.cost = min(diag, left, upper);
                opt[i][j] = path;
            }
        }
        for (int M = a.length(); M >= 0; M--) {
            for (int N = b.length(); N >= 0; N--) {
                if (M==0 || N==0) {
                    break;
                }
                if (opt[M][N].cost == opt[M-1][N-1].cost + 1 || (aReverse.charAt(M-1) == bReverse.charAt(N-1))) {
                    opt[M][N].next = opt[M-1][N-1];
                }
                if (opt[M][N].cost == opt[M-1][N].cost + 2) {
                    opt[M][N].next = opt[M-1][N];
                }
                if (opt[M][N].cost == opt[M][N-1].cost + 2) {
                    opt[M][N].next = opt[M][N-1];
                }
            }
        }

        return opt[a.length()][b.length()]; // Temporary holder to compile;
    }

    /*
     * return the penalty for aligning character a with character b
    */
    private static int penalty (char a, char b) {
        /* If there is a gap */
        if (a == ' ' || b == ' ') {
            return 2;
        }
        /* If there is a match no penatly */
        if (a == b) {
            return 0;
        }
        /* If there is a mismatchs */
        return 1;
    }

    /*
     * return the min of 3 integers
    */
    private static int min(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }

    public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
        String a = sc.nextLine();
        String b = sc.nextLine();
        Path path = Match.match(a, b);
        for (Path p = path; p != null; p = p.next) {
            System.out.println(p.row + " " + p.col + " cost: " + p.cost);
        }
    }
}
