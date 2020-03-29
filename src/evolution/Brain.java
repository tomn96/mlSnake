package evolution;

import machinelearning.SimpleComputationalGraph;
import math.RandomUniformBetweenMinusOneToOne;

public class Brain extends SimpleComputationalGraph {
    public Brain(int input, int hidden, int output, int hiddenLayers) {
        iNodes = input;
        hNodes = hidden;
        oNodes = output;
        hLayers = hiddenLayers;

        weights = new DNA[hLayers + 1];
        weights[0] = new DNA(hNodes, iNodes + 1, new RandomUniformBetweenMinusOneToOne());
        for (int i = 1; i < hLayers; i++) {
            weights[i] = new DNA(hNodes, hNodes + 1, new RandomUniformBetweenMinusOneToOne());
        }
        weights[hLayers] = new DNA(oNodes, hNodes + 1, new RandomUniformBetweenMinusOneToOne());
    }

    public Brain(Brain b) {
        iNodes = b.iNodes;
        hNodes = b.hNodes;
        oNodes = b.oNodes;
        hLayers = b.hLayers;
        weights = new DNA[b.weights.length];
        for (int i = 0; i < b.weights.length; i++) {
            weights[i] = new DNA(b.weights[i]);
        }
    }
}
