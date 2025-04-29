package entities;

import graphics.GamePanel;
import utils.ResourceLoader;

import java.awt.*;

import static utils.Constants.*;

public class Slime extends Entity {

    private boolean movingLeft = true;
    private final int maxHP = 20;
    private int currentHP = maxHP;

    private boolean jumping = false;
    private boolean jumpTriggered = false;
    private double velocityY = 0;
    private final double gravity = 0.5;
    private final double jumpStrength = -Math.sqrt(tileSize); // pentru ½ tile
    private double groundY;

    private enum Direction { LEFT, RIGHT, UP, DOWN }
    private Direction currentDirection = Direction.LEFT;

    public Slime(double spawnX, double spawnY) {

        this.groundY = spawnY;

        this.worldX = spawnX;
        this.worldY = spawnY;

        this.speed = 2;

        // Ex: spritesheet cu slime
        res = new ResourceLoader("/enemys/slimes/slime.png", 3, 3); // adaptează path-ul

        // Zona de coliziune
        solidArea = new Rectangle(10, 10, tileSize - 20, tileSize - 20);
    }

    public void update(GamePanel gp) {
        collisionOn = false;

        // Mișcare normală doar dacă nu sare
        if (!jumping) {
            switch (currentDirection) {
                case LEFT -> {
                    currentAnimationState = AnimationState.WALKING_LEFT;
                    worldX -= speed;
                }
                case RIGHT -> {
                    currentAnimationState = AnimationState.WALKING_RIGHT;
                    worldX += speed;
                }
                case UP -> {
                    currentAnimationState = AnimationState.WALKING_UP;
                    worldY -= speed;
                }
                case DOWN -> {
                    currentAnimationState = AnimationState.WALKING_DOWN;
                    worldY += speed;
                }
            }
        }

        // Verifică coliziune cu tile sau cu player
        gp.collisionChecker.checkTile(this);
        if (getHitbox().intersects(gp.player.getHitbox())) {
            collisionOn = true;
        }

        // Dacă s-a lovit și nu a sărit deja → inițiază săritură verticală
        if (collisionOn && !jumping && !jumpTriggered) {
            jumpTriggered = true;
            jumping = true;
            velocityY = jumpStrength;
            groundY = worldY;
        }

        // Săritură verticală
        if (jumping) {
            worldY += velocityY;
            velocityY += gravity;

            // Aterizare
            if (worldY >= groundY) {
                worldY = groundY;
                velocityY = 0;
                jumping = false;
                jumpTriggered = false;

                // După săritură → schimbă direcția
                changeDirection();
            }
        }

        updateAnimation(3);
    }

    public void draw(Graphics g, Player p) {
        double screenX = worldX - p.worldX + p.screenX;
        double screenY = worldY - p.worldY + p.screenY;

        res.drawImage(g, (int) screenX, (int) screenY, 2, tileSize, animationRow(), currentFrame);
    }

    private void changeDirection() {
        switch (currentDirection) {
            case LEFT -> currentDirection = Direction.RIGHT;
            case RIGHT -> currentDirection = Direction.LEFT;
            case UP -> currentDirection = Direction.DOWN;
            case DOWN -> currentDirection = Direction.UP;
        }
    }

    private int animationRow() {
        return switch (currentAnimationState) {
            case WALKING_LEFT -> 1;
            case WALKING_RIGHT -> 1;
            default -> 2;
        };
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
