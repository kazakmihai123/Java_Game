package utils;

import entities.Player;
import graphics.GamePanel;
import objects.OBJ_Door;

import static utils.Constants.tileSize;

public class LevelLoader {

    GamePanel gp;

    public LevelLoader(GamePanel gp) {
        this.gp = gp;
    }

    public void loadLevel(String mapFile) {
        // 1. Încarcă tiles
        gp.tileManager.loadMap(mapFile);

        // 2. Golește obiectele vechi (dacă există)
        gp.objectManager.objects.clear();


        // ENEMY
        gp.enemySetter.load(gp.currentLevel);
        // TURRETS
        gp.turretSetter.load(gp.currentLevel);

        // 3. Adaugă obiecte specifice level-ului
        // Aici poți folosi if-uri pe `mapFile` sau să ai o convenție per nivel
        if (mapFile.equals("/maps/map01.txt")) {
            // Sterge player si creaza;
            gp.player = new Player();
            gp.player.setDefaultValues("level_01");

            gp.objectManager.addObject(new OBJ_Door(), 12 * tileSize, 9 * tileSize); // 36 41
        } else if (mapFile.equals("/maps/map02.txt")) {
            gp.enemySetter.load(gp.currentLevel);
            gp.turretSetter.load(gp.currentLevel);
            gp.objectManager.addObject(new OBJ_Door(), 25 * tileSize, 18 * tileSize);
            gp.player.setDefaultValues("level_02");
        }

    }
}
