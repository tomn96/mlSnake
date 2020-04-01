package evolution;

import game.SmartSnake;
import game.WindowGame;

public class TestMain {

    public static void main(String[] args) {
        EvolutionCommunity<SmartSnake> ec = new EvolutionCommunityWithBest<>(new SmartSnake());
//        EvolutionCommunity<SmartSnake> ec = new EvolutionCommunity<>(new SmartSnake());

//        int highScore = 0;
//        while (true) {
//            ec.iterate();
//            System.out.println(ec);
//            if (ec.getHighScore() > highScore) {
//                highScore = ec.getHighScore();
//                SmartSnake snake = ec.getHighScoreSnake();
//                WindowGame windowGame = new WindowGame(snake);
//                while (windowGame.running);
//            }
//            System.out.println("");
//        }

        while (true) {
            ec.runIfMakeThis(30, 10, true);
            ec = new EvolutionCommunityWithBest<>(new SmartSnake());
        }
    }
}
