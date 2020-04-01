package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class HumanSnake extends BaseSnake implements KeyListener {

    BoardCoordinate velocity;

    public HumanSnake(BaseBoard board) {
        super(board);
        velocity = new BoardCoordinate(1, 0);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {}

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_UP:
                velocity = new BoardCoordinate(0, -1);
                break;
            case KeyEvent.VK_DOWN:
                velocity = new BoardCoordinate(0, 1);
                break;
            case KeyEvent.VK_LEFT:
                velocity = new BoardCoordinate(-1, 0);
                break;
            case KeyEvent.VK_RIGHT:
                velocity = new BoardCoordinate(1, 0);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {}

    @Override
    protected void move() {
        shiftBody(velocity);
    }
}
