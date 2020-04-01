package run;

import evolution.EvolutionCommunity;
import evolution.EvolutionCommunityWithBest;
import game.SmartSnake;


public class TrainSmartSnake {

    public static void main(String[] args) {
        EvolutionCommunity<SmartSnake> ec1 = new EvolutionCommunityWithBest<>(new SmartSnake());
        EvolutionCommunity<SmartSnake> ec2 = new EvolutionCommunity<>(new SmartSnake());

        int[][] conditions = {{30, 10}, {100, 50}, {200, 75}};

        while (true) {
            ec1.runIfMakeConditions(conditions, true);
            ec1 = new EvolutionCommunityWithBest<>(new SmartSnake());

            ec2.runIfMakeConditions(conditions, true);
            ec2 = new EvolutionCommunity<>(new SmartSnake());
        }
    }
}
