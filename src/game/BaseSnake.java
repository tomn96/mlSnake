package game;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseSnake extends GameObject {

    protected Board board;
    protected BoardCoordinate head;

    protected List<BoardCoordinate> body = new ArrayList<>();
    protected boolean dead = false;

    protected void createHead() {
        int x = math.Utils.marginRandom(board.getWidth());
        int y = math.Utils.marginRandom(board.getHeight());
        this.head = new BoardCoordinate(x, y);
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
            if (board.collide(other, bc)) {
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

    protected void eat() {
        if (body.size() >= 1) {
            body.add(new BoardCoordinate(body.get(body.size() - 1)));
        } else {
            body.add(new BoardCoordinate(head));
        }

        board.generateFood(); // TODO - maybe after shiftBody?
    }

    protected abstract void move();

    protected void shiftBody(BoardCoordinate velocity) {  // shift the body to follow the head
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
