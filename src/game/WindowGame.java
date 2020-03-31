package game;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class WindowGame extends Game {
    HumanSnake snake;

    public WindowGame() {
        snake = new HumanSnake();
        new Window(640, 480, "Snake", this);
        this.addKeyListener(snake);
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
        g.fillRect(0, 0, 640, 480);
        ((RenderBoard)snake.getBoard()).render(g);

        g.dispose();
        bs.show();
    }

    @Override
    public void tick() {
        snake.tick();
    }

    public static void main(String[] args) {
        WindowGame g = new WindowGame();
    }
}
