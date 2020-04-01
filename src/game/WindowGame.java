package game;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class WindowGame extends Game {
    public static final int WIDTH = 260;
    public static final int HEIGHT = 600;

    private BaseSnake snake;
    private Window window;

    public WindowGame(BaseSnake snake) {
        this.snake = snake;
        window = new Window(WindowGame.WIDTH, WindowGame.HEIGHT, "mlSnake", this);
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
        g.fillRect(0, 0, WindowGame.WIDTH, WindowGame.HEIGHT);
        snake.getBoard().render(g);

        g.dispose();
        bs.show();
    }

    @Override
    public void tick() {
        if (!snake.isDead()) {
            snake.tick();
        } else {
            stop();
        }
    }

    @Override
    public void stop() {
        super.stop();
        window.close();
    }
}
