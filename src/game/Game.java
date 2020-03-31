package game;

import java.awt.*;

public abstract class Game extends Canvas implements Runnable, Tickable, Renderable<Object> {
    private Thread thread;
    private boolean running = false;

    @Override
    public void run() {
        double amountOfTicksPerSec = 5.0;
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
                render(null);
                tick();
                delta--;
            }
//            if (running) {
//                render(null);
//            }

            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
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
}
