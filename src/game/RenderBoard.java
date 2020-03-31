package game;

import java.awt.*;
import java.util.List;

public class RenderBoard extends Board implements Renderable<Graphics> {
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;

    private static final float EPSILON = 10;

    private float e;

    public RenderBoard(BaseSnake snake, float epsilon) {
        super(snake, RenderBoard.WIDTH, RenderBoard.HEIGHT);
        e = Math.max(0, epsilon);
    }

    public RenderBoard(BaseSnake snake) {
        this(snake, RenderBoard.EPSILON);
    }

    @Override
    protected boolean collide(BoardCoordinate a, BoardCoordinate b) {
        return BoardCoordinate.distance(a, b) <= e;
    }

    @Override
    public boolean wallCollision(BoardCoordinate boardCoordinate) {
        return boardCoordinate.x < e || boardCoordinate.x >= width - e || boardCoordinate.y < e || boardCoordinate.y >= height - e;
    }

    @Override
    public void render(Graphics object) {
        object.setColor(Color.RED);
        object.fillRect((int)food.x, (int)food.y, 10, 10);

        // TODO - fix snake
        object.setColor(Color.GREEN);
        List<BoardCoordinate> coordinates = snake.getCoordinates();
        for (int i = 0; i < coordinates.size(); i++) {
            object.fillRect((int)coordinates.get(i).x + i * 10, (int)coordinates.get(i).y + i * 10, 10, 10);
        }
//        for (BoardCoordinate coordinate : snake.getCoordinates()) {
//            object.fillRect((int)coordinate.x, (int)coordinate.y, 10, 10);
//        }
    }
}
