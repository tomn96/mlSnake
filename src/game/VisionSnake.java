package game;

import java.util.ArrayList;
import java.util.List;

public class VisionSnake extends BaseSnake {


    public VisionSnake(Board board) {
        super(board);
    }

    public VisionSnake() {
        this(new Board());
    }

    private int foodDistance(BoardCoordinate direction) {
        BoardCoordinate pos = new BoardCoordinate(head);
        int distance = 0;
        while (!board.wallCollision(pos) && !board.foundFood(pos)) {
            pos.add(direction);
            distance += 1;
        }
        if (board.wallCollision(pos)) {
            return -1;
        } else {
            return distance;
        }
    }

    private int bodyDistance(BoardCoordinate direction) {
        BoardCoordinate pos = new BoardCoordinate(head);
        int distance = 0;
        while (!board.wallCollision(pos) && !bodyCollision(pos)) {
            pos.add(direction);
            distance += 1;
        }
        if (board.wallCollision(pos) || distance == 0) {
            return -1;
        } else {
            return distance;
        }
    }

    private int wallDistance(BoardCoordinate direction) {
        BoardCoordinate pos = new BoardCoordinate(head);
        int distance = 0;
        while (!board.wallCollision(pos)) {
            pos.add(direction);
            distance += 1;
        }
        return distance;
    }

    protected List<List<Integer>> narrowLook() {
        List<List<Integer>> results = new ArrayList<>(4);

        for (BoardCoordinate direction : DIRECTIONS) {
            List<Integer> temp = new ArrayList<>(3);
            temp.add(foodDistance(direction));
            temp.add(bodyDistance(direction));
            temp.add(wallDistance(direction));
            results.add(temp);
        }

        return results;
    }

    protected void decide(List<List<Integer>> vision) {
        float[] badDecision = {0f, 0f, 0f, 0f};
        int minWall = -2;
        int idxMinWall = -2;

        for (int i = 0; i < vision.size(); i++) {
            int food = vision.get(i).get(0);
            int body = vision.get(i).get(1);
            int wall = vision.get(i).get(2);

            if (food != -1) {  // found food
                if (body == -1 || food < body) { // body is before of the food
                    velocity = new BoardCoordinate(DIRECTIONS[i]);
                    return;
                }
            }
            if (body != -1) { // found body
                badDecision[i] = Math.max(1f / body, badDecision[i]);
            }

            if (wall <= 1) { // could be also 'wall <= 2'
                badDecision[i] = Math.max(1f / wall, badDecision[i]);
            }

            // find min wall distance:
            if (minWall == -2) {
                minWall = wall;
                idxMinWall = i;
            } else {
                if (wall < minWall) {
                    minWall = wall;
                    idxMinWall = i;
                }
            }
        }

        badDecision[idxMinWall] = Math.max(1f / minWall, badDecision[idxMinWall]);

        int idx = 0;
        float minVal = badDecision[0];

        for (int i = 1; i < badDecision.length; i++) {
            if (badDecision[i] < minVal) {
                idx = i;
                minVal = badDecision[i];
            }
        }

        int lastIdx = 0;
        for (int i = 0; i < DIRECTIONS.length; i++) {
            if (DIRECTIONS[i].equals(velocity)) {
                lastIdx = i;
            }
        }
        if (badDecision[lastIdx] == minVal) {
            return;
        }

        velocity = new BoardCoordinate(DIRECTIONS[idx]);
    }

    @Override
    protected void move() {
        List<List<Integer>> vision = narrowLook();
        decide(vision);
        shiftBody();
    }
}
