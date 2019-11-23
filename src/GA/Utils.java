package GA;

import java.util.Random;

public class Utils {

    public static char rndChar (Random r) {
        String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvwxyz.";
        return abc.charAt(r.nextInt(abc.length()));
    }

    public static double[] copy(double[] vector) {

        double[] returnVector = new double[vector.length];

        for (int row = 0; row < vector.length; row++) {
            returnVector[row] = vector[row];
        }

        return returnVector;
    }

    public static double[][] copy(double[][] matrix) {

        double[][] returnMatrix = new double[matrix.length][];

        for (int row = 0; row < matrix.length; row++) {
            returnMatrix[row] = copy(matrix[row]);
        }

        return returnMatrix;
    }

    public static double[][][] copy(double[][][] m) {
        double[][][] copy = new double[m.length][][];

        for (int index = 0; index < m.length; index++) {
            copy[index] = copy(m[index]);
        }

        return copy;
    }

    public static int numberOfItems(double[] vector) {

        return vector.length;

    }

    public static int numberOfItems(double[][] matrix) {

        int number = 0;

        for (int row = 0; row < matrix.length; row++) {
            number += numberOfItems(matrix[row]);
        }

        return number;
    }

    public static int numberOfItems(double[][][] m) {
        int number = 0;

        for (int row = 0; row < m.length; row++) {
            number += numberOfItems(m[row]);
        }

        return number;
    }

}
