package utils;

import entities.Player;
import graphics.GamePanel;
import objects.OBJ_Door;
import objects.OBJ_Potion;

import static utils.Constants.*;

public class LevelLoader {

    GamePanel gp;

    public LevelLoader(GamePanel gp) {
        this.gp = gp;
    }

    public void loadLevel(String mapFile) {
        // 1. Încarcă tiles
        if (mapFile.equals("/maps/map01.txt") || mapFile.equals("/maps/map02.txt")) {
            gp.tileManager.loadMap(mapFile, maxWorldRow, maxWorldCol);
        }
        else if (mapFile.equals("/maps/map03.txt")) {
            gp.tileManager.loadMap(mapFile, 31, 20); // <- fix esențial
        }

        // 2. Golește obiectele vechi (dacă există)
        gp.objectManager.objects.clear();
        gp.projectiles.clear();

        // ENEMY
        gp.enemySetter.load(gp.currentLevel);
        // TURRETS
        gp.turretSetter.load(gp.currentLevel);

        gp.enemySetter.finalBoss = null;

        // 3. Adaugă obiecte specifice level-ului
        // Aici poți folosi if-uri pe `mapFile` sau să ai o convenție per nivel
        if (gp.player == null) {
            gp.player = new Player();
        }

        if (mapFile.equals("/maps/map01.txt")) {
            gp.player.setDefaultValues("level_01");

            gp.objectManager.addObject(new OBJ_Door(), 35 * tileSize, 39 * tileSize); // 36 41
            //gp.objectManager.addObject(new OBJ_Potion(), 11 * tileSize, 9 * tileSize);

        }
        else if (mapFile.equals("/maps/map02.txt")) {
            gp.player.setDefaultValues("level_02");

            gp.enemySetter.load(gp.currentLevel);
            gp.turretSetter.load(gp.currentLevel);

            gp.objectManager.addObject(new OBJ_Door(), 44 * tileSize, 30 * tileSize);
            gp.objectManager.addObject(new OBJ_Potion(), 47 * tileSize, 37 * tileSize);
            gp.objectManager.addObject(new OBJ_Potion(), 12 * tileSize, 44 * tileSize);

        }
        else if (mapFile.equals("/maps/map03.txt")) {
            gp.player.setDefaultValues("level_03");
            gp.enemySetter.load(gp.currentLevel);
            gp.turretSetter.load(gp.currentLevel);
        }
    }
}
