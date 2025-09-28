import java.io.File;

public class Driver {
    public static void main(String[] args) {
        // Create polynomial from arrays
        double[] c1 = {6, -2, 5}; // 6 - 2x + 5x^2
        int[] e1 = {0, 1, 2};
        Polynomial p1 = new Polynomial(c1, e1);

        double[] c2 = {1, 4}; // 1 + 4x
        int[] e2 = {0, 1};
        Polynomial p2 = new Polynomial(c2, e2);

        // Test add
        Polynomial sum = p1.add(p2);
        System.out.println("sum(2) = " + sum.evaluate(2));

        // Test multiply
        Polynomial prod = p1.multiply(p2);
        System.out.println("prod(2) = " + prod.evaluate(2));

        // Test hasRoot
        if (sum.hasRoot(1)) System.out.println("1 is root of sum");
        else System.out.println("1 is not root of sum");

        // Test read from file
        File f = new File("poly.txt"); // e.g. file contains: 5-3x2+7x3
        Polynomial pFromFile = new Polynomial(f);
        System.out.println("pFromFile(2) = " + pFromFile.evaluate(2));

        // Test saveToFile
        prod.saveToFile("result.txt");
        System.out.println("Product saved to result.txt");
    }
}