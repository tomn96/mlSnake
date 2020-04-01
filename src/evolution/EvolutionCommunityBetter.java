package evolution;

import game.SmartSnake;
import game.Tickable;
import game.WindowGame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class EvolutionCommunityBetter<T extends Community<T>> implements Tickable, Alive, Runnable {
    private static final int DEFAULT_SIZE = 2000;
    private static final float DEFAULT_MUTATION_RATE = 0.1f;
    private static final float MAX_MUTATION_RATE = 0.35f;

    private List<Integer> evolutionScore = new LinkedList<>();
    private List<Double> evolutionFitness = new LinkedList<>();
    private int generation = 0;

    private float mutationRate;
    private float initialMutationRate;
    private int sameBest = 0;

    private List<T> snakes;

    private T bestFitnessSnake;

    private T bestScoreSnake;
    private int highScore;
    private int highScoreGeneration = 0;

    public EvolutionCommunityBetter(int size, float mutationRate, T initial) {
        this.mutationRate = Math.min(mutationRate, EvolutionCommunityBetter.MAX_MUTATION_RATE);
        this.initialMutationRate = this.mutationRate;

        this.bestFitnessSnake = initial.copy();

        this.bestScoreSnake = initial.copy();
        this.highScore = this.bestScoreSnake.getScore();

        snakes = new ArrayList<>(size);
        snakes.add(initial.duplicate());
        for (int i = 1; i < size; i++) {
            snakes.add(initial.newIndividual());
        }
    }

    public EvolutionCommunityBetter(T initial) {
        this(EvolutionCommunityBetter.DEFAULT_SIZE, EvolutionCommunityBetter.DEFAULT_MUTATION_RATE, initial);
    }

    @Override
    public boolean isDead() {  // check if all the snakes in the population are dead
        for (T snake : snakes) {
            if (!snake.isDead()) {
                return false;
            }
        }
        return bestFitnessSnake.isDead();
    }

    @Override
    public void tick() {  // update all the snakes in the generation
        if (!bestFitnessSnake.isDead()) {  // if the best snake is not dead update it, this snake is a replay of the best from the past generation
            bestFitnessSnake.tick();
        }
        for (T snake : snakes) {
            if (!snake.isDead()) {
                snake.tick();
            }
        }
    }

    private void setBestFitnessSnake() {  // set the best snake of the generation
        double bestFitness = bestFitnessSnake.fitness();
        int bestFitnessSnakeScore = bestFitnessSnake.getScore();

        int maxIndex = 0;
        double max = snakes.get(0).fitness();
        for (int i = 1; i < snakes.size(); i++) {
            double f = snakes.get(i).fitness();
            if (f > max) {
                max = f;
                maxIndex = i;
            }
        }

        if (max > bestFitness) {
            bestFitnessSnake = snakes.get(maxIndex).copy();
            bestFitness = max;
            bestFitnessSnakeScore = snakes.get(maxIndex).getScore();

            if (bestFitnessSnakeScore > highScore) {
                bestScoreSnake = bestFitnessSnake.copy();
                highScore = bestFitnessSnakeScore;
                highScoreGeneration = generation;
            }

            sameBest = 0;
            mutationRate = initialMutationRate;
        } else {
            bestFitnessSnake = bestFitnessSnake.copy();

            sameBest++;
            if (sameBest % 3  == 0) {
                mutationRate = (float) Math.min(mutationRate + 0.025, EvolutionCommunityBetter.MAX_MUTATION_RATE);
            }
        }

        evolutionFitness.add(bestFitness);
        evolutionScore.add(bestFitnessSnakeScore);
    }

    private T selectParent() {  // selects a random number in range of the fitnesssum and if a snake falls in that range then select it
        Random random = new Random();
        int rand = random.nextInt((int) Math.floor(fitnessSum()));
        double summation = 0;
        for (T snake : snakes) {
            summation += snake.fitness();
            if (summation > rand) {
                return snake;
            }
        }
        return snakes.get(0);
    }

    private double fitnessSum() {  // calculate the sum of all the snakes fitnesses
        double result = 0;
        for (T snake : snakes) {
            result += snake.fitness();
        }
        return result;
    }

    public void naturalSelection() {
        setBestFitnessSnake();

        List<T> newSnakes = new ArrayList<>(snakes.size());
        newSnakes.add(bestFitnessSnake.duplicate());  // add the best snake of the prior generation into the new generation
        for(int i = 1; i < snakes.size(); i++) {
            T child = selectParent().combine(selectParent());
            child.mutate(mutationRate);
            newSnakes.add(child);
        }
        snakes = newSnakes;

        generation++;
    }

    private void printHelper(List<?> l) {
        int end = l.size();
        int start = Math.max(0, end - 10);
        if (start > 0) {
            System.out.print("...");
        }
        System.out.println(l.subList(start, end));
    }

    @Override
    public void run() {
        while (true) {
            while (!isDead()) {
                tick();
            }
            naturalSelection();
            System.out.println("Generation: " + generation + ", Same Best: " + sameBest + ", MutationRate: " + mutationRate);
            System.out.println("HighScore: " + highScore + ", Achieved at Generation: " + highScoreGeneration);
            System.out.print("Scores: ");
            printHelper(evolutionScore);
            System.out.print("Fitness: ");
            printHelper(evolutionFitness);

            if (sameBest == 0) {
                WindowGame windowGame = new WindowGame(((SmartSnake) bestFitnessSnake).copy());
                while (windowGame.running) {}
            }
        }
    }
}
