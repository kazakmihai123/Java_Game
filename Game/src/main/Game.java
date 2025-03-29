package main;

import graphics.GamePanel;
import graphics.GameWindow;
import java.lang.*;

import static java.lang.Thread.sleep;

public class Game implements Runnable {
    private boolean running;
    GamePanel gamePanel = new GamePanel();
    GameWindow gameWindow = new GameWindow(gamePanel);

    public void start() {
        running = true;
        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        final int FPS = 60;
        final double timePerFrame = 1_000_000_000.0 / FPS;
        long previousTime = System.nanoTime();

        while (running) {
            long currentTime = System.nanoTime();
            if (currentTime - previousTime >= timePerFrame) {
                updateGame();
                renderGame();
                previousTime = currentTime;
            }
        }
    }

    private void updateGame() {
        gamePanel.update();
    }

    private void renderGame() {
        gamePanel.repaint();
    }
}
