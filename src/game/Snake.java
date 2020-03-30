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

    private Random random = new Random();

    private int score = 3;
    private boolean dead = false;
    private int lifeLeft = 200;  // amount of moves the snake can make before it dies
    private int lifetime = 0;  // amount of time the snake has been alive

    private BoardCoordinate head = new BoardCoordinate(0, 0); // TODO - random?
    private List<BoardCoordinate> body = new ArrayList<>();

    private Brain brain;
    private Board board;

    // TODO - move these out of this class (all food in general):
    private BoardCoordinate food;
    private List<BoardCoordinate> foodList = new ArrayList<>();  // list of food positions (used to replay the best snake)
    private int foodItterate = 0;  // itterator to run through the foodlist (used for replay)

    private boolean replay = false;  // if this snake is a replay of best snake


    public Snake(Brain brain) {
        this.board = new Board(this);
        this.brain = new Brain(brain);

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

    public BoardCoordinate getHead() {
        return head;
    }

    private boolean bodyCollision(BoardCoordinate other) {  // check if snake collides with itself
        for (BoardCoordinate bc : body) {
            if (other.equals(bc)) {
                return true;
            }
        }
        return false;
    }

    private boolean bodyCollision() {  // check if snake collides with itself
        return bodyCollision(head);
    }

    private void move() {  // TODO - change to 'tick'
        if (dead) {
            return;
        }

        lifetime++;
        lifeLeft--;
        if (board.snakeFoundFood()) {
            eat();
        }

        realMove();

        if (Board.wallCollision(head) || bodyCollision() || lifeLeft <= 0) {
            dead = true;
        }
    }

    private void realMove() {  // TODO - change to 'move'
        float[] vision = look();
        BoardCoordinate velocity = think(vision);
        shiftBody(velocity);
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
            while (board.snakeFoundFood()) {
                food = new BoardCoordinate(random.nextInt(Board.WIDTH), random.nextInt(Board.HEIGHT));
            }
            foodList.add(new BoardCoordinate(food));
        } else {  //if the snake is a replay, then we dont want to create new random foods, we want to see the positions the best snake had to collect
            food = new BoardCoordinate(foodList.get(foodItterate));
            foodItterate++;
        }
    }

    private void shiftBody(BoardCoordinate velocity) {  // shift the body to follow the head
        BoardCoordinate temp1 = new BoardCoordinate(head);
        head.add(velocity);

        BoardCoordinate temp2;
        for (int i = 0; i < body.size(); i++) {
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

    private float fitness() {
        int a = Math.min(10, score);
        int b = Math.max(1, score - 9);
        return (float) (Math.floor(lifetime * lifetime) * Math.pow(2, a) * b);
    }

    private float[] look() {  // look in all 8 directions and check for food, body and wall
        float[] vision = new float[24];

        int k = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                float[] temp = lookInDirection(new BoardCoordinate(i, j));
                System.arraycopy(temp, 0, vision, k, 3);
                k += 3;
            }
        }

        return vision;
    }

    private float[] lookInDirection(BoardCoordinate direction) {  // look in a direction and check for food, body and wall
        float[] result = new float[3]; // [food, body, wall]

        BoardCoordinate pos = new BoardCoordinate(head);
        pos.add(direction);
        float distance = 1;

        boolean foodFound = false;
        boolean bodyFound = false;

        while (!Board.wallCollision(pos)) {
            if (!foodFound && pos.equals(food)) {
                foodFound = true;
                result[0] = 1;
            }
            if (!bodyFound && bodyCollision(pos)) {
                bodyFound = true;
                result[1] = 1;
            }
            pos.add(direction);
            distance += 1;
        }
        result[2] = 1 / distance;
        return result;
    }

    private BoardCoordinate think(float[] vision) {  // think about what direction to move
        int decision = brain.output(vision);

        switch(decision) {
            case 0:
                return new BoardCoordinate(-1, 0);
            case 1:
                return new BoardCoordinate(1, 0);
            case 2:
                return new BoardCoordinate(0, -1);
            case 3:
                return new BoardCoordinate(0, 1);
            default:
                System.err.println("Something went wrong. The snake didn't make any decision where to move to. The decision he made was: " + decision);
                return new BoardCoordinate(0, 0);
        }
    }
}
