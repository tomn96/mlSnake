package machinelearning;

import math.Matrix;
import math.MatrixException;
import math.RandomUniformBetweenMinusOneToOne;

public class SimpleComputationalGraph {
    protected int iNodes, hNodes, oNodes, hLayers;
    protected Matrix[] weights;

    public SimpleComputationalGraph(int input, int hidden, int output, int hiddenLayers) {
        iNodes = input;
        hNodes = hidden;
        oNodes = output;
        hLayers = hiddenLayers;

        weights = new Matrix[hLayers+1];
        weights[0] = new Matrix(hNodes, iNodes+1, new RandomUniformBetweenMinusOneToOne());
        for(int i=1; i<hLayers; i++) {
            weights[i] = new Matrix(hNodes,hNodes+1, new RandomUniformBetweenMinusOneToOne());
        }
        weights[hLayers] = new Matrix(oNodes,hNodes+1, new RandomUniformBetweenMinusOneToOne());
    }

    float[] compute(float[] inputArray) throws MatrixException {
        Matrix input = Matrix.Utils.arrayToColMatrix(inputArray);
        Matrix input_with_bias = Matrix.Utils.addBiasToVector(input);

        for(int i=0; i<hLayers; i++) {
            Matrix hidden_output = Matrix.Utils.dot(weights[i], input_with_bias);
            Matrix hidden_output_activated = Matrix.Utils.transformEachElement(hidden_output, new ReluActivation());
            input_with_bias = Matrix.Utils.addBiasToVector(hidden_output_activated);
        }

        Matrix output = Matrix.Utils.dot(weights[hLayers], input_with_bias);
        Matrix output_activated = Matrix.Utils.transformEachElement(output, new ReluActivation());

        return output_activated.toArray();
    }

    int output(float[] inputArray) {
        float[] computed = null;
        try {
            computed = compute(inputArray);
        } catch (MatrixException e) {
            e.printStackTrace();
            System.err.println("ComputationalGraph's compute function failed because of a matrix calculation error");
        }
        if (computed == null || computed.length == 0) {
            return 0;
        }

        float max = computed[0];
        int index = 0;
        for (int i=0; i < computed.length; i++) {
            if (computed[i] > max) {
                max = computed[i];
                index = i;
            }
        }
        return index;
    }
}
