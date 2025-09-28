import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Polynomial {
    private double[] coefficients; 
    private int[] exponents;      
    private int size;             

    public Polynomial() {
        coefficients = new double[0];
        exponents = new int[0];
        size = 0;
    }

    public Polynomial(double[] coeff, int[] exp) {
        this.size = coeff.length;
        coefficients = new double[size];
        exponents = new int[size];
        for (int i = 0; i < size; i++) {
            coefficients[i] = coeff[i];
            exponents[i] = exp[i];
        }
    }

    public Polynomial(File file) {
        try {
            FileReader fr = new FileReader(file);
            String text = "";
            int c;
            while ((c = fr.read()) != -1) {
                text += (char) c;
            }
            fr.close();
            parsePolynomial(text.trim());
        } catch (IOException e) {
            coefficients = new double[0];
            exponents = new int[0];
            size = 0;
        }
    }

    private void parsePolynomial(String poly) {
        if (poly.charAt(0) != '-') {
            poly = "+" + poly;
        }

        int count = 0;
        for (int i = 0; i < poly.length(); i++) {
            if (poly.charAt(i) == '+' || poly.charAt(i) == '-') count++;
        }

        coefficients = new double[count];
        exponents = new int[count];
        size = 0;

        int i = 0;
        while (i < poly.length()) {
            char sign = poly.charAt(i);
            i++;
            String term = "";
            while (i < poly.length() && poly.charAt(i) != '+' && poly.charAt(i) != '-') {
                term += poly.charAt(i);
                i++;
            }

            double coef;
            int exp;
            if (term.contains("x")) {
                String[] parts = term.split("x");
                if (parts[0].equals("")) coef = 1;
                else coef = Double.parseDouble(parts[0]);
                if (parts.length > 1 && !parts[1].equals("")) {
                    exp = Integer.parseInt(parts[1]);
                } else exp = 1;
            } else {
                coef = Double.parseDouble(term);
                exp = 0;
            }
            if (sign == '-') coef = -coef;

            insertTerm(coef, exp);
        }
    }

    private void insertTerm(double coef, int exp) {
        for (int i = 0; i < size; i++) {
            if (exponents[i] == exp) {
                coefficients[i] += coef;
                return;
            }
        }
        double[] newCoeff = new double[size + 1];
        int[] newExp = new int[size + 1];
        for (int i = 0; i < size; i++) {
            newCoeff[i] = coefficients[i];
            newExp[i] = exponents[i];
        }
        newCoeff[size] = coef;
        newExp[size] = exp;
        coefficients = newCoeff;
        exponents = newExp;
        size++;
    }

    // Evaluate
    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < size; i++) {
            result += coefficients[i] * Math.pow(x, exponents[i]);
        }
        return result;
    }

    public boolean hasRoot(double x) {
        return Math.abs(evaluate(x)) < 1e-9;
    }

    // Add
    public Polynomial add(Polynomial other) {
        Polynomial result = new Polynomial();
        for (int i = 0; i < this.size; i++) {
            result.insertTerm(this.coefficients[i], this.exponents[i]);
        }
        for (int i = 0; i < other.size; i++) {
            result.insertTerm(other.coefficients[i], other.exponents[i]);
        }
        return result;
    }

    // Multiply
    public Polynomial multiply(Polynomial other) {
        Polynomial result = new Polynomial();
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < other.size; j++) {
                result.insertTerm(this.coefficients[i] * other.coefficients[j],
                                  this.exponents[i] + other.exponents[j]);
            }
        }
        return result;
    }

    // Save to file
    public void saveToFile(String filename) {
        try {
            FileWriter fw = new FileWriter(filename);
            for (int i = 0; i < size; i++) {
                if (coefficients[i] >= 0 && i > 0) fw.write("+");
                fw.write(coefficients[i] == 1 && exponents[i] != 0 ? "" : "" + coefficients[i]);
                if (exponents[i] > 0) {
                    fw.write("x");
                    if (exponents[i] > 1) fw.write("" + exponents[i]);
                }
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Error writing file.");
        }
    }
}