package utils;

public class Constants {
    public static int originalTileSize = 32;
    public static int scale = 2;

    public static int tileSize = originalTileSize * scale;
    public static int maxScreenRow = 12;
    public static int maxScreenCol = 16;
    public static int screenWidth = tileSize * maxScreenCol;
    public static int screenHeight = tileSize * maxScreenRow;

    public static int projectileSize = tileSize / 2;

    // WORLD
    public static final int maxWorldCol = 50;
    public static final int maxWorldRow = 50;
    public static final int worldWidth = tileSize * maxWorldCol;
    public static final int worldHeight = tileSize * maxWorldRow;

    // DIRECTION
    public enum ShootDirection {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    // GAME STATE
    public static int titleState = 0;
    public static int playState = 1;
    public static int pauseState = 2;
    public static int deadState = 3;
    public static int endingState = 4;
}
