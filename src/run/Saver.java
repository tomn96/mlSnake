package run;

import game.SmartSnake;

import java.io.*;

public class Saver {

    public static void main(String[] args) {
        SmartSnake snake = new SmartSnake();
        while (!snake.isDead()) {
            snake.tick();
        }
        try {
            FileOutputStream fileOut = new FileOutputStream("/tmp/snakesaver/snake");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(snake);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SmartSnake s = null;
        try {
            FileInputStream fileIn = new FileInputStream("/tmp/snakesaver/snake");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            s = (SmartSnake) in.readObject();
            in.close();
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert s != null;
    }
}
