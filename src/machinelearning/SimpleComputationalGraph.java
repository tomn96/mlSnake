package machinelearning;

import math.Matrix;
import math.MatrixException;

public abstract class SimpleComputationalGraph {
    protected int iNodes, hNodes, oNodes, hLayers;
    protected Matrix[] weights;

    float[] compute(float[] inputArray) throws MatrixException {
        Matrix input = Matrix.Utils.arrayToColMatrix(inputArray);
        Matrix input_with_bias = Matrix.Utils.addBiasToVector(input);

        for (int i = 0; i < hLayers; i++) {
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
        for (int i = 0; i < computed.length; i++) {
            if (computed[i] > max) {
                max = computed[i];
                index = i;
            }
        }
        return index;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("iNodes=").append(iNodes).append(", hNodes=").append(hNodes).append(", oNodes=").append(oNodes).append(", hLayers=").append(hLayers).append(", weights:\n");
        for (int i = 0; i < weights.length; i++) {
            result.append("weights[").append(i).append("]\n").append(weights[i]).append("\n\n");
        }
        return result.toString();
    }
}
