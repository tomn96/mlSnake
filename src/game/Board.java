package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;
    private static final int MAX_TRIES_GENERATE_FOOD = 1000;

    private Random random = new Random();

    private int width;
    private int height;

    private Snake snake;
    private BoardCoordinate food;


    private List<BoardCoordinate> foodList;  // list of food positions (used to replay the best snake)
    private int foodIndex = 0;  // itterator to run through the foodlist (used for replay)

    private Board(Snake snake, int width, int height, boolean initiateFood) {
        this.width = width;
        this.height = height;
        this.snake = snake;
        if (initiateFood) {
            food = new BoardCoordinate(random.nextInt(this.width), random.nextInt(this.height));
            this.foodList = new ArrayList<>();
            this.foodList.add(this.food);
        }
    }

    public Board(Snake snake, int width, int height) {
        this(snake, width, height, true);
    }

    public Board(Snake snake) {
        this(snake, Board.WIDTH, Board.HEIGHT);
    }

    public Board(Snake snake, List<BoardCoordinate> foods) {
        this(snake, Board.WIDTH, Board.HEIGHT, false);

        foodList = new ArrayList<>(foods.size());
        for (BoardCoordinate f : foods) {
            foodList.add(new BoardCoordinate(f));
        }

        food = new BoardCoordinate(foodList.get(foodIndex));
        foodIndex++;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void generateFood() {
        food = new BoardCoordinate(random.nextInt(width), random.nextInt(height));
        int tries = 0;
        while (tries < MAX_TRIES_GENERATE_FOOD && (snakeFoundFood() || snake.bodyCollision(food))) {
            food = new BoardCoordinate(random.nextInt(width), random.nextInt(height));
            tries++;
        }
        if (tries >= MAX_TRIES_GENERATE_FOOD) {
            food = new BoardCoordinate(random.nextInt(width), random.nextInt(height));
            tries = 0;
            while (tries < MAX_TRIES_GENERATE_FOOD && snakeFoundFood()) {
                food = new BoardCoordinate(random.nextInt(width), random.nextInt(height));
                tries++;
            }
            if (tries >= MAX_TRIES_GENERATE_FOOD) {
                food = new BoardCoordinate(random.nextInt(width), random.nextInt(height));
            }
        }
        foodList.add(new BoardCoordinate(food));
    }

    public boolean foundFood(BoardCoordinate bc) {
        return bc.equals(food);
    }

    public boolean snakeFoundFood() {  // check if a snake found food
        return foundFood(snake.getHead());
    }

    public boolean wallCollision(BoardCoordinate boardCoordinate) {
        return boardCoordinate.x < 0 || boardCoordinate.x >= width || boardCoordinate.y < 0 || boardCoordinate.y >= height;
    }

    public void renderCoordinate(StringBuilder graphic, BoardCoordinate coordinate, char sign) {
        if (wallCollision(coordinate)) {
            return;
        }
        int location = (int) ((coordinate.y * width) + coordinate.x);
        graphic.replace(location, location + 1, String.valueOf(sign));
    }

    private void renderFood(StringBuilder graphic) {
        renderCoordinate(graphic, food, '0');
    }

    private void renderSnake(StringBuilder graphic) {
        for (BoardCoordinate coordinate : snake.getCoordinates()) {
            renderCoordinate(graphic, coordinate, 'x');
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("-".repeat(width * height));
        renderFood(result);
        renderSnake(result);

        for (int i = 1; i < height; i++) {
            result.insert((i * width) + i - 1, "\n");
        }
        return result.toString();
    }
}
