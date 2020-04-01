package run;

import evolution.EvolutionCommunity;
import evolution.EvolutionCommunityWithBest;
import game.SmartSnake;
import game.WindowGame;

public class TrainSmartSnake {

    public static void main(String[] args) {
        EvolutionCommunityWithBest<SmartSnake> ec = new EvolutionCommunityWithBest<>(new SmartSnake());
//        EvolutionCommunity<SmartSnake> ec = new EvolutionCommunity<>(new SmartSnake());

        int highScore = 0;
        while (true) {
            ec.iterate();
            System.out.println(ec);
//            if (ec.getHighScore() > highScore) {
//                highScore = ec.getHighScore();
//                SmartSnake snake = ec.getHighScoreSnake();
//                WindowGame windowGame = new WindowGame(snake);
//                windowGame.start();
//                while (windowGame.running);
//            }

            SmartSnake snake = ec.getBestFitnessSnake();
            WindowGame windowGame = new WindowGame(snake);
            windowGame.join();
//            while (windowGame.running);

            System.out.println("abcdefg");
        }

//        while (true) {
//            ec.runIfMakeThis(30, 10, true);
//            ec = new EvolutionCommunityWithBest<>(new SmartSnake());
//        }
    }
}
