package objects;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.*;

public class SuperObject {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;

    // Desenează obiectul pe ecran
    public void draw(Graphics2D g2, int tileSize, double screenX, double screenY) {
        g2.drawImage(image, (int)screenX, (int)screenY, tileSize, tileSize, null);
    }
}
