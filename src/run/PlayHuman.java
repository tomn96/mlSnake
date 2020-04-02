package run;

import game.HumanSnake;
import game.StringBoard;
import game.WindowGame;

import java.util.concurrent.TimeUnit;

public class PlayHuman {

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            WindowGame.generation++;
            HumanSnake snake = new HumanSnake(new StringBoard());
            WindowGame game = new WindowGame(snake, 5);
            game.join();
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
