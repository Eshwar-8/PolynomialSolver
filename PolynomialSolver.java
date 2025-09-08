public class PolynomialSolver {

    public static void main(String[] args) {
        // Hardcoded inputs matching your sample JSON roots (converted to decimal)
        // Format: x values, y values
        // JSON sample had k=3 roots: 
        // "1": base 10, value "4"  => y=4
        // "2": base 2, value "111" => y=7 (binary 111 = 7)
        // "3": base 10, value "12" => y=12
        
        int k = 3;
        double[] xs = {1, 2, 3};
        double[] ys = {4, 7, 12};
        
        // Solve for coefficients a,b,c of ax^2 + bx + c = y
        double[][] A = new double[k][k];
        for (int i = 0; i < k; i++) {
            A[i][0] = xs[i] * xs[i]; // x^2
            A[i][1] = xs[i];         // x
            A[i][2] = 1;             // constant
        }
        
        try {
            double[] coeffs = solve3x3(A, ys);
            double c = coeffs[2];
            System.out.println("Constant term c = " + c);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Solve 3x3 linear system Ax = b using Cramer's rule
    private static double[] solve3x3(double[][] A, double[] b) throws Exception {
        double detA = determinant3x3(A);
        if (detA == 0) throw new Exception("Matrix is singular, no unique solution");

        double[][] A1 = replaceColumn(A, b, 0);
        double[][] A2 = replaceColumn(A, b, 1);
        double[][] A3 = replaceColumn(A, b, 2);

        double x1 = determinant3x3(A1) / detA;
        double x2 = determinant3x3(A2) / detA;
        double x3 = determinant3x3(A3) / detA;

        return new double[]{x1, x2, x3};
    }

    private static double determinant3x3(double[][] m) {
        return m[0][0]*(m[1][1]*m[2][2] - m[1][2]*m[2][1])
             - m[0][1]*(m[1][0]*m[2][2] - m[1][2]*m[2][0])
             + m[0][2]*(m[1][0]*m[2][1] - m[1][1]*m[2][0]);
    }

    private static double[][] replaceColumn(double[][] A, double[] b, int col) {
        double[][] res = new double[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(A[i], 0, res[i], 0, 3);
            res[i][col] = b[i];
        }
        return res;
    }
}
