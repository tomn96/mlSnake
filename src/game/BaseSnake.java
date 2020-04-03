package game;

import evolution.Alive;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class BaseSnake implements Alive, Serializable {

    protected static final BoardCoordinate[] DIRECTIONS = {new BoardCoordinate(0, -1), new BoardCoordinate(0, 1), new BoardCoordinate(-1, 0), new BoardCoordinate(1, 0)};

    protected BaseBoard board;

    protected BoardCoordinate head;
    protected List<BoardCoordinate> body = new ArrayList<>();
    protected BoardCoordinate velocity;

    protected boolean dead = false;
    protected int score = 1;

    public BaseSnake(BaseBoard board) {
        this.board = board;
        this.board.setSnake(this);
        int x = math.Utils.marginRandom(this.board.getWidth());
        int y = math.Utils.marginRandom(this.board.getHeight());
        this.head = new BoardCoordinate(x, y);

        this.eat(false);
        this.eat(false);

        Random random = new Random();
        this.velocity = new BoardCoordinate(DIRECTIONS[random.nextInt(DIRECTIONS.length)]);
    }

    public BoardCoordinate getHead() {
        return head;
    }

    public BaseBoard getBoard() {
        return board;
    }

    public int getScore() {
        return score;
    }

    @Override
    public boolean isDead() {
        return dead;
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

    protected boolean bodyCollision() {  // check if snake collides with itself
        return bodyCollision(head);
    }

    public void tick() {
        if (dead) {
            return;
        }

        if (board.snakeFoundFood()) {
            eat();
        }

        move();

        if (board.wallCollision(head) || bodyCollision()) {
            dead = true;
        }
    }

    protected void eat(boolean createNewFood) {
        score++;

        if (body.size() >= 1) {
            body.add(new BoardCoordinate(body.get(body.size() - 1)));
        } else {
            body.add(new BoardCoordinate(head));
        }

        if (createNewFood) {
            board.generateFood(); // TODO - maybe after shiftBody?
        }
    }

    protected void eat() {
        this.eat(true);
    }

    protected abstract void move();

    protected void shiftBody() {  // shift the body to follow the head
        BoardCoordinate temp1 = new BoardCoordinate(head);
        head.add(velocity);

        BoardCoordinate temp2;
        for (int i = 0; i < body.size(); i++) {
            temp2 = new BoardCoordinate(body.get(i));
            body.set(i, temp1);
            temp1 = new BoardCoordinate(temp2);
        }
    }
}
