package game;

import evolution.Brain;
import evolution.Combinable;
import evolution.Mutable;

import java.util.ArrayList;
import java.util.List;

public class Snake extends GameObject implements Mutable, Combinable<Snake> {
    private static final int HIDDEN_NODES = 16;
    private static final int HIDDEN_LAYERS = 2;

    private int score = 3;
    private boolean dead = false;
    private int lifeLeft = 200;  // amount of moves the snake can make before it dies
    private int lifetime = 0;  // amount of time the snake has been alive

    private BoardCoordinate head = new BoardCoordinate(0, 0); // TODO - random?
    private List<BoardCoordinate> body = new ArrayList<>();

    private Brain brain;
    private Board board;

    public Snake(Brain brain) {
        if (brain == null) {
            this.brain = new Brain(24, Snake.HIDDEN_NODES, 4, Snake.HIDDEN_LAYERS);
        } else {
            this.brain = new Brain(brain);
        }
        board = new Board(this);

        int x = math.Utils.marginRandom(board.getWidth());
        int y = math.Utils.marginRandom(board.getHeight());
        this.head = new BoardCoordinate(x, y);
    }

    public Snake() {
        this(null);
    }

    public BoardCoordinate getHead() {
        return head;
    }

    public Board getBoard() {
        return board;
    }

    public List<BoardCoordinate> getCoordinates() {
        List<BoardCoordinate> result = new ArrayList<>(1 + body.size());
        result.add(head);
        result.addAll(body);
        return result;
    }

    public boolean bodyCollision(BoardCoordinate other) {  // check if snake collides with itself
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

    public void move() {  // TODO - change to 'tick'
        if (dead) {
            return;
        }

        lifetime++;
        lifeLeft--;
        if (board.snakeFoundFood()) {
            eat();
        }

        realMove();

        if (board.wallCollision(head) || bodyCollision() || lifeLeft <= 0) {
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

        board.generateFood(); // TODO - maybe after shift body?
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

        while (!board.wallCollision(pos)) {
            if (!foodFound && board.foundFood(pos)) {
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

        switch (decision) {
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
