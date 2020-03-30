package evolution;

import machinelearning.SimpleComputationalGraph;
import math.Matrix;
import math.RandomUniformBetweenMinusOneToOne;

public class Brain extends SimpleComputationalGraph implements Mutable, Combinable<Brain> {

    public Brain(int input, int hidden, int output, int hiddenLayers, boolean initial_weights) {
        iNodes = input;
        hNodes = hidden;
        oNodes = output;
        hLayers = hiddenLayers;

        weights = new DNA[hLayers + 1];
        if (initial_weights) {
            weights[0] = new DNA(hNodes, iNodes + 1, new RandomUniformBetweenMinusOneToOne());
            for (int i = 1; i < hLayers; i++) {
                weights[i] = new DNA(hNodes, hNodes + 1, new RandomUniformBetweenMinusOneToOne());
            }
            weights[hLayers] = new DNA(oNodes, hNodes + 1, new RandomUniformBetweenMinusOneToOne());
        }
    }

    public Brain(int input, int hidden, int output, int hiddenLayers) {
        this(input, hidden, output, hiddenLayers, true);
    }

    public Brain(Brain b) {
        this(b.iNodes, b.hNodes, b.oNodes, b.hLayers, false);
        for (int i = 0; i < b.weights.length; i++) {
            weights[i] = new DNA(b.weights[i]);
        }
    }

    @Override
    public void mutate(float rate) {
        for (Matrix weight : weights) {
            ((DNA) weight).mutate(rate);
        }
    }

    @Override
    public Brain combine(Brain other) {
        if (iNodes != other.iNodes || hNodes != other.hNodes || oNodes != other.oNodes || hLayers != other.hLayers) {
            System.err.println("Something went wrong. Trying to combine two Brains with different size.\nthis: " + this + "\nother: " + other);
        }
        Brain combined = new Brain(iNodes, hNodes, oNodes, hLayers, false);
        for (int i = 0; i < weights.length; i++) {
            combined.weights[i] = ((DNA) this.weights[i]).combine(((DNA) other.weights[i]));
        }
        return combined;
    }
}
