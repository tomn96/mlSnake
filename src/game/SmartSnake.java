package game;

import evolution.Brain;
import evolution.Combinable;
import evolution.Mutable;

public class SmartSnake extends BaseSnake implements Mutable, Combinable<SmartSnake> {
    private static final int INPUT_NODES = 24;
    private static final int HIDDEN_NODES = 16;
    private static final int OUTPUT_NODES = 4;
    private static final int HIDDEN_LAYERS = 2;

    private int score = 3;
    private int lifeLeft = 200;  // amount of moves the snake can make before it dies
    private int lifetime = 0;  // amount of time the snake has been alive

    private Brain brain;
    private BoardCoordinate initialHead;

    public SmartSnake(Board board, Brain brain) {
        super(board);
        this.brain = new Brain(brain);
        initialHead = new BoardCoordinate(head);
    }

    public SmartSnake(Board board) {
        this(board, new Brain(SmartSnake.INPUT_NODES, SmartSnake.HIDDEN_NODES, SmartSnake.OUTPUT_NODES, SmartSnake.HIDDEN_LAYERS));
    }

    public SmartSnake() {
        this(new SimpleBoard());
    }

    public static SmartSnake copy(SmartSnake smartSnake) {
        Board board = SimpleBoard.copy((SimpleBoard) smartSnake.board);

        SmartSnake result = new SmartSnake(board, smartSnake.brain);
        result.head = new BoardCoordinate(smartSnake.initialHead);
        result.initialHead = new BoardCoordinate(smartSnake.initialHead);
        return result;
    }

    @Override
    public void tick() {
        if (dead) {
            return;
        }

        lifetime++;
        lifeLeft--;

        super.tick();

        if (lifeLeft <= 0) {
            dead = true;
        }
    }

    @Override
    protected void eat() {
        score++;
        lifeLeft = Math.min(lifeLeft + 100, 500);
        super.eat();
    }

    @Override
    protected void move() {
        float[] vision = look();
        BoardCoordinate velocity = think(vision);
        shiftBody(velocity);
    }

    private float[] look() {  // look in all 8 directions and check for food, body and wall
        float[] vision = new float[SmartSnake.INPUT_NODES];

        int k = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                float[] temp = lookInDirection(new BoardCoordinate(i, j));
                System.arraycopy(temp, 0, vision, k, 3);
                k += 3;
            }
        }

        return vision;
    }

    private float[] lookInDirection(BoardCoordinate direction) {  // look in a direction and check for food, body and wall
        float[] result = new float[3]; // [food, body, wall]

        BoardCoordinate pos = new BoardCoordinate(head);
        pos.add(direction);
        float distance = 1;

        boolean foodFound = false;
        boolean bodyFound = false;

        while (!board.wallCollision(pos)) {
            if (!foodFound && board.foundFood(pos)) {
                foodFound = true;
                result[0] = 1;
            }
            if (!bodyFound && bodyCollision(pos)) {
                bodyFound = true;
                result[1] = 1;
            }
            pos.add(direction);
            distance += 1;
        }
        result[2] = 1 / distance;
        return result;
    }

    private BoardCoordinate think(float[] vision) {  // think about what direction to move
        int decision = brain.output(vision);

        switch (decision) {
            case 0:
                return new BoardCoordinate(-1, 0);
            case 1:
                return new BoardCoordinate(1, 0);
            case 2:
                return new BoardCoordinate(0, -1);
            case 3:
                return new BoardCoordinate(0, 1);
            default:
                System.err.println("Something went wrong. The snake didn't make any decision where to move to. The decision he made was: " + decision);
                return new BoardCoordinate(0, 0);
        }
    }

    @Override
    public SmartSnake combine(SmartSnake other) {
        SmartSnake combined = new SmartSnake();
        combined.brain = this.brain.combine(other.brain);
        return combined;
    }

    @Override
    public void mutate(float rate) {
        brain.mutate(rate);
    }

    private float fitness() {
        int a = Math.min(10, score);
        int b = Math.max(1, score - 9);
        return (float) (Math.floor(lifetime * lifetime) * Math.pow(2, a) * b);
    }
}
