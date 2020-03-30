package game;

public class Board {
    public static final float WIDTH = 10f;
    public static final float HEIGHT = 10f;

    public static boolean wallCollision(BoardCoordinate boardCoordinate) {
        return boardCoordinate.x < 0 || boardCoordinate.x >= Board.WIDTH || boardCoordinate.y < 0 || boardCoordinate.y >= Board.HEIGHT;
    }
}
