package evolution;

import game.Tickable;

public interface Community<T> extends Alive, Tickable, Combinable<T>, Mutable {
    float fitness();
    int getScore();
    T copy();
}
