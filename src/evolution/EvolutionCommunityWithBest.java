package evolution;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EvolutionCommunityWithBest<T extends Community<T>> extends EvolutionCommunity<T> {

    private List<Integer> bestEvolutionScore = new LinkedList<>();
    private List<Double> bestEvolutionFitness = new LinkedList<>();

    private float initialMutationRate;
    private int sameBest = 0;

    private T bestFitnessSnake;

    public EvolutionCommunityWithBest(int size, float mutationRate, T initial) {
        super(size, mutationRate, initial);

        this.initialMutationRate = this.mutationRate;
        this.bestFitnessSnake = initial.copy();
    }

    public EvolutionCommunityWithBest(T initial) {
        this(EvolutionCommunityWithBest.DEFAULT_SIZE, EvolutionCommunityWithBest.DEFAULT_MUTATION_RATE, initial);
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
        int bestFitnessSnakeScore = bestFitnessSnake.getScore();

        double max = snakes.get(0).fitness();
        if (max > bestFitness) {
            bestFitnessSnake = snakes.get(0).copy();
            bestFitness = max;
            bestFitnessSnakeScore = snakes.get(0).getScore();
            fixMutationRate(false);
        } else {
            bestFitnessSnake = bestFitnessSnake.copy();
            fixMutationRate(true);
        }

        bestEvolutionFitness.add(bestFitness);
        bestEvolutionScore.add(bestFitnessSnakeScore);
    }

    @Override
    public void naturalSelection() {
        sortSnakesByFitness();
        setBestFitnessSnake();

        List<T> newSnakes = new ArrayList<>(snakes.size());
        newSnakes.add(bestFitnessSnake.duplicate());  // add the best snake of the prior generation into the new generation
        naturalSelectionHelper(newSnakes, snakes.size() - 1);
    }

    @Override
    public String toString() {
        return super.toString() + "\nBestScores: \n" + EvolutionCommunity.bigListStringify(bestEvolutionScore) +
                "\nBestFitness: \n" + EvolutionCommunity.bigListStringify(bestEvolutionFitness);
    }
}
