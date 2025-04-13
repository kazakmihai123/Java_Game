package entities;

import graphics.GamePanel;
import utils.ResourceLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.*;

public class Projectiles extends Entity {
    private final int dmg;
    private boolean active = true;
    private final double speedX, speedY;

    private final double angleDegrees;

    boolean fromPlayer;

    public Projectiles(int dmg, double playerX, double playerY, int cursorX, int cursorY, boolean fromPlayer) {
        int speed = 10;
        this.dmg = dmg;
        this.fromPlayer = fromPlayer;

        worldX = playerX;
        worldY = playerY;

        double angle = Math.atan2(cursorY - (double) (tileSize / 8) - playerY, cursorX - (double) (tileSize / 8) - playerX);
        speedX = Math.cos(angle) * speed;
        speedY = Math.sin(angle) * speed;

        res = new ResourceLoader("/res/fireball/fireball.png", 16, 6);

        double deltaX = cursorX - (worldX + (double) projectileSize / 2);
        double deltaY = cursorY - (worldY + (double) projectileSize / 2);
        angleDegrees = Math.toDegrees(Math.atan2(deltaY, deltaX));

        // Collision
        if (fromPlayer)
            solidArea = new Rectangle(8, 8, 4, 4);
        else
            solidArea = new Rectangle(4, 4, 8, 8);
    }

    public void update(GamePanel gp) {
        worldX += speedX;
        worldY += speedY;

        collisionOn = false;
        gp.collisionChecker.checkTile(this);

        // Collision
        if (collisionOn) {
            active = false;
            return;
        }

        updateAnimation(3);
    }

    public void draw(Graphics g, Player p) {
        double screenX = worldX - p.worldX + p.screenX;
        double screenY = worldY - p.worldY + p.screenY;
        if (fromPlayer)
        {
            res.drawFrame(g, res.rotateImage(res.getFrame(4, 2 + currentFrame), angleDegrees), screenX, screenY, 2, projectileSize);
        }
        else
        {
            res.drawFrame(g, res.rotateImage(res.getFrame(4, 2 + currentFrame), angleDegrees), screenX, screenY, 4, projectileSize);
        }
    }

    public boolean getActive() {
        return active;
    }

    public int getDmg() {
        return dmg;
    }

    public void setInactive() {
        active = false;
    }

    public Rectangle getHitbox() {
        return new Rectangle((int) worldX + solidArea.x, (int) worldY + solidArea.y, solidArea.width, solidArea.height);
    }

    public boolean getFromWho() {
        return fromPlayer;
    }
}
