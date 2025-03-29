package entities;

import graphics.GamePanel;
import inputs.KeyboardInput;

import java.awt.*;

import static utils.Constants.*;
import utils.ResourceLoader;

public class Player extends Entity {

    public final int screenX;
    public final int screenY;

    private long lastShotTime = 0;
    private long shootCooldown = 100; // Adica o secunda
    private int dmg;
    private AnimationState currentAnimationState = AnimationState.IDLE;

    public Player() {
        setDefaultValues();

        screenX = screenWidth / 2 - tileSize / 2;
        screenY = screenHeight / 2 - tileSize / 2;

        // Initializare sprite
        res = new ResourceLoader("/res/mage/mage-light.png", 4, 3);
    }

    public void update(KeyboardInput keyboardInput, GamePanel gp)
    {
        // Actiuni pentru deplasare si attack plus currentAnimationRunning
        if (keyboardInput.isUpPressed()) {
            worldY -= speed;
            currentAnimationState = AnimationState.WALKING_UP;
        }
        if (keyboardInput.isDownPressed()) {
            worldY += speed;
            currentAnimationState = AnimationState.WALKING_DOWN;
        }
        if (keyboardInput.isLeftPressed()) {
            worldX -= speed;
            currentAnimationState = AnimationState.WALKING_LEFT;
        }
        if (keyboardInput.isRightPressed()) {
            worldX += speed;
            currentAnimationState = AnimationState.WALKING_RIGHT;
        }
        if (keyboardInput.consumeLeftClick()) {
            long now = System.currentTimeMillis();
            if (now - lastShotTime >= shootCooldown) {
                shootProjectile(gp, keyboardInput);
                lastShotTime = now;
            }
        }

        if (!(keyboardInput.isUpPressed() || keyboardInput.isDownPressed() ||
                keyboardInput.isLeftPressed() || keyboardInput.isRightPressed())) {
            currentAnimationState = AnimationState.IDLE;
        }

        // currentFrames;
        updateAnimation(3);
    }

    public void draw(Graphics g)
    {
//        g.setColor(Color.BLACK);
//        g.fillRect((int) worldX, (int) worldY, 48, 48);
        if (currentAnimationState == AnimationState.IDLE)
            res.drawImage(g, screenX, screenY, 1, tileSize, 2, 1);
        else
            res.drawImage(g, screenX, screenY, 1, tileSize, animationRow(), currentFrame);
    }

    private int animationRow()
    {
        return switch (currentAnimationState) {
            case WALKING_UP -> 0;
            case WALKING_DOWN -> 2;
            case WALKING_RIGHT -> 1;
            case WALKING_LEFT -> 3;
            case IDLE -> 2; // exemplu idle cu rândul 2, cadru fix
            default -> 2;
        };
    }

    public void setDefaultValues()
    {
        worldX = 100;
        worldY = 100;
        speed = 10;
        dmg = 10;
    }

    private void shootProjectile(GamePanel gp, KeyboardInput keyboardInput) {
        double mouseWorldX = worldX - screenX + keyboardInput.getMouseX();
        double mouseWorldY = worldY - screenY + keyboardInput.getMouseY();
        gp.addProj(dmg, worldX + (double) (tileSize / 4), worldY + (double) (tileSize / 4),
                (int)mouseWorldX, (int)mouseWorldY);
    }

}
