package math;

import java.util.Random;

public class Utils {
    public static int marginRandom(int bound) {
        Random random = new Random();
        if (bound > 2) {
            return random.nextInt(bound - 2) + 1;
        } else {
            return random.nextInt(bound);
        }
    }
}
