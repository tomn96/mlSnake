package evolution;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EvolutionCommunityWithBest<T extends Community<T>> extends EvolutionCommunity<T> {
    private List<Double> bestEvolutionFitness = new LinkedList<>();

    private float initialMutationRate;
    private int sameBest = 0;

    private T bestFitnessSnake;

    public EvolutionCommunityWithBest(int size, float mutationRate, int legacy, T initial) {
        super(size, mutationRate, legacy, initial);

        this.initialMutationRate = this.mutationRate;
        this.bestFitnessSnake = initial.copy();
    }

    public EvolutionCommunityWithBest(T initial) {
        this(EvolutionCommunityWithBest.DEFAULT_SIZE, EvolutionCommunityWithBest.DEFAULT_MUTATION_RATE, EvolutionCommunityWithBest.DEFAULT_LEGACY_AMOUNT, initial);
    }

    public T getBestFitnessSnake() {
        return bestFitnessSnake.copy();
    }

    @Override
    public boolean isDead() {  // check if all the snakes in the population are dead
        if (!bestFitnessSnake.isDead()) {
            return false;
        }
        return super.isDead();
    }

    @Override
    public void tick() {  // update all the snakes in the generation
        if (!bestFitnessSnake.isDead()) {  // if the best snake is not dead update it, this snake is a replay of the best from the past generation
            bestFitnessSnake.tick();
        }
        super.tick();
    }

    private void fixMutationRate(boolean isBestSame) {
        if (isBestSame) {
            sameBest++;
            if (sameBest % 3  == 0) {
                mutationRate = (float) Math.min(mutationRate + 0.025, EvolutionCommunityWithBest.MAX_MUTATION_RATE);
            }
        } else {
            sameBest = 0;
            mutationRate = initialMutationRate;
        }
    }

    private void setBestFitnessSnake() {  // set the best snake of the generation
        double bestFitness = bestFitnessSnake.fitness();
        double max = snakes.get(0).fitness();
        if (max > bestFitness) {
            bestFitnessSnake = snakes.get(0).copy();
            bestFitness = max;
            fixMutationRate(false);
        } else {
            bestFitnessSnake = bestFitnessSnake.copy();
            fixMutationRate(true);
        }

        bestEvolutionFitness.add(bestFitness);
    }

    @Override
    protected int setHighScoreSnake() {
        int maxScore = super.setHighScoreSnake();
        validateAndSetHighScoreSnake(bestFitnessSnake);
        return Math.max(maxScore, bestFitnessSnake.getScore());
    }

    @Override
    public void naturalSelection() {
        survival();
        setBestFitnessSnake();

        List<T> newSnakes = new ArrayList<>(snakes.size());
        newSnakes.add(bestFitnessSnake.duplicate());  // add the best snake of the prior generation into the new generation
        reproduce(newSnakes, snakes.size() - 1);
    }

    @Override
    public String toString() {
        return super.toString() + "\nBestFitness: " + EvolutionCommunityWithBest.bigListStringify(bestEvolutionFitness);
    }
}
