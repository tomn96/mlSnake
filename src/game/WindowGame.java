package game;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class WindowGame extends Game {
    SmartSnake snake;

    public WindowGame() {
        snake = new SmartSnake();
        new Window(260, 600, "Snake", this);
//        this.addKeyListener(snake);
    }

    @Override
    public void render(Object object) {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 260, 600);
        ((SimpleBoard)snake.getBoard()).render(g);

        g.dispose();
        bs.show();
    }

    @Override
    public void tick() {
        if (!snake.isDead()) {
            snake.tick();
        } else {
            snake = new SmartSnake();
        }
    }

    public static void main(String[] args) {
        WindowGame g = new WindowGame();
    }
}
