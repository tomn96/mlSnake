package run;

import game.BaseSnake;

import java.io.*;

public class ModelManager {

    public static void saveSnake(BaseSnake snake, String filepath) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(snake);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BaseSnake loadSnake(String filepath) {
        BaseSnake snake = null;
        try {
            FileInputStream fileIn = new FileInputStream(filepath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            snake = (BaseSnake) in.readObject();
            in.close();
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return snake;
    }
}
