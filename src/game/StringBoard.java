package game;

import java.awt.*;
import java.util.List;

public class StringBoard extends Board implements Renderable<Graphics> {
    public static final int WIDTH = 38;
    public static final int HEIGHT = 38;

    public StringBoard(BaseSnake snake) {
        super(snake, StringBoard.WIDTH, StringBoard.HEIGHT);
    }

    public StringBoard() {
        this(null);
    }

    protected StringBoard(BaseSnake snake, int width, int height, List<BoardCoordinate> foods) {
        super(snake, width, height, foods);
    }

    @Override
    protected boolean collide(BoardCoordinate a, BoardCoordinate b) {
        return a.equals(b);
    }

    @Override
    public boolean wallCollision(BoardCoordinate boardCoordinate) {
        return boardCoordinate.x < 0 || boardCoordinate.x >= width || boardCoordinate.y < 0 || boardCoordinate.y >= height;
    }

    public static StringBoard copy(StringBoard board) {
        return new StringBoard(null, board.width, board.height, board.foodList);
    }

    @Override
    public void render(Graphics object) {
        int x_offset = 50;
        int y_offset = 100;
        int tile_size = 5;

        if (WindowGame.highscore < snake.getScore()) {
            WindowGame.highscore = snake.getScore();
        }

        object.setColor(Color.BLACK);
        object.drawString("Score: " + snake.getScore(), 95, 60);
        object.drawRect(x_offset, y_offset, StringBoard.WIDTH * tile_size, StringBoard.HEIGHT * tile_size);

        object.setColor(Color.RED);
        object.fillRect((int) ((food.x * tile_size) + x_offset), (int) ((food.y * tile_size) + y_offset), tile_size, tile_size);
        object.setColor(Color.BLACK);
        object.drawRect((int) ((food.x * tile_size) + x_offset), (int) ((food.y * tile_size) + y_offset), tile_size, tile_size);

        List<BoardCoordinate> coordinates = snake.getCoordinates();
        for (int i = 0; i < coordinates.size(); i++) {
            if (i == 0) {
                object.setColor(Color.GRAY);
            } else {
                object.setColor(Color.GREEN);
            }
            BoardCoordinate coordinate = coordinates.get(i);
            object.fillRect((int) ((coordinate.x * tile_size) + x_offset), (int) ((coordinate.y * tile_size) + y_offset), tile_size, tile_size);
        }

        for (BoardCoordinate coordinate : coordinates) {
            object.setColor(Color.BLACK);
            object.drawRect((int) ((coordinate.x * tile_size) + x_offset), (int) ((coordinate.y * tile_size) + y_offset), tile_size, tile_size);
        }
    }
}
