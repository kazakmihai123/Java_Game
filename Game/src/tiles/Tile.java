package tiles;

import java.awt.image.BufferedImage;

public class Tile {
    public BufferedImage image;
    public boolean collision;
    public TileType tileType;

    public enum TileType {
        GRASS, WALL, WATER, TELEPORT, LAVA
    }

    public Tile(BufferedImage image, boolean collision)
    {
        this.image = image;
        this.collision = collision;
    }
}
