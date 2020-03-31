package evolution;

public interface Alive {
    default boolean isAlive() {
        return !isDead();
    }
    boolean isDead();
}
