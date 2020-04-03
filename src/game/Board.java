package game;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board implements Renderable<Graphics>, Serializable {
    public static final int WIDTH = 38;
    public static final int HEIGHT = 38;

    public static Board copy(Board board) {
        return new Board(null, board.width, board.height, board.foodList);
    }

    protected int width;
    protected int height;
    protected BaseSnake snake;
    protected BoardCoordinate food;

    protected List<BoardCoordinate> foodList;  // list of food positions (used to replay the best snake)
    private int foodIndex = 0;  // iterator to run through the foodlist (used for replay)

    protected Board(BaseSnake snake, int width, int height, boolean initiateFood) {
        this.width = width;
        this.height = height;
        this.snake = snake;
        if (initiateFood) {
            Random random = new Random();
            food = new BoardCoordinate(random.nextInt(this.width), random.nextInt(this.height));
            this.foodList = new ArrayList<>();
            this.foodList.add(this.food);
            foodIndex++;
        }
    }

    protected Board(BaseSnake snake, int width, int height) {
        this(snake, width, height, true);
    }

    protected Board(BaseSnake snake, int width, int height, List<BoardCoordinate> foods) {
        this(snake, width, height, false);

        foodList = new ArrayList<>(foods.size());
        for (BoardCoordinate f : foods) {
            foodList.add(new BoardCoordinate(f));
        }

        if (foodList.size() > 0) {
            food = new BoardCoordinate(foodList.get(foodIndex));
        } else {
            Random random = new Random();
            food = new BoardCoordinate(random.nextInt(width), random.nextInt(height));
            foodList = new ArrayList<>();
            foodList.add(food);
        }
        foodIndex++;
    }

    public Board(BaseSnake snake) {
        this(null, Board.WIDTH, Board.HEIGHT);
    }

    public Board() {
        this(null);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setSnake(BaseSnake snake) {
        this.snake = snake;
    }

    public void generateFood() {
        if (foodIndex < foodList.size()) {
            food = new BoardCoordinate(foodList.get(foodIndex));
        } else {
            Random random = new Random();
            food = new BoardCoordinate(random.nextInt(width), random.nextInt(height));
            if (snake != null) {
                while (snakeFoundFood() || snake.bodyCollision(food)) {  // TODO - might be infinite
                    food = new BoardCoordinate(random.nextInt(width), random.nextInt(height));
                }
            }
            foodList.add(new BoardCoordinate(food));
        }
        foodIndex++;
    }

    protected boolean collide(BoardCoordinate a, BoardCoordinate b) {
        return a.equals(b);
    }

    public boolean foundFood(BoardCoordinate bc) {
        return collide(bc, food);
    }

    public boolean snakeFoundFood() {  // check if a snake found food
        if (snake == null) {
            return false;
        }
        return collide(snake.getHead(), food);
    }

    public boolean wallCollision(BoardCoordinate boardCoordinate) {
        return boardCoordinate.x < 0 || boardCoordinate.x >= width || boardCoordinate.y < 0 || boardCoordinate.y >= height;
    }

    public void stringifyCoordinate(StringBuilder graphic, BoardCoordinate coordinate, char sign) {
        if (wallCollision(coordinate)) {
            return;
        }
        int location = (int) ((coordinate.y * width) + coordinate.x);
        graphic.replace(location, location + 1, String.valueOf(sign));
    }

    private void stringifyFood(StringBuilder graphic) {
        stringifyCoordinate(graphic, food, '0');
    }

    private void stringifySnake(StringBuilder graphic) {
        if (snake == null) {
            return;
        }
        for (BoardCoordinate coordinate : snake.getCoordinates()) {
            stringifyCoordinate(graphic, coordinate, 'x');
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("-".repeat(width * height));
        stringifyFood(result);
        stringifySnake(result);

        for (int i = 1; i < height; i++) {
            result.insert((i * width) + i - 1, "\n");
        }
        return result.toString();
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
        object.drawRect(x_offset, y_offset, Board.WIDTH * tile_size, Board.HEIGHT * tile_size);

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
