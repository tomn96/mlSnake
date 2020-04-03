package run;

import game.*;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Play {

    private static void play(Callable<BaseSnake> generator, double amountOfTicksPerSec, long rounds, long timeout) {

        while (rounds != 0) {
            BaseSnake snake = null;
            try {
                snake = generator.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
            WindowGame.generation++;
            WindowGame game = new WindowGame(snake, amountOfTicksPerSec);
            game.join();
            if (timeout > 0) {
                try {
                    TimeUnit.SECONDS.sleep(timeout);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (rounds > 0) {
                rounds--;
            }
        }
    }

    private static void play(Callable<BaseSnake> generator, double amountOfTicksPerSec) {
        Play.play(generator, amountOfTicksPerSec, -1, 1);
    }

    private static void playSimpleVisionAlgorithm() {
        play(VisionSnake::new, 60);
    }

    private static void playHuman() {
        play(HumanSnake::new, 10);
    }

    public static void main(String[] args) throws InterruptedException {
        playHuman();
//        playSimpleVisionAlgorithm();
    }
}
