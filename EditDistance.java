/*
 * EditDistance.java
 */
//package cs310;
import java.util.Scanner;

public class EditDistance {
    public static void main(String[] args) {
        //String a = StdIn.readLine();    // Read 1st Genomic Sequence
	//String b = StdIn.readLine();    // Read 2nd Genomic Sequence
	Scanner sc = new Scanner(System.in);
	String a = sc.nextLine();
	String b = sc.nextLine();

        Path path = Match.match(a, b);  // Call match method, and retrieves path
        System.out.println("Edit distance: = " + path.cost);    // Print Optimal Cost

        /* Go through the path */
        for (Path p = path; p != null; p = p.next) {
            /* Takes care of the null at the end */
            if (p.next != null) {
                /* Checks if texts are aligned */
                if (p.col == p.next.col+1 && p.row == p.next.row+1) {
                    /* Follow two if statements Checks if characters are equal or not */
                    if (a.charAt(a.length()-p.row) == b.charAt(b.length()-p.col)) {
                        System.out.println(a.charAt(a.length()-p.row) + " " + b.charAt(b.length()-p.col) + " " + 0);
                    }
                    else {
                        System.out.println(a.charAt(a.length()-p.row) + " " + b.charAt(b.length()-p.col) + " " + 1);
                    }
                }
                /* Checks if matrix shifted left - then print a "-" for a string */
                if (p.row == p.next.row) {
                    System.out.println("-" + " " + b.charAt(b.length()-p.col) + " " + 2);
                }
                if (p.col == p.next.col) {
                    System.out.println(a.charAt(a.length()-p.row) + " " + "-" + " " + 2);
                }
            }
        }
    }
}
