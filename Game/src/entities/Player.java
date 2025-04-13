package entities;

import graphics.GamePanel;
import inputs.KeyboardInput;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utils.Constants.*;
import utils.ResourceLoader;

import javax.imageio.ImageIO;

public class Player extends Entity {

    public final int screenX;
    public final int screenY;

    // SHOOT
    private long lastShotTime = 0;
    private final long shootCooldown = 1000; // Adica o secunda
    private int dmg;

    // HP
    private int maxHP = 100;
    private int currentHP;
    private long lastDamageTime = 0;
    private final long invincibilityDuration = 750; // 0.75 secundă în milisecunde

    public Player() {

        dmg = 10;
        speed = 15;

        screenX = screenWidth / 2 - tileSize / 2;
        screenY = screenHeight / 2 - tileSize / 2;

        // Initializare sprite
        res = new ResourceLoader("/res/mage/mage-light.png", 4, 3);

        // Collision
        solidArea = new Rectangle(17, 28, 29, 30);
    }

    public void update(KeyboardInput keyboardInput, GamePanel gp) {

        boolean moved = false;

        if (keyboardInput.isUpPressed())
            moved = tryMove(gp, AnimationState.WALKING_UP, 0, -speed) || moved;
        if (keyboardInput.isDownPressed())
            moved = tryMove(gp, AnimationState.WALKING_DOWN, 0, speed) || moved;
        if (keyboardInput.isLeftPressed())
            moved = tryMove(gp, AnimationState.WALKING_LEFT, -speed, 0) || moved;
        if (keyboardInput.isRightPressed())
            moved = tryMove(gp, AnimationState.WALKING_RIGHT, speed, 0) || moved;
        if (!moved) {
            currentAnimationState = AnimationState.IDLE;
        }

        if (keyboardInput.consumeLeftClick()) {
            long now = System.currentTimeMillis();
            if (now - lastShotTime >= shootCooldown) {
                shootProjectile(gp, keyboardInput);
                lastShotTime = now;
            }
        }

        // currentFrames;
        updateAnimation(3);
    }

    public void draw(Graphics g) {
        long now = System.currentTimeMillis();
        boolean invincible = now - lastDamageTime < invincibilityDuration;

        if (!invincible || (now / 100) % 2 == 0) { // Clipsește la fiecare 100ms
            if (currentAnimationState == AnimationState.IDLE)
                res.drawImage(g, screenX, screenY, 1, tileSize, 2, 1);
            else
                res.drawImage(g, screenX, screenY, 1, tileSize, animationRow(), currentFrame);
        }
    }

    private boolean tryMove(GamePanel gp, AnimationState direction, int dx, int dy) {
        collisionOn = false;
        currentAnimationState = direction;

        gp.collisionChecker.checkTile(this);
        if (!collisionOn) {
            worldX += dx;
            worldY += dy;
            return true;
        }
        return false;
    }

    private int animationRow() {
        return switch (currentAnimationState) {
            case WALKING_UP -> 0;
            case WALKING_DOWN -> 2;
            case WALKING_RIGHT -> 1;
            case WALKING_LEFT -> 3;
            case IDLE -> 2; // exemplu idle cu rândul 2, cadru fix
            default -> 2;
        };
    }

    public void setDefaultValues(String lvl) {

        switch (lvl)
        {
            case "level_01":
                // START POSITION
                worldX = tileSize * 14;
                worldY = tileSize * 9;

                currentHP = 70;
                maxHP = 100; // HP
                break;
            case"level_02":
                // START POSITION
                worldX = tileSize * 14;
                worldY = tileSize * 9;
                break;
        }

    }

    private void shootProjectile(GamePanel gp, KeyboardInput keyboardInput) {
        double mouseWorldX = worldX - screenX + keyboardInput.getMouseX();
        double mouseWorldY = worldY - screenY + keyboardInput.getMouseY();
        gp.addProj(dmg, worldX + (double) (tileSize / 4), worldY + (double) (tileSize / 4),
                (int)mouseWorldX, (int)mouseWorldY, true);
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public double getMaxHP() {
        return maxHP;
    }

    public long getShootCooldown() {
        return shootCooldown;
    }

    public long getTimeSinceLastShot() {
        return System.currentTimeMillis() - lastShotTime;
    }

    public void takeDamage(int dmg) {
        long now = System.currentTimeMillis();
        if (now - lastDamageTime >= invincibilityDuration) {
            currentHP -= dmg;
            if (currentHP < 0) {
                currentHP = 0;
            }
            lastDamageTime = now;
        }
    }

    public Rectangle getHitbox() {
        return new Rectangle((int) worldX + solidArea.x, (int) worldY + solidArea.y, solidArea.width, solidArea.height);
    }

}
