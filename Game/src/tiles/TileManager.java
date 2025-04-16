package tiles;

import entities.Player;
import utils.ResourceLoader;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static utils.Constants.*;

public class TileManager {
    public Tile[] tiles;
    ResourceLoader tileSetForMap_01 = new ResourceLoader("/tiles/map1/forest_tiles.png", 16, 16);
    ResourceLoader tileSetForMap_02 = new ResourceLoader("/tiles/map2/Dungeon_Tileset.png", 10, 10);
    public int[][] mapTileNum;

    public TileManager()
    {
        tiles = new Tile[50]; // sau câte ai tu
        mapTileNum = new int[maxWorldCol][maxWorldRow];

        loadTileImages();
        loadMap("/maps/map01.txt"); // fișier text cu layoutul hărții
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
        tiles[30] = new Tile(tileSetForMap_02.getFrame(0, 0), true);
        tiles[31] = new Tile(tileSetForMap_02.getFrame(0, 1), true);
        tiles[32] = new Tile(tileSetForMap_02.getFrame(0, 5), true);
        tiles[33] = new Tile(tileSetForMap_02.getFrame(1, 0), true);

        // DUNGEON FLOOR
        tiles[34] = new Tile(tileSetForMap_02. getFrame(1, 1), false);
        tiles[35] = new Tile(tileSetForMap_02.getFrame(1, 2), false);

        // DUNGEON WALLS
        tiles[36] = new Tile(tileSetForMap_02.getFrame(4, 0), true);
        tiles[37] = new Tile(tileSetForMap_02.getFrame(4, 3), true);
        tiles[38] = new Tile(tileSetForMap_02.getFrame(4, 4), true);

        // DUNGEON TORCH
        tiles[39] = new Tile(tileSetForMap_02.getFrame(9, 0), false);
        tiles[40] = new Tile(tileSetForMap_02.getFrame(9, 1), false);
    }

    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (row < maxWorldRow) {
                String line = br.readLine();
                String[] numbers = line.split(" +");

                for (col = 0; col < maxWorldCol; col++) {
                    int value = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = (value == 99) ? -1 : value;
                }
                row++;
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g, Player p) {
        for (int worldRow = 0; worldRow < maxWorldRow; worldRow++) {
            for (int worldCol = 0; worldCol < maxWorldCol; worldCol++) {

                int worldX = tileSize * worldCol;
                int worldY = tileSize * worldRow;
                double screenX = worldX - p.worldX + p.screenX;
                double screenY = worldY - p.worldY + p.screenY;

                int tileNum = mapTileNum[worldCol][worldRow];

                if (tileNum != -1) {
                    if (worldX + tileSize > p.worldX - p.screenX &&
                            worldX - tileSize < p.worldX + p.screenX &&
                            worldY + tileSize > p.worldY - p.screenY &&
                            worldY - tileSize < p.worldY + p.screenY) {
                        tileSetForMap_01.drawFrame(g, tiles[0].image, screenX, screenY, 2, tileSize);
                        tileSetForMap_01.drawFrame(g, tiles[tileNum].image, screenX, screenY, 2, tileSize);
                    }
                }
            }
        }
    }
}
