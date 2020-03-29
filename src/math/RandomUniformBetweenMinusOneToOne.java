package math;

import java.util.concurrent.Callable;
import java.util.Random;

public class RandomUniformBetweenMinusOneToOne implements Callable<Float> {
    @Override
    public Float call() throws Exception {
        Random rand = new Random();
        return (rand.nextFloat() * 2) - 1;
    }
}
