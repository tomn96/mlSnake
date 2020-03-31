package evolution;

import game.SmartSnake;

public class TestMain {

    public static void main(String[] args) {
        EvolutionCommunity<SmartSnake> ec = new EvolutionCommunity<>(new SmartSnake());
        ec.run();
    }
}
