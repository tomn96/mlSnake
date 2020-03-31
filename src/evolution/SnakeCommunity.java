package evolution;

import game.SmartSnake;
import game.Tickable;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SnakeCommunity implements Tickable {

    private static final int DEFAULT_SIZE = 200;
    private static final float DEFAULT_MUTATION_RATE = 0.1f;

    private List<Integer> evolution = new LinkedList<>();
    private int generation = 0;

    private float mutationRate;

    private SmartSnake[] snakes;
    private SmartSnake bestSnake;

    private float bestFitness = 0;
    private int bestSnakeScore = 0;
//    private int samebest = 0;

    public SnakeCommunity(int size, float mutationRate) {
        this.mutationRate = mutationRate;
        snakes = new SmartSnake[size];
        for (int i = 0; i < snakes.length; i++) {
            snakes[i] = new SmartSnake();
        }
        bestSnake = SmartSnake.clone(snakes[0]);
    }

    public SnakeCommunity() {
        this(SnakeCommunity.DEFAULT_SIZE, SnakeCommunity.DEFAULT_MUTATION_RATE);
    }


    public boolean done() {  // check if all the snakes in the population are dead
        for (SmartSnake snake : snakes) {
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
        for (SmartSnake snake : snakes) {
            if (!snake.isDead()) {
                snake.tick();
            }
        }
    }

    private void setBestSnake() {  // set the best snake of the generation
        float max = 0;
        int maxIndex = 0;
        for (int i = 0; i < snakes.length; i++) {
            if (snakes[i].fitness() > max) {
                max = snakes[i].fitness();
                maxIndex = i;
            }
        }

        if (max > bestFitness) {
            bestSnake = SmartSnake.clone(snakes[maxIndex]);
            bestFitness = max;
            bestSnakeScore = snakes[maxIndex].getScore();
        } else {
            bestSnake = SmartSnake.clone(bestSnake);
        }
    }

    private SmartSnake selectParent() {  // selects a random number in range of the fitnesssum and if a snake falls in that range then select it
        Random random = new Random();
        float rand = random.nextInt((int) fitnessSum());
        float summation = 0;
        for (SmartSnake snake : snakes) {
            summation += snake.fitness();
            if (summation > rand) {
                return snake;
            }
        }
        return snakes[0];
    }

    private float fitnessSum() {  // calculate the sum of all the snakes fitnesses
        float result = 0;
        for (SmartSnake snake : snakes) {
            result += snake.fitness();
        }
        return result;
    }

    public void naturalSelection() {
        SmartSnake[] newSnakes = new SmartSnake[snakes.length];

        setBestSnake();

        newSnakes[0] = SmartSnake.clone(bestSnake);  // add the best snake of the prior generation into the new generation
        for(int i = 1; i < snakes.length; i++) {
            SmartSnake child = selectParent().combine(selectParent());
            child.mutate(mutationRate);
            newSnakes[i] = child;
        }
        snakes = newSnakes;
        evolution.add(bestSnakeScore);
        generation++;
    }

    public List<Integer> getEvolution() {
        return evolution;
    }

    public int getGeneration() {
        return generation;
    }
}
