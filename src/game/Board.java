package game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board implements Renderable<Graphics> {
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;
    private static final int MAX_TRIES_GENERATE_FOOD = 1000;

    private Random random = new Random();

    private int width;
    private int height;
    private BaseSnake snake;
    private BoardCoordinate food;
    private List<BoardCoordinate> foodList;  // list of food positions (used to replay the best snake)

    private int foodIndex = 0;  // iterator to run through the foodlist (used for replay)

    private Board(BaseSnake snake, int width, int height, boolean initiateFood) {
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

    public Board(BaseSnake snake, int width, int height) {
        this(snake, width, height, true);
    }

    public Board(BaseSnake snake) {
        this(snake, Board.WIDTH, Board.HEIGHT);
    }

    private Board(BaseSnake snake, int width, int height, List<BoardCoordinate> foods) {
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


    public static Board copy(BaseSnake snake, Board board) {
        return new Board(snake, board.width, board.height, board.foodList);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void generateFood() {
        if (foodIndex < foodList.size()) {
            food = new BoardCoordinate(foodList.get(foodIndex));
        } else {
            food = new BoardCoordinate(random.nextInt(width), random.nextInt(height));
            while (snakeFoundFood() || snake.bodyCollision(food)) {  // TODO - might be infinite
                food = new BoardCoordinate(random.nextInt(width), random.nextInt(height));
            }
            foodList.add(new BoardCoordinate(food));
        }
        foodIndex++;
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

//    @Override
//    public void render(Graphics object) {
//        object.setColor(Color.BLACK);
//        String all = this.toString();
//        String[] lines = all.split("\n");
//        for (int i = 0; i < lines.length; i++) {
//            object.drawString(lines[i], 10, 10 + (i*5));
//        }
//    }

    @Override
    public void render(Graphics object) {
        object.setColor(Color.RED);
        object.fillRect((int)food.x, (int)food.y, 10, 10);

        object.setColor(Color.GREEN);
        for (BoardCoordinate coordinate : snake.getCoordinates()) {
            object.fillRect((int)coordinate.x, (int)coordinate.y, 10, 10);
        }
    }
}
