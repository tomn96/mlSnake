package machinelearning;

import java.util.function.Function;

public class ReluActivation implements Function<Float, Float> {
    @Override
    public Float apply(Float aFloat) {
        return Math.max(0, aFloat);
    }
}
