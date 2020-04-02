package game;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

public class WindowGame extends BaseGame {
    public static final int WIDTH = 260;
    public static final int HEIGHT = 600;

    private BaseSnake snake;
    private Window window;

    public WindowGame(BaseSnake snake, double amountOfTicksPerSec) {
        super(amountOfTicksPerSec);
        this.snake = snake;
        window = new Window(WindowGame.WIDTH, WindowGame.HEIGHT, "mlSnake", this);

        if (this.snake instanceof KeyListener) {
            this.addKeyListener((KeyListener) this.snake);
        }
    }

    public WindowGame(BaseSnake snake) {
        this(snake, WindowGame.DEFAULT_AMOUNT_OF_TICKS_PER_SEC);
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
