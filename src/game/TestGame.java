package game;

import java.awt.*;

public class TestGame implements Runnable, Tickable, Renderable {
    private Thread thread;
    private boolean running = false;

    SmartSnake snake;

    public TestGame() {
        snake = new SmartSnake();
    }

    @Override
    public void run() {
        double amountOfTicksPerSec = 1;
        double ns = Math.pow(10, 9) / amountOfTicksPerSec;
        double delta = 0;
        int frames = 0;
        long timer = System.currentTimeMillis();
        long lastTime = System.nanoTime();
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1){
                tick();
                if (running) {
                    render(null);
                }
                delta--;
            }
//            if (running) {
//                render(null);
//            }

            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
//                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    public synchronized void start() {
        thread = new Thread(this);
        running = true;
        thread.start();
    }

    public void stop() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        running = false;
    }

    @Override
    public void render(Graphics g) {
        System.out.println(snake.getBoard());
        System.out.println("\n");
    }

    @Override
    public void tick() {
        snake.move();
    }

    public static void main(String[] args) {
        TestGame g = new TestGame();
        g.start();
    }
}
