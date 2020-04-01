package evolution;

import game.SmartSnake;
import game.Tickable;
import game.WindowGame;

import java.util.*;

public class EvolutionCommunityBetter<T extends Community<T>> implements Tickable, Alive, Runnable {
    private static final int DEFAULT_SIZE = 2000;
    private static final float DEFAULT_MUTATION_RATE = 0.1f;
    private static final float MAX_MUTATION_RATE = 0.35f;

    private List<Integer> evolutionScore = new LinkedList<>();
    private List<Double> evolutionFitness = new LinkedList<>();
    private int generation = 0;

    private float mutationRate;

    private List<T> snakes;

    private int highScore = 0;
    private int highScoreGeneration = 0;

    public EvolutionCommunityBetter(int size, float mutationRate, T initial) {
        this.mutationRate = Math.min(mutationRate, EvolutionCommunityBetter.MAX_MUTATION_RATE);

        snakes = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
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
        return true;
    }

    @Override
    public void tick() {  // update all the snakes in the generation
        for (T snake : snakes) {
            if (!snake.isDead()) {
                snake.tick();
            }
        }
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

    private void logData() {
        int score = snakes.get(0).getScore();
        evolutionScore.add(score);
        evolutionFitness.add(snakes.get(0).fitness());

        if (score > highScore) {
            highScore = score;
            highScoreGeneration = generation;
        }
    }

    public void naturalSelection() {
        snakes.sort((t1, t2) -> (int) (t2.fitness() - t1.fitness()));
        logData();

        List<T> newSnakes = new ArrayList<>(snakes.size());

        int legacy = 20;
        int i = 0;
        for (; i < legacy; i++) {
            newSnakes.add(snakes.get(i).duplicate());
        }
        for(; i < snakes.size(); i++) {
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
            System.out.println("Generation: " + generation + ", MutationRate: " + mutationRate);
            System.out.println("HighScore: " + highScore + ", Achieved at Generation: " + highScoreGeneration);
            System.out.print("Scores: ");
            printHelper(evolutionScore);
            System.out.print("Fitness: ");
            printHelper(evolutionFitness);
        }
    }
}
