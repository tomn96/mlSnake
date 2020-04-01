package game;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class WindowGame extends Game {
    SmartSnake snake;
    Window window;

    public WindowGame(SmartSnake snake) {
        this.snake = snake;
        window = new Window(260, 600, "Snake", this);
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
        window.close();
        super.stop();
    }

    public static void main(String[] args) {
        WindowGame g = new WindowGame(new SmartSnake());
    }
}
