package game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class BaseBoard implements Renderable<Graphics> {
    private Random random = new Random();

    protected int width;
    protected int height;
    protected BaseSnake snake;
    protected BoardCoordinate food;

    protected List<BoardCoordinate> foodList;  // list of food positions (used to replay the best snake)
    private int foodIndex = 0;  // iterator to run through the foodlist (used for replay)

    protected BaseBoard(BaseSnake snake, int width, int height, boolean initiateFood) {
        this.width = width;
        this.height = height;
        this.snake = snake;
        if (initiateFood) {
            food = new BoardCoordinate(random.nextInt(this.width), random.nextInt(this.height));
            this.foodList = new ArrayList<>();
            this.foodList.add(this.food);
            foodIndex++;
        }
    }

    public BaseBoard(BaseSnake snake, int width, int height) {
        this(snake, width, height, true);
    }

    protected BaseBoard(BaseSnake snake, int width, int height, List<BoardCoordinate> foods) {
        this(snake, width, height, false);

        foodList = new ArrayList<>(foods.size());
        for (BoardCoordinate f : foods) {
            foodList.add(new BoardCoordinate(f));
        }

        if (foodList.size() > 0) {
            food = new BoardCoordinate(foodList.get(foodIndex));
        } else {
            food = new BoardCoordinate(random.nextInt(width), random.nextInt(height));
            foodList = new ArrayList<>();
            foodList.add(food);
        }
        foodIndex++;
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

    protected abstract boolean collide(BoardCoordinate a, BoardCoordinate b);

    public boolean foundFood(BoardCoordinate bc) {
        return collide(bc, food);
    }

    public boolean snakeFoundFood() {  // check if a snake found food
        if (snake == null) {
            return false;
        }
        return collide(snake.getHead(), food);
    }

    public abstract boolean wallCollision(BoardCoordinate boardCoordinate);

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

}
