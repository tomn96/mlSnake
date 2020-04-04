package run;

import game.SmartSnake;
import game.WindowGame;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModelRunner {

    private static volatile SmartSnake replay;

    private static int getGeneration(String path) {
        int g = -1;
        String[] s = path.split("_");
        if (s.length >= 2) {
            g = Integer.parseInt(s[s.length - 2]);
        }
        return g;
    }

    private static double ticksOfGeneration(int generation) {
        if (generation < 5) {
            return 100;
        }
        double r = (600 * generation + 33200) / 169.0;
        return Math.min(2000, r);
    }

    private static void runModel(String path, int generation, boolean slowDown, int onScore) {
        SmartSnake snake = (SmartSnake) ModelManager.loadSnake(path);
        replay = snake.copy();
        WindowGame game = new WindowGame(replay, ticksOfGeneration(generation));
        if (slowDown) {
            while (replay.getScore() < onScore - 1);
            game.setAmountOfTicksPerSec(60);
            while (replay.getScore() < onScore);
            game.setAmountOfTicksPerSec(5);
        }
        game.join();
    }

    private static void runModel(String path, int generation) {
        runModel(path, generation, false, -1);
    }

    private static void runModels(String[] paths, int[] scores) {
        if (scores != null && paths.length != scores.length) {
            return;
        }

        int currentHighscore = 0;
        for (int i = 0; i < paths.length; i++) {
            String path = paths[i];

            int g = getGeneration(path);
            WindowGame.generation = g;

            if (scores == null) {
                runModel(path, g);
            } else {
                if (scores[i] > currentHighscore) {
                    currentHighscore = scores[i];
                    runModel(path, g, i == paths.length - 1, currentHighscore);
                }
            }
        }
    }

    private static void runModels(String[] paths) {
        runModels(paths, null);
    }


    private static int[] getScores(String[] paths, boolean print) {
        int[] scores = new int[paths.length];
        for (int i = 0; i < paths.length; i++) {
            if (print) {
                System.out.printf("Calculating Scores: %06.2f%%", (((double) i) / paths.length) * 100);
            }
            SmartSnake snake = (SmartSnake) ModelManager.loadSnake(paths[i]);
            while (!snake.isDead()) {
                snake.tick();
            }
            scores[i] = snake.getScore();
            if (print) {
                System.out.print("\r");
            }
        }
        if (print) {
            System.out.printf("Calculating Scores: %06.2f%%\n", 100.0);
            System.out.println(Arrays.toString(scores));
        }
        return scores;
    }

    private static int[] getScores(String[] paths) {
        return getScores(paths, true);
    }

    private static void runDirOfModels(String dirPath, boolean score, int[] scores) {
        File dir = new File(dirPath);
        if (!dir.isDirectory()) {
            return;
        }
        File[] directoryListing = dir.listFiles();
        if (directoryListing == null) {
            return;
        }

        List<String> lpaths= new ArrayList<>(directoryListing.length);
        for (File file : directoryListing) {
            lpaths.add(file.getPath());
        }

        lpaths.sort((path_1, path_2) -> {
            int g1 = getGeneration(path_1);
            int g2 = getGeneration(path_2);
            if (g1 == -1 || g2 == -1) {
                return path_1.compareTo(path_2);
            }
            return g1 - g2;
        });

        String[] paths = new String[lpaths.size()];
        for (int i = 0; i < lpaths.size(); i++) {
            paths[i] = lpaths.get(i);
        }

        if (score) {
            if (scores != null) {
                runModels(paths, scores);
            } else {
                runModels(paths, getScores(paths));
            }
        } else {
            runModels(paths);
        }
    }

    private static void runDirOfModels(String dirPath, boolean score) {
        runDirOfModels(dirPath, score, null);
    }

    public static void main(String[] args) {
        runDirOfModels("train_0", true);
    }
}
