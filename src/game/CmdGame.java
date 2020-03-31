package game;

public class CmdGame extends Game {
    SmartSnake snake;

    public CmdGame() {
        snake = new SmartSnake();
        this.start();
    }

    @Override
    public void render(Object object) {
        System.out.println(snake.getBoard());
        if (snake.isDead()) {
            System.out.println("DEAD!");
        }
        System.out.println("");
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
        CmdGame g = new CmdGame();
    }
}
