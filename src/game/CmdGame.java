package game;

public class CmdGame extends Game {
    BaseSnake snake;

    public CmdGame(BaseSnake snake) {
        this.snake = snake;
        this.start();
    }

    @Override
    public void render(Object object) {
        System.out.println(snake.getBoard());
        System.out.println("");
    }

    @Override
    public void tick() {
        if (!snake.isDead()) {
            snake.tick();
        } else {
            stop();
        }
    }
}
