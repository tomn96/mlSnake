package game;

import java.util.Objects;

public class BoardCoordinate {
    public float x;
    public float y;

    public BoardCoordinate(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public BoardCoordinate(BoardCoordinate boardCoordinate) {
        this(boardCoordinate.x, boardCoordinate.y);
    }

    public void add(BoardCoordinate boardCoordinate) {
        this.x += boardCoordinate.x;
        this.y += boardCoordinate.y;
    }

    public static float distance(BoardCoordinate a, BoardCoordinate b) {
        return (float) Math.sqrt(((a.x - b.x)*(a.x - b.x)) + ((a.y - b.y)*(a.y - b.y)));
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoardCoordinate)) return false;
        BoardCoordinate that = (BoardCoordinate) o;
        return Float.compare(that.x, x) == 0 &&
                Float.compare(that.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
