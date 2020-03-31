package game;

import java.awt.*;

public interface Renderable<T> {
    void render(T object);
}
