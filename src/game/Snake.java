package game;

import evolution.Brain;
import evolution.Combinable;
import evolution.Mutable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Snake extends GameObject implements Mutable, Combinable<Snake> {
    private static final int HIDDEN_NODES = 16;
    private static final int HIDDEN_LAYERS = 2;

    Random random = new Random();

    private int score = 3;
    private float fitness = 0;
    private boolean dead = false;
    private int lifeLeft = 200;  // amount of moves the snake can make before it dies
    private int lifetime = 0;  // amount of time the snake has been alive

    private float[] vision = new float[24];
    private float[] decision = new float[4];

    private int xVelocity = 0;
    private int yVelocity = 0;

    private BoardCoordinate head = new BoardCoordinate(0, 0); // TODO - random?
    private List<BoardCoordinate> body = new ArrayList<>();

    private Brain brain;

    // TODO - move these out of this class (all food in general):
    private BoardCoordinate food;
    private List<BoardCoordinate> foodList = new ArrayList<>();  // list of food positions (used to replay the best snake)
    private int foodItterate = 0;  // itterator to run through the foodlist (used for replay)

    private boolean replay = false;  // if this snake is a replay of best snake


    public Snake(Brain brain) {
        brain = new Brain(brain);

        // TODO - move this out of here:
        food = new BoardCoordinate(1, 1);
        foodList.add(new BoardCoordinate(food));
    }

    public Snake(int hidden_nodes, int hidden_layer) {
        this(new Brain(24, hidden_nodes, 4, hidden_layer)); // TODO: can be faster - don't call this() constructor but build myself
    }

    public Snake() {
        this(Snake.HIDDEN_NODES, Snake.HIDDEN_LAYERS);
    }

    public Snake(List<BoardCoordinate> foods) {  // this constructor passes in a list of food positions so that a replay can replay the best snake
        this();

        replay = true;

        foodList = new ArrayList<>(foods.size());
        for (BoardCoordinate f : foods) {
            foodList.add(new BoardCoordinate(f));
        }

        food = new BoardCoordinate(foodList.get(foodItterate));
        foodItterate++;
    }

    boolean bodyCollision() {  // check if snake collides with itself
        for (BoardCoordinate bc : body) {
            if (head.equals(bc)) {
                return true;
            }
        }
        return false;
    }

    boolean foundFood() {  // check if a snake found food
        return head.equals(food);
    }

    boolean wallCollision() {
        return Board.wallCollision(head);
    }

    void move() {
        if (!dead) {
            lifetime++;
            lifeLeft--;
            if (foundFood()) {
                eat();
            }
            shiftBody();
            if (wallCollision()) {
                dead = true;
            } else if (bodyCollision()) {
                dead = true;
            } else if (lifeLeft <= 0) {
                dead = true;
            }
        }
    }

    private void eat() {
        score++;
        lifeLeft = Math.min(lifeLeft + 100, 500);

        if (body.size() >= 1) {
            body.add(new BoardCoordinate(body.get(body.size() - 1)));
        } else {
            body.add(new BoardCoordinate(head));
        }

        if (!replay) { // TODO: remove replay from here - all the generation of food in general
            food = new BoardCoordinate(random.nextInt(Board.WIDTH), random.nextInt(Board.HEIGHT));
            while (foundFood()) {
                food = new BoardCoordinate(random.nextInt(Board.WIDTH), random.nextInt(Board.HEIGHT));
            }
            foodList.add(new BoardCoordinate(food));
        } else {  //if the snake is a replay, then we dont want to create new random foods, we want to see the positions the best snake had to collect
            food = new BoardCoordinate(foodList.get(foodItterate));
            foodItterate++;
        }
    }

    private void shiftBody() {  // shift the body to follow the head
        BoardCoordinate temp1 = new BoardCoordinate(head);
        head.x += xVelocity;
        head.y += yVelocity;

        BoardCoordinate temp2;
        for(int i = 0; i < body.size(); i++) {
            temp2 = new BoardCoordinate(body.get(i));
            body.set(i, temp1);
            temp1 = new BoardCoordinate(temp2);
        }
    }

    @Override
    public Snake combine(Snake other) {
        Snake combined = new Snake();
        combined.brain = this.brain.combine(other.brain);
        return combined;
    }

    @Override
    public void mutate(float rate) {
        brain.mutate(rate);
    }
}
