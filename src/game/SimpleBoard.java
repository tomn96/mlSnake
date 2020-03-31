package game;

import java.util.List;

public class SimpleBoard extends Board {
    public static final int WIDTH = 40;
    public static final int HEIGHT = 40;

    public SimpleBoard(BaseSnake snake) {
        super(snake, SimpleBoard.WIDTH, SimpleBoard.HEIGHT);
    }

    public SimpleBoard() {
        this(null);
    }

    protected SimpleBoard(BaseSnake snake, int width, int height, List<BoardCoordinate> foods) {
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

    public static SimpleBoard copy(SimpleBoard board) {
        return new SimpleBoard(null, board.width, board.height, board.foodList);
    }

//    @Override
//    public void render(Graphics object) {
//        object.setColor(Color.BLACK);
//        String all = this.toString();
//        String[] lines = all.split("\n");
//        for (int i = 0; i < lines.length; i++) {
//            object.drawString(lines[i], 10, 10 + (i*5));
//        }
//    }
}
