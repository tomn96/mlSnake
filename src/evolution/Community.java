package evolution;

import game.Tickable;

public interface Community<T> extends Alive, Tickable, Combinable<T>, Mutable {
    double fitness();
    int getScore();
    T copy();
    T newIndividual();
}
