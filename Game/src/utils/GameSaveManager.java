package utils;

import entities.Player;
import graphics.GamePanel;

import java.io.*;

public class GameSaveManager {

    private static final String SAVE_FILE = "save.dat";

    public static void save(GamePanel gp) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            oos.writeInt(gp.currentLevel);
            oos.writeDouble(gp.player.worldX);
            oos.writeDouble(gp.player.worldY);
            oos.writeInt(gp.player.getCurrentHP());
            oos.writeInt(gp.ui.getScore());

            System.out.println("Game saved!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load(GamePanel gp) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            int savedLevel = ois.readInt();
            double playerX = ois.readDouble();
            double playerY = ois.readDouble();
            int hp = ois.readInt();
            int score = ois.readInt();

            gp.currentLevel = savedLevel;
            String nextMap = "/maps/map0" + savedLevel + ".txt";
            gp.levelLoader.loadLevel(nextMap);

            gp.player.worldX = playerX;
            gp.player.worldY = playerY;
            gp.player.setHP(hp);

            gp.ui.setScore(score);

            System.out.println("Game loaded!");
        } catch (IOException e) {
            System.out.println("No save file found.");
        }
    }
}
