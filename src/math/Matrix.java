package math;

import java.util.concurrent.Callable;

public class Matrix {
    private int rows, cols;
    private float[][] m;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.m = new float[this.rows][this.cols];
    }

    public Matrix(Matrix matrix) {
        this(matrix.rows, matrix.cols);
        for(int i = 0; i < matrix.rows; i++) {
            System.arraycopy(matrix.m[i], 0, this.m[i], 0, matrix.cols);
        }
    }

    public Matrix(float[][] raw_matrix) {
        this(raw_matrix.length, raw_matrix[0].length);
        for(int i = 0; i < raw_matrix.length; i++) {
            System.arraycopy(raw_matrix[i], 0, this.m[i], 0, raw_matrix[i].length);
        }
    }

    public Matrix(int rows, int cols, Callable<Float> init_function) {
        this(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                float val = 0;
                try {
                    val = init_function.call();
                } catch (Exception e) {
                    val = 0;
                }
                this.m[i][j] = val;
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }


    public float get(int row, int col) throws MatrixException {
        if (0 <= row && row < rows && 0 <= col && col < cols) {
            return m[row][col];
        }
        throw new MatrixException("Wrong row/col");
    }

    public void set(int row, int col, float value) throws MatrixException {
        if (0 <= row && row < rows && 0 <= col && col < cols) {
            m[row][col] = value;
        } else {
            throw new MatrixException("Wrong row/col");
        }
    }

    public Matrix dot(Matrix other) throws MatrixException {
        if(cols == other.rows) {
            Matrix result = new Matrix(rows, other.cols);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < other.cols; j++) {
                    float sum = 0;
                    for (int k = 0; k < cols; k++) {
                        sum += m[i][k] * other.m[k][j];
                    }
                    result.m[i][j] = sum;
                }
            }
            return result;
        } else {
            throw new MatrixException("Can't dot these Matrices because of dimension reasons: this.cols does not equal other.rows");

//            System.err.println("Can't dot these Matrices because of dimension reasons: this.cols does not equal other.rows");
//
//            System.err.println("this:");
//            System.err.println(this.toString());
//            System.err.println("other:");
//            System.err.println(other.toString());
//
//            System.exit(1);
//            return null;
        }
    }

    public float[] toArray() {
        float[] result = new float[rows*cols];
        for(int i = 0; i < rows; i++) {
            System.arraycopy(m[i], 0, result, i * cols, cols);
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                result.append(String.valueOf(m[i][j]));
                result.append(" ");
            }
            result.append("\n");
        }
        return result.toString();
    }

    public static class Utils {

        public static Matrix arrayToColMatrix(float[] array) {
            Matrix matrix = new Matrix(array.length, 1);
            for(int i = 0; i < array.length; i++) {
                matrix.m[i][0] = array[i];
            }
            return matrix;
        }

        public static Matrix arrayToRowMatrix(float[] array) {
            Matrix matrix = new Matrix(1, array.length);
            System.arraycopy(array, 0, matrix.m[0], 0, array.length);
            return matrix;
        }

        public static Matrix addBiasToVector(Matrix vector) throws MatrixException {
            if (vector.cols != 1) {
                throw new MatrixException("Not a Col Vector");
            }
            Matrix new_vector = new Matrix(vector.rows + 1, 1);
            for(int i = 0; i < vector.rows; i++) {
                new_vector.m[i][0] = vector.m[i][0];
            }
            new_vector.m[vector.rows][0] = 1;
            return new_vector;
        }
    }

}
