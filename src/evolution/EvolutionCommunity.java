package evolution;

import game.Tickable;

import java.util.*;

public class EvolutionCommunity<T extends Community<T>> implements Tickable, Alive {
    protected static final int DEFAULT_SIZE = 2000;
    protected static final float DEFAULT_MUTATION_RATE = 0.1f;
    protected static final float MAX_MUTATION_RATE = 0.35f;

    private List<Integer> evolutionScore = new LinkedList<>();
    private List<Double> evolutionFitness = new LinkedList<>();
    private int generation = 0;

    protected float mutationRate;

    protected List<T> snakes;

    private T highScoreSnake = null;
    private int highScore = 0;
    private int highScoreGeneration = 0;

    private double tempFitnessSum = 0;

    public EvolutionCommunity(int size, float mutationRate, T initial) {
        this.mutationRate = Math.min(mutationRate, EvolutionCommunity.MAX_MUTATION_RATE);

        snakes = new ArrayList<>(size);
        snakes.add(initial.duplicate());
        for (int i = 1; i < size; i++) {
            snakes.add(initial.newIndividual());
        }
    }

    public EvolutionCommunity(T initial) {
        this(EvolutionCommunity.DEFAULT_SIZE, EvolutionCommunity.DEFAULT_MUTATION_RATE, initial);
    }

    public int getHighScore() {
        return highScore;
    }

    public T getHighScoreSnake() {
        return highScoreSnake;
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

    private void setData() {
        int score = snakes.get(0).getScore();
        evolutionScore.add(score);
        evolutionFitness.add(snakes.get(0).fitness());

        if (score > highScore) {
            highScore = score;
            highScoreGeneration = generation;
            highScoreSnake = snakes.get(0).copy();
        }
    }

    protected void sortSnakesByFitness() {
        snakes.sort((t1, t2) -> (int) (t2.fitness() - t1.fitness()));
        setData();
    }

    private void calcFitnessSum() {  // calculate the sum of all the snakes fitnesses
        double result = 0;
        for (T snake : snakes) {
            result += snake.fitness();
        }
        tempFitnessSum = result;
    }

    private double fitnessSum() {
        return tempFitnessSum;
    }

    protected void naturalSelectionHelper(List<T> newSnakes, int moreToAdd) {
        int legacy = 20;
        int i = 0;
        for (; i < legacy && i < moreToAdd; i++) {
            newSnakes.add(snakes.get(i).duplicate());
        }
        calcFitnessSum();
        for(; i < moreToAdd; i++) {
            T child = selectParent().combine(selectParent());
            child.mutate(mutationRate);
            newSnakes.add(child);
        }
        snakes = newSnakes;

        generation++;
    }

    public void naturalSelection() {
        sortSnakesByFitness();
        naturalSelectionHelper(new ArrayList<>(snakes.size()), snakes.size());
    }

    protected static String bigListStringify(List<?> l) {
        StringBuilder result = new StringBuilder();
        int end = l.size();
        int start = Math.max(0, end - 10);
        if (start > 0) {
            result.append("...");
        }
        result.append(l.subList(start, end).toString());
        return result.toString();
    }

    @Override
    public String toString() {
        return "Generation: " + generation + ", MutationRate: " + mutationRate + '\n' +
        "HighScore: " + highScore + ", Achieved at Generation: " + highScoreGeneration + '\n' +
        "Scores: " + EvolutionCommunity.bigListStringify(evolutionScore) + "\nFitness: " + EvolutionCommunity.bigListStringify(evolutionFitness);
    }

    public void iterate() {
            while (!isDead()) {
                tick();
            }
            naturalSelection();
    }

    public void multipleIterate(int n) {
        for (int i = 0; i < n; i++) {
            iterate();
        }
    }

    public void runIfMakeThis(int generation, int score, boolean print) {
        while (this.generation < generation || this.highScore >= score) {
            iterate();
            if (print) {
                System.out.println(this);
                System.out.println("");
            }
        }
    }

    public void runIfMakeThis(int generation, int score) {
        runIfMakeThis(generation, score, false);
    }
}
