package tiles;

import entities.Player;
import graphics.GamePanel;
import utils.ResourceLoader;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static utils.Constants.*;

public class TileManager {
    public Tile[] tiles;
    ResourceLoader tileSetForMap_01 = new ResourceLoader("/tiles/map1/forest_tiles.png", 16, 16);
    ResourceLoader tileSetForMap_02and03 = new ResourceLoader("/tiles/map2/Dungeon_Tileset.png", 10, 10);
    public int[][] mapTileNum;

    public int currentMapRows = maxWorldRow;
    public int currentMapCols = maxWorldCol;

    private GamePanel gp;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tiles = new Tile[50]; // sau câte ai tu
        mapTileNum = new int[maxWorldCol][maxWorldRow];

        loadTileImages();
        loadMap("/maps/map01.txt", maxWorldRow, maxWorldCol); // fișier text cu layoutul hărții
    }

    private void loadTileImages() {
        // exemplu hardcodat
        tiles[0] = new Tile(tileSetForMap_01.getFrame(0, 0), false); // grass
        tiles[1] = new Tile(tileSetForMap_01.getFrame(0, 1), false); // flori
        tiles[2] = new Tile(tileSetForMap_01.getFrame(1, 11), true); // piatra;

        // APA
        tiles[3] = new Tile(tileSetForMap_01.getFrame(7, 4), true); // apa
        tiles[4] = new Tile(tileSetForMap_01.getFrame(7, 5), true); // apa
        tiles[5] = new Tile(tileSetForMap_01.getFrame(7, 6), true); // apa
        tiles[6] = new Tile(tileSetForMap_01.getFrame(8, 4), true); // apa
        tiles[7] = new Tile(tileSetForMap_01.getFrame(8, 5), true); // apa
        tiles[8] = new Tile(tileSetForMap_01.getFrame(8, 6), true); // apa
        tiles[9] = new Tile(tileSetForMap_01.getFrame(9, 4), true); // apa
        tiles[10] = new Tile(tileSetForMap_01.getFrame(9, 5), true); // apa
        tiles[11] = new Tile(tileSetForMap_01.getFrame(9, 6), true); // apa

        // LEMN
        tiles[12] = new Tile(tileSetForMap_01.getFrame(3, 7), true); // lemn
        tiles[13] = new Tile(tileSetForMap_01.getFrame(3, 8), true); // lemn

        // COPAC
        tiles[14] = new Tile(tileSetForMap_01.getFrame(8, 0), true);
        tiles[15] = new Tile(tileSetForMap_01.getFrame(8, 1), true);
        tiles[16] = new Tile(tileSetForMap_01.getFrame(9, 0), true);
        tiles[17] = new Tile(tileSetForMap_01.getFrame(9, 1), true);

        // COPACI
        tiles[18] = new Tile(tileSetForMap_01.getFrame(10, 0), true);
        tiles[19] = new Tile(tileSetForMap_01.getFrame(10, 1), true);
        tiles[20] = new Tile(tileSetForMap_01.getFrame(11, 0), true);
        tiles[21] = new Tile(tileSetForMap_01.getFrame(11, 1), true);

        // COLT APA
        tiles[22] = new Tile(tileSetForMap_01.getFrame(11, 9), true);

        // COPAC SUBTIRE
        tiles[23] = new Tile(tileSetForMap_01.getFrame(1, 10), true);
        tiles[24] = new Tile(tileSetForMap_01.getFrame(2, 10), true);

        // TURRET
        tiles[25] = new Tile(tileSetForMap_01.getFrame(3, 12), false);

        // SECRET
        tiles[26] = new Tile(tileSetForMap_01.getFrame(8, 0), false);
        tiles[27] = new Tile(tileSetForMap_01.getFrame(8, 1), false);

        // DUNGEON WALLS
        tiles[30] = new Tile(tileSetForMap_02and03.getFrame(0, 0), true);
        tiles[31] = new Tile(tileSetForMap_02and03.getFrame(0, 1), true);
        tiles[32] = new Tile(tileSetForMap_02and03.getFrame(0, 5), true);
        tiles[33] = new Tile(tileSetForMap_02and03.getFrame(1, 0), true);

        // DUNGEON FLOOR
        tiles[34] = new Tile(tileSetForMap_02and03. getFrame(1, 1), false);
        tiles[35] = new Tile(tileSetForMap_02and03.getFrame(1, 2), false);

        // DUNGEON WALLS
        tiles[36] = new Tile(tileSetForMap_02and03.getFrame(4, 0), true);
        tiles[37] = new Tile(tileSetForMap_02and03.getFrame(4, 3), true);
        tiles[38] = new Tile(tileSetForMap_02and03.getFrame(4, 4), true);

        // DUNGEON TORCH
        tiles[39] = new Tile(tileSetForMap_02and03.getFrame(9, 0), false);
        tiles[40] = new Tile(tileSetForMap_02and03.getFrame(9, 1), false);

        tiles[41] = new Tile(tileSetForMap_02and03.getFrame(4, 5), true);
    }

    public void loadMap(String filePath, int sizeWorldRow, int sizeWorldCol) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            if (is == null) {
                throw new IllegalArgumentException("❌ Fișierul hărții nu a fost găsit: " + filePath);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            // Actualizăm dimensiunile curente
            currentMapRows = sizeWorldRow;
            currentMapCols = sizeWorldCol;
            mapTileNum = new int[currentMapCols][currentMapRows];

            for (int row = 0; row < currentMapRows; row++) {
                String line = br.readLine();
                String[] numbers = line.trim().split(" +");

                for (int col = 0; col < currentMapCols; col++) {
                    int value = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = (value == 99) ? -1 : value;
                }
            }

            br.close();
        } catch (Exception e) {
            System.err.println("Eroare la încărcarea hărții: " + filePath);
            e.printStackTrace();
        }
    }

    public void draw(Graphics g, Player p) {
        for (int row = 0; row < currentMapRows; row++) {
            for (int col = 0; col < currentMapCols; col++) {
                int worldX = col * tileSize;
                int worldY = row * tileSize;
                double screenX = worldX - p.worldX + p.screenX;
                double screenY = worldY - p.worldY + p.screenY;

                int tileNum = mapTileNum[col][row];

                if (tileNum != -1 &&
                        worldX + tileSize > p.worldX - p.screenX &&
                        worldX - tileSize < p.worldX + p.screenX &&
                        worldY + tileSize > p.worldY - p.screenY &&
                        worldY - tileSize < p.worldY + p.screenY) {

                    if (gp.currentLevel == 1)
                        tileSetForMap_01.drawFrame(g, tiles[0].image, screenX, screenY, 2, tileSize);
                    else
                        tileSetForMap_01.drawFrame(g, tiles[34].image, screenX, screenY, 2, tileSize);
                    tileSetForMap_01.drawFrame(g, tiles[tileNum].image, screenX, screenY, 2, tileSize);
                }
            }
        }
    }
}
