package run;

import game.HumanSnake;
import game.SimpleBoard;
import game.WindowGame;

import java.util.concurrent.TimeUnit;

public class PlayHuman {

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            HumanSnake snake = new HumanSnake(new SimpleBoard());
            WindowGame game = new WindowGame(snake, 5);
            game.join();
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
