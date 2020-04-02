package game;

import java.awt.*;

public abstract class
BaseGame extends Canvas implements Runnable, Tickable, Renderable<Object> {
    protected static final double DEFAULT_AMOUNT_OF_TICKS_PER_SEC = 60.0;

    private Thread thread = null;
    public boolean running = false;

    double amountOfTicksPerSec;

    public BaseGame(double amountOfTicksPerSec) {
        this.amountOfTicksPerSec = amountOfTicksPerSec;
    }

    public BaseGame() {
        this(BaseGame.DEFAULT_AMOUNT_OF_TICKS_PER_SEC);
    }

    @Override
    public void run() {
        double ns = Math.pow(10, 9) / amountOfTicksPerSec;
        double delta = 0;
        int frames = 0;
        long timer = System.currentTimeMillis();
        long lastTime = System.nanoTime();
        if (running) {
            render(null);
        }
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1){
                tick();
                delta--;
            }
            if (running) {
                render(null);
            }

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
        running = false;
    }

    public void join() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
