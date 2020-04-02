package game;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class BaseBoard implements Renderable<Graphics>, Serializable {
    protected int width;
    protected int height;
    protected BaseSnake snake;
    protected BoardCoordinate food;

    private BoardCoordinate renderSnakeHead;
    private List<BoardCoordinate> renderSnakeCoordinates;
    private int lastSnakeBodySize;
    private BoardCoordinate lastSnakeHead;

    protected List<BoardCoordinate> foodList;  // list of food positions (used to replay the best snake)
    private int foodIndex = 0;  // iterator to run through the foodlist (used for replay)

    protected BaseBoard(BaseSnake snake, int width, int height, boolean initiateFood) {
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
            Random random = new Random();
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

    public void helper(Graphics object) {
        if (snake == null) {
            return;
        }

        float parts = 30;

        if (renderSnakeCoordinates == null) {
            renderSnakeCoordinates = new ArrayList<>();
            renderSnakeHead = new BoardCoordinate(snake.getHead());
            renderSnakeCoordinates.add(new BoardCoordinate(snake.getHead().x + (1 / parts), snake.getHead().y + (1 / parts)));
            for (int i = 2; i < parts; i++) {
                renderSnakeCoordinates.add(new BoardCoordinate(snake.getHead().x + (i / parts), snake.getHead().y + (i / parts)));
            }
            lastSnakeBodySize = 0;
            lastSnakeHead = snake.getHead();
        }

        if (lastSnakeBodySize != snake.body.size()) {
            for (int i = 0; i < snake.body.size() - lastSnakeBodySize; i++) {
                for (int j = 0; j < parts; j++) {
                    renderSnakeCoordinates.add(new BoardCoordinate(renderSnakeCoordinates.get(renderSnakeCoordinates.size() - 1)));
                }
            }
            lastSnakeBodySize = snake.body.size();
        }

        if (BoardCoordinate.distance(renderSnakeHead, lastSnakeHead) > 1 / parts) {
            BoardCoordinate direction;
            float x = lastSnakeHead.x - renderSnakeHead.x;
            float y = lastSnakeHead.y - renderSnakeHead.y;
            if (Math.abs(x) > Math.abs(y)) {
                direction = new BoardCoordinate(Math.signum(x) / parts, 0);
            } else {
                direction = new BoardCoordinate(0, Math.signum(y) / parts);
            }

            BoardCoordinate temp1 = new BoardCoordinate(renderSnakeHead);
            renderSnakeHead.add(direction);

            BoardCoordinate temp2;
            for (int i = 0; i < renderSnakeCoordinates.size(); i++) {
                temp2 = new BoardCoordinate(renderSnakeCoordinates.get(i));
                renderSnakeCoordinates.set(i, temp1);
                temp1 = new BoardCoordinate(temp2);
            }
        } else {
            lastSnakeHead = snake.getHead();
        }

        object.setColor(Color.GREEN);
        object.fillRect((int)(renderSnakeHead.x * 20), (int)(renderSnakeHead.y * 20), 20, 20);
        for (BoardCoordinate coordinate : renderSnakeCoordinates) {
            object.fillRect((int)(coordinate.x * 20), (int)(coordinate.y * 20), 20, 20);
        }
    }


    @Override
    public void render(Graphics object) {
        object.setColor(Color.RED);
        object.fillRect((int)food.x * 20, (int)food.y * 20, 20, 20);

        helper(object);
    }

}
