package game;

import evolution.Brain;

import java.util.ArrayList;
import java.util.List;

public class Snake extends GameObject {
    private static final int HIDDEN_NODES = 16;
    private static final int HIDDEN_LAYERS = 2;

    private int score = 3;
    private int lifeLeft = 200;  // amount of moves the snake can make before it dies
    private int lifetime = 0;  // amount of time the snake has been alive
    private int xVelocity, yVelocity;
    private float fitness = 0;
    private boolean dead = false;

    private float[] vision = new float[24];  // snake's vision
    private float[] decision = new float[4];  // snake's decision

    private BoardCoordinate head;
    private List<BoardCoordinate> body = new ArrayList<>(1);  // snake's body
    private Brain brain;


    // not sure yet:

    private BoardCoordinate food;

    private boolean replay = false;  // if this snake is a replay of best snake
    private int foodItterate = 0;  // itterator to run through the foodlist (used for replay)
    private List<BoardCoordinate> foodList = new ArrayList<>();  // list of food positions (used to replay the best snake)

    public Snake(int hidden_nodes, int hidden_layer) {
        head = new BoardCoordinate(0, 0);
        body.add(head);
        brain = new Brain(24, hidden_nodes, 4, hidden_layer);

        food = new BoardCoordinate(1, 1);
        foodList.add(new BoardCoordinate(food));
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

        food = foodList.get(foodItterate);
        foodItterate++;
    }

    boolean bodyCollision() {  // check if snake collides with itself
        for (int i = 1; i < body.size(); i++) {
            if (head == body.get(i)) { // addresses are equal
                System.err.println("Something has gone wrong. The snake's head is not the first Object in the body list.");
                return true;
            } else if (head.equals(body.get(i))) {
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

    private void shiftBody() {
    }

    private void eat() {
    }

}
