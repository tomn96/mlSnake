package evolution;

import game.Tickable;
//import math.Chart;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class EvolutionCommunity<T extends Community<T>> implements Tickable, Alive, Runnable {
    private static final int DEFAULT_SIZE = 2000;
    private static final float DEFAULT_MUTATION_RATE = 0.1f;
    private static final float MAX_MUTATION_RATE = 0.35f;

    private List<Integer> evolutionScore = new LinkedList<>();
    private List<Float> evolutionFitness = new LinkedList<>();
    private int generation = 0;

    private float mutationRate;

    private List<T> snakes;
    private T bestSnake;

    private float bestFitness = 0;
    private int bestSnakeScore = 0;

    private int sameBest = 0;
    private float initialMutationRate;

    private T best;
    private int highScore = 0;

    public EvolutionCommunity(int size, float mutationRate, T sample) {
        this.mutationRate = Math.min(mutationRate, EvolutionCommunity.MAX_MUTATION_RATE);
        this.initialMutationRate = this.mutationRate;
        snakes = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            snakes.add(sample.newIndividual());
        }
        bestSnake = snakes.get(0).copy();
    }

    public EvolutionCommunity(T sample) {
        this(EvolutionCommunity.DEFAULT_SIZE, EvolutionCommunity.DEFAULT_MUTATION_RATE, sample);
    }

    @Override
    public boolean isDead() {  // check if all the snakes in the population are dead
        for (T snake : snakes) {
            if (!snake.isDead()) {
                return false;
            }
        }
        return bestSnake.isDead();
    }

    @Override
    public void tick() {  // update all the snakes in the generation
        if (!bestSnake.isDead()) {  // if the best snake is not dead update it, this snake is a replay of the best from the past generation
            bestSnake.tick();
        }
        for (T snake : snakes) {
            if (!snake.isDead()) {
                snake.tick();
            }
        }
    }

    private void setBestSnake() {  // set the best snake of the generation
        float max = 0;
        int maxIndex = 0;
        for (int i = 0; i < snakes.size(); i++) {
            float f = snakes.get(i).fitness();
            if (f > max) {
                max = f;
                maxIndex = i;
            }
        }

        if (max > bestFitness) {
            bestSnake = snakes.get(maxIndex).copy();
            bestFitness = max;
            bestSnakeScore = snakes.get(maxIndex).getScore();

            sameBest = 0;
            mutationRate = initialMutationRate;
        } else {
            bestSnake = bestSnake.copy();

            sameBest++;
            if (sameBest > 7) {
                mutationRate = (float) Math.min(mutationRate + 0.025, EvolutionCommunity.MAX_MUTATION_RATE);
                sameBest = 0;
            }
        }
    }

    private T selectParent() {  // selects a random number in range of the fitnesssum and if a snake falls in that range then select it
        Random random = new Random();
        float rand = random.nextInt((int) fitnessSum());
        float summation = 0;
        for (T snake : snakes) {
            summation += snake.fitness();
            if (summation > rand) {
                return snake;
            }
        }
        return snakes.get(0);
    }

    private float fitnessSum() {  // calculate the sum of all the snakes fitnesses
        float result = 0;
        for (T snake : snakes) {
            result += snake.fitness();
        }
        return result;
    }

//    public void plot() {
//        Chart demo = new Chart("XY Evolution");
//        for (int i = 0; i < snakes.size(); i++) {
//            demo.insert(i, snakes.get(i).fitness());
//        }
//        demo.plot();
//    }

    public void naturalSelection() {
        setBestSnake();

//        plot();

        List<T> newSnakes = new ArrayList<>(snakes.size());
        newSnakes.add(bestSnake.copy());  // add the best snake of the prior generation into the new generation
        for(int i = 1; i < snakes.size(); i++) {
            T child = selectParent().combine(selectParent());
            child.mutate(mutationRate);
            newSnakes.add(child);
        }
        snakes = newSnakes;

        if (bestSnakeScore > highScore) {
            best = bestSnake.copy();
            highScore = bestSnakeScore;
        }
        evolutionScore.add(bestSnakeScore);
        evolutionFitness.add(bestFitness);
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
            System.out.println("Generation: " + generation + ", HighScore: " + highScore + ", MutationRate: " + mutationRate);
            System.out.print("Scores: ");
            printHelper(evolutionScore);
            System.out.print("Fitness: ");
            printHelper(evolutionFitness);
        }
    }
}
