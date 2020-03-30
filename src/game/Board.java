package game;

import java.util.ArrayList;
import java.util.List;

public class Board {
    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;

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
            this.food = generateFood();
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

    private BoardCoordinate generateFood() {  // TODO - change implementation
        return new BoardCoordinate(1,1);
    }

    public boolean snakeFoundFood() {  // check if a snake found food
        return snake.getHead().equals(food);
    }

    static boolean wallCollision(BoardCoordinate boardCoordinate) {
        return boardCoordinate.x < 0 || boardCoordinate.x >= Board.WIDTH || boardCoordinate.y < 0 || boardCoordinate.y >= Board.HEIGHT;
    }
}
