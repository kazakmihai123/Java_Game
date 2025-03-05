package main;

import graphics.GameWindow;

public class Main {
    public static void main(String[] args) {
        // Inițializează fereastra jocului
        GameWindow window = new GameWindow();

        // Creează instanța jocului și pornește game loop-ul
        Game game = new Game(window);
        game.start();
    }
}
