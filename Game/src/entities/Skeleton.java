package entities;

import graphics.GamePanel;
import utils.ResourceLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.tileSize;

public class Skeleton extends Entity {

    private int maxHP = 30;
    private int currentHP = maxHP;

    private boolean movingRight = true;

    private final double detectionRange = tileSize * 6; // ~192px

    public Skeleton(double spawnX, double spawnY) {
        this.worldX = spawnX;
        this.worldY = spawnY;

        this.speed = 2;

        // Înlocuiește cu sprite-ul real
        res = new ResourceLoader("/enemys/skeletons/skeleton.png", 1, 1);

        // Hitbox (poate fi ajustat în funcție de sprite)
        solidArea = new Rectangle(10, 10, tileSize - 20, tileSize - 20);
    }

    public void update(GamePanel gp) {
        collisionOn = false;

        double dx = gp.player.worldX - worldX;
        double dy = gp.player.worldY - worldY;
        double distance = Math.sqrt(dx * dx + dy * dy);

        double nextX = worldX;
        double nextY = worldY;

        if (distance <= detectionRange) {
            // FOLLOW MODE
            if (Math.abs(dx) > Math.abs(dy)) {
                if (dx > 0) {
                    currentAnimationState = AnimationState.WALKING_RIGHT;
                    nextX += speed;
                } else {
                    currentAnimationState = AnimationState.WALKING_LEFT;
                    nextX -= speed;
                }
            } else {
                if (dy > 0) {
                    currentAnimationState = AnimationState.WALKING_DOWN;
                    nextY += speed;
                } else {
                    currentAnimationState = AnimationState.WALKING_UP;
                    nextY -= speed;
                }
            }
        } else {
            // PATROL MODE
            if (movingRight) {
                currentAnimationState = AnimationState.WALKING_RIGHT;
                nextX += speed;
            } else {
                currentAnimationState = AnimationState.WALKING_LEFT;
                nextX -= speed;
            }
        }

        // Simulezi poziția viitoare și verifici coliziunea
        double originalX = worldX;
        double originalY = worldY;

        worldX = nextX;
        worldY = nextY;

        gp.collisionChecker.checkTile(this);

        if (collisionOn) {
            // revenim dacă era perete
            worldX = originalX;
            worldY = originalY;
            if (distance > detectionRange) {
                movingRight = !movingRight; // schimbă direcția doar dacă e în patrol
            }
        }

        updateAnimation(3);
    }

    public void draw(Graphics g, Player p) {
        double screenX = worldX - p.worldX + p.screenX;
        double screenY = worldY - p.worldY + p.screenY;

        BufferedImage img = res.getFrame(0, 0);

//        if (currentAnimationState == AnimationState.WALKING_LEFT) {
//            img = res.rotateImage(img, 90);
//        }

        res.drawImage(g, (int) screenX, (int) screenY, 3, tileSize, 0, 0);
        // res.drawFrame(g, img, (int) screenX, (int) screenY, 3, tileSize);
    }

    public boolean isDead() {
        return currentHP <= 0;
    }

    public void takeDamage(int dmg) {
        currentHP -= dmg;
    }

    public Rectangle getHitbox() {
        return new Rectangle((int) worldX + solidArea.x, (int) worldY + solidArea.y, solidArea.width, solidArea.height);
    }
}
