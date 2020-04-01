package evolution;

import game.SmartSnake;

public class TestMain {

    public static void main(String[] args) {
        EvolutionCommunityWithBest<SmartSnake> ec = new EvolutionCommunityWithBest<>(new SmartSnake());
        EvolutionCommunity<SmartSnake> ecb = new EvolutionCommunity<>(new SmartSnake());
    }
}
