package game;

public class CmdGame extends Game {
    VisionSnake snake;

    public CmdGame() {
        snake = new VisionSnake();
        this.start();
    }

    @Override
    public void render(Object object) {
        System.out.println(snake.getBoard());
        System.out.println("");
    }

    @Override
    public void tick() {
        snake.tick();
    }

    public static void main(String[] args) {
        CmdGame g = new CmdGame();
    }
}
