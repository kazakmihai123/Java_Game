package utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ResourceLoader {

    private int width;
    private int height;

    String path;
    BufferedImage spriteSheet;
    BufferedImage[][] frames;

    public ResourceLoader(String path, int row, int col) {
        spriteSheet = loadImage(path);

        frames = new BufferedImage[row][col];

        try
        {
            width = spriteSheet.getWidth() / col;
            height = spriteSheet.getHeight() / row;

            for (int j = 0; j < row; j++) {
                for (int i = 0; i < col; i++) {
                    frames[j][i] = spriteSheet.getSubimage(i * width, j * height, width, height);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /// Încărcare imagine
    public BufferedImage loadImage(String path) {
        try {
            BufferedImage spriteSheet = ImageIO.read(ResourceLoader.class.getResource(path));

            if (spriteSheet == null) {
                System.err.println("Imaginea nu a fost găsită: " + path);
                return null;
            }
            return spriteSheet;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /// Roteste imaginea
    public BufferedImage rotateImage(BufferedImage img, double angleDegrees) {
        double rads = Math.toRadians(angleDegrees);

        int width = img.getWidth();
        int height = img.getHeight();

        BufferedImage rotated = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();

        // Centrarea imaginii rotite
        g2d.rotate(rads, width / 2.0, height / 2.0);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        return rotated;
    }

    public BufferedImage getFrame(int row, int col)
    {
        return frames[row][col];
    }

    public void drawImage(Graphics g, double worldX, double worldY, int scale, int tileSize, int row, int frame) {
        BufferedImage currentImg = frames[row][frame];

        int scaledWidth = width * scale;
        int scaledHeight = height * scale;

        int drawX = (int) worldX - scaledWidth / 2 + tileSize / 2;
        int drawY = (int) worldY - scaledHeight / 2 + tileSize / 2;

        g.drawImage(currentImg, drawX, drawY, scaledWidth, scaledHeight, null);
    }

    public void drawFrame(Graphics g, BufferedImage f, double worldX, double worldY, int scale, int tileSize)
    {
        int scaledWidth = width * scale;
        int scaledHeight = height * scale;

        int drawX = (int) worldX - scaledWidth / 2 + tileSize / 2;
        int drawY = (int) worldY - scaledHeight / 2 + tileSize / 2;

        g.drawImage(f, drawX, drawY, scaledWidth, scaledHeight, null);
    }
}
