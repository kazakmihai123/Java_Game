package entities;

import utils.ResourceLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.*;

public class Projectiles extends Entity {
    private final int dmg;
    private boolean active = true;
    private final double speedX, speedY;

    private double angleDegrees;

    public Projectiles(int dmg, double playerX, double playerY, int cursorX, int cursorY)
    {
        int speed = 10;
        this.dmg = dmg;
        worldX = playerX;
        worldY = playerY;

        double angle = Math.atan2(cursorY - (double)(tileSize / 8) - playerY, cursorX - (double)(tileSize / 8) - playerX);
        speedX = Math.cos(angle) * speed;
        speedY = Math.sin(angle) * speed;

        /// Sprite:
        res = new ResourceLoader("/res/fireball/fireball.png", 16, 6);

        double deltaX = cursorX - (worldX + projectileSize / 2);
        double deltaY = cursorY - (worldY + projectileSize / 2);
        angleDegrees = Math.toDegrees(Math.atan2(deltaY, deltaX));

    }

    public void update()
    {
        worldX += speedX;
        worldY += speedY;

//        if (worldX < -40 || worldX > 768 || worldY < -40 || worldY > 12 * 16 * 3) {
//            active = false;
//        }

        updateAnimation(3);
    }

    public void draw(Graphics g, Player p)
    {
        double screenX = worldX - p.worldX + p.screenX;
        double screenY = worldY - p.worldY + p.screenY;
        res.drawFrame(g, res.rotateImage(res.getFrame(4, 2 + currentFrame), angleDegrees), screenX, screenY, 2, projectileSize);
    }

    public boolean getActive()
    {
        return active;
    }

    public int getDmg()
    {
        return dmg;
    }
}
