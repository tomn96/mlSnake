package game;

import evolution.Brain;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

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

    private Food food = new Food();

    private boolean replay = false;  // if this snake is a replay of best snake
    private int foodItterate = 0;  // itterator to run through the foodlist (used for replay)
    private List<Food> foodList = new ArrayList<>();  // list of food positions (used to replay the best snake)

    public Snake(int hidden_nodes, int hidden_layer) {
        head = new BoardCoordinate(0, 0);
        body.add(head);
        brain = new Brain(24, hidden_nodes,4, hidden_layer);

        foodList.add(new Food(food));
    }

    public Snake() {
        this(Snake.HIDDEN_NODES, Snake.HIDDEN_LAYERS);
    }

    public Snake(List<Food> foods) {  // this constructor passes in a list of food positions so that a replay can replay the best snake
        this();

        replay = true;

        foodList = new ArrayList<>(foods.size());
        for(Food f: foods) {
            foodList.add(new Food(f));
        }

        food = foodList.get(foodItterate);
        foodItterate++;
    }

    boolean collision(BoardCoordinate other) {  //check if a position collides with the snakes body
        for (BoardCoordinate body_part : body) {
            if (other.x == body_part.x && other.y == body_part.y) {
                return true;
            }
        }
        return false;
    }
}
