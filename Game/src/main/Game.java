package main;

import graphics.GameWindow;
import java.lang.*;

public class Game implements Runnable {
    private boolean running;
    GameWindow window;

    public Game(GameWindow window) {
        this.window = window;
    }

    public void start() {
        running = true;
        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        final int FPS = 60;
        final double timePerFrame = 1_000_000_000.0 / FPS;
        long lastFrameTime = System.nanoTime();

        while(running) {
            long currentTime = System.nanoTime();
            if (currentTime - lastFrameTime >= timePerFrame) {
                updateGame();
                renderGame();
                lastFrameTime = currentTime;
            }
        }
    }

    private void updateGame() {
        System.out.println("Update Game !");
    }

    private void renderGame() {
        System.out.println("Render Game !");
    }
}
