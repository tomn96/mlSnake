package game;

import evolution.Brain;
import evolution.Community;

public class SmartSnake extends BaseSnake implements Community<SmartSnake> {
    private static final int INPUT_NODES = 24;
    private static final int HIDDEN_NODES = 16;
    private static final int OUTPUT_NODES = 4;
    private static final int HIDDEN_LAYERS = 2;

    private int lifeLeft = 200;  // amount of moves the snake can make before it dies
    private int lifetime = 0;  // amount of time the snake has been alive

    private Brain brain;
    private BoardCoordinate initialHead;

    double tempFitness = 0;

    public SmartSnake(BaseBoard board, Brain brain) {
        super(board);
        this.brain = new Brain(brain);
        initialHead = new BoardCoordinate(head);
    }

//    public SmartSnake(Board board) {
//        this(board, new Brain(SmartSnake.INPUT_NODES, SmartSnake.HIDDEN_NODES, SmartSnake.OUTPUT_NODES, SmartSnake.HIDDEN_LAYERS));
//    }
//
//    public SmartSnake(Brain brain) {
//        this(new SimpleBoard(), brain);
//    }

    public SmartSnake() {
        this(new StringBoard(), new Brain(SmartSnake.INPUT_NODES, SmartSnake.HIDDEN_NODES, SmartSnake.OUTPUT_NODES, SmartSnake.HIDDEN_LAYERS));
    }

    @Override
    public SmartSnake newIndividual() {
        return new SmartSnake();
    }

    public SmartSnake same(BaseBoard b) {
        SmartSnake result = new SmartSnake(b, this.brain);
        result.head = new BoardCoordinate(this.initialHead);
        result.initialHead = new BoardCoordinate(this.initialHead);
        return result;
    }

    @Override
    public SmartSnake copy() {
        BaseBoard b = StringBoard.copy((StringBoard) this.board);
        return same(b);
    }

    @Override
    public SmartSnake duplicate() {
        return same(new StringBoard());
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

        calcFitness();
    }

    @Override
    protected void eat() {
        lifeLeft = Math.min(lifeLeft + 100, 500);
        super.eat();
    }

    @Override
    protected void move() {
        float[] vision = look();
        think(vision);
        shiftBody();
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

    private void think(float[] vision) {  // think about what direction to move
        int decision = brain.output(vision);
        if (decision >= 0 && decision < DIRECTIONS.length) {  // don't need to do this - just to be sure...
            velocity = new BoardCoordinate(DIRECTIONS[decision]);
        } else {
            System.err.println("Something went wrong. The snake didn't make any decision where to move to. The decision he made was: " + decision);
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

    private void calcFitness() {
        int a = Math.min(10, score);
        int b = Math.max(1, score - 9);
        tempFitness =  Math.floor(lifetime * lifetime) * Math.pow(2, a) * b;
    }

    @Override
    public double fitness() {
        return tempFitness;
    }
}
