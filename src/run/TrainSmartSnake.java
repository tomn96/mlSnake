package run;

import evolution.EvolutionCommunity;
import evolution.EvolutionCommunityWithBest;
import game.SmartSnake;

import java.io.File;
import java.time.LocalDateTime;



public class TrainSmartSnake {

    public static void main(String[] args) {
        EvolutionCommunity<SmartSnake> ec1 = new EvolutionCommunityWithBest<>(new SmartSnake());
        EvolutionCommunity<SmartSnake> ec2 = new EvolutionCommunity<>(new SmartSnake());

        int[][] conditions = {{30, 10}, {100, 50}, {200, 75}, {500, 100}, {1000, 150}, {10000, 300}};

        String path = "train_1";
        (new File(path)).mkdir();

        LocalDateTime time;
        String fullname;
        while (true) {
            time = LocalDateTime.now();
            fullname = path + "/" + time.toString() + "_withbest";
            (new File(fullname)).mkdir();
            ec1.runIfMakeConditions(conditions, true, fullname);
            ec1 = new EvolutionCommunityWithBest<>(new SmartSnake());

            time = LocalDateTime.now();
            fullname = path + "/" + time.toString() + "_normal";
            (new File(fullname)).mkdir();
            ec2.runIfMakeConditions(conditions, true, fullname);
            ec2 = new EvolutionCommunity<>(new SmartSnake());
        }
    }
}
