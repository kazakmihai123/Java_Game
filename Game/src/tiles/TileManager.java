package tiles;

import entities.Player;
import utils.Constants;
import utils.ResourceLoader;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static utils.Constants.*;

public class TileManager {
    Tile[] tiles;
    ResourceLoader tileSet = new ResourceLoader("/tiles/forest/forest_tiles.png", 16, 16);
    int[][] mapTileNum;

    public TileManager()
    {
        tiles = new Tile[15]; // sau câte ai tu
        mapTileNum = new int[maxWorldCol][maxWorldRow];

        loadTileImages();
        loadMap("/maps/map01.txt"); // fișier text cu layoutul hărții
    }

    private void loadTileImages() {
        // exemplu hardcodat
        tiles[0] = new Tile(tileSet.getFrame(0, 0), false); // grass
        tiles[1] = new Tile(tileSet.getFrame(0, 1), false); // flori
        tiles[2] = new Tile(tileSet.getFrame(1, 11), true); // piatra;

        tiles[3] = new Tile(tileSet.getFrame(7, 4), true); // apa
        tiles[4] = new Tile(tileSet.getFrame(7, 5), true); // apa
        tiles[5] = new Tile(tileSet.getFrame(7, 6), true); // apa
        tiles[6] = new Tile(tileSet.getFrame(8, 4), true); // apa
        tiles[7] = new Tile(tileSet.getFrame(8, 5), true); // apa
        tiles[8] = new Tile(tileSet.getFrame(8, 6), true); // apa
        tiles[9] = new Tile(tileSet.getFrame(9, 4), true); // apa
        tiles[10] = new Tile(tileSet.getFrame(9, 5), true); // apa
        tiles[11] = new Tile(tileSet.getFrame(9, 6), true); // apa

        tiles[12] = new Tile(tileSet.getFrame(3, 7), true); // lemn
        tiles[13] = new Tile(tileSet.getFrame(3, 8), true); // lemn
        // etc.
    }

    private void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (row < maxWorldRow) {
                String line = br.readLine();
                String[] numbers = line.split(" ");

                for (col = 0; col < maxWorldCol; col++) {
                    mapTileNum[col][row] = Integer.parseInt(numbers[col]);
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

                if (worldX + tileSize > p.worldX - p.screenX &&
                    worldX - tileSize < p.worldX + p.screenX &&
                    worldY + tileSize > p.worldY - p.screenY &&
                    worldY - tileSize < p.worldY + p.screenY) {
                    tileSet.drawFrame(g, tiles[0].image, screenX, screenY, 2, tileSize);
                    tileSet.drawFrame(g, tiles[tileNum].image, screenX, screenY, 2, tileSize);
                }
            }
        }
    }
}
