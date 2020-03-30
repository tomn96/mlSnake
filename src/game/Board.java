package game;

public class Board {
    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;

    public static boolean wallCollision(BoardCoordinate boardCoordinate) {
        return boardCoordinate.x < 0 || boardCoordinate.x >= Board.WIDTH || boardCoordinate.y < 0 || boardCoordinate.y >= Board.HEIGHT;
    }
}
