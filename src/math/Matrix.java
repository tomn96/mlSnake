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
        }
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
}
