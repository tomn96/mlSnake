package game;

import java.awt.*;
import java.util.List;

public class StringBoard extends BaseBoard implements Renderable<Graphics> {
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
        object.setColor(Color.BLACK);
        object.drawString("Score: " + snake.getScore(), 100, 25);
        String all = this.toString();
        String[] lines = all.split("\n");
        for (int i = 0; i < lines.length; i++) {
            object.drawString(lines[i], 50, 50 + (i*10));
        }
    }
}
