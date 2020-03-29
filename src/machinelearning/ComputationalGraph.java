package machinelearning;

import math.Matrix;
import math.RandomUniformBetweenMinusOneToOne;

public class ComputationalGraph {
    protected int iNodes, hNodes, oNodes, hLayers;
    protected Matrix[] weights;

    public ComputationalGraph(int input, int hidden, int output, int hiddenLayers) {
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

}
