package evolution;

import math.Matrix;

import java.util.Random;
import java.util.concurrent.Callable;

public class DNA extends Matrix implements Mutable, Combinable<DNA> {
    public DNA(int rows, int cols) {
        super(rows, cols);
    }

    public DNA(Matrix matrix) {
        super(matrix);
    }

    public DNA(float[][] raw_matrix) {
        super(raw_matrix);
    }

    public DNA(int rows, int cols, Callable<Float> init_function) {
        super(rows, cols, init_function);
    }

    @Override
    public void mutate(float rate) {
        Random rand = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                float res = rand.nextFloat();
                if (res < rate) {
                    m[i][j] += rand.nextGaussian() / 5;
                    m[i][j] = Math.min(m[i][j], 1);
                    m[i][j] = Math.max(m[i][j], -1);
                }
            }
        }
    }

    @Override
    public DNA combine(DNA other) {
        if (rows != other.rows || cols != other.cols) {
            System.err.println("Something went wrong. Trying to combine two DNA with different size.\nthis:\n" + this + "\nother:\n" + other);
        }

        DNA combined = new DNA(rows, cols);

        Random rand = new Random();
        int randR = rand.nextInt(rows);
        int randC = rand.nextInt(cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if ((i < randR) || (i == randR && j <= randC)) {
                    combined.m[i][j] = m[i][j];
                } else {
                    combined.m[i][j] = other.m[i][j];
                }
            }
        }
        return combined;
    }
}
