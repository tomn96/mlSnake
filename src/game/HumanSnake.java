package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class HumanSnake extends BaseSnake implements KeyListener {

    public HumanSnake(Board board) {
        super(board);
    }

    public HumanSnake() {
        this(new Board());
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {}

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_UP:
                velocity = new BoardCoordinate(DIRECTIONS[0]);
                break;
            case KeyEvent.VK_DOWN:
                velocity = new BoardCoordinate(DIRECTIONS[1]);
                break;
            case KeyEvent.VK_LEFT:
                velocity = new BoardCoordinate(DIRECTIONS[2]);
                break;
            case KeyEvent.VK_RIGHT:
                velocity = new BoardCoordinate(DIRECTIONS[3]);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {}

    @Override
    protected void move() {
        shiftBody();
    }
}
